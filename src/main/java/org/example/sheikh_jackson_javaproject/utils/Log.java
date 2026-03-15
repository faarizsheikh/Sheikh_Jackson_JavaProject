// Log.java:

package org.example.sheikh_jackson_javaproject.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Log {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Log() {}

    private static String time() {
        return LocalDateTime.now().format(FORMAT);
    }

    public static void error(String msg, Exception e) {
        System.err.println("[ERROR " + time() + "] " + msg + " | " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    public static void info(String msg) {
        System.out.println("[INFO  " + time() + "] " + msg);
    }

    public static void warn(String msg) {
        System.out.println("[WARN  " + time() + "] " + msg);
    }

}
