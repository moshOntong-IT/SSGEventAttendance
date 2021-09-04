package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.codx.StageTool;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingController implements Initializable {


    @FXML
    private HBox systemHeader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    void exitBTN(ActionEvent event) {
       systemHeader.getScene().getWindow().hide();

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) systemHeader.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    void registerAction(ActionEvent event) {
        StageTool registerPanel = new StageTool("registerPage1.fxml");
        registerPanel.hide((Stage)systemHeader.getScene().getWindow());
        registerPanel.setOnMovable();
    }
}
