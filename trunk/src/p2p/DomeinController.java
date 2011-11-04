/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cédric
 */
public class DomeinController {
    
    private Map<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private Map<InetAddress, ArrayList<String>> sharedMap = new HashMap<InetAddress, ArrayList<String>>();
    private Properties properties = new Properties();
    private String CONFIG_FILE = "config";
    
    
    


    ExecutorService executor = Executors.newFixedThreadPool(3);

    public DomeinController() {
        executor.execute(new Broadcaster(true));
    }
    
    
    
    public void addToUserMap(InetAddress inIp, String inPcname) {
        this.userMap.put(inIp, inPcname);
    }
    
    public void removeUser(InetAddress inIp) {
        this.userMap.remove(inIp);
    }

    public Map<InetAddress, String> getUserMap() {
        return this.userMap;
    }
    
     public void addToSharedMap(InetAddress ip, ArrayList<String> sharedList) {
        this.sharedMap.put(ip, sharedList);
    }

    public void removeSharedList(InetAddress ip) {
        this.sharedMap.remove(ip);
    }
    
    public String getUserNameList(int index) {
        List<String> userList = new ArrayList<String>();
        for (Map.Entry<InetAddress, String> anEntry : userMap.entrySet()) {
            String pcname = anEntry.getValue();
            userList.add(pcname);
            }
        return userList.get(index);
        }
    
    public int getUserMapSize(){
        return userMap.size();
    }
    
    public List<String> getSharedFilesList() {
        List<String> sharedFilesList = new ArrayList<String>();
        for (Map.Entry<InetAddress, ArrayList<String>> anEntry : sharedMap.entrySet()) {
            String bestanden = anEntry.getValue().toString();
            sharedFilesList.add(bestanden);
        }
        return sharedFilesList;
    }
    
    public int getSharedMapSize(){
        return sharedMap.size();
    }
    
    //TODO:
    //getFileName(int index)
    public String getFileName(int index){
        return getSharedFilesList().get(index);
        
    }
    //getFileSize(int index)
    public double getFileSize(int index){
       //voorlopige waarde
        return 50.0;
    }
    
    public void sendDownloadRequest(String filename, String ip) {
        Socket link = null;
        int filesize=6022386;
        int bytesRead;
        int current = 0;
        try {
            InetAddress host = InetAddress.getByName(ip);
            link = new Socket(host, 1238);
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            out.println(filename);
            
            FileInputStream in = new FileInputStream(CONFIG_FILE);
            properties.load(in);
            in.close();
            
            
            byte [] mybytearray  = new byte [filesize];
            InputStream is = link.getInputStream();
            
            String strDir = properties.getProperty("directorydownloads");
            File dir = new File(strDir);
            if (!dir.exists())
            dir.mkdirs();
            FileOutputStream fos = new FileOutputStream(strDir + "/" + "source-copy.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;
            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);
            bos.write(mybytearray, 0 , current);
            bos.flush();
            bos.close();
        } catch (IOException ex) {
            Logger.getLogger(P2Pclient.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                link.close();
            } catch (IOException ex) {
                Logger.getLogger(P2Pclient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
        
}