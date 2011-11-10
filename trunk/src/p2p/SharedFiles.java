package p2p;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class SharedFiles {

    private final static String CONFIG_FILE = "config";
    private static SharedFiles instance;
    private static List<TsjakkaFile> sharedTsjakkaList;
    private static Properties properties;
    private static String ip;

    private SharedFiles() {        
        sharedTsjakkaList = new ArrayList<TsjakkaFile>();
        properties = new Properties();
        updateSharedList();
    }
    
    public static synchronized SharedFiles getInstance() {
        if (instance == null)
            instance = new SharedFiles();
        return instance;
    }


    public List<TsjakkaFile> getSharedTsjakkaList() {
        updateSharedList();
        return sharedTsjakkaList;
    }
    
    
    
    public static void updateSharedList() {
        if (!sharedTsjakkaList.isEmpty())
            sharedTsjakkaList.clear();
        try {
            FileInputStream in = new FileInputStream(CONFIG_FILE);
            properties.load(in);
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(SharedFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        findShared(properties.getProperty("directoryshared"));
    }

    private static void findShared(String strDir) {
        File dir = new File(strDir);
        if (!dir.exists())
            dir.mkdirs();
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        File[] children = dir.listFiles(filter);
        if (children == null) {
            System.out.println("foute map");
        } else {
            for (int i = 0; i < children.length; i++) {
                if (!children[i].isDirectory()) {
                    FileSystemView view = FileSystemView.getFileSystemView();      
                    //Icon icon = view.getSystemIcon(children[i]);
                    TsjakkaFile tsjakkaFile = new TsjakkaFile(children[i].getName(), children[i].length(), ip/*, icon*/);
                    sharedTsjakkaList.add(tsjakkaFile);
                } else {
                    findShared(children[i].getPath());
                }
            }
        }
    }
    
    public void changeDirectory(String dir) {
        properties.setProperty("directoryshared", dir);
        try {
            FileOutputStream out = new FileOutputStream(CONFIG_FILE);
            properties.store(out, null);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SharedFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateSharedList();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    
    
}
