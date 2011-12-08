/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileTransferListener implements Runnable {

    private static ServerSocket servSocket;
    private static final int PORT = 1238;

    /**
     * Listens for connections made by the DownloadRequester-threads
     * Starts for each of these connection an individual thread to handle them
     * 
     */
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
