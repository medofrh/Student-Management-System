package domain;

public class Course {
    private String courseName;
    private String uniqueCode;
    private String assignedTeacher;

    public Course(String courseName, String uniqueCode, String assignedTeacher) {
        this.courseName = courseName;
        this.uniqueCode = uniqueCode;
        this.assignedTeacher = assignedTeacher;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(String assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }
}