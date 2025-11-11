package domain;

import java.util.Date;

public class Exam {
    private Date examDate;
    private String examType;
    private Course relatedCourse;

    public Exam(Date examDate, String examType, Course relatedCourse) {
        this.examDate = examDate;
        this.examType = examType;
        this.relatedCourse = relatedCourse;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Course getRelatedCourse() {
        return relatedCourse;
    }

    public void setRelatedCourse(Course relatedCourse) {
        this.relatedCourse = relatedCourse;
    }
}