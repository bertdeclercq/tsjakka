/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistency.Config;

/**
 *
 * @author Jimmy
 */
public class DownloadRequester implements Callable {

    private String filename;
    private String ip;
    private DomeinController dc;

    public DownloadRequester(String filename, String ip, DomeinController dc) {
        this.filename = filename;
        this.ip = ip;
        this.dc = dc;
    }

    //@Override
    public void run() {

        Socket link = null;
        try {
            InetAddress host = InetAddress.getByName(ip);
            link = new Socket(host, 1238);
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            //download request versturen met naam van het bestand en ip.
            out.println(dc.getDirectory(filename, ip));

            String strDir = Config.getInstance().get("directorydownloads");
            File dir = new File(strDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // inlezen van binnenkomend bestand
            InputStream is = link.getInputStream();
            FileOutputStream fos = new FileOutputStream(strDir + "/" + filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int length;
            byte[] buffer = new byte[65536];
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            StatusMessage.setStatus("Bestand ontvangen!");
            bos.close();
        } catch (IOException ex) {
            Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException aex) {
            StatusMessage.setStatus("Mongool, download es geen leeg bestand!");
            aex.printStackTrace();
        } finally {
            try {
                link.close();
            } catch (IOException ex) {
                Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }



    }

//    @Override
    public String call() throws Exception {
        String test = null;
        Socket link = null;
        try {
            InetAddress host = InetAddress.getByName(ip);
            link = new Socket(host, 1238);
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            //download request versturen met naam van het bestand en ip.
            out.println(dc.getDirectory(filename, ip));

            String strDir = Config.getInstance().get("directorydownloads");
            File dir = new File(strDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // inlezen van binnenkomend bestand
            InputStream is = link.getInputStream();
            FileOutputStream fos = new FileOutputStream(strDir + "/" + filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int length;
            byte[] buffer = new byte[65536];
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            test = "Bestand ontvangen!";
            bos.close();
        } catch (IOException ex) {
            Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException aex) {
            test = "Mongool download is geen leeg bestand!";
            aex.printStackTrace();
        } finally {
 
            try {
                link.close();
            } catch (IOException ex) {
                Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
            }
             return test;
        }
    }
    
}
