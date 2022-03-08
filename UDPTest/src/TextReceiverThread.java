/*
 * TextReceiver.java
 */

/**
 *
 * @author  abj
 */
import java.net.*;
import java.io.*;
import CMPC3M06.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;

public class TextReceiverThread implements Runnable{

    static DatagramSocket receiving_socket;


    AudioPlayer player = new AudioPlayer();

    public TextReceiverThread() throws LineUnavailableException {
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run (){

        //***************************************************
        //Port to open socket on
        int PORT = 55555;
        //***************************************************

        //***************************************************
        //Open a socket to receive from on port PORT
        //DatagramSocket receiving_socket;
        try{
            receiving_socket = new DatagramSocket(PORT);
            receiving_socket.setSoTimeout(32);
        } catch (SocketException e){
            System.out.println("ERROR: TextReceiver: Could not open UDP socket to receive from.");
            e.printStackTrace();
            System.exit(0);
        }
        //***************************************************

        //***************************************************
        //Main loop.

        boolean running = true;

        while (running){

            try{
                //Receive a DatagramPacket (note that the string cant be more than 80 chars)
                byte[] buffer = new byte[80];
                DatagramPacket packet = new DatagramPacket(buffer, 0, 80);

                receiving_socket.receive(packet);

                player.playBlock(packet.getData());

            } catch (SocketTimeoutException e){
                System.out.println(".");
            } catch (IOException e){
                System.out.println("ERROR: TextReceiver: Some random IO error occured!");
                e.printStackTrace();
            }
        }
        //Close the socket
        receiving_socket.close();
        //***************************************************
    }
}