/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;

/**
 *
 * @author Jimmy
 */
public class NewClass {
    
    public static void main(String args[]) {
        File file = new File("freecap.zip");
        System.out.println((double) (file.length()/1024.0/1024.0) + "MB");
    }   
    
}
