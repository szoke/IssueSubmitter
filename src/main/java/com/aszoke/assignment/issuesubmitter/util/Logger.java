package com.aszoke.assignment.issuesubmitter.util;

import java.time.LocalDateTime;

public class Logger {

    public static void logInfo(final Object o) {
        System.out.println(LocalDateTime.now() + " [INFO] " + o);
    }

    public static void logInfo(final String message) {
        System.out.println(LocalDateTime.now() + " [INFO] " + message);
    }

    public static void logError(final String message) {
        System.out.println(LocalDateTime.now() + " [ERROR] " + message);
    }
}
