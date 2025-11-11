package cli;

import core.AuthService;
import core.DataStore;
import portal.AdminPortal;
import portal.StudentPortal;
import portal.TeacherPortal;
import domain.Role;

import java.util.Optional;
import java.util.Scanner;

public class MenuCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService;
    private final StudentPortal studentPortal;
    private final TeacherPortal teacherPortal;
    private final AdminPortal adminPortal;

    public MenuCLI(AuthService authService, DataStore dataStore) {
        this.authService = authService;
        this.studentPortal = new StudentPortal(dataStore, scanner);
        this.teacherPortal = new TeacherPortal(dataStore, scanner);
        this.adminPortal = new AdminPortal(dataStore, scanner);
    }

    public void start() {
        System.out.println("Willkommen im Studentenverwaltungssystem");
        while (true) {
            System.out.println("\nBitte Rolle auswaehlen:");
            System.out.println("1) Student");
            System.out.println("2) Lehrer");
            System.out.println("3) Admin");
            System.out.println("0) Beenden");
            int choice = readChoice();
            switch (choice) {
                case 1 -> handleLogin(Role.STUDENT);
                case 2 -> handleLogin(Role.TEACHER);
                case 3 -> handleLogin(Role.ADMIN);
                case 0 -> {
                    System.out.println("Auf Wiedersehen!");
                    return;
                }
                default -> System.out.println("Ungueltige Auswahl.");
            }
        }
    }

    private void handleLogin(Role role) {
        System.out.print("Benutzername: ");
        String username = scanner.nextLine().trim();
        System.out.print("Passwort: ");
        String password = scanner.nextLine().trim();
        Optional<String> referenceId = authService.authenticate(role, username, password);
        if (referenceId.isEmpty()) {
            System.out.println("Anmeldedaten ungueltig.");
            return;
        }
        System.out.println("Erfolgreich angemeldet!");
        switch (role) {
            case STUDENT -> studentPortal.open(referenceId.get());
            case TEACHER -> teacherPortal.open(referenceId.get());
            case ADMIN -> adminPortal.open(referenceId.get());
        }
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
