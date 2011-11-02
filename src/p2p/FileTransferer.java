/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Joachim
 */
public class FileTransferer implements Runnable {

    private Socket link;
    private String filename;

    public FileTransferer(Socket link,String filename) {
        this.link = link;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            File myFile = new File("config");
            byte[] mybytearray = new byte[(int) myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            OutputStream os = link.getOutputStream();
            System.out.println("Sending...");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                link.close();
            } catch (IOException e) {
                System.out.println("Unable to disconnect");
                System.exit(1);
            }
        }
    }
}
