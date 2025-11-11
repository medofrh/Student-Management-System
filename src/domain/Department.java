package domain;

import java.util.List;

public class Department {
    private String name;
    private List<Teacher> teachers;
    private List<Student> students;

    public Department(String name, List<Teacher> teachers, List<Student> students) {
        this.name = name;
        this.teachers = teachers;
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}