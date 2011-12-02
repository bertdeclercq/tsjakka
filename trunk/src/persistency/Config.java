package persistency;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Config class gives the capability to read and write key/value pairs from and to a config file.
 */
public class Config {

    private final static String CONFIG_FILE = "config";
    private static Config instance;
    private static Properties properties;
    
    private Config() {
        properties = new Properties();
    }

    /**
     * Gets an instance from this class.
     * 
     * @return an instance from this class
     */
    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
    
    /**
     * Searches for the value with the specified key in the config file.
     * 
     * @param key the config key
     * 
     * @return the value from the config file with the specified key value
     */
    public String get(String key) {
        readConfig();
        return properties.getProperty(key);
    }
    
    /**
     * Stores the specified key/value pair in the config file.
     * 
     * @param key the config key
     * @param value the config value
     */
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
