package org.codx.Controller;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Model.Department;
import org.codx.Model.Student;
import org.codx.Services.DbConnection;
import org.codx.StageTool;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadingController implements Initializable {

    private ObservableList<Student> studentObservableList;


    @FXML
    private ProgressBar progressBarIndicator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentObservableList = FXCollections.observableArrayList();

        loadProgress();
    }

    private void init_student_list() {
        Connection conn = DbConnection.connectDb();
        String statement = "Select u.user_id, u.password,\n" +
                "s.student_id ,s.first_name, s.middle_name, s.last_name,\n" +
                "s.age, s.gender, s.email, s.phone_number, s.section, s.school_name,\n" +
                "s.school_year,\n" +
                "d.dep_id, d.dep_name\n" +
                "from user_info u\n" +
                "left join student_info s\n" +
                "on u.user_id = s.user_id\n" +
                "left join department d\n" +
                "on s.dep_id = d.dep_id;";

        try {
            PreparedStatement stmt = conn.prepareStatement(statement);
            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                long userID = rst.getLong("user_id");
                String password = rst.getString("password");


                long studentID = rst.getLong("student_id");
                String fName = rst.getString("first_name");
                String mName = rst.getString("middle_name");
                String lName = rst.getString("last_name");
                int age = rst.getInt("age");
                String gender = rst.getString("gender");
                String email = rst.getString("email");
                String phoneNumber = rst.getLong("phone_number") + "";
                String section = rst.getString("section");
                String schoolName = rst.getString("school_name");
                String schoolYear = rst.getString("school_year");
                Department dep = new Department(rst.getLong("dep_id"),rst.getString("dep_name"));


                Student student = new Student(userID, password,
                        studentID, fName, mName, lName, age, gender, email, phoneNumber, section, dep, schoolName, schoolYear);
                studentObservableList.add(student);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadProgress() {
        Task<Void> loadingData = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 1; i <= 100; i++) {
                    updateProgress(i, 100);
                    if (i == 30) {
                        init_student_list();
                    }
                    Thread.sleep(50);
                }

                return null;
            }

        };

        loadingData.setOnSucceeded(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("landingPage.fxml")));
                Parent root = loader.load();
                LandingController landingController = loader.getController();
                landingController.setStudentObservableList(studentObservableList);

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                StageTool.setOnMovable(root, stage);

                stage.show();

                progressBarIndicator.getScene().getWindow().hide();

                new FadeIn(root).play();
            } catch (IOException exception) {
                Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
            }
        });

        progressBarIndicator.progressProperty().unbind();
        progressBarIndicator.progressProperty().bind(loadingData.progressProperty());

        Thread runProgress = new Thread(loadingData);
        runProgress.setDaemon(true);
        runProgress.start();
    }


}
