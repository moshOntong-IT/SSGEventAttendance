package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private Button eventButton;

    @FXML
    private Button systemRoleButton;

    @FXML
    private Button studentButton;

    @FXML
    private Button departmentButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void menuNavAction(ActionEvent event) {

        if(event.getSource() == eventButton){

        }

    }
}
