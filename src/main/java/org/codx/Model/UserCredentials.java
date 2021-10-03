package org.codx.Model;

public class UserCredentials {

    private long userID;
    private String password;

    public UserCredentials(){

    }
    public UserCredentials(long userID, String password) {
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
