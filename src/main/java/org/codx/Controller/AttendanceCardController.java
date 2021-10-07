package org.codx.Controller;

import Builder.EventConvert;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Model.EventInfo;
import org.codx.Model.LoginHistory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceCardController implements Initializable {


    private EventInfo eventInfo;
    private Connection conn;
    private MainPageController main;
    private String timeNow;
    private String eventTime;
    private String result;
    private String dateNow;
    private LoginHistory log;

    @FXML
    private Label title;

    @FXML
    private HBox availabilityBox;

    @FXML
    private HBox morningBox;

    @FXML
    private HBox afternoonBox;

    @FXML
    private TextField activeStudentsField;

    @FXML
    private Label activeStatus;

    @FXML
    private TextField absentStudentField;

    @FXML
    private Label absentStatus;

    @FXML
    private Button attendanceButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        title.setText(eventInfo.getEvent_name());
    }

    @FXML
    void attend(ActionEvent event) {

        try {
            EventConvert.parseDate((date) -> this.dateNow = date);
            EventConvert.parseAATime(dateNow, (time) -> this.timeNow = time);
            if (!eventInfo.getMorning_begin().equals("")) {
                EventConvert.parseAATime(eventInfo.getMorning_begin(), (time) -> this.eventTime = time);
            } else {
                EventConvert.parseAATime(eventInfo.getAfternoon_begin(), (time) -> this.eventTime = time);
            }
            EventConvert.greaterThan(timeNow, eventTime, (result) -> this.result = result);
            if (result.equals("Ready")) {
                showAttendancePage();
            } else {
                showMessage();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void showMessage() {
        Parent root = main.stackLayout.getScene().getRoot();
        ColorAdjust adj = new ColorAdjust(0, 0, -0.8, 0);
        GaussianBlur blur = new GaussianBlur(10);
        adj.setInput(blur);
        root.setEffect(adj);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("eventMessageModal.fxml"));
            Parent form = loader.load();
            EventMessageModalController controller = loader.getController();
            controller.setTime(eventTime);
            Stage stage = new Stage();
            Scene scene = new Scene(form);
            form.requestFocus();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.showAndWait();


            root.setEffect(null);

        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAttendancePage() {
        Parent root = absentStatus.getScene().getRoot();
        ColorAdjust adj = new ColorAdjust(0, 0, -0.8, 0);
        GaussianBlur blur = new GaussianBlur(10);
        adj.setInput(blur);
        root.setEffect(adj);
//        System.out.print("asa  "+ eventInfo.getMorning_begin());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("eventPage.fxml"));
            Parent form = loader.load();

            EventPageController controller = loader.getController();
            controller.setAdminLog(log);
            controller.setEventInfo(eventInfo);

            Stage stage = new Stage();
            Scene scene = new Scene(form);
            form.requestFocus();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.showAndWait();
            main.init_attendanceList();

            root.setEffect(null);

        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setEventInfo(EventInfo info) {

        this.eventInfo = info;
        if (eventInfo.getMorning_begin().equals("") || eventInfo.getMorning_begin() == null){
            availabilityBox.getChildren().remove(morningBox);
        }
        if (eventInfo.getAfternoon_begin().equals("") || eventInfo.getAfternoon_begin() == null){
            availabilityBox.getChildren().remove(afternoonBox);
        }


        title.setText(info.getEvent_name());
        activeStudentsField.setText(eventInfo.getActive(eventInfo.getEvent_id(), conn) + "");
        absentStudentField.setText(eventInfo.getAbsent(eventInfo.getEventSchoolYear(), conn) + "");

        String durationDate = eventInfo.getDurationDate();
        int durationDateInteger = Integer.parseInt(durationDate.replaceAll("[^0-9]", ""));


        if (durationDateInteger <= 5 && durationDateInteger > 0) {
            attendanceButton.setDisable(true);

            attendanceButton.setText(durationDate + (durationDateInteger == 1 ?
                    " Day" : "  Days") + " left");

        } else if (durationDateInteger > 5) {
            attendanceButton.setDisable(true);
            EventConvert.dateWordDateFormat(info.getEvent_date(), (date) -> attendanceButton.setText(date));
        } else {
            attendanceButton.setText("Attendance");
        }


    }

    public void setLog(LoginHistory log) {
        this.log = log;
    }

    public void setMain(MainPageController main) {
        this.main = main;
    }
}
