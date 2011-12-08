package domain;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import utility.Config;

/**
 * The SharedFiles class creates a list of all files in the directory you want to share. It also provides options to return this list or update it.
 */
public class SharedFiles {

    private static SharedFiles instance;
    private static List<TsjakkaFile> sharedList;
    private static String ip;

    private SharedFiles() {
        sharedList = new ArrayList<TsjakkaFile>();
        updateSharedList();
    }

    /**
     * Gets an instance from this class.
     * 
     * @return an instance from this class
     */
    public static synchronized SharedFiles getInstance() {
        if (instance == null) {
            instance = new SharedFiles();
        }
        return instance;
    }

    /**
     * Returns a list of tsjakkafiles that are in the shared folder.
     * 
     * @return a list of tsjakkafiles that are shared
     */
    public List<TsjakkaFile> getSharedList() {
        updateSharedList();
        return sharedList;
    }

    /**
     * Updates the list of shared files so that it contains the latest files.
     */
    public static void updateSharedList() {
        if (!sharedList.isEmpty()) {
            sharedList.clear();
        }
        findShared(Config.getInstance().get("directoryshared"));
    }

    private static void findShared(String strDir) {
        File dir = new File(strDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        File[] children = dir.listFiles(filter);
        if (children != null) {
            for (int i = 0; i < children.length; i++) {
                if (!children[i].isDirectory()) {
                    sharedList.add(new TsjakkaFile(children[i]));
                } else {
                    findShared(children[i].getPath());
                }
            }
        }
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
}
