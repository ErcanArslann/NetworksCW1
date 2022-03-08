/*
 * TextDuplex.java
 */

import CMPC3M06.AudioPlayer;
import CMPC3M06.AudioRecorder;

import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author  abj
 */
public class TextDuplex {

    public static void main (String[] args) throws Exception {

        TextReceiverThread receiver = new TextReceiverThread();
        TextSenderThread sender = new TextSenderThread();

        receiver.start();
        sender.start();

    }

}