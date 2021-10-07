package org.codx.Model;

public class StudentAttendance {
    private String morningEntrance;
    private String morningExit;
    private String afternoonEntrance;
    private String afternoonExit;
    private long event_id;
    private long attendance_id;
    private long user_id;

    public StudentAttendance() {
    }

    public StudentAttendance(String morningEntrance, String morningExit, String afternoonEntrance, String afternoonExit, long event_id, long attendance_id, long user_id) {

        this.morningEntrance = morningEntrance;
        this.morningExit = morningExit;
        this.afternoonEntrance = afternoonEntrance;
        this.afternoonExit = afternoonExit;
        this.event_id = event_id;
        this.attendance_id = attendance_id;
        this.user_id = user_id;
    }

    public String getMorningEntrance() {
        return morningEntrance;
    }

    public void setMorningEntrance(String morningEntrance) {
        this.morningEntrance = morningEntrance;
    }

    public String getMorningExit() {
        return morningExit;
    }

    public void setMorningExit(String morningExit) {
        this.morningExit = morningExit;
    }

    public String getAfternoonEntrance() {
        return afternoonEntrance;
    }

    public void setAfternoonEntrance(String afternoonEntrance) {
        this.afternoonEntrance = afternoonEntrance;
    }

    public String getAfternoonExit() {
        return afternoonExit;
    }

    public void setAfternoonExit(String afternoonExit) {
        this.afternoonExit = afternoonExit;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public long getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(long attendance_id) {
        this.attendance_id = attendance_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
