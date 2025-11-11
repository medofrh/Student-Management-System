package domain;

import java.util.Date;
import java.util.Map;

public class Schedule {
    private Map<Date, Course> timetable;
    private Classroom classroom;
    private Teacher teacher;

    public Schedule(Map<Date, Course> timetable, Classroom classroom, Teacher teacher) {
        this.timetable = timetable;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public Map<Date, Course> getTimetable() {
        return timetable;
    }

    public void setTimetable(Map<Date, Course> timetable) {
        this.timetable = timetable;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}