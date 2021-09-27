package org.codx.Model;

public class UserCredentials {

    private String userID;
    private String password;

    public UserCredentials(String userID) {
        this.userID = userID;
    }

    public Long getUserID() {
        return Long.parseLong(userID);
    }

    public void setUserID(Long userID) {
        this.userID = userID.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
