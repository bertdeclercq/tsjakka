package domain;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.Config;

/**
 * This class will send a request to a user to download one of his files
 * Afterwards it will also receive the file
 */

public class DownloadRequester implements Callable {

    private String pathname;
    private String ip;
    private DomeinController dc;

    /**
     * Initializes a newly created DownloadRequester object
     * 
     * @param pathname The pathname which leads to the file that has to be downloaded 
     * @param ip The ip address of the user you want to download from
     * @param dc An instance of the domaincontroller
     */
    public DownloadRequester(String pathname, String ip, DomeinController dc) {
        this.pathname = pathname;
        this.ip = ip;
        this.dc = dc;
    }

    /**
     * Sends a downloadrequest to a user asking for a file
     * Receives the file it has requested 
     * 
     * @throws Exception 
     */
    @Override
    public String call() throws Exception {
        String test = null;
        Socket link = null;
        try {
            InetAddress host = InetAddress.getByName(ip);
            link = new Socket(host, 1238);
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            //download request versturen met naam van het bestand en ip.
            DomeinController.addStatusToArea("Download started");
            out.println(dc.getDirectory(pathname, ip));

            String strDir = Config.getInstance().get("directorydownloads");
            File dir = new File(strDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // inlezen van binnenkomend bestand
            InputStream is = link.getInputStream();
            FileOutputStream fos = new FileOutputStream(strDir + "/" + pathname);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int length;
            byte[] buffer = new byte[65536];
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            DomeinController.addStatusToArea("File received!");
            bos.close();
        } catch (IOException ex) {
            DomeinController.addStatusToArea("Failed to download file");
        } catch (ArrayIndexOutOfBoundsException aex) {
            DomeinController.addStatusToArea("You downloaded an empty file!");
        } finally {
            try {
                link.close();
            } catch (IOException ex) {
                DomeinController.addStatusToArea("Error during closing connection!");
            }
            return test;
        }
    }
}
