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

public class SharedFiles {

    private final static String CONFIG_FILE = "config";
    private static SharedFiles instance;
    private static List<String> sharedList;
    private static Properties properties;

    private SharedFiles() {        
        sharedList = new ArrayList<String>();
        properties = new Properties();
        updateSharedList();
    }
    
    public static synchronized SharedFiles getInstance() {
        if (instance == null)
            instance = new SharedFiles();
        return instance;
    }

    public List<String> getSharedList() {
        return sharedList;
    }
    
    public static void updateSharedList() {
        try {
            FileInputStream in = new FileInputStream(CONFIG_FILE);
            properties.load(in);
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(SharedFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        findShared(properties.getProperty("directory"));
    }

    private static void findShared(String strDir) {
        if (!sharedList.isEmpty())
            sharedList.clear();
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
                    sharedList.add(children[i].getName());
                } else {
                    findShared(children[i].getPath());
                }
            }
        }
    }
    
    public void changeDirectory(String dir) {
        properties.setProperty("directory", dir);
        try {
            FileOutputStream out = new FileOutputStream(CONFIG_FILE);
            properties.store(out, null);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SharedFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateSharedList();
    }
}
