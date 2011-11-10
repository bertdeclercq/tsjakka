/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Jimmy
 */
public class TsjakkaFile implements Serializable {
    
    private String filename;
    private double fileSize;
    private String ip;
    private ImageIcon icon;

    public TsjakkaFile(String filename, double fileSize, String ip) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.ip = ip;
    }
    
        public TsjakkaFile(String filename, double fileSize, String ip, ImageIcon icon) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.ip = ip;
        this.icon = icon;
    }

    public TsjakkaFile(String filename, double fileSize) {
        this.filename = filename;
        this.fileSize = fileSize;
    }

    public TsjakkaFile() {
    }

    public double getFileSize() {
        return fileSize;
    }

    public String getFilename() {
        return filename;
    }

    public String getIp() {
        return ip;
    }
    
    public double getFileSizeInMegaByte()
    {
        double inbetweenresult = (fileSize / 1048576) * 100;
        inbetweenresult = Math.round(inbetweenresult);
        inbetweenresult = inbetweenresult/100;
        return inbetweenresult;
    }
    

    public ImageIcon getIcon() {
        return this.icon;
    }

    @Override
    public String toString() {
        return "File : " + this.filename + " with : " + this.getFileSizeInMegaByte() + "MB";
    }
    
    
    
    
}
