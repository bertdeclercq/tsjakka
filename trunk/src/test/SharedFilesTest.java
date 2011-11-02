package test;

import java.util.Scanner;
import p2p.SharedFiles;

public class SharedFilesTest {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(SharedFiles.getInstance().getSharedList().toString());
        System.out.println("Geef nieuwe directory");
        String dir = in.nextLine();
        SharedFiles.getInstance().changeDirectory(dir);
        System.out.println(SharedFiles.getInstance().getSharedList().toString());
    }
}
