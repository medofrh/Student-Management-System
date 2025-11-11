package portal;

import core.DataStore;
import domain.Course;
import domain.Grade;
import domain.Student;
import domain.Teacher;

import java.util.List;
import java.util.Scanner;

public class TeacherPortal {
    private final DataStore dataStore;
    private final Scanner scanner;

    public TeacherPortal(DataStore dataStore, Scanner scanner) {
        this.dataStore = dataStore;
        this.scanner = scanner;
    }

    public void open(String teacherId) {
        Teacher teacher = dataStore.getTeacher(teacherId);
        if (teacher == null) {
            System.out.println("Lehrkraft wurde nicht gefunden.");
            return;
        }
        while (true) {
            System.out.println("\n--- Dozentenmenue (" + teacher.getName() + ") ---");
            System.out.println("1) Eigene Kurse anzeigen");
            System.out.println("2) Pruefung anlegen");
            System.out.println("3) Note erfassen");
            System.out.println("4) Anwesenheit buchen");
            System.out.println("5) Stundenplan anpassen");
            System.out.println("0) Abmelden");
            int choice = readChoice();
            switch (choice) {
                case 1 -> showOwnCourses(teacherId);
                case 2 -> createExam(teacherId);
                case 3 -> addGrade(teacherId);
                case 4 -> markAttendance(teacherId);
                case 5 -> updateSchedule(teacherId);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Ungueltige Auswahl.");
            }
        }
    }

    private void showOwnCourses(String teacherId) {
        List<Course> courses = dataStore.getCoursesForTeacher(teacherId);
        if (courses.isEmpty()) {
            System.out.println("Keine Kurse zugewiesen.");
            return;
        }
        courses.forEach(course ->
                System.out.println("- " + course.getCourseName() + " (" + course.getUniqueCode() + ")"));
        List<String> schedule = dataStore.getScheduleForTeacher(teacherId);
        if (!schedule.isEmpty()) {
            System.out.println("Mein aktueller Stundenplan:");
            schedule.forEach(item -> System.out.println("* " + item));
        }
    }

    private void createExam(String teacherId) {
        System.out.print("Kurscode: ");
        String courseCode = scanner.nextLine().trim();
        if (!ownsCourse(teacherId, courseCode)) {
            System.out.println("Kurs ist dir nicht zugewiesen.");
            return;
        }
        System.out.print("Beschreibung der Pruefung: ");
        String description = scanner.nextLine().trim();
        dataStore.addExam(courseCode, description);
        System.out.println("Pruefung erstellt und Studierende informiert.");
    }

    private void addGrade(String teacherId) {
        System.out.print("Kurscode: ");
        String courseCode = scanner.nextLine().trim();
        if (!ownsCourse(teacherId, courseCode)) {
            System.out.println("Kurs ist dir nicht zugewiesen.");
            return;
        }
        System.out.print("Studenten-ID: ");
        String studentId = scanner.nextLine().trim();
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student wurde nicht gefunden.");
            return;
        }
        System.out.print("Note: ");
        double score;
        try {
            score = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            System.out.println("Ungueltiger Wert.");
            return;
        }
        Course course = dataStore.getCoursesForTeacher(teacherId).stream()
                .filter(c -> c.getUniqueCode().equalsIgnoreCase(courseCode))
                .findFirst()
                .orElse(null);
        if (course == null) {
            System.out.println("Kurs nicht gefunden.");
            return;
        }
        Grade grade = new Grade(student, course, score, "Manueller Eintrag", null);
        dataStore.addGrade(studentId, grade);
        System.out.println("Note gespeichert.");
    }

    private void markAttendance(String teacherId) {
        System.out.print("Kurscode: ");
        String courseCode = scanner.nextLine().trim();
        if (!ownsCourse(teacherId, courseCode)) {
            System.out.println("Kurs ist dir nicht zugewiesen.");
            return;
        }
        System.out.print("Studenten-ID: ");
        String studentId = scanner.nextLine().trim();
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student wurde nicht gefunden.");
            return;
        }
        System.out.print("Status eingeben (anwesend/abwesend): ");
        String status = scanner.nextLine().trim();
        dataStore.addAttendance(studentId, courseCode + " - " + status);
        System.out.println("Eintrag aktualisiert.");
    }

    private void updateSchedule(String teacherId) {
        System.out.print("Kurscode: ");
        String courseCode = scanner.nextLine().trim();
        if (!ownsCourse(teacherId, courseCode)) {
            System.out.println("Kurs ist dir nicht zugewiesen.");
            return;
        }
        System.out.print("Neuer Slot (z.B. Mittwoch 12:00 - MATH101): ");
        String slot = scanner.nextLine().trim();
        dataStore.updateScheduleForCourse(courseCode, slot);
        System.out.println("Stundenplan aktualisiert und Studierende informiert.");
    }

    private boolean ownsCourse(String teacherId, String courseCode) {
        return dataStore.getTeacher(teacherId) != null
                && dataStore.getTeacher(teacherId).getAssignedCourses().stream()
                .anyMatch(code -> code.equalsIgnoreCase(courseCode));
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
