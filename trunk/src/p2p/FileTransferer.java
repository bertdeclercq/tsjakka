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
            int count;
            FileInputStream infile = new FileInputStream(CONFIG_FILE);
            properties.load(infile);
            infile.close();
            BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream()));
            inMessage  = in.readLine();
            filename = inMessage;           
            File myFile = new File(filename);
            byte[] mybytearray = new byte[65536];
            FileInputStream fis = new FileInputStream(myFile);
            OutputStream os = link.getOutputStream();
            
         //   StatusMessage.setStatus("Someone is stealing the following file : " + filename);
          DomeinController.addStatusToArea("someone is leaching: " + filename);
          
            while((count = fis.read(mybytearray)) > 0){
                os.write(mybytearray, 0, count);
            }
            os.flush();
            DomeinController.addStatusToArea(filename + " was succesfully transfered to other user");
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
