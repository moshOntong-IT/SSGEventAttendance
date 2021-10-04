package org.codx.Model;

import Builder.EventConvert;
import org.codx.Services.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class EventInfo extends EventConvert {
    private long event_id;
    private String event_name;

    private String event_date;
    private String event_desc;
    private String event_author;
    private String morning_begin;
    private String morning_end;
    private String afternoon_begin;
    private String afternoon_end;
    private String eventSchoolYear;
    private int active;
    private int absent;

    private String todayDate;
    private String durationDate;

    public EventInfo(long event_id, String event_name, String event_date, String event_desc,
                     String event_author, String morning_begin, String morning_end,
                     String afternoon_begin, String afternoon_end, String eventSchoolYear) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_desc = event_desc;
        this.event_author = event_author;
        this.morning_begin = morning_begin;
        this.morning_end = morning_end;
        this.afternoon_begin = afternoon_begin;
        this.afternoon_end = afternoon_end;
        this.eventSchoolYear = eventSchoolYear;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_author() {
        return event_author;
    }

    public void setEvent_author(String event_author) {
        this.event_author = event_author;
    }

    public String getMorning_begin() {
        return morning_begin;
    }

    public void setMorning_begin(String morning_begin) {
        this.morning_begin = morning_begin;
    }

    public String getMorning_end() {
        return morning_end;
    }

    public void setMorning_end(String morning_end) {
        this.morning_end = morning_end;
    }

    public String getAfternoon_begin() {
        return afternoon_begin;
    }

    public void setAfternoon_begin(String afternoon_begin) {
        this.afternoon_begin = afternoon_begin;
    }

    public String getAfternoon_end() {
        return afternoon_end;
    }

    public void setAfternoon_end(String afternoon_end) {
        this.afternoon_end = afternoon_end;
    }

    public String getEventSchoolYear() {
        return eventSchoolYear;
    }

    public void setEventSchoolYear(String eventSchoolYear) {
        this.eventSchoolYear = eventSchoolYear;
    }

    public int getActive(long id,Connection conn){


        active = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement("Select * from attendance where event_id = ?");
            stmt.setLong(1,id);

            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                active++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return active;
    }

    public int getAbsent(String eventSchoolYear, Connection conn){


        try {
            PreparedStatement stmt = conn.prepareStatement("Select count(u.student_id) from student_info u;");
            ResultSet rst = stmt.executeQuery();
            if(rst.next()){
                absent = rst.getInt("count") - active;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absent;
    }
    public String getDurationDate(){
        parseDate((date)-> this.todayDate = date);
        try {
            getDuration(todayDate,event_date,(date)-> this.durationDate = date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return durationDate;
    }




}
