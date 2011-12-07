package p2p;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A TsjakkaFile is a file located at a specific computer at a specific location. This class provides the capabilities to create a TsjakkaFile object and get the different parameters from it.
 */
public class TsjakkaFile extends File implements Serializable {

    private File file;
    private String ip;

    /**
     * Creates a TsjakkaFile located at specified path.
     * 
     * @param path the file of which you want to make a TsjakkaFile
     */
    public TsjakkaFile(String path) {
        super(path);
        try {
            this.ip = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            //TODO exception here
            Logger.getLogger(TsjakkaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Return the name of this TsjakkaFile.
     * 
     * @return the name
     */
    public String getFilename() {
        return super.getName();
    }
    
    /**
     * return the extension of this TsjakkaFile.
     * 
     * @return the extension
     */
    public String getExtension() {
        int dotpos = getFilename().lastIndexOf(".");
        return getFilename().substring(dotpos + 1).toLowerCase();
    }    

    /**
     * return the directory of where this TsjakkaFile is located.
     * 
     * @return the directory
     */
    public String getDirectory() {
        return super.getPath();
    }
    
    /**
     * return the size in bytes of this TsjakkaFile.
     * 
     * @return the size in bytes
     */
    public long getFileSize() {
        return super.length();
    }

    /**
     * return the size in megabytes of this TsjakkaFile.
     * 
     * @return the size in megabytes
     */
    public double getFileSizeInMegaByte() {
        return (double)getFileSize() / (1024 * 1024);
    }
    
    /**
     * return the ip address of the computer on which it is located.
     * @return 
     */
    public String getIp() {
        return ip;
    }

    /**
     * Returns a string representation of this TsjakkaFile. The string representation consists of the filename, followed by the size in megabytes enclosed in square brackets ("[ ]").
     * 
     * @return a string representation of this TsjakkaFile 
     */
    @Override
    public String toString() {
        return getFilename() + " [" + getFileSizeInMegaByte() + " MB]";
    }

    /**
     * Calculates the MD5 hash of this TsjakkaFile and returns it as a string.
     * 
     * @return the MD5 hash
     */
    public String generateMD5() {
        MessageDigest digest = null;
        InputStream is = null;
        String output = "";
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("digest faalt");
            Logger.getLogger(TsjakkaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println("inputstream faalt");
            Logger.getLogger(TsjakkaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] buffer = new byte[8192];
        int read;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            output = bigInt.toString(16);
            System.out.println("MD5: " + output);
        } catch (IOException e) {
            throw new RuntimeException("Unable to process file for MD5", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
            }
        }
        return output;
    }
}
