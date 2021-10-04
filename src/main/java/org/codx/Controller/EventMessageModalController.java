package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EventMessageModalController implements Initializable {

    @FXML
    private Label time;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void exit(ActionEvent event) {
        time.getScene().getWindow().hide();
    }

    public void setTime(String time){
        this.time.setText(time);
    }
}
