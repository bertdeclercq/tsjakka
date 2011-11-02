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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class BroadcastListener implements Runnable {

    private MulticastSocket multiSocket;
    private DatagramPacket multiPacket;
    private String ownIp, inIp, broadcastMessage, inPcname, inChoice;
    //private String[] messageTags;;
    private InetAddress multiGroup, inIpaddress;
    private Map<InetAddress, ArrayList<String>> sharedMap;

    @Override
    public void run() {
        try {
            multiGroup = InetAddress.getByName("230.0.0.1");
           // buf = new byte[256];
          //  multiPacket = new DatagramPacket(buf, buf.length);
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
                    if (message.isOnlineMessage())
                        P2Pclient.getInstance().addToUserMap(inIpaddress, inPcname);
//                        sharedList.addAll((ArrayList<String>) message.getContent());
                        System.out.println(message.getContent());
//                        System.out.println(sharedList.toString());
                    if (message.isSignOutMessage())
                        System.out.println("Yooo bedankt e!");
                    ois.close();
    //            try {
    //                broadcastMessage = new String(multiPacket.getData(), 0, multiPacket.getLength());
    //                messageTags = broadcastMessage.split("[*]");
    //                inIp = messageTags[0];
    //                inIpaddress = InetAddress.getByName(inIp);
    //                inPcname = messageTags[1];
    //                inChoice = messageTags[2];
    //                if (inIp.equals(ownIp)) {
    //                    System.out.println("eigen broadcast ontvangen");
    //                }
    //                message = new Message(inChoice);
    //                if (message.isOnlineMessage()) {
    //                    P2Pclient.getInstance().addToUserMap(inIpaddress, inPcname);
    //                }
    //                if (message.isSignOutMessage()) {
    //                }
    //                }
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (true);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
