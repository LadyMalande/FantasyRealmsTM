package client;

// Java implementation for multithreaded chat client
/*
Client -> Server message first tokens:
GIVE - request to server to give card from deck
TOOK - message to server that player took this card (by id) from table
PUT - message to server that player put this card (by id) on table

Server -> Client message first tokens:
YOUR_TURN - enable clicking on buttons
CARD_TO_HAND - sends info about card to hand (Creates simplified Card object)
INTERACTIVE - sends info about interactive dialogue
*/

import interactive.*;

import java.io.*;
import java.net.*;
import java.util.Locale;

/**
 * Client thread for listening to server messages and to sending messages to attached server.
 * @author Tereza Miklóšová
 */
public class Client implements Runnable
{
    /**
     * Set server port to listen on.
     */
    final static int ServerPort = 1234;

    /**
     * IP address of the server.
     */
    InetAddress ip;

    /**
     * Player object for getting information about him.
     */
    Player player;

    /**
     * Input stream for listening to messages.
     */
    ObjectInputStream dis;

    /**
     * Object output stream for sending messages.
     */
    ObjectOutputStream dos;

    /**
     * Board to operate on with changes sent by the server.
     */
    BoardController board;

    /**
     * Locale to do everything in the right localization for the client.
     */
    Locale locale;

    /**
     * Constructor for Client.
     * @param player Player information necessary for operating with player.
     * @param board Board information necessary for operating on the board.
     */
    public Client(Player player, BoardController board){
        this.player = player;
        this.board = board;
        this.locale = player.getLocale();
    }

    /**
     * Runs the client thread to listen to the messages the server wants to convey.
     * Given the message it does different actions regarding the board.
     */
    @Override
    public void run() {
        // getting localhost ip
        ip = null;
        try {
            ip = InetAddress.getByName("localhost");
            // establish the connection
            Socket s = new Socket(ip, ServerPort);
            // obtaining input and out streams
            dos = new ObjectOutputStream(s.getOutputStream());
            dis = new ObjectInputStream(s.getInputStream());

            // sendMessage thread
            Thread sendMessage = new Thread(() -> {
                // read the message to deliver.
                String IWantRandomDeck;
                if(player.isRandomDeck()){
                    IWantRandomDeck = "true";
                }
                else{
                    IWantRandomDeck = "false";
                }
                String msg = "INIT#" + player.getName() + "#" + player.getMaxPlayers() + "#" + IWantRandomDeck + "#" + locale.getLanguage() + "#" + board.getNumberOfAI();
                try {
                    // write on the output stream
                    dos.writeUTF(msg);
                    dos.flush();
                    System.out.println("Posted lobby info: " + msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // readMessage thread
            Thread readCards = new Thread(() -> {
                int receivedCount = 0;
                while (true) {
                    try {
                        String received = dis.readUTF();
                        System.out.println("Received string from Server: " + received);
                        if (received.startsWith("INIT_CARD_TO_HAND")) {
                            // read the message sent to this client
                            String[] message = received.split("#");
                            SimplifiedCard card = new SimplifiedCard(Integer.parseInt(message[1]), message[2],
                                    Integer.parseInt(message[3]), message[4], message[5]);
                            BoardController.getPlayer().simhand.add(card);
                            receivedCount++;
                            if (receivedCount == 7) {
                                System.out.println("Got them all 7. Starting init method for hand images.");
                                BoardController.gotAllCards.set(true);

                            }
                        }
                        if (received.startsWith("CARD_TO_HAND")) {

                            String[] message = received.split("#");
                            SimplifiedCard card = new SimplifiedCard(Integer.parseInt(message[1]), message[2],
                                    Integer.parseInt(message[3]), message[4], message[5]);

                            BoardController.getPlayer().simhand.add(card);
                            board.putCardToHand();
                        }

                        if (received.startsWith("CARD_TO_TABLE")) {
                            String[] message = received.split("#");
                            SimplifiedCard card = new SimplifiedCard(Integer.parseInt(message[1]), message[2],
                                    Integer.parseInt(message[3]), message[4], message[5]);
                            board.putCardOnTable(card);
                        }

                        if (received.startsWith("REMOVE_CARD_FROM_TABLE")) {
                            String[] message = received.split("#");
                            int id = Integer.parseInt(message[1]);
                            board.removeCardFromTable(id);
                        }
                        if (received.startsWith("END")) {
                            board.putEndGameTextOnLabel();
                        }
                        if (received.startsWith("NAMES")) {
                            System.out.println(received);
                            String[] message = received.split("#");
                            int size = message.length - 1;

                            String[] names = new String[size];
                            System.arraycopy(message, 1, names, 0, size);
                            board.buildPlayerLabels(names);
                        }
                        if (received.startsWith("SCORES")) {
                            System.out.println(received + "\n");
                            String[] message = received.split("#");
                            int size = message.length - 2;

                            String[] scores = new String[size];
                            System.arraycopy(message, 2, scores, 0, size);
                            board.buildPlayerScores(scores);
                        }

                        if (received.startsWith("ChangeColor")) {
                            String[] message = received.split("#");
                            int id = Integer.parseInt(message[1]);
                            ChangeColor.askPlayer(board, id, locale);
                        }

                        if (received.startsWith("CopyNameAndType")) {
                            String[] message = received.split("#");
                            int id = Integer.parseInt(message[1]);
                            String[] splitted = message[2].split(",");
                            CopyNameAndType.askPlayer(board, id, splitted, locale);
                        }

                        if (received.startsWith("CopyCardFromHand")) {
                            String[] message = received.split("#");
                            int id = Integer.parseInt(message[1]);
                            CopyNameColorStrengthMalusFromHand.askPlayer(board, id, locale);
                        }

                        if (received.startsWith("DeleteOneMalusOnType")) {
                            String[] message = received.split("#");
                            int id = Integer.parseInt(message[1]);
                            if (message.length > 2) {
                                String[] splitted = message[2].split("%");
                                DeleteOneMalusOnType.askPlayer(board, id, splitted, locale);
                            }
                        }

                        if (received.startsWith("TakeCardOfType")) {
                            String[] message = received.split("#");
                            int id = Integer.parseInt(message[1]);
                            String[] splitted;
                            if(message.length > 2){
                                splitted = message[2].split(",");
                            } else{
                                splitted = null;
                            }

                            TakeCardOfTypeAtTheEnd.askPlayer(board, id, splitted, locale);
                        }

                    } catch (SocketException se){
                        board.disconnectedFromServer();
                    } catch (EOFException eof){
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sendMessage.start();
            System.out.println("Before readCards.start()");
            readCards.start();
        }catch(EOFException eof){
            System.out.println("EOF");
        }catch (IOException   e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a String message to the connected server.
     * @param msg The message to send.
     */
    public void sendMessage(String msg){
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
