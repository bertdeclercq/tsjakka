package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import p2p.DomeinController;
import p2p.SharedFiles;
import p2p.TsjakkaFile;

public class FilterTest {

    private static DomeinController dc = new DomeinController();

    public static void main(String[] args) {
        System.out.println(dc.getSharedTsjakkaFilesList());
        System.out.println("Geef extensies van bestanden die je wil zien (type 'stop' om te stoppen)");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (!input.equals("stop")) {
            dc.addToFilterList(input);
            input = in.nextLine();
        }
        System.out.println(dc.getFilterList());
        System.out.println("Nieuwe lijst:");
        System.out.println(dc.FilterList());
    }
}
