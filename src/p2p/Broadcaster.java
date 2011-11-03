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
    private boolean flag = false;

    public Broadcaster(boolean flag) {
        this.flag = flag;
    }

    
    
    /* Elke 10 sec wordt een multicast packet gestuurd om te kijken
     * wie er op de LAN zit
     */
    @Override
    public void run() {
        try {
            multiSocket = new MulticastSocket(4446);
            multiGroup = InetAddress.getByName("230.0.0.1");
            ownIp = InetAddress.getLocalHost();
            multiSocket.joinGroup(multiGroup);
            do {
                
                //String content = OwnAddress + "*" + ownHostname;
                //buf = message.getBytes
                System.out.println(flag);
                if (flag)
                    sendOnlineMessage();
                else
                    sendSignoutMessage();
                Thread.sleep(8000);
            } while (true);
        } catch (InterruptedException ex) {
            Logger.getLogger(Broadcaster.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Broadcaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void sendOnlineMessage() throws IOException
    {
        Message message;
        byte[] buf = new byte[65535];
        
        ByteArrayOutputStream b_out = new ByteArrayOutputStream();
        ObjectOutputStream o_out = new ObjectOutputStream(b_out);             
        message = new Message("<online>", ownIp.getHostAddress().toString(), ownIp.getHostName(), SharedFiles.getInstance().getSharedList());           
        o_out.writeObject(message);
        buf = b_out.toByteArray();
        multiPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
        multiSocket.send(multiPacket);
        b_out.close();
                
    }
    
    public void sendSignoutMessage() throws IOException
    {
        Message message;
        byte[] buf = new byte[65535];
        
        ByteArrayOutputStream b_out = new ByteArrayOutputStream();
        ObjectOutputStream o_out = new ObjectOutputStream(b_out);             
        message = new Message("<signout>");
        o_out.writeObject(message);
        buf = b_out.toByteArray();
        multiPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
        multiSocket.send(multiPacket);
        b_out.close();
    }
}
