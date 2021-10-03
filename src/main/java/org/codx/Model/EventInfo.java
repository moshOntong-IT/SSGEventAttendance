package org.codx.Model;

public class EventInfo {
    private String event_id;
    private String event_name;

    private String event_date;
    private String event_desc;
    private String event_author;
    private String morning_begin;
    private String morning_end;
    private String afternoon_begin;
    private String afternoon_end;

    public EventInfo(String event_id, String event_name) {
        this.event_id = event_id;
        this.event_name = event_name;
    }



    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
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


}
