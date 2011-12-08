/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import java.io.IOError;
import java.net.InetAddress;
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
import java.util.concurrent.Future;
import utility.Config;

/**
 *
 * @author Cédric
 */
public class DomeinController extends Observable {

    private Map<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private Map<InetAddress, ArrayList<TsjakkaFile>> sharedTsjakkaMap = new HashMap<InetAddress, ArrayList<TsjakkaFile>>();
    private Broadcaster onOffBroadcaster = new Broadcaster(true);
    private List<String> userList = new ArrayList<String>();
    private List<TsjakkaFile> sharedFilesList = new ArrayList<TsjakkaFile>();
    private List<String> filterList = new ArrayList<String>();
    private static StatusMessage statusMessage = new StatusMessage("Welcome!");
    ExecutorService executor = Executors.newCachedThreadPool();
    ExecutorService fexecutor = Executors.newFixedThreadPool(3);
    private Filter filter = new Filter();

    public DomeinController() {
        try{
        fexecutor.execute(onOffBroadcaster);
        }catch(IOError ex){
            addStatusToArea("Fatale fout opgetreden in broadcaster! Herstart programma aub.");
        }
        
        try{
        fexecutor.execute(new FileTransferListener());
        }catch (IOError ex){
            addStatusToArea("Fout tijdens het downloaden van een bestand!");
        }
        
        try{
        fexecutor.execute(new BroadcastListener(this));
        }catch (IOError ex){
            addStatusToArea("Fatale fout opgetreden in broadcastListener! Herstart programma aub.");
        }
    }

    public void addToUserMap(InetAddress inIp, String inPcname) throws UnknownHostException {
        String ownIp = "";
        String inIpString = "";
            inIpString = inIp.getHostAddress();
            ownIp = InetAddress.getLocalHost().getHostAddress();
        if (!ownIp.equals(inIpString)) {
            if (newUser(inIp, inPcname)) {
                this.userMap.put(inIp, inPcname);
                setChanged();
                notifyObservers();
            }
        }
    }

    private boolean newUser(InetAddress inIp, String inPcname) {
        if (userMap.containsKey(inIp)) {
            if (userMap.get(inIp).equals(inPcname)) {
                return false;
            }
        }
        return true;
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
//        if (!ownIp.equals(inIpString)) {
            if (sharedTsjakkaMap.get(inIp) == null) {
                this.sharedTsjakkaMap.put(inIp, sharedList);
                setChanged();
                notifyObservers();
            } else {
                if (listChanged(sharedTsjakkaMap.get(inIp), sharedList)) {
                    this.sharedTsjakkaMap.put(inIp, sharedList);
                    setChanged();
                    notifyObservers();
                }
            }
//        }
    }

    private boolean listChanged(ArrayList<TsjakkaFile> oldList, ArrayList<TsjakkaFile> newList) {
        if (oldList.size() != newList.size()) {
            return true;
        } else {
            while (!(!oldList.isEmpty() && !newList.isEmpty())) {
                for (TsjakkaFile fileNew : newList) {
                    for (TsjakkaFile fileOld : oldList) {
                        if (fileNew.getFilename().equals(fileOld.getFilename()) && fileNew.getFileSize() == fileOld.getFileSize()) {
                            newList.remove(fileNew);
                            oldList.remove(fileOld);
                        }
                    }
                }
            }
        }
        return false;
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
        if (!filter.isEmpty()) {
            return filter.filter(sharedFilesList);
        }
//        sharedFilesList = filter.filter(sharedFilesList);
        return sharedFilesList;
    }

    public int getSharedTsjakkaMapSize() {
        return getSharedTsjakkaFilesList().size();
    }

    public String getFileName(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFilename();

    }

    public double getFileSize(int index) {
//        return this.getSharedTsjakkaFilesList().get(index).getFileSize();
        return this.getSharedTsjakkaFilesList().get(index).getFileSizeInMegaByte();
    }

    public String getFileIp(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getIp();
    }

//    public Icon getFileIcon(int index) {
//        return this.getSharedTsjakkaFilesList().get(index).getIcon();
//    }
    public void sendDownloadRequest(String filename, String ip) {
        try{
        Future<String> future = executor.submit(new DownloadRequester(filename, ip, this));
        }catch (IOError ex){
            addStatusToArea("Downloaden van bestand" + filename + "mislukt");
        }
    }

    public void signout() {
        onOffBroadcaster.setFlag(false);
    }

    public void signin() {
        onOffBroadcaster.setFlag(true);
    }

    public String getUsername() throws UnknownHostException {
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
//    public List<TsjakkaFile> filterList() {
//        List<TsjakkaFile> list = new ArrayList<TsjakkaFile>();
//        for (String extension : filterList) {
//            for (TsjakkaFile tsjakkaFile : sharedFilesList) {
//                if (tsjakkaFile.getExtension().equals(extension)) {
//                    list.add(tsjakkaFile);
//                }
//            }
//        }
//        return list;
//    }

    public void addToFilterList(String filter) {
        this.filter.add(filter);
        setChanged();
        notifyObservers();
    }

    public void emptyList() {
        filter.clear();
        setChanged();
        notifyObservers();
    }

    public List<String> getFilterList() {
        return filter.get();
    }
    
    public File getSharedDirectory() {
        return new File(Config.getInstance().get("directoryshared"));
    }
    
    public File getDownloadsDirectory() {
        return new File(Config.getInstance().get("directorydownloads"));
    }
    
    public void changeSharedDir(String dir) {
        Config.getInstance().set("directoryshared", dir);
    }
    
    public void changeDownloadsDir(String dir) {
        Config.getInstance().set("directorydownloads", dir);
    }
    
    public static void addStatusToArea(String stat) {        
        statusMessage.addStatus(stat);
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }
    
    
}
