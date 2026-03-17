// Log.java:

package org.example.sheikh_jackson_javaproject.utils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public final class Log {

    private static final String FILE = "game_log.txt";
    private static final long MAX_SIZE = 1024 * 1024;

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Log() {}

    private static String time() {
        return LocalDateTime.now().format(FORMAT);
    }

    private static void writeToFile(String msg) {
        try {
            File file = new File(FILE);

            if (file.exists() && file.length() > MAX_SIZE) new FileWriter(FILE, false).close();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
                bw.write(msg);
                bw.newLine();
            }

        } catch (IOException e) {
            System.err.println("Log file write failed: " + e.getMessage());
        }
    }

    public static void action(String action, String details) {
        String msg = "[" + time() + "] [" + action + "] " + details;
        System.out.println(msg);
        writeToFile(msg);
    }

    public static void info(String msg) {
        System.out.println("[INFO  " + time() + "] " + msg);
    }

    public static void warn(String msg) {
        System.out.println("[WARN  " + time() + "] " + msg);
    }

    public static void error(String msg, Exception e) {
        System.err.println("[ERROR " + time() + "] " + msg + " | " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}
