package app.portal;

import app.core.DataStore;
import domain.Course;
import domain.Grade;
import domain.Student;

import java.util.List;
import java.util.Scanner;

public class StudentPortal {
    private final DataStore dataStore;
    private final Scanner scanner;

    public StudentPortal(DataStore dataStore, Scanner scanner) {
        this.dataStore = dataStore;
        this.scanner = scanner;
    }

    public void open(String studentId) {
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student wurde nicht gefunden.");
            return;
        }
        while (true) {
            System.out.println("\n--- Studentenmenue (" + student.getName() + ") ---");
            System.out.println("1) Kurse anzeigen");
            System.out.println("2) Noten anzeigen");
            System.out.println("3) Anwesenheit anzeigen");
            System.out.println("4) Stundenplan anzeigen");
            System.out.println("5) Leistungsnachweis drucken");
            System.out.println("6) Mitteilungen anzeigen");
            System.out.println("0) Abmelden");
            int choice = readChoice();
            switch (choice) {
                case 1 -> showCourses(studentId);
                case 2 -> showGrades(studentId);
                case 3 -> showAttendance(studentId);
                case 4 -> showSchedule(studentId);
                case 5 -> printReport(studentId);
                case 6 -> showNotifications(studentId);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Ungueltige Auswahl.");
            }
        }
    }

    private void showCourses(String studentId) {
        List<Course> courses = dataStore.getCoursesForStudent(studentId);
        if (courses.isEmpty()) {
            System.out.println("Keine Kurse gefunden.");
            return;
        }
        courses.forEach(course ->
                System.out.println("- " + course.getCourseName() + " (" + course.getUniqueCode() + ")"));
    }

    private void showGrades(String studentId) {
        List<Grade> grades = dataStore.getGradesForStudent(studentId);
        if (grades.isEmpty()) {
            System.out.println("Noch keine Noten vorhanden.");
            return;
        }
        grades.forEach(grade ->
                System.out.println("- " + grade.getCourse().getUniqueCode() + ": " + grade.getScore() + " (" + grade.getExamType() + ")"));
    }

    private void showAttendance(String studentId) {
        List<String> attendance = dataStore.getAttendanceForStudent(studentId);
        if (attendance.isEmpty()) {
            System.out.println("Keine Anwesenheitsdaten vorhanden.");
            return;
        }
        attendance.forEach(record -> System.out.println("- " + record));
    }

    private void showSchedule(String studentId) {
        List<String> schedule = dataStore.getScheduleForStudent(studentId);
        if (schedule.isEmpty()) {
            System.out.println("Kein Stundenplan vorhanden.");
            return;
        }
        schedule.forEach(item -> System.out.println("- " + item));
    }

    private void printReport(String studentId) {
        Student student = dataStore.getStudent(studentId);
        List<Grade> grades = dataStore.getGradesForStudent(studentId);
        double average = grades.isEmpty() ? 0 : grades.stream().mapToDouble(Grade::getScore).average().orElse(0);
        System.out.println("\nLeistungsuebersicht fuer " + student.getName());
        showGrades(studentId);
        System.out.println("Durchschnitt: " + String.format("%.2f", average));
        System.out.println("Anzahl Anwesenheitsbuchungen: " + dataStore.getAttendanceForStudent(studentId).size());
    }

    private void showNotifications(String studentId) {
        List<String> notifications = dataStore.getNotificationsForStudent(studentId);
        if (notifications.isEmpty()) {
            System.out.println("Keine neuen Mitteilungen.");
            return;
        }
        notifications.forEach(note -> System.out.println("- " + note));
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
