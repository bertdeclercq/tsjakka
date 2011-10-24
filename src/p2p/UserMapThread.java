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

    private Map<InetAddress, String> userMap;

    @Override
    public void run() {
        do{
            userMap = P2Pclient.getInstance().getUserMap();
        for (InetAddress ip : userMap.keySet()) {
            try {
                if (!ip.isReachable(200)) {
                    userMap.remove(ip);
                }
                 
            } catch (IOException ex) {
                Logger.getLogger(UserMapThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        P2Pclient.getInstance().printUserMap();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserMapThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        }while(true);
    }
}
