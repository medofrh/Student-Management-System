package domain;

import java.util.List;

public class Student {
    private String name;
    private String id;
    private String studentClass;
    private List<String> enrolledCourses;

    public Student(String name, String id, String studentClass, List<String> enrolledCourses) {
        this.name = name;
        this.id = id;
        this.studentClass = studentClass;
        this.enrolledCourses = enrolledCourses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}