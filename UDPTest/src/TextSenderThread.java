/*
 * TextSender.java
 */

/**
 *
 * @author  abj
 */
import java.net.*;
import java.io.*;
import CMPC3M06.AudioRecorder;

import javax.sound.sampled.LineUnavailableException;

public class TextSenderThread implements Runnable{

    static DatagramSocket sending_socket;
    AudioRecorder recorder = new AudioRecorder();

    public TextSenderThread() throws LineUnavailableException {
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run (){

        //***************************************************
        //Port to send to
        int PORT = 55555;
        //IP ADDRESS to send to
        InetAddress clientIP = null;
        try {
            clientIP = InetAddress.getByName("Tomass-MacBook-Pro.local");  //CHANGE localhost to IP or NAME of client machine
        } catch (UnknownHostException e) {
            System.out.println("ERROR: TextSender: Could not find client IP");
            e.printStackTrace();
            System.exit(0);
        }
        //***************************************************

        //***************************************************
        //Open a socket to send from
        //We dont need to know its port number as we never send anything to it.
        //We need the try and catch block to make sure no errors occur.

        //DatagramSocket sending_socket;
        try{
            sending_socket = new DatagramSocket(55555);
        } catch (SocketException e){
            System.out.println("ERROR: TextSender: Could not open UDP socket to send from.");
            e.printStackTrace();
            System.exit(0);
        }
        //***************************************************

        //***************************************************
        //Get a handle to the Standard Input (console) so we can read user input

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //***************************************************

        //***************************************************
        //Main loop.

        boolean running = true;

        while (running){
            try{
                //Read in a string from the standard input
                String str = in.readLine();

                //Convert it to an array of bytes
                byte[] block = recorder.getBlock();

                //Make a DatagramPacket from it, with client address and port number
                DatagramPacket packet = new DatagramPacket(block, block.length, clientIP, PORT);

                //Send it
                sending_socket.send(packet);

                //The user can type EXIT to quit
                if (str.equals("EXIT")){
                    running=false;
                }

            } catch (IOException e){
                System.out.println("ERROR: TextSender: Some random IO error occured!");
                e.printStackTrace();
            }
        }
        //Close the socket
        sending_socket.close();
        //***************************************************
    }
}