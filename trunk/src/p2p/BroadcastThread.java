/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
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
    private DatagramPacket multiPacket, sharedListPacket;

    /* Elke 10 sec wordt een multicast packet gestuurd om te kijken
     * wie er op de LAN zit
     */
    @Override
    public void run() {
        BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
        try {
            multiSocket = new MulticastSocket(4446);
            multiGroup = InetAddress.getByName("230.0.0.1");
            ownIp = InetAddress.getLocalHost();
            String OwnAddress = ownIp.getHostAddress().toString();
            String ownHostname = ownIp.getHostName();
            do {
                String userMessage = userEntry.readLine();
                multiSocket.joinGroup(multiGroup);
                byte[] buf = new byte[256];
                String message = OwnAddress + "*" + ownHostname + "*" + userMessage;
                buf = message.getBytes();
                multiPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
                multiSocket.send(multiPacket);
                //vestuur lijst met gedeelde bestanden
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(stream);
                out.writeObject(SharedFiles.getInstance().getSharedList());
                out.close();
                buf = stream.toByteArray();
                sharedListPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
                multiSocket.send(sharedListPacket);
                System.out.println("sent");
                Thread.sleep(8000);
            } while (true);
        } catch (InterruptedException ex) {
            Logger.getLogger(BroadcastThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
