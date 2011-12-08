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

    /**
     * initialize a newly created DomeinController object with stat
     * starts the threads who are needed the whole time so the
     * application will update
     */
    public DomeinController() {
        
        fexecutor.execute(onOffBroadcaster);
        fexecutor.execute(new FileTransferListener());
        fexecutor.execute(new BroadcastListener(this));
    }

    /**
     * adds the user to the map.
     * this map is then shown in the gui screen as a userlist
     * @param inIp
     * @param inPcname
     * @throws UnknownHostException 
     
     */
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

    /**
     * 
     * @param inIp
     * @param inPcname
     * @return boolean
     * checks whether the user is a new user in the list or not
     * 
     */
    private boolean newUser(InetAddress inIp, String inPcname) {
        if (userMap.containsKey(inIp)) {
            if (userMap.get(inIp).equals(inPcname)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param inIp 
     * removes the user from the usermap and lets the observers know
     * it has changed
     */
    public void removeUser(InetAddress inIp) {
        this.userMap.remove(inIp);
        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @return the usermap as a map 
     */
    public Map<InetAddress, String> getUserMap() {
        return this.userMap;
    }

    /**
     * 
     * @param inIp
     * @param sharedList
     * @throws UnknownHostException 
     * adds the list of shared files to the sharedfilesList
     */
    public void addToSharedTsjakkaMap(InetAddress inIp, ArrayList<TsjakkaFile> sharedList) throws UnknownHostException {
        String ownIp = "";
        String inIpString = "";
            inIpString = inIp.getHostAddress();
            ownIp = InetAddress.getLocalHost().getHostAddress();
        if (!ownIp.equals(inIpString)) {
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
        }
    }

    /**
     * 
     * @param oldList
     * @param newList
     * @return boolean
     * checks if the fileslist has changed or not
     * to see if there are new or updated files.
     */
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

    /**
     * 
     * @param ip 
     * removes a users files from the filelist.
     */
    public void removeSharedTsjakkaList(InetAddress ip) {
        this.sharedTsjakkaMap.remove(ip);
        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @param index
     * @return the username as a string
     */
    public String getUserNameUser(int index) {
        return getUserNameList().get(index);
    }

    /**
     * 
     * @return a list of strings 
     * these strings are all the online users.
     */
    public List<String> getUserNameList() {
        Collection<String> coll = userMap.values();
        userList.clear();

        for (String content : coll) {
            userList.add(content);
        }
        return userList;
    }

    /**
     * 
     * @return the size of the map which contains the users.
     * show how many users are online.
     */
    public int getUserMapSize() {
        return userMap.size();
    }

    /**
     * 
     * @return list of tjakkafiles. the list of files that are being shared by the users.
     */
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
        return sharedFilesList;
    }

    /**
     * 
     * @return integer the size of the map which contains the files
     */
    public int getSharedTsjakkaMapSize() {
        return getSharedTsjakkaFilesList().size();
    }

    /**
     * 
     * @param index
     * @return String the name of the tsjakkafile
     */
    public String getFileName(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFilename();

    }

    /**
     * 
     * @param index
     * @return double returns the size of the tsjakkafile
     */
    public double getFileSize(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFileSizeInMegaByte();
    }

    /**
     * 
     * @param index
     * @return String returns the ip of the file
     */
    public String getFileIp(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getIp();
    }

    /**
     * 
     * @param filename
     * @param ip 
     * starts a new thread to download the requested file with the
     * given name from the given ip
     */
    public void sendDownloadRequest(String filename, String ip) {
        try{
        Future<String> future = executor.submit(new DownloadRequester(filename, ip, this));
        }catch (IOError ex){
            addStatusToArea("Downloaden van bestand" + filename + "mislukt");
        }
    }

    /**
     * logs off the user
     */
    public void signout() {
        onOffBroadcaster.setFlag(false);
    }

    /**
     * signs the user in
     */
    public void signin() {
        onOffBroadcaster.setFlag(true);
    }

    /**
     * 
     * @return String the username for the welcome in the gui
     * @throws UnknownHostException 
     */
    public String getUsername() throws UnknownHostException {
        String username = "Tsjakka";
            username = InetAddress.getLocalHost().getHostName().toString();

            return username;
    }

    /**
     * 
     * @param filename
     * @param ip
     * @return String the directory of the user in which the files are stored
     * @throws UnknownHostException 
     * 
     */
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

    /**
     * adds a new filter to the tablefilter
     * @param filter 
     */
    public void addToFilterList(String filter) {
        this.filter.add(filter);
        setChanged();
        notifyObservers();
    }

    /**
     * clears the filter list of the tablefilter
     */
    public void emptyList() {
        filter.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @return the list of strings that the filter list contains
     */
    public List<String> getFilterList() {
        return filter.get();
    }
    
    /**
     * 
     * @return the file in which the shared files are stored
     */
    public File getSharedDirectory() {
        return new File(Config.getInstance().get("directoryshared"));
    }
    
    /**
     * 
     * @return the file in which the downloaded files are stored 
     */
    public File getDownloadsDirectory() {
        return new File(Config.getInstance().get("directorydownloads"));
    }
    
    /**
     * changes the directory of the shared files
     * @param dir 
     */
    public void changeSharedDir(String dir) {
        Config.getInstance().set("directoryshared", dir);
    }
    
    /**
     * changes the directory of the downloaded files
     * @param dir 
     */
    public void changeDownloadsDir(String dir) {
        Config.getInstance().set("directorydownloads", dir);
    }
    
    /**
     * adds a new status to the statusMessage
     * @param stat 
     */
    public static void addStatusToArea(String stat) {        
        statusMessage.addStatus(stat);
    }

    /**
     * returns the current statusmessage
     * @return 
     */
    public StatusMessage getStatusMessage() {
        return statusMessage;
    }
    
    
}
