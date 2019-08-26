package com.aszoke.assignment.issuesubmitter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class Logger {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void logInfo(final Object o) {
        System.out.println(LocalDateTime.now() + " [INFO] " + o);
    }

    public static void logInfo(final String message) {
        System.out.println(LocalDateTime.now() + " [INFO] " + message);
    }

    public static void logError(final String message) {
        System.out.println(LocalDateTime.now() + " [ERROR] " + message);
    }

    public static String toJson(final Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logError("Error while serializing issue to JSON.");
            throw new RuntimeException(e);
        }
    }

}
