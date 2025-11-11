package service;

import java.time.LocalDateTime;

public class Logger {

    public void log(String action, String details) {
        System.out.println("[" + LocalDateTime.now() + "] ACTION: " + action + " DETAILS: " + details);
    }

    public void logLogin(String username) {
        log("LOGIN", "User " + username + " logged in.");
    }

    public void logUpdate(String entity, String entityId) {
        log("UPDATE", "Updated " + entity + " with ID " + entityId + ".");
    }

    public void logDeletion(String entity, String entityId) {
        log("DELETION", "Deleted " + entity + " with ID " + entityId + ".");
    }
}