package domain;

import java.util.List;

public class Teacher {
    private String name;
    private String subjectSpecialization;
    private List<String> assignedCourses;

    public Teacher(String name, String subjectSpecialization, List<String> assignedCourses) {
        this.name = name;
        this.subjectSpecialization = subjectSpecialization;
        this.assignedCourses = assignedCourses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectSpecialization() {
        return subjectSpecialization;
    }

    public void setSubjectSpecialization(String subjectSpecialization) {
        this.subjectSpecialization = subjectSpecialization;
    }

    public List<String> getAssignedCourses() {
        return assignedCourses;
    }

    public void setAssignedCourses(List<String> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }
}