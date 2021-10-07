package org.codx.Controller;

import animatefx.animation.FadeIn;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Model.EventInfo;
import org.codx.Model.LoginHistory;
import org.codx.Model.Student;
import org.codx.Model.SystemInfo;
import org.codx.Services.DbConnection;
import org.codx.StageTool;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPageController implements Initializable {

    private ObservableList<Student> studentObservableList;
    private ObservableList<EventInfo> eventInfoObservableList;
    private Connection conn;
    private LoginHistory adminInfo;
    private EventInfo info;
    private SystemInfo [] systemRoleArray;
    @FXML
    private Button eventButton;

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button systemRoleButton;

    @FXML
    private ImageView systemRoleIcon;

    @FXML
    private Button studentButton;

    @FXML
    private ImageView studentIcon;

    @FXML
    private Button departmentButton;

    @FXML
    private ImageView departmentIcon;

    @FXML
    public StackPane stackLayout;

    @FXML
    private VBox systemRole;

    @FXML
    private VBox eventPane;

    @FXML
    private HBox attendanceBox;
    @FXML
    private VBox admin;

    @FXML
    private Label adminName;

    @FXML
    private VBox moderator;

    @FXML
    private Label moderatorName;

    @FXML
    private VBox editor1;

    @FXML
    private Label editorName1;

    @FXML
    private VBox editor3;

    @FXML
    private Label editorName3;

    @FXML
    private VBox editor2;

    @FXML
    private Label editorName2;

    @FXML
    private VBox editor4;

    @FXML
    private Label editorName4;


    @FXML
    private VBox studentRecord;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentObservableList = FXCollections.observableArrayList();
        systemRoleArray = new SystemInfo[6];


    }

    public void init_attendanceList() {
        eventInfoObservableList = FXCollections.observableArrayList();
        if (!attendanceBox.getChildren().isEmpty()) {
            attendanceBox.getChildren().clear();
        }

        if (!eventInfoObservableList.isEmpty()) {
            eventInfoObservableList.clear();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("Select * from event_info where event_date > CURRENT_DATE\n" +
                    "ORDER BY event_date;");

            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                EventInfo info;

                long id = rst.getLong("event_id");
                String name = rst.getString("event_name");
                String desc = rst.getString("event_desc");
                String author = rst.getString("event_author");
                String event_date = rst.getTimestamp("event_date").toString();

                //morning
//                System.out.println(rst.getTimestamp("morning_begin") == null ? author:"");
                String morningStart = rst.getTimestamp("morning_begin")
                        == null ? "" : rst.getTimestamp("morning_begin").toString();
                String morningEnd = rst.getTimestamp("morning_end")
                        == null ? "" : rst.getTimestamp("morning_end").toString();
                //afternoon
                String afternoonStart = rst.getTimestamp("afternoon_begin")
                        == null ? "" : rst.getTimestamp("afternoon_begin").toString();
                String afternoonEnd = rst.getTimestamp("afternoon_end")
                        == null ? "" : rst.getTimestamp("afternoon_end").toString();

                String eventSchoolYear = rst.getString("event_school_year");

                info = new EventInfo(id, name, event_date, desc, author, morningStart, morningEnd, afternoonStart
                        , afternoonEnd, eventSchoolYear);

                eventInfoObservableList.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < eventInfoObservableList.size(); i++) {
//            System.out.println(eventInfoObservableList.get(i).getEvent_name());
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("attendanceCard.fxml")));

            try {
                VBox box = loader.load();
                AttendanceCardController controller = loader.getController();
                controller.setConn(conn);
                controller.setMain(this);
                controller.setLog(adminInfo);
//                System.out.println(controller);
                controller.setEventInfo(eventInfoObservableList.get(i));
                attendanceBox.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void sync(ActionEvent event) {
        Platform.runLater(
                () -> init_attendanceList()
        );
    }

    @FXML
    void menuNavAction(ActionEvent event) {


        ArrayList<Image> imagePath = new ArrayList<>();
        imagePath.add(new Image("assets/icons8-event-24 (2).png"));
        imagePath.add(new Image("assets/system-pink.png"));
        imagePath.add(new Image("assets/users-pink.png"));
        imagePath.add(new Image("assets/department-icon.png"));

        ArrayList<Image> imageHighlightPath = new ArrayList<>();
        imageHighlightPath.add(new Image("assets/icons8-event-24.png"));
        imageHighlightPath.add(new Image("assets/system-white.png"));
        imageHighlightPath.add(new Image("assets/users-white.png"));
        imageHighlightPath.add(new Image("assets/department-white.png"));

        eventButton.getStyleClass().clear();
        systemRoleButton.getStyleClass().clear();
        studentButton.getStyleClass().clear();
        departmentButton.getStyleClass().clear();

        eventButton.getStyleClass().add("menu-nav-left-btn");
        systemRoleButton.getStyleClass().add("menu-nav-left-btn");
        studentButton.getStyleClass().add("menu-nav-left-btn");
        departmentButton.getStyleClass().add("menu-nav-left-btn");

        eventIcon.setImage(imagePath.get(0));
        systemRoleIcon.setImage(imagePath.get(1));
        studentIcon.setImage(imagePath.get(2));
        departmentIcon.setImage(imagePath.get(3));

        if (event.getSource() == eventButton) {
            eventPane.toFront();
            eventIcon.setImage(imageHighlightPath.get(0));
            eventButton.getStyleClass().clear();
            eventButton.getStyleClass().add("menu-nav-left-btn-highlight");

        } else if (event.getSource() == systemRoleButton) {
            systemRole.toFront();
            systemRoleIcon.setImage(imageHighlightPath.get(1));
            systemRoleButton.getStyleClass().clear();
            systemRoleButton.getStyleClass().add("menu-nav-left-btn-highlight");


        } else if (event.getSource() == studentButton) {
            studentRecord.toFront();
            studentIcon.setImage(imageHighlightPath.get(2));
            studentButton.getStyleClass().clear();
            studentButton.getStyleClass().add("menu-nav-left-btn-highlight");

        } else if (event.getSource() == departmentButton) {
            departmentIcon.setImage(imageHighlightPath.get(3));
            departmentButton.getStyleClass().clear();
            departmentButton.getStyleClass().add("menu-nav-left-btn-highlight");

        }

    }


    private void ini_system_role(){
        try {
            PreparedStatement stmt2 = conn.prepareStatement("Select u.user_id, s.admin_id " +
                    ", s.admin_pos, stdt.first_name, stdt.last_name  from user_info u " +
                    "Inner join system_info s " +
                    "ON u.user_id = s.user_id " +
                    "Inner join student_info stdt " +
                    "ON s.user_id = stdt.user_id");
            ResultSet rst = stmt2.executeQuery();

           for (int i = 0; rst.next(); i++){
               long userID = rst.getLong("user_id");
               long admin_id = rst.getLong("admin_id");
               String role = rst.getString("admin_pos");
               String fName = rst.getString("first_name");
               String lName = rst.getString("last_name");
               SystemInfo info = new SystemInfo(userID,admin_id,role);
               info.setfName(fName);
               info.setlName(lName);
               systemRoleArray[i] = info;
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < systemRoleArray.length; i++){
            SystemInfo target = systemRoleArray[i];
            if(target != null){
                if(target.getPosition().equals("Administrator") ){
                    adminName.setText(target.getfName()+" "+target.getlName());
                }
                if(target.getPosition().equals("Moderator")){
                    moderatorName.setText(target.getfName()+" "+target.getlName());
                }
                if(target.getPosition().equals("Editor")){
                    if(editorName1.getText().equals("(Available)")){
                        editorName1.setText(target.getfName()+" "+target.getlName());
                    }else if(editorName2.getText().equals("(Available)")){
                        editorName2.setText(target.getfName()+" "+target.getlName());
                    }else if(editorName3.getText().equals("(Available)")){
                        editorName3.setText(target.getfName()+" "+target.getlName());
                    }else{
                        editorName4.setText(target.getfName()+" "+target.getlName());
                    }
                }
            }
        }

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) eventButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void systemRoleChanged(MouseEvent event) {
        VBox box = (VBox) event.getSource();
        System.out.println(box.getId());
    }
    @FXML
    void exit(ActionEvent event) {
        try {
            LoginController.setLogout(adminInfo, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void setAdminLog(LoginHistory adminLog) {
        this.adminInfo = adminLog;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
        runEvent();
    }

    public void runEvent() {
        Platform.runLater(() -> {
            init_attendanceList();
            ini_system_role();
        });
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            LoginController.setLogout(adminInfo, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StageTool tool = new StageTool("landingPage.fxml");
        tool.setOnMovable();

        tool.hide((Stage) this.attendanceBox.getScene().getWindow());
    }

    @FXML
    void addEvent(ActionEvent event) {
        info = new EventInfo();


        while (true) {
            createEventPage();
            if(info.isBack()){
                break;
            }

        }
        init_attendanceList();
    }

    private void createEventPage() {
        info.setNext(false);
        Parent root = attendanceBox.getScene().getRoot();

        ColorAdjust adj = new ColorAdjust(0, 0, -0.8, 0);
        GaussianBlur blur = new GaussianBlur(10);
        adj.setInput(blur);
        root.setEffect(adj);
//        System.out.print("asa  "+ eventInfo.getMorning_begin());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createEventPage1.fxml"));
            Parent form = loader.load();

            CreateEventPageController controller = loader.getController();
            controller.setAdminLog(adminInfo);
            controller.setInfo(info);
            controller.setConn(conn);


            Stage stage = new Stage();
            Scene scene = new Scene(form);
            form.requestFocus();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.showAndWait();

          if(info.isNext()){
              createEventPage2(info);
//              System.out.println(info.isBack());

//              System.out.println(info.getEvent_date());
//            init_attendanceList();
          }

            root.setEffect(null);

        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createEventPage2(EventInfo info) {
        info.setBack(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createEventPage2.fxml"));
            Parent form = loader.load();

            CreateEventPageController controller = loader.getController();
            controller.setAdminLog(this.adminInfo);
            controller.setInfo(info);
            controller.setConn(conn);

            Stage stage = new Stage();
            Scene scene = new Scene(form);
            form.requestFocus();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);


            stage.showAndWait();


        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
