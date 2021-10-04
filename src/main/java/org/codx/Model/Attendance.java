package org.codx.Model;

public class Attendance {

    private long attendance_id;
    private long event_id;
    private long student_id;

    private String morning_entrance;
    private String afternoon_entrance;
    private String morning_exit;
    private String afternoon_exit;


    public Attendance(long attendance_id) {
        this.attendance_id = attendance_id;
    }

    public long getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(long attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getMorning_entrance() {
        return morning_entrance;
    }

    public void setMorning_entrance(String morning_entrance) {
        this.morning_entrance = morning_entrance;
    }

    public String getAfternoon_entrance() {
        return afternoon_entrance;
    }

    public void setAfternoon_entrance(String afternoon_entrance) {
        this.afternoon_entrance = afternoon_entrance;
    }

    public String getMorning_exit() {
        return morning_exit;
    }

    public void setMorning_exit(String morning_exit) {
        this.morning_exit = morning_exit;
    }

    public String getAfternoon_exit() {
        return afternoon_exit;
    }

    public void setAfternoon_exit(String afternoon_exit) {
        this.afternoon_exit = afternoon_exit;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }
}
