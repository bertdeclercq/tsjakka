package persistency;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    private final static String CONFIG_FILE = "config";
    private static Config instance;
    private static Properties properties;

    private Config() {
        properties = new Properties();
    }

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
    
    public String get(String key) {
        readConfig();
        return properties.getProperty(key);
    }
    
    public void set(String key, String value) {
        properties.setProperty(key, value);
        writeConfig();
    }
    
    private static void readConfig() {
        try {
            FileInputStream in = new FileInputStream(CONFIG_FILE);
            properties.load(in);
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void writeConfig() {
        try {
            FileOutputStream out = new FileOutputStream(CONFIG_FILE);
            properties.store(out, null);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
