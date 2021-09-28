package org.codx.Model;

public class UserCredentials {

    private String userID;
    private String password;

    public UserCredentials(){

    }
    public UserCredentials(String userID, String password) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
