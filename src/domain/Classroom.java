package domain;

import java.util.List;

public class Classroom {
    private String uniqueNumber;
    private List<Student> students;
    private Teacher supervisingTeacher;

    public Classroom(String uniqueNumber, List<Student> students, Teacher supervisingTeacher) {
        this.uniqueNumber = uniqueNumber;
        this.students = students;
        this.supervisingTeacher = supervisingTeacher;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getSupervisingTeacher() {
        return supervisingTeacher;
    }

    public void setSupervisingTeacher(Teacher supervisingTeacher) {
        this.supervisingTeacher = supervisingTeacher;
    }
}