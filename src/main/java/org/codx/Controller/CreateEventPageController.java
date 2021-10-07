package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import org.codx.Model.EventInfo;
import org.codx.Model.LoginHistory;
import org.codx.StageTool;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateEventPageController implements Initializable {

    private LoginHistory adminLog;
    private EventInfo info;
    private Connection conn;


    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;
    @FXML
    private ImageView imageID;
    @FXML
    private DatePicker eventDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void discardAction(ActionEvent event) {
        info.setBack(true);
        imageID.getScene().getWindow().hide();
    }

    @FXML
    void nextAction(ActionEvent event) {

        info.setEvent_name(titleField.getText());
        info.setEvent_desc(descriptionField.getText());
        info.setNext(true);

        titleField.getScene().getWindow().hide();

    }

    public void setInfo(EventInfo info) {
        this.info = info;
        if (titleField != null) {
            if (info.getEvent_name() != null) {
                titleField.setText(info.getEvent_name());
            }
        }
        if (descriptionField != null) {
            if (info.getEvent_desc() != null) {
                descriptionField.setText(info.getEvent_desc());
            }
        }
    }


    public void setAdminLog(LoginHistory adminLog) {
        this.adminLog = adminLog;
    }


    @FXML
    void activate(ActionEvent event) {
        if (validateDate()) {
            Button button = (Button) event.getSource();
            button.setStyle("-fx-background-color: #3641B7");

            if (button.getText().equals("Morning")) {

                info.setMorningActive(true);

            }
            if (button.getText().equals("Afternoon")) {

                info.setAfternoonActive(true);
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("modifyDay.fxml"));
                Parent form = loader.load();

                ModifyDayController controller = loader.getController();
                if (button.getText().equals("Morning")) {
                    controller.setMorningToSet(true);
                } else {
                    controller.setMorningToSet(false);
                }
                controller.setEventDate(getDate());
                controller.setBtnSource(button);
                controller.setInfo(info);

                Stage stage = new Stage();
                Scene scene = new Scene(form);
                form.requestFocus();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                StageTool.setOnMovable(form, stage);

                stage.showAndWait();


            } catch (IOException ex) {
                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        return (eventDate.getValue()).format(formatter);

    }

    @FXML
    void back(ActionEvent event) {
        info.setBack(false);
        eventDate.getScene().getWindow().hide();
    }

    private boolean validateDate() {
        if (eventDate.getValue() != null) {
            eventDate.setStyle("-fx-border-color: gray");
            return true;
        } else {
            eventDate.setStyle("-fx-border-color: red");
            return false;
        }
    }

    private String getUserStudentAuthor() {
        String name = "";
        try {
            PreparedStatement stmt = conn.prepareStatement("Select first_name, last_name, school_year" +
                    " from student_info where user_id = ?");
            stmt.setLong(1, adminLog.getUserID());
            ResultSet rst = stmt.executeQuery();
            if (rst.next()) {
                name = rst.getString("first_name") + " " + rst.getString("last_name");
                info.setEventSchoolYear(rst.getString("school_year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return name;
    }


    @FXML
    void submit(ActionEvent event) {
//        System.out.println(info.isAfternoonActive());
        if (info.isMorningActive() || info.isAfternoonActive()) {
            if(info.getMorning_begin() != null){
                info.setEvent_date(info.getMorning_begin());
            }else{
                info.setEvent_date(info.getAfternoon_begin());
            }
            info.setEvent_author(getUserStudentAuthor());

//            System.out.println("Event name "+ info.getEvent_name());
//            System.out.println("Event description "+ info.getEvent_desc());
//            System.out.println("Event author "+ info.getEvent_author());
//            System.out.println("Event date "+ info.getEvent_date());
//            System.out.println("Event Morning begin"+ info.getMorning_begin());
//            System.out.println("Event Morning end"+ info.getMorning_end());
//            System.out.println("Event Afternoon begin"+ info.getAfternoon_begin());
//            System.out.println("Event Afternoon end"+ info.getAfternoon_end());
//            System.out.println("Event School Year"+ info.getEventSchoolYear());

            try {
                PreparedStatement stmt = conn.prepareStatement("Insert into" +
                        " event_info (event_name,event_date,event_desc,event_author," +
                        "morning_begin,morning_end,afternoon_begin,afternoon_end," +
                        "event_school_year) VALUES (?,?,?,?,?,?,?,?,?)");
                stmt.setString(1, info.getEvent_name());
                stmt.setObject(2, info.getEvent_date(), Types.TIMESTAMP);
                stmt.setString(3, info.getEvent_desc());
                stmt.setString(4, info.getEvent_author());
                stmt.setObject(5, info.getMorning_begin(), Types.TIMESTAMP);
                stmt.setObject(6, info.getMorning_end(), Types.TIMESTAMP);
                stmt.setObject(7, info.getAfternoon_begin(), Types.TIMESTAMP);
                stmt.setObject(8, info.getAfternoon_end(), Types.TIMESTAMP);
                stmt.setString(9, info.getEventSchoolYear());
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        info.setBack(true);
        eventDate.getScene().getWindow().hide();
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
