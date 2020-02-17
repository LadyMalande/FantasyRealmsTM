package sample;


// Java implementation for multithreaded chat client
// Save file as Client.java

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



public class Client implements Runnable
{
    final static int ServerPort = 1234;
    boolean sentLobbyInfo = false;
    InetAddress ip;
    String name;
    int maxPlayers;
    ObjectInputStream dis;
    ObjectOutputStream dos;
    BoardController board;
    public Client(String name, int maxPlayers, BoardController board){
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.board = board;
        System.out.println("In client constructor");
    }
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
            System.out.println("after client dis,dos,ois init");

            // sendMessage thread
            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    // read the message to deliver.
                    String msg = "INIT#" + name + "#" + maxPlayers + "#" + "false";
                    try {
                        // write on the output stream
                        dos.writeUTF(msg);
                        dos.flush();
                        System.out.println("Posted lobby info");
                        sentLobbyInfo = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // readMessage thread
            Thread readCards = new Thread(new Runnable() {
                @Override
                public void run() {
                    int receivedCount = 0;
                    while (true) {
                        try {
                            String received = dis.readUTF();
                            //System.out.println("Received string from Server: " + received);
                            if (received.startsWith("INIT_CARD_TO_HAND")) {
                                // read the message sent to this client
                                    //System.out.println("CARD_TO_HAND command:");
                                    String[] message = received.split("#");
                                    SimplifiedCard card = new SimplifiedCard(Integer.parseInt(message[1]),message[2],
                                            Integer.parseInt(message[3]),message[4],message[5]);
                                    //System.out.println("Card name received: " + card.name);
                                    if(card == null){
                                        System.out.println("CARD IS NULL!!");
                                    }
                                    BoardController.player.simhand.add(card);
                                    receivedCount++;
                                    //System.out.println("Added card to hand. The end of CARD_TO_HAND command");
                                    if(receivedCount == 7){
                                        System.out.println("Got them all 7. Starting init method for hand images.");
                                        BoardController.gotAllCards = true;

                                    }
                            }
                            if (received.startsWith("CARD_TO_HAND")) {

                                String[] message = received.split("#");
                                SimplifiedCard card = new SimplifiedCard(Integer.parseInt(message[1]),message[2],
                                        Integer.parseInt(message[3]),message[4],message[5]);

                                if(card == null){
                                    System.out.println("CARD IS NULL!!");
                                }
                                BoardController.player.simhand.add(card);
                                board.putCardToHand(card);
                            }

                            if (received.startsWith("CARD_TO_TABLE")) {
                                String[] message = received.split("#");
                                SimplifiedCard card = new SimplifiedCard(Integer.parseInt(message[1]),message[2],
                                        Integer.parseInt(message[3]),message[4],message[5]);
                                board.putCardOnTable(card);
                            }

                            if (received.startsWith("REMOVE_CARD_FROM_TABLE")) {
                                String[] message = received.split("#");
                                int id = Integer.parseInt(message[1]);
                                board.removeCardFromTable(id);
                            }
                            if (received.startsWith("END")) {
                                board.enableFirstActionButtons(false);
                                board.enableSecondActionButtons(false);
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
                                System.out.println(received);
                                String[] message = received.split("#");
                                int size = message.length - 1;

                                String[] scores = new String[size];
                                System.arraycopy(message, 1, scores, 0, size);
                                board.buildPlayerScores(scores);
                            }

                            if (received.startsWith("ChangeColor")) {
                                ChangeColor.askPlayer(board);
                            }

                            if (received.startsWith("CopyNameAndType")) {
                                String[] message = received.split("#");
                                int id = Integer.parseInt(message[1]);
                                String[] splitted = message[2].split(",");
                                CopyNameAndType.askPlayer(board, id, splitted);
                            }

                            if (received.startsWith("CopyCardFromHand")) {
                                String[] message = received.split("#");
                                int id = Integer.parseInt(message[1]);
                                CopyNameColorStrengthMalusFromHand.askPlayer(board,id);
                            }

                            if (received.startsWith("DeleteOneMalusOnType")) {
                                String[] message = received.split("#");
                                int id = Integer.parseInt(message[1]);
                                String[] splitted = message[2].split(",");
                                DeleteOneMalusOnType.askPlayer(board, id, splitted);
                            }

                            if (received.startsWith("TakeCardOfType")) {
                                String[] message = received.split("#");
                                int id = Integer.parseInt(message[1]);
                                String[] splitted = message[2].split(",");
                                TakeCardOfTypeAtTheEnd.askPlayer(board, id, splitted);
                            }

                        } catch (EOFException eof){
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



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
public void sendMessage(String msg){
    try {
        dos.writeUTF(msg);
        dos.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}
