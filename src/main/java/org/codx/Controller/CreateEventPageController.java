package org.codx.Controller;

import javafx.fxml.Initializable;
import org.codx.Model.LoginHistory;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateEventPageController implements Initializable {

    private LoginHistory adminLog;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAdminLog(LoginHistory adminLog) {
        this.adminLog = adminLog;
    }
}
