package domain;

import java.util.List;

public class ReportCard {
    private Student student;
    private List<Grade> grades;
    private Attendance attendance;

    public ReportCard(Student student, List<Grade> grades, Attendance attendance) {
        this.student = student;
        this.grades = grades;
        this.attendance = attendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public void generateSummary() {
        // Logic to generate performance summary
    }
}