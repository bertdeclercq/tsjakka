/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class P2Pclient {

    private static Map<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private static P2Pclient instance;

    public static P2Pclient getInstance() {
        if (instance == null) {
            instance = new P2Pclient();
        }
        return instance;
    }

    public static void main(String[] args) {
        ExecutorService threadExecutor =
                Executors.newFixedThreadPool(3);

        threadExecutor.execute(new FileTransferListener());
        threadExecutor.execute(new Broadcaster(true));
        threadExecutor.execute(new BroadcastListener());

        sendDownloadRequest();
        
    }

    public void addToUserMap(InetAddress inIp, String inPcname) {
        this.userMap.put(inIp, inPcname);
    }
    
    public void removeUser(InetAddress inIp)
    {
        this.userMap.remove(inIp);
    }

    public Map<InetAddress, String> getUserMap() {
        return this.userMap;
    }

    public static void printUserMap() {
        for (Map.Entry<InetAddress, String> anEntry : userMap.entrySet()) {
            InetAddress ip = anEntry.getKey();
            String pcname = anEntry.getValue();
            System.out.println(pcname + ": " + ip);
        }

    }

    public static void sendDownloadRequest() {
        
//        int PORT = 1237;
//        DatagramSocket dgramSocket = null;
//        DatagramPacket outPacket;
//        String outMessage;
//        try {
//            dgramSocket = new DatagramSocket();
//            outMessage = ("Bert Olé Olé");
//            outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length(), InetAddress.getLocalHost(), PORT);
//            dgramSocket.send(outPacket);
//        } catch (IOException ex) {
//            Logger.getLogger(P2Pclient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        finally{
//            dgramSocket.close();
//        }
    }
}
