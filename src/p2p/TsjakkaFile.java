/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

/**
 *
 * @author Jimmy
 */
public class TsjakkaFile {
    
    private String filename;
    private int FileSize;
    private String ip;

    public TsjakkaFile(String filename, int FileSize, String ip) {
        this.filename = filename;
        this.FileSize = FileSize;
        this.ip = ip;
    }

    public TsjakkaFile(String filename, int FileSize) {
        this.filename = filename;
        this.FileSize = FileSize;
    }

    public TsjakkaFile() {
    }

    public int getFileSize() {
        return FileSize;
    }

    public String getFilename() {
        return filename;
    }

    public String getIp() {
        return ip;
    }
    
    
    
}
