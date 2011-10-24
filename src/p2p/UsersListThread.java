/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class UsersListThread implements Runnable {

    private MulticastSocket multiSocket;
    private DatagramPacket multiPacket;
    private String ownIp;
    private Map<Integer, InetAddress> usersList;

    @Override
    public void run() {
        try {
            do {
                InetAddress multiGroup = InetAddress.getByName("230.0.0.1");
                byte[] buf = new byte[256];
                multiPacket = new DatagramPacket(buf, buf.length);
                multiSocket = new MulticastSocket(4446);
                multiSocket.joinGroup(multiGroup);
                multiSocket.receive(multiPacket);
                ownIp = InetAddress.getLocalHost().getHostAddress().toString();
                String broadcastMessage = new String(multiPacket.getData(), 0, multiPacket.getLength());
                StringTokenizer inMessage = new StringTokenizer(broadcastMessage, "*");
                if (inMessage.nextToken().equals(ownIp)) {
                    System.out.println("eigen broadcast ontvangen");
                }
                System.out.println(inMessage.nextToken());
                System.out.println(broadcastMessage + " from " + multiPacket.getAddress().toString());
            } while (true);
        } catch (IOException ex) {
            Logger.getLogger(UsersListThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
