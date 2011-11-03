/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

        sendDownloadRequest("testfile","192.168.1.100");

    }

    public void addToUserMap(InetAddress inIp, String inPcname) {
        this.userMap.put(inIp, inPcname);
    }

    public void removeUser(InetAddress inIp) {
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

    public void addToSharedMap(InetAddress ip, ArrayList<String> sharedList) {
        this.sharedMap.put(ip, sharedList);
    }

    public void removeSharedList(InetAddress ip) {
        this.sharedMap.remove(ip);
    }

    public static void printSharedMap() {
        for (Map.Entry<InetAddress, ArrayList<String>> anEntry : sharedMap.entrySet()) {
            InetAddress ip = anEntry.getKey();
            String bestanden = anEntry.getValue().toString();
            System.out.println(ip + " : " + bestanden);
        }
    }

    public static void sendDownloadRequest(String filename, String ip) {
        Socket link = null;

        try {
            InetAddress host = InetAddress.getByName(ip);
            link = new Socket(host, 1238);
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            out.println(filename);
        } catch (IOException ex) {
            Logger.getLogger(P2Pclient.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                link.close();
            } catch (IOException ex) {
                Logger.getLogger(P2Pclient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}
