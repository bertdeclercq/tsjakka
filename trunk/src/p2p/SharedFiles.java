package p2p;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class SharedFiles {

    private static String SHARED_DIR = "./shared";
    private static List<String> sharedList = new ArrayList<String>();

    public static void main(String[] args) {
        findShared(SHARED_DIR);
        System.out.println(sharedList.toString());
    }

    public static void findShared(String strDir) {
        File dir = new File(strDir);
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
}
