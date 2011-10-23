/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class BroadcastThread implements Runnable {

    private MulticastSocket multiSocket;
    private InetAddress multiGroup;
    private InetAddress ownIp;
    private DatagramPacket multiPacket;

    /* Elke 10 sec wordt een multicast packet gestuurd om te kijken
     * wie er op de LAN zit
     */
    @Override
    public void run() {
        try {
            do {
                multiSocket = new MulticastSocket(4446);
                multiGroup = InetAddress.getByName("230.0.0.1");
                multiSocket.joinGroup(multiGroup);
                ownIp = InetAddress.getLocalHost();
                String OwnAddress = ownIp.getHostAddress().toString();
                String ownHostname = ownIp.getHostName();
                byte[] buf = new byte[256];
                String message = OwnAddress + " * " + ownHostname;
                buf = message.getBytes();
                multiPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
                multiSocket.send(multiPacket);
                System.out.println("sent");
                Thread.sleep(10000);
            } while (true);
        } catch (InterruptedException ex) {
            Logger.getLogger(BroadcastThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
