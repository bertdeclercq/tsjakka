/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class BroadcastListener implements Runnable {

    private static final long serialVersionUID = 1;
    private MulticastSocket multiSocket;
    private DatagramPacket multiPacket;
    private String inPcname,ownIp;
    private InetAddress multiGroup, inIpaddress;
    private DomeinController dc;

    public BroadcastListener(DomeinController dc) {
        this.dc = dc;
    }
    
    

    @Override
    public void run() {
        try {
            multiGroup = InetAddress.getByName("230.0.0.1");
            multiSocket = new MulticastSocket(4446);
            ownIp = InetAddress.getLocalHost().getHostAddress().toString();
            Message message = null;
            multiSocket.joinGroup(multiGroup);
            do {              
                byte[] buffer = new byte[65535];
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                multiPacket = new DatagramPacket(buffer, buffer.length);
                multiSocket.receive(multiPacket);
                ObjectInputStream ois = new ObjectInputStream(bais);
                
                try {
                    Object o = ois.readObject();
                    multiPacket.setLength(buffer.length);
                    bais.reset();
                    message = (Message) o;
                    message.checkStatusTag();
                    inIpaddress = InetAddress.getByName(message.getOwnAddress());
                    if (message.isOnlineMessage())
                    {
                        
                        inPcname = message.getHostName();
                        dc.addToUserMap(inIpaddress, inPcname);
                        dc.addToSharedTsjakkaMap(inIpaddress, (ArrayList<TsjakkaFile>) message.getContent());
                        
                    }
                    if (message.isSignOutMessage())
                    {
                        
                        dc.removeUser(inIpaddress);
                        dc.removeSharedTsjakkaList(inIpaddress);
                    }
                    ois.close();
                    bais.close();
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (true);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
