package domain;

import java.util.Date;

public class Grade {
    private Student student;
    private Course course;
    private double score;
    private String examType;
    private Date examDate;

    public Grade(Student student, Course course, double score, String examType, Date examDate) {
        this.student = student;
        this.course = course;
        this.score = score;
        this.examType = examType;
        this.examDate = examDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }
}