package p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Joachim
 */
public class UserMapThread implements Runnable {

    private Map<InetAddress, String> userMap = new HashMap<InetAddress, String>();

    public void addToUserMap(InetAddress inIp, String inPcname) {
        userMap.put(inIp, inPcname);
    }

    @Override
    public void run() {
        do{
        for (InetAddress ip : userMap.keySet()) {
            try {
                if (!ip.isReachable(200)) {
                    userMap.remove(ip);
                }
                printUserMap();
                
            } catch (IOException ex) {
                Logger.getLogger(UserMapThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserMapThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        }while(true);
    }
    
    public void printUserMap(){
        System.out.println("jimmy isn en dikken klootzak");
        for(Map.Entry<InetAddress, String> anEntry : userMap.entrySet()){
 			InetAddress ip = anEntry.getKey();
                        String pcname = anEntry.getValue();
 			System.out.println(pcname +": "+ ip);
 		}

    }
}
