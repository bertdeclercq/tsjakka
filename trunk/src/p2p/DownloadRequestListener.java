/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class DownloadRequestListener implements Runnable {

    private static ServerSocket servSocket;
    private static final int TCPPORT = 1238;
    private static final int UDPPORT = 1237;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket;
    private static byte[] buffer;
    private static String messageIn;
    private static InetAddress clientAddress;

    public static InetAddress getClientAddress() {
        return clientAddress;
    }

    @Override
    public void run() {
        try {
            dgramSocket = new DatagramSocket(UDPPORT);
            Socket link = null;
            ExecutorService threadExecutor = Executors.newCachedThreadPool();
            String filename;
            do {
                buffer = new byte[256];
                inPacket = new DatagramPacket(buffer, buffer.length);
                dgramSocket.receive(inPacket);
                clientAddress = inPacket.getAddress();
                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println(messageIn);
                servSocket = new ServerSocket(TCPPORT);
                link = servSocket.accept();
                filename = messageIn;
                threadExecutor.execute(new FileTransferer(link,filename));
            } while (true);
        } catch (IOException ex) {
            Logger.getLogger(DownloadRequestListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            dgramSocket.close();
        }
    }
}
