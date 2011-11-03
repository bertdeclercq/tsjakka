/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Joachim
 */
public class P2Pclient {

    private static Map<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private static Map<InetAddress, ArrayList<String>> sharedMap = new HashMap<InetAddress, ArrayList<String>>();
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
    
    public void addToSharedMap(InetAddress ip, ArrayList<String> sharedList)
    {
        this.sharedMap.put(ip, sharedList);
    }
    
    public void removeSharedList(InetAddress ip)
    {
        this.sharedMap.remove(ip);
    }
    
    public static void printSharedMap()
    {
        for (Map.Entry<InetAddress, ArrayList<String>> anEntry : sharedMap.entrySet()) 
        {
            InetAddress ip = anEntry.getKey();
            String bestanden = anEntry.getValue().toString();
            System.out.println(ip + " : " + bestanden);
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
