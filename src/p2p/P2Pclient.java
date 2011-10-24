/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Joachim
 */
public class P2Pclient {

    public static void main(String[] args) {
        
        ExecutorService threadExecutor =
                Executors.newFixedThreadPool(3);
        
        threadExecutor.execute(new BroadcastThread());
        threadExecutor.execute(new BroadcastListenerThread());
    }
}
