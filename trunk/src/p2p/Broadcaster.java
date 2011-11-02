/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class Broadcaster implements Runnable {

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
            multiSocket = new MulticastSocket(4446);
            multiGroup = InetAddress.getByName("230.0.0.1");
            ownIp = InetAddress.getLocalHost();
            Message message;
            do {
                multiSocket.joinGroup(multiGroup);
                byte[] buf = new byte[65535];
                //String content = OwnAddress + "*" + ownHostname;
                //buf = message.getBytes();
                ByteArrayOutputStream b_out = new ByteArrayOutputStream();
                ObjectOutputStream o_out = new ObjectOutputStream(b_out);             
                message = new Message("<online>", ownIp.getHostAddress().toString(), ownIp.getHostName(), SharedFiles.getInstance().getSharedList());           
                o_out.writeObject(message);
                buf = b_out.toByteArray();
                multiPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
                multiSocket.send(multiPacket);
                b_out.close();
                System.out.println("sent");
                Thread.sleep(8000);
            } while (true);
        } catch (InterruptedException ex) {
            Logger.getLogger(Broadcaster.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Broadcaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
