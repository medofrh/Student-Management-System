package app.core;

import app.observer.Observer;
import domain.Admin;
import domain.Course;
import domain.Grade;
import domain.Role;
import domain.Student;
import domain.Teacher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataStore {
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Teacher> teachers = new HashMap<>();
    private final Map<String, Admin> admins = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<String, List<Grade>> gradesByStudent = new HashMap<>();
    private final Map<String, List<String>> attendanceByStudent = new HashMap<>();
    private final Map<String, List<String>> scheduleByStudent = new HashMap<>();
    private final Map<String, List<String>> notificationsByStudent = new HashMap<>();
    private final Map<String, List<String>> teacherSchedules = new HashMap<>();
    private final Map<String, List<String>> examsByCourse = new HashMap<>();
    private final Map<String, Account> accounts = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    public DataStore() {
        seed();
    }

    private void seed() {
        Teacher math = new Teacher("Ahmad Saleh", "Mathematics", new ArrayList<>(List.of("MATH101", "STAT201")));
        Teacher cs = new Teacher("Laila Nour", "Computer Science", new ArrayList<>(List.of("CS105")));
        teachers.put("T-1", math);
        teachers.put("T-2", cs);

        Student sara = new Student("Sara Khaled", "S-1", "CS-1", new ArrayList<>(List.of("MATH101", "CS105")));
        Student omar = new Student("Omar Hani", "S-2", "CS-1", new ArrayList<>(List.of("MATH101", "STAT201", "CS105")));
        students.put(sara.getId(), sara);
        students.put(omar.getId(), omar);

        Admin admin = new Admin("director", "admin123");
        admins.put("A-1", admin);

        Course calculus = new Course("Calculus I", "MATH101", math);
        Course statistics = new Course("Probability & Statistics", "STAT201", math);
        Course programming = new Course("Programming 101", "CS105", cs);
        courses.put(calculus.getUniqueCode(), calculus);
        courses.put(statistics.getUniqueCode(), statistics);
        courses.put(programming.getUniqueCode(), programming);

        addGrade("S-1", new Grade(sara, calculus, 90, "Midterm", null));
        addGrade("S-1", new Grade(sara, programming, 95, "Project", null));
        addGrade("S-2", new Grade(omar, calculus, 76, "Quiz", null));

        attendanceByStudent.put("S-1", new ArrayList<>(List.of("MATH101 - anwesend", "CS105 - abwesend")));
        attendanceByStudent.put("S-2", new ArrayList<>(List.of("MATH101 - anwesend", "STAT201 - anwesend", "CS105 - abwesend")));

        scheduleByStudent.put("S-1", new ArrayList<>(List.of("Sonntag 09:00 - MATH101", "Montag 11:00 - CS105")));
        scheduleByStudent.put("S-2", new ArrayList<>(List.of("Sonntag 09:00 - MATH101", "Dienstag 10:00 - STAT201", "Montag 11:00 - CS105")));

        teacherSchedules.put("T-1", new ArrayList<>(List.of("Sonntag 09:00 - MATH101", "Dienstag 10:00 - STAT201")));
        teacherSchedules.put("T-2", new ArrayList<>(List.of("Montag 11:00 - CS105")));

        notificationsByStudent.put("S-1", new ArrayList<>(List.of("Zwischenergebnisse sind jetzt verfuegbar", "Erinnerung: Projektabgabe morgen")));
        notificationsByStudent.put("S-2", new ArrayList<>(List.of("Neuer kurzer Statistiktest angekuendigt")));

        registerAccount("sara", "123", Role.STUDENT, "S-1");
        registerAccount("omar", "123", Role.STUDENT, "S-2");
        registerAccount("ahmad", "teach", Role.TEACHER, "T-1");
        registerAccount("laila", "teach", Role.TEACHER, "T-2");
        registerAccount("admin", "admin", Role.ADMIN, "A-1");
    }

    private void registerAccount(String username, String password, Role role, String referenceId) {
        accounts.put(username, new Account(username, password, role, referenceId));
    }

    public Optional<Account> findAccount(String username) {
        return Optional.ofNullable(accounts.get(username));
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }

    public Teacher getTeacher(String teacherId) {
        return teachers.get(teacherId);
    }

    public Admin getAdmin(String adminId) {
        return admins.get(adminId);
    }

    public List<Course> getCoursesForStudent(String studentId) {
        Student student = students.get(studentId);
        if (student == null) {
            return List.of();
        }
        List<Course> result = new ArrayList<>();
        for (String code : student.getEnrolledCourses()) {
            Course course = courses.get(code);
            if (course != null) {
                result.add(course);
            }
        }
        return result;
    }

    public List<Course> getCoursesForTeacher(String teacherId) {
        Teacher teacher = teachers.get(teacherId);
        if (teacher == null) {
            return List.of();
        }
        List<Course> result = new ArrayList<>();
        for (String code : teacher.getAssignedCourses()) {
            Course course = courses.get(code);
            if (course != null) {
                result.add(course);
            }
        }
        return result;
    }

    public List<Grade> getGradesForStudent(String studentId) {
        return gradesByStudent.getOrDefault(studentId, List.of());
    }

    public List<String> getAttendanceForStudent(String studentId) {
        return attendanceByStudent.getOrDefault(studentId, List.of());
    }

    public List<String> getScheduleForStudent(String studentId) {
        return scheduleByStudent.getOrDefault(studentId, List.of());
    }

    public List<String> getNotificationsForStudent(String studentId) {
        return notificationsByStudent.getOrDefault(studentId, List.of());
    }

    public List<String> getScheduleForTeacher(String teacherId) {
        return teacherSchedules.getOrDefault(teacherId, List.of());
    }

    public void addGrade(String studentId, Grade grade) {
        gradesByStudent.computeIfAbsent(studentId, id -> new ArrayList<>()).add(grade);
        addNotification(studentId, "Neue Note fuer " + grade.getCourse().getUniqueCode() + " gespeichert");
        notifyObservers("GRADE_ADDED", studentId + " / " + grade.getCourse().getUniqueCode());
    }

    public void addAttendance(String studentId, String record) {
        attendanceByStudent.computeIfAbsent(studentId, id -> new ArrayList<>()).add(record);
        addNotification(studentId, "Aktualisierte Anwesenheit: " + record);
        notifyObservers("ATTENDANCE_RECORDED", studentId + " -> " + record);
    }

    public void updateScheduleForCourse(String courseCode, String slotDescription) {
        for (Student student : students.values()) {
            if (student.getEnrolledCourses().contains(courseCode)) {
                List<String> schedule = scheduleByStudent.computeIfAbsent(student.getId(), id -> new ArrayList<>());
                schedule.removeIf(item -> item.contains(courseCode));
                schedule.add(slotDescription);
                addNotification(student.getId(), "Stundenplan fuer " + courseCode + " wurde angepasst");
            }
        }
        for (Map.Entry<String, Teacher> entry : teachers.entrySet()) {
            if (entry.getValue().getAssignedCourses().contains(courseCode)) {
                List<String> schedule = teacherSchedules.computeIfAbsent(entry.getKey(), id -> new ArrayList<>());
                schedule.removeIf(item -> item.contains(courseCode));
                schedule.add(slotDescription);
            }
        }
        notifyObservers("SCHEDULE_UPDATED", courseCode + " -> " + slotDescription);
    }

    public void addExam(String courseCode, String description) {
        examsByCourse.computeIfAbsent(courseCode, code -> new ArrayList<>()).add(description);
        for (Student student : students.values()) {
            if (student.getEnrolledCourses().contains(courseCode)) {
                addNotification(student.getId(), "Neue Pruefung fuer " + courseCode + ": " + description);
            }
        }
        notifyObservers("EXAM_ADDED", courseCode + " :: " + description);
    }

    public List<String> getExamsForCourse(String courseCode) {
        return examsByCourse.getOrDefault(courseCode, List.of());
    }

    public boolean addAccount(String username, String password, Role role, String referenceId) {
        if (accounts.containsKey(username)) {
            return false;
        }
        registerAccount(username, password, role, referenceId);
        notifyObservers("ACCOUNT_ADDED", username + " (" + role + ")");
        return true;
    }

    public boolean updatePassword(String username, String newPassword) {
        Account account = accounts.get(username);
        if (account == null) {
            return false;
        }
        accounts.put(username, new Account(username, newPassword, account.role(), account.referenceId()));
        notifyObservers("ACCOUNT_PASSWORD_UPDATED", username);
        return true;
    }

    public boolean deleteAccount(String username) {
        boolean removed = accounts.remove(username) != null;
        if (removed) {
            notifyObservers("ACCOUNT_DELETED", username);
        }
        return removed;
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }

    public boolean addCourse(String code, String name, String teacherId) {
        if (courses.containsKey(code)) {
            return false;
        }
        Teacher teacher = teachers.get(teacherId);
        if (teacher == null) {
            return false;
        }
        Course course = new Course(name, code, teacher);
        courses.put(code, course);
        teacher.getAssignedCourses().add(code);
        notifyObservers("COURSE_ADDED", code + " -> " + name);
        return true;
    }

    public boolean deleteCourse(String code) {
        Course removed = courses.remove(code);
        if (removed == null) {
            return false;
        }
        for (Teacher teacher : teachers.values()) {
            teacher.getAssignedCourses().removeIf(courseCode -> courseCode.equalsIgnoreCase(code));
        }
        for (Student student : students.values()) {
            student.getEnrolledCourses().removeIf(courseCode -> courseCode.equalsIgnoreCase(code));
        }
        gradesByStudent.values().forEach(list -> list.removeIf(grade -> grade.getCourse().getUniqueCode().equalsIgnoreCase(code)));
        notifyObservers("COURSE_DELETED", code);
        return true;
    }

    public List<String> describeAccounts() {
        List<String> lines = new ArrayList<>();
        for (Account account : accounts.values()) {
            lines.add(account.username() + " - " + account.role() + " -> " + account.referenceId());
        }
        return lines;
    }

    public void addNotification(String studentId, String message) {
        notificationsByStudent.computeIfAbsent(studentId, id -> new ArrayList<>()).add(message);
    }

    public record Account(String username, String password, Role role, String referenceId) {
    }

    private void notifyObservers(String event, String details) {
        for (Observer observer : observers) {
            observer.onEvent(event, details);
        }
    }
}
