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

import java.io.*;
import java.net.*;


public class Client implements Runnable
{
    final static int ServerPort = 1234;
    boolean sentLobbyInfo = false;
    InetAddress ip;
    String name;
    int maxPlayers;
    public Client(String name, int maxPlayers){
        this.name = name;
        this.maxPlayers = maxPlayers;
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
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
            System.out.println("after client dis,dos,ois init");

            // sendMessage thread
            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    // read the message to deliver.
                    String msg = name + "#" + maxPlayers + "#" + "false";
                    try {
                        // write on the output stream
                        dos.writeUTF("INIT");
                        dos.flush();
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
                    while (true) {
                        try {
                            String received = dis.readUTF();
                            if (received.equals("CARD_TO_HAND")) {
                                // read the message sent to this client
                                try {
                                    Card card = (Card) dis.readObject();
                                    System.out.println(card.name);
                                    BoardController.player.hand.add(card);
                                } catch (EOFException eof) {
                                    break;
                                }

                            }
                        }catch (EOFException eof){
                            break;
                        } catch (IOException | ClassNotFoundException e) {

                            e.printStackTrace();
                        }
                        BoardController.gotAllCards = true;

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


}
