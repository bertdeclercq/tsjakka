/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    private List<String> filterList = new ArrayList<String>();
    private StatusMessage statusMessage = new StatusMessage(this);
    ExecutorService executor = Executors.newCachedThreadPool();

    public DomeinController() {
        executor.execute(onOffBroadcaster);
        executor.execute(new FileTransferListener());
        executor.execute(new BroadcastListener(this));
    }

    public void addToUserMap(InetAddress inIp, String inPcname) throws UnknownHostException {
        String ownIp = "";
        String inIpString = "";
            inIpString = inIp.getHostAddress();
            ownIp = InetAddress.getLocalHost().getHostAddress();
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

    public void addToSharedTsjakkaMap(InetAddress inIp, ArrayList<TsjakkaFile> sharedList) throws UnknownHostException {
        String ownIp = "";
        String inIpString = "";
        inIpString = inIp.getHostAddress();
        ownIp = InetAddress.getLocalHost().getHostAddress();
       
        // if (!ownIp.equals(inIpString)) {
        this.sharedTsjakkaMap.put(inIp, sharedList);
        setChanged();
        notifyObservers();
        // }
    }

    public void removeSharedTsjakkaList(InetAddress ip) {
        this.sharedTsjakkaMap.remove(ip);
        setChanged();
        notifyObservers();
    }

    public String getUserNameUser(int index) {
        return getUserNameList().get(index);
    }

    public List<String> getUserNameList() {
        Collection<String> coll = userMap.values();
        userList.clear();

        for (String content : coll) {
            userList.add(content);
        }
        return userList;
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
        if (!filterList.isEmpty()) {
            return filterList();
        }
        return sharedFilesList;
    }

    public int getSharedTsjakkaMapSize() {
        return getSharedTsjakkaFilesList().size();
    }

    public String getFileName(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFilename();

    }

    public double getFileSizeInMB(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFileSizeInMegaByte();
    }

    public String getFileIp(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getIp();
    }

//    public Icon getFileIcon(int index) {
//        return this.getSharedTsjakkaFilesList().get(index).getIcon();
//    }
    public String sendDownloadRequest(String filename, String ip) throws InterruptedException, ExecutionException {
        Future<String> future = executor.submit(new DownloadRequester(filename, ip, this));
        return future.get();
        
    }

    public void signout() {
        onOffBroadcaster.setFlag(false);
    }

    public void signin() {
        onOffBroadcaster.setFlag(true);
    }

    public void addStatusMessage(String status) {
        statusMessage.setStatus(status);
        setChanged();
        notifyObservers();
    }

    public String getStatusMessage() {
        return statusMessage.getStatus();
    }

    public String getUsername() throws UnknownHostException 
    {
        String username = "Tsjakka";
        username = InetAddress.getLocalHost().getHostName().toString();
        return username;

    }

    public String getDirectory(String filename, String ip) throws UnknownHostException {
        List<TsjakkaFile> list = new ArrayList<TsjakkaFile>();

            list = sharedTsjakkaMap.get(InetAddress.getByName(ip));
        for (TsjakkaFile file : list) {
            if (file.getFilename().equals(filename)) {
                return file.getDirectory();
            }
        }
        return null;
    }

    // filter
    public List<TsjakkaFile> filterList() {
        List<TsjakkaFile> list = new ArrayList<TsjakkaFile>();
        for (String extension : filterList) {
            for (TsjakkaFile tsjakkaFile : sharedFilesList) {
                if (tsjakkaFile.getExtension().equals(extension)) {
                    list.add(tsjakkaFile);
                }
            }
        }
        setChanged();
        notifyObservers();
        return list;
    }

    public void addToFilterList(String extension) {
        filterList.add(extension);
    }

    public void emptyList() {
        filterList.clear();
    }

    public List<String> getFilterList() {
        return filterList;
    }
}
