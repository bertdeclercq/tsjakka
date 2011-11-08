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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cédric
 */
public class DomeinController extends Observable {

    private Map<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private Map<InetAddress, ArrayList<TsjakkaFile>> sharedTsjakkaMap = new HashMap<InetAddress, ArrayList<TsjakkaFile>>();
    private Properties properties = new Properties();
    private String CONFIG_FILE = "config";
    private Broadcaster onOffBroadcaster = new Broadcaster(true);
    private List<String> userList = new ArrayList<String>();
    private List<TsjakkaFile> sharedFilesList = new ArrayList<TsjakkaFile>();
    ExecutorService executor = Executors.newFixedThreadPool(4);

    public DomeinController() {
        executor.execute(onOffBroadcaster);
        executor.execute(new FileTransferListener());
        executor.execute(new BroadcastListener(this));
    }

    public void addToUserMap(InetAddress inIp, String inPcname) {
        String ownIp = "";
        String inIpString = "";
        try {
            inIpString = inIp.getHostAddress();
            ownIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!ownIp.equals(inIpString)) {
            this.userMap.put(inIp, inPcname);
            setChanged();
            notifyObservers();
        }

    }

    public void removeUser(InetAddress inIp) {
        this.userMap.remove(inIp);
        setChanged();
        notifyObservers();
    }

    public Map<InetAddress, String> getUserMap() {
        return this.userMap;
    }

    public void addToSharedTsjakkaMap(InetAddress ip, ArrayList<TsjakkaFile> sharedList) {
        this.sharedTsjakkaMap.put(ip, sharedList);
        setChanged();
        notifyObservers();
    }

    public void removeSharedTsjakkaList(InetAddress ip) {
        this.sharedTsjakkaMap.remove(ip);
        setChanged();
        notifyObservers();
    }

    public String getUserNameList(int index) {
        for (Map.Entry<InetAddress, String> anEntry : userMap.entrySet()) {
            String pcname = anEntry.getValue();
            userList.add(pcname);
        }
        return userList.get(index);
    }

    public int getUserMapSize() {
        return userMap.size();
    }

    public List<TsjakkaFile> getSharedTsjakkaFilesList() {
        Collection<ArrayList<TsjakkaFile>> coll = sharedTsjakkaMap.values();
        sharedFilesList.clear();

        for (ArrayList<TsjakkaFile> fileList : coll) {
            for (TsjakkaFile file : fileList) {
                sharedFilesList.add(file);
                if (!sharedFilesList.contains(file)) {
                    sharedFilesList.add(file);
                }

            }
        }
        return sharedFilesList;
    }

    public int getSharedTsjakkaMapSize() {
        return getSharedTsjakkaFilesList().size();
    }

    public String getFileName(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFilename();

    }

    public double getFileSize(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFileSizeInMegaByte();
    }

    public void sendDownloadRequest(String filename, String ip) {
        Socket link = null;
        int filesize = 6022386;
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


            byte[] mybytearray = new byte[filesize];
            InputStream is = link.getInputStream();

            String strDir = properties.getProperty("directorydownloads");
            File dir = new File(strDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(strDir + "/" + "source-copy.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;
            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);
            bos.write(mybytearray, 0, current);
            bos.flush();
            bos.close();
        } catch (IOException ex) {
            Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                link.close();
            } catch (IOException ex) {
                Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }



    }

    public void signout() {
        onOffBroadcaster.setFlag(false);
    }

    public void signin() {
        onOffBroadcaster.setFlag(true);
    }

    public String getUsername() {
        String username = "Tsjakka";
        try {
            username = InetAddress.getLocalHost().getHostName().toString();
        } catch (UnknownHostException ex) {
            Logger.getLogger(DomeinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;
    }
}
