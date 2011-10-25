package p2p;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SharedFiles {

    private static String SHARED_DIR = "./shared";
    private static List<String> sharedList = new ArrayList<String>();

    public static void main(String[] args) {
        File dir = new File(SHARED_DIR);
        File[] children = dir.listFiles();
        if (children == null) {
            System.out.println("foute map");
        } else {
            for (int i = 0; i < children.length; i++) {
                if (!children[i].isDirectory()) {
                    sharedList.add(children[i].getName());
                }
            }
        }
        System.out.println(sharedList.toString());
    }
}
