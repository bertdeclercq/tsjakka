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
 * The DomeinController class is the connection between the domain and the gui.
 */
public class DomeinController extends Observable {

    private Map<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private Map<InetAddress, ArrayList<TsjakkaFile>> sharedTsjakkaMap = new HashMap<InetAddress, ArrayList<TsjakkaFile>>();
    private Broadcaster onOffBroadcaster = new Broadcaster(true);
    private List<String> userList = new ArrayList<String>();
    private List<TsjakkaFile> sharedFilesList = new ArrayList<TsjakkaFile>();
    private static StatusMessage statusMessage = new StatusMessage("Welcome!");
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ExecutorService fexecutor = Executors.newFixedThreadPool(3);
    private Filter filter = new Filter();

    /**
     * Initialize a newly created DomeinController object which starts the threads who are needed the whole time so the application will update.
     */
    public DomeinController() {
        fexecutor.execute(onOffBroadcaster);
        fexecutor.execute(new FileTransferListener());
        fexecutor.execute(new BroadcastListener(this));
    }

    /**
     * Adds the user to the map. This map is then shown in the gui screen as a userlist.
     * 
     * @param inIp the ip address
     * @param inPcname the name of the computer
     * 
     * @throws UnknownHostException an exception
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
    
    private boolean newUser(InetAddress inIp, String inPcname) {
        if (userMap.containsKey(inIp)) {
            if (userMap.get(inIp).equals(inPcname)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the user from the usermap and lets the observers know it has changed.
     * 
     * @param inIp the ip address
     */
    public void removeUser(InetAddress inIp) {
        this.userMap.remove(inIp);
        setChanged();
        notifyObservers();
    }

    /**
     * Returns a map of users.
     * 
     * @return the usermap as a map 
     */
    public Map<InetAddress, String> getUserMap() {
        return this.userMap;
    }

    /**
     * Adds the list of shared files to the sharedfilesList
     * 
     * @param inIp the ip address
     * @param sharedList a list of shared files
     * 
     * @throws UnknownHostException an exception
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
    
    private boolean listChanged(ArrayList<TsjakkaFile> oldList, ArrayList<TsjakkaFile> newList) {
        if (oldList.size() != newList.size()) {
            return true;
        } else {
            while (!(!oldList.isEmpty() && !newList.isEmpty())) {
                for (TsjakkaFile fileNew : newList) {
                    for (TsjakkaFile fileOld : oldList) {
                        if (fileNew.getFilename().equals(fileOld.getFilename()) && fileNew.getFileSizeInByte() == fileOld.getFileSizeInByte()) {
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
     * Removes a users files from the filelist.
     * 
     * @param ip the ip address
     */
    public void removeSharedTsjakkaList(InetAddress ip) {
        this.sharedTsjakkaMap.remove(ip);
        setChanged();
        notifyObservers();
    }

    /**
     * Return the username as a string.
     * 
     * @param index the location
     * 
     * @return the username as a string
     */
    public String getUserNameUser(int index) {
        return getUserNameList().get(index);
    }

    /**
     * Returns a list of strings with the name of all the users.
     * 
     * @return a list of strings
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
     * Returns the number of users online.
     * 
     * @return the number of users online
     */
    public int getUserMapSize() {
        return userMap.size();
    }

    /**
     * Returns a list of tsjakkafiles that are being shared by the users.
     * 
     * @return list of tjakkafiles
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
     * Returns the number of tiles being shared.
     * 
     * @return the number of files being shared
     */
    public int getSharedTsjakkaMapSize() {
        return getSharedTsjakkaFilesList().size();
    }

    /**
     * Returns the filename as a string.
     * 
     * @param index the location
     * 
     * @return the filename as a string
     */
    public String getFileName(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFilename();

    }

    /**
     * Returns the file size as a string.
     * 
     * @param index the location
     * 
     * @return the file size as a string
     */
    public String getFileSize(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getFileSize();
    }

    /**
     * Returns the ip address.
     * 
     * @param index the location
     * 
     * @return the ip
     */
    public String getFileIp(int index) {
        return this.getSharedTsjakkaFilesList().get(index).getIp();
    }

    /**
     * Starts a new thread to download the requested file with the given name from the given ip.
     * 
     * @param filename the name of the file
     * 
     * @param ip the ip address
     */
    public void sendDownloadRequest(String filename, String ip) {
        try{
        Future<String> future = executor.submit(new DownloadRequester(filename, ip, this));
        }catch (IOError ex){
            addStatusToArea("Downloaden van bestand" + filename + "mislukt");
        }
    }

    /**
     * Logs the user off.
     */
    public void signout() {
        onOffBroadcaster.setFlag(false);
    }

    /**
     * Signs the user in.
     */
    public void signin() {
        onOffBroadcaster.setFlag(true);
    }

    /**
     * Returns the username as a string.
     * 
     * @return the username as a string
     * 
     * @throws UnknownHostException an exception
     */
    public String getUsername() throws UnknownHostException {
        String username = "Tsjakka";
            username = InetAddress.getLocalHost().getHostName().toString();

            return username;
    }

    /**
     * Returns the directory as a string.
     * 
     * @param filename the filename
     * @param ip the ip address
     * 
     * @return the directory as a string
     * 
     * @throws UnknownHostException an exception
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
     * Adds a new filter to the tablefilter.
     * 
     * @param filter a string that you want to filter
     */
    public void addToFilterList(String filter) {
        this.filter.add(filter);
        setChanged();
        notifyObservers();
    }

    /**
     * Clears the filter list of the tablefilter.
     */
    public void emptyList() {
        filter.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Returns a list of strings from all the filters in use.
     * 
     * @return a list of strings containing the filter
     */
    public List<String> getFilterList() {
        return filter.get();
    }
    
    /**
     * Returns the directory of the shared files as a file.
     * 
     * @return the directory where the shared files are saved
     */
    public File getSharedDirectory() {
        return new File(Config.getInstance().get("directoryshared"));
    }
    
    /**
     * Returns the directory of the downloaded files as a file.
     * 
     * @return the directory where the downloaded files are saved
     */
    public File getDownloadsDirectory() {
        return new File(Config.getInstance().get("directorydownloads"));
    }
    
    /**
     * Changes the directory of the shared files.
     * 
     * @param dir the location of the shared directory
     */
    public void changeSharedDir(String dir) {
        Config.getInstance().set("directoryshared", dir);
    }
    
    /**
     * Changes the directory of the downloaded files.
     * 
     * @param dir the location of the downloads directory
     */
    public void changeDownloadsDir(String dir) {
        Config.getInstance().set("directorydownloads", dir);
    }
    
    /**
     * Adds a new status to the statusMessage.
     * 
     * @param stat a status message
     */
    public static void addStatusToArea(String stat) {        
        statusMessage.addStatus(stat);
    }

    /**
     * Returns the current statusmessage.
     * 
     * @return the status message
     */
    public StatusMessage getStatusMessage() {
        return statusMessage;
    }
}
