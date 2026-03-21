// DBConfig.java:

package org.example.sheikh_jackson_javaproject.database;

import java.io.*;
import java.util.Properties;
import org.example.sheikh_jackson_javaproject.utils.Log;

/**
 * Utility class responsible for persisting and loading database configuration.

 * This class stores database credentials in a local properties file to enable
 * automatic reconnection on subsequent application launches.

 * Design Choice: Uses a simple file-based approach for persistence,
 * allowing lightweight configuration management without external dependencies.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class DBConfig {

    /**
     * Path to the properties file used for storing database configuration.
     */
    private static final String CONFIG_FILE = "dbconfig.properties";

    /**
     * Prevent instantiation of utility class.
     */
    private DBConfig() {}

    /**
     * Saves database credentials to a local properties file.
     * This allows the application to automatically reconnect using previously
     * stored configuration on future launches.
     *
     * @param user database username
     * @param pass database password
     * @param server database server address
     * @param db database name
     *
     * @implNote Credentials are stored in plain text (not encrypted).
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
     * Loads database configuration from the properties file.
     * If the configuration file does not exist or cannot be read,
     * the method returns {@code null}.
     *
     * @return Properties object containing database credentials,
     *         or {@code null} if configuration is unavailable
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
