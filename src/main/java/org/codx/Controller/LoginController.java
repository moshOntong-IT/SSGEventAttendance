package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.codx.StageTool;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void submitButton(ActionEvent event) {
        StageTool mainPage = new StageTool("mainPage.fxml");
        mainPage.hide((Stage)loginBTN.getScene().getWindow());
        mainPage.setOnMovable();
    }
}
