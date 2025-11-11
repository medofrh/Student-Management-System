package portal;

import core.DataStore;
import domain.Course;
import domain.Role;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminPortal {
    private final DataStore dataStore;
    private final Scanner scanner;

    public AdminPortal(DataStore dataStore, Scanner scanner) {
        this.dataStore = dataStore;
        this.scanner = scanner;
    }

    public void open(String adminId) {
        while (true) {
            System.out.println("\n--- Adminmenue ---");
            System.out.println("1) Benutzer anzeigen");
            System.out.println("2) Benutzer anlegen");
            System.out.println("3) Passwort aktualisieren");
            System.out.println("4) Benutzer loeschen");
            System.out.println("5) Kurse anzeigen");
            System.out.println("6) Kurs anlegen");
            System.out.println("7) Kurs loeschen");
            System.out.println("0) Abmelden");
            int choice = readChoice();
            switch (choice) {
                case 1 -> listAccounts();
                case 2 -> addUser();
                case 3 -> updatePassword();
                case 4 -> deleteUser();
                case 5 -> listCourses();
                case 6 -> addCourse();
                case 7 -> deleteCourse();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Ungueltige Auswahl.");
            }
        }
    }

    private void listAccounts() {
        List<String> accounts = dataStore.describeAccounts();
        accounts.forEach(line -> System.out.println("- " + line));
        if (accounts.isEmpty()) {
            System.out.println("Keine Benutzer vorhanden.");
        }
    }

    private void addUser() {
        System.out.print("Benutzername: ");
        String username = scanner.nextLine().trim();
        System.out.print("Passwort: ");
        String password = scanner.nextLine().trim();
        System.out.print("Rolle (STUDENT/TEACHER/ADMIN): ");
        Role role;
        try {
            role = Role.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            System.out.println("Unbekannte Rolle.");
            return;
        }
        System.out.print("Referenz-ID: ");
        String referenceId = scanner.nextLine().trim();
        boolean added = dataStore.addAccount(username, password, role, referenceId);
        System.out.println(added ? "Benutzer angelegt." : "Benutzername bereits vergeben.");
    }

    private void updatePassword() {
        System.out.print("Benutzername: ");
        String username = scanner.nextLine().trim();
        System.out.print("Neues Passwort: ");
        String password = scanner.nextLine().trim();
        boolean updated = dataStore.updatePassword(username, password);
        System.out.println(updated ? "Passwort aktualisiert." : "Benutzer nicht gefunden.");
    }

    private void deleteUser() {
        System.out.print("Benutzername: ");
        String username = scanner.nextLine().trim();
        boolean deleted = dataStore.deleteAccount(username);
        System.out.println(deleted ? "Benutzer geloescht." : "Benutzer nicht gefunden.");
    }

    private void listCourses() {
        Collection<Course> list = dataStore.getAllCourses();
        if (list.isEmpty()) {
            System.out.println("Keine Kurse vorhanden.");
            return;
        }
        list.forEach(course ->
                System.out.println("- " + course.getCourseName() + " (" + course.getUniqueCode() + ")"));
    }

    private void addCourse() {
        System.out.print("Kurscode: ");
        String code = scanner.nextLine().trim();
        System.out.print("Kursname: ");
        String name = scanner.nextLine().trim();
        System.out.print("Verantwortliche Lehrer-ID: ");
        String teacherId = scanner.nextLine().trim();
        boolean added = dataStore.addCourse(code, name, teacherId);
        System.out.println(added ? "Kurs angelegt." : "Kurs konnte nicht angelegt werden.");
    }

    private void deleteCourse() {
        System.out.print("Kurscode: ");
        String code = scanner.nextLine().trim();
        boolean deleted = dataStore.deleteCourse(code);
        System.out.println(deleted ? "Kurs geloescht." : "Kurs nicht gefunden.");
    }

    private int readChoice() {
        try {
            System.out.print("Auswahl: ");
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
