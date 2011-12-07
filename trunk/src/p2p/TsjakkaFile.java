package p2p;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TsjakkaFile implements Serializable {

    private String filename;
    private long fileSize;
    private String directory;
    private String ip;

    public TsjakkaFile(File file, String ip) {
        this.filename = file.getName();
        this.fileSize = file.length();
        this.directory = file.getPath();
        this.ip = ip;
    }
    
    public String getFilename() {
        return filename;
    }

    public long getFileSize() {
        return fileSize;
    }
    
    public String getDirectory() {
        return directory;
    }
    
    public String getIp() {
        return ip;
    }

    public double getFileSizeInMegaByte() {
        double inbetweenresult = (fileSize / 1048576) * 100;
        inbetweenresult = Math.round(inbetweenresult);
        inbetweenresult = inbetweenresult / 100;
        return inbetweenresult;
//        return (double)fileSize / (1024 * 1024);
    }

    @Override
    public String toString() {
        return "File: " + getFilename() + " | Size: " + getFileSizeInMegaByte() + " MB";
    }

    public String getExtension() {
        int dotpos = filename.lastIndexOf(".");
        return filename.substring(dotpos + 1).toLowerCase();
    }

    public static String generateMD5(File file) {
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
