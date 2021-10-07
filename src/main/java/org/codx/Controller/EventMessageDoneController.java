package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EventMessageDoneController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setText(String name){
        this.name.setText(name);
    }
    public void setMessage(String name){
        message.setText(name);
    }

    @FXML
    void okayAction(ActionEvent event) {
        name.getScene().getWindow().hide();

    }
}
