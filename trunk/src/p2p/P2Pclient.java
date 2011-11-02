/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
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

    private Map<InetAddress, String> userMap = new ConcurrentHashMap<InetAddress, String>();
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

        threadExecutor.execute(new DownloadRequestListener());
        threadExecutor.execute(new Broadcaster());
        threadExecutor.execute(new BroadcastListener());
        
        
    }

    public void addToUserMap(InetAddress inIp, String inPcname) {
        this.userMap.put(inIp, inPcname);
    }

    public Map<InetAddress, String> getUserMap() {
        return this.userMap;
    }

    public void printUserMap() {
        for (Map.Entry<InetAddress, String> anEntry : userMap.entrySet()) {
            InetAddress ip = anEntry.getKey();
            String pcname = anEntry.getValue();
            System.out.println(pcname + ": " + ip);
        }

    }

    public void sendDownloadRequest() {
        int PORT = 1234;
        DatagramSocket dgramSocket;
        DatagramPacket outPacket;
        String outMessage;
        try {
            dgramSocket = new DatagramSocket(PORT);
            outMessage = ("test");
            outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length(), DownloadRequestListener.getClientAddress(), PORT);
            dgramSocket.send(outPacket);
        } catch (IOException ex) {
            Logger.getLogger(P2Pclient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
