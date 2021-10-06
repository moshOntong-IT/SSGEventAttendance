package org.codx.Model;

public class LoginHistory extends  SystemInfo{

    //TODO lisdan ang event_author sa user_id
    private long historyId;
    private String loginDate;
    private String logoutHistory;

    public LoginHistory(long userID) {
        super(userID);
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLogoutHistory() {
        return logoutHistory;
    }

    public void setLogoutHistory(String logoutHistory) {
        this.logoutHistory = logoutHistory;
    }
}
