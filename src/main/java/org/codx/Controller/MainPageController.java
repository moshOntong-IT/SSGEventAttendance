package org.codx.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Model.EventInfo;
import org.codx.Model.Student;
import org.codx.Services.DbConnection;

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
    private StackPane stackLayout;

    @FXML
    private VBox systemRole;

    @FXML
    private VBox eventPane;

    @FXML
    private HBox attendanceBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentObservableList = FXCollections.observableArrayList();
        eventInfoObservableList = FXCollections.observableArrayList();
        init_attendanceList();
    }

    public void init_attendanceList() {
        Connection conn = DbConnection.connectDb();

        try {
            PreparedStatement stmt = conn.prepareStatement("Select * from event_info where event_date > Now()::timestamp"+
                    " ORDER BY event_date ASC;");
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
                        == null ? "": rst.getTimestamp("morning_begin").toString() ;
                String morningEnd = rst.getTimestamp("morning_begin")
                        == null ? "": rst.getTimestamp("morning_begin").toString() ;
                //afternoon
                String afternoonStart = rst.getTimestamp("afternoon_begin")
                        == null ? "": rst.getTimestamp("afternoon_begin").toString() ;
                String afternoonEnd =rst.getTimestamp("afternoon_end")
                        == null ? "": rst.getTimestamp("afternoon_end").toString() ;

                String eventSchoolYear = rst.getString("event_school_year");

                info = new EventInfo(id,name,event_date,desc,author,morningStart,morningEnd,afternoonStart
                ,afternoonEnd,eventSchoolYear);

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
//                System.out.println(controller);
                controller.setEventInfo(eventInfoObservableList.get(i));
                attendanceBox.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
            studentIcon.setImage(imageHighlightPath.get(2));
            studentButton.getStyleClass().clear();
            studentButton.getStyleClass().add("menu-nav-left-btn-highlight");

        } else if (event.getSource() == departmentButton) {
            departmentIcon.setImage(imageHighlightPath.get(3));
            departmentButton.getStyleClass().clear();
            departmentButton.getStyleClass().add("menu-nav-left-btn-highlight");

        }

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) eventButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }


}
