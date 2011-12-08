
package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * The Filetransferer Class will send a file to the user that send a downloadrequest
 * 
 */
public class FileTransferer implements Runnable {

    private Socket link;
    private String filename;
    private Properties properties = new Properties();
    private String CONFIG_FILE = "config";
    
    
    /**
     * Initializes a newly created FileTransferer object
     * 
     * @param link the socket which has an active connection and has been passed by the FileTransferListener
     */
    public FileTransferer(Socket link) {
        this.link = link;  
    }

    /**
     * Sends a file that has been requested by a DownloadRequester-thread
     * 
     */
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
