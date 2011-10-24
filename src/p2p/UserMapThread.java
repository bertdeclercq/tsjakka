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
    /*
     * met static setter map opnieuw opvullen in p2pclient of gewoon met 
     * P2Pclient.getInstance().getUsermap().remove werken?
     */

    @Override
    public void run() {
        userMap = P2Pclient.getInstance().getUserMap();
        do {
            for (InetAddress ip : userMap.keySet()) {
                try {
                    if (!ip.isReachable(2000)) {
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
        } while (true);
    }
}
