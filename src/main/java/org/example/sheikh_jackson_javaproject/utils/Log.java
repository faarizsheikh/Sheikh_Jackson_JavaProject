// Log.java:

package org.example.sheikh_jackson_javaproject.utils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Provides logging functionality for the application.
 * Logs messages to the console and optionally to a file with timestamps.
 * Supports action, info, warning, and error logging levels.
 * Design Choice:
 * Centralized logging improves debugging, tracking, and maintainability.
 * File size is limited to prevent excessive storage usage.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public final class Log {

    private static final String FILE = "game_log.txt";
    private static final long MAX_SIZE = 1024 * 1024;

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Private constructor to prevent instantiation.
     */
    private Log() {}

    /**
     * Gets the current timestamp.
     *
     * @return formatted current date and time
     */
    private static String time() {
        return LocalDateTime.now().format(FORMAT);
    }

    /**
     * Writes a log message to file.
     *
     * @param msg message to log to the console and to the log file
     */
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

    /**
     * Logs a detailed action.
     *
     * @param action action type
     * @param details additional details to log to the log file
     */
    public static void action(String action, String details) {
        String msg = "[" + time() + "] [" + action + "] " + details;
        System.out.println(msg);
        writeToFile(msg);
    }

    /**
     * Logs an informational message.
     *
     * @param msg message to log to the console
     */
    public static void info(String msg) {
        System.out.println("[INFO  " + time() + "] " + msg);
    }

    /**
     * Logs a warning message.
     *
     * @param msg message to log to the console
     */
    public static void warn(String msg) {
        System.out.println("[WARN  " + time() + "] " + msg);
    }

    /**
     * Logs an error message with exception details.
     *
     * @param msg error message to log to the console
     * @param e exception thrown
     */
    public static void error(String msg, Exception e) {
        System.err.println("[ERROR " + time() + "] " + msg + " | " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}
