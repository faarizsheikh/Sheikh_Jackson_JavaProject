// DBConfig.java:

package org.example.sheikh_jackson_javaproject.database;

import java.io.*;
import java.util.Properties;
import org.example.sheikh_jackson_javaproject.utils.Log;

/**
 * Handles saving and loading database configuration from a file.
 * Design Choice:
 * Allows persistent login credentials for automatic reconnection.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class DBConfig {

    private static final String CONFIG_FILE = "dbconfig.properties";

    /**
     * Saves database credentials to a configuration file.
     *
     * @param user username
     * @param pass password
     * @param server server address
     * @param db database name
     */
    public static void saveConfig(String user, String pass, String server, String db) {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        props.setProperty("server", server);
        props.setProperty("database", db);

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Database Login Credentials");
            Log.info("Database config saved to " + CONFIG_FILE);

        } catch (IOException e) {
            Log.error("Failed to save database config.", e);
        }
    }

    /**
     * Loads database credentials from configuration file.
     *
     * @return Properties object containing credentials, or null if not found
     */
    public static Properties loadConfig() {
        File file = new File(CONFIG_FILE);

        if (!file.exists()) {
            Log.warn("Database config file not found: " + CONFIG_FILE);
            return null;
        }
        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
            Log.info("Database config loaded from " + CONFIG_FILE);

        } catch (IOException e) {
            Log.error("Failed to load database config.", e);
            return null;
        }
        return props;
    }
}
