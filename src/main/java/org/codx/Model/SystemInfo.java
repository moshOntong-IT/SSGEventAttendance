package org.codx.Model;

public class SystemInfo extends Student {

    private long userID;
    private long adminID;
    private String position;

    public SystemInfo(long userID) {
        this.userID = userID;

    }

    public SystemInfo(long userID, long adminID, String position) {
        this.userID = userID;
        this.adminID = adminID;
        this.position = position;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getAdminID() {
        return adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
