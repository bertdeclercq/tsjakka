/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author Joachim
 */
public class FileTransferer implements Runnable {

    private Socket link;
    private String filename;
    private Properties properties = new Properties();
    private String CONFIG_FILE = "config";

    public FileTransferer(Socket link) {
        this.link = link;
    }

    @Override
    public void run() {
        String inMessage;
        try {
            FileInputStream infile = new FileInputStream(CONFIG_FILE);
            properties.load(infile);
            infile.close();
            BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream()));
            inMessage  = in.readLine();
            filename = inMessage;           
//            String strDir = properties.getProperty("directoryshared");           
            File myFile = new File(filename);
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
