package p2p;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
//import javax.swing.Icon;
//import javax.swing.filechooser.FileSystemView;
import persistency.Config;

public class SharedFiles {
    
    private static SharedFiles instance;
    private static List<TsjakkaFile> sharedTsjakkaList;
    private static String ip;

    private SharedFiles() {
        sharedTsjakkaList = new ArrayList<TsjakkaFile>();
        updateSharedList();
    }

    public static synchronized SharedFiles getInstance() {
        if (instance == null) {
            instance = new SharedFiles();
        }
        return instance;
    }

    public List<TsjakkaFile> getSharedTsjakkaList() {
        updateSharedList();
        return sharedTsjakkaList;
    }

    public static void updateSharedList() {
        if (!sharedTsjakkaList.isEmpty()) {
            sharedTsjakkaList.clear();
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
//                    FileSystemView view = FileSystemView.getFileSystemView();      
//                    Icon icon = view.getSystemIcon(children[i]);
                    sharedTsjakkaList.add(new TsjakkaFile(children[i], ip));
                } else {
                    findShared(children[i].getPath());
                }
            }
        }
    }

    public void changeDirectory(String dir) {
        Config.getInstance().set("directoryshared", dir);
        updateSharedList();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
