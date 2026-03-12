// DBConfig.java:

package org.example.sheikh_jackson_javaproject.database;

import java.io.*;
import java.util.Properties;

public class DBConfig {

    private static final String CONFIG_FILE = "dbconfig.properties";

    public static void saveConfig(String user, String pass, String server, String db) throws IOException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        props.setProperty("server", server);
        props.setProperty("database", db);

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Database Login Credentials");
        }
    }

    public static Properties loadConfig() throws IOException {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) return null;

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
        }
        return props;
    }

    public static boolean configExists() { // Maybe remove?
        return new File(CONFIG_FILE).exists();
    }
}