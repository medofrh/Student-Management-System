package domain;

import java.util.Date;
import java.util.Map;

public class Attendance {
    private Date date;
    private Map<Student, Boolean> attendanceRecord;

    public Attendance(Date date, Map<Student, Boolean> attendanceRecord) {
        this.date = date;
        this.attendanceRecord = attendanceRecord;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<Student, Boolean> getAttendanceRecord() {
        return attendanceRecord;
    }

    public void setAttendanceRecord(Map<Student, Boolean> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }
}