/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joachim
 */
public class FileTransferListener implements Runnable {

    private static ServerSocket servSocket;
    private static final int PORT = 1238;

    @Override
    public void run() {
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        try {
            servSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            Logger.getLogger(FileTransferListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        do {
            Socket link = null;
            try {
                link = servSocket.accept();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            threadExecutor.execute(new FileTransferer(link));
        } while (true);
    }
}
