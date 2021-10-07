package org.codx.Controller;

import Builder.EventDateTimeBuilder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.codx.Model.EventInfo;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ModifyDayController implements Initializable {

    @FXML
    private Label title;

    @FXML
    private TextField entranceHH;

    @FXML
    private TextField entranceMM;

    @FXML
    private ComboBox<String> aaEn;

    @FXML
    private TextField exitHH;

    @FXML
    private TextField exitMM;

    @FXML
    private ComboBox<String> aaExit;


    private boolean isMorningToSet;
    private String eventDate;
    private EventInfo info;
    private Button btnSource;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        aaEn.getItems().addAll("AM", "PM");
        aaExit.getItems().addAll("AM", "PM");

    }


    public void setMorningToSet(boolean morningToSet) {
        isMorningToSet = morningToSet;
        if(morningToSet){
            title.setText("Edit Morning Time");
        }else {
            title.setText("Edit Afternoon Time");
        }
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setInfo(EventInfo info) {
        this.info = info;
    }

    @FXML
    void confirm(ActionEvent event) {
        String entrance = entranceHH.getText() + ":" + entranceMM.getText() + " " + aaEn.getValue();
        String exit = exitHH.getText() + ":" + exitMM.getText() + " " + aaExit.getValue();
        if (isMorningToSet) {
            info.setMorning_begin(parseTime(eventDate + " " + entrance));
            info.setMorning_end(parseTime(eventDate + " " + exit));
        } else {
            info.setAfternoon_begin(parseTime(eventDate + " " + entrance));
            info.setAfternoon_end(parseTime(eventDate + " " + exit));
        }

        aaExit.getScene().getWindow().hide();


    }

    private String parseTime(String time) {
        try {
            SimpleDateFormat dateFormat
                    = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm aa");
            DateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pre_format = dateFormat.parse(time);
            return dF.format(pre_format) + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setBtnSource(Button btnSource) {
        this.btnSource = btnSource;
    }

    @FXML
    void discard(ActionEvent event) {
        btnSource.setStyle("-fx-background-color: darkgrey");
        aaExit.getScene().getWindow().hide();

    }
}
