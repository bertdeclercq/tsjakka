/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.InetAddress;
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
    private static P2Pclient instance;
    
    public static P2Pclient getInstance(){
        if(instance == null)
            instance = new P2Pclient();
        return instance;
    }
    public static void main(String[] args) {
        
        ExecutorService threadExecutor =
                Executors.newFixedThreadPool(3);
        
        threadExecutor.execute(new UserMapThread());
        threadExecutor.execute(new BroadcastThread());
        threadExecutor.execute(new BroadcastListenerThread());
    }
    
    public void addToUserMap(InetAddress inIp, String inPcname) {
        this.userMap.put(inIp, inPcname);
    }
    
    public Map<InetAddress, String> getUserMap(){
        return this.userMap;
    }
    public void printUserMap(){
        for(Map.Entry<InetAddress, String> anEntry : userMap.entrySet()){
 			InetAddress ip = anEntry.getKey();
                        String pcname = anEntry.getValue();
 			System.out.println(pcname +": "+ ip);
 		}

    }
}
