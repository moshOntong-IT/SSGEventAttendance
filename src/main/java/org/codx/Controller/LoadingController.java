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
        studentObservableList =  FXCollections.observableArrayList();

        loadProgress();
    }

    private void init_student_list(){
        Connection conn = DbConnection.connectDb();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * from public.\"user_info\"");
            ResultSet rst = stmt.executeQuery();
            while (rst.next()){
                long userID = rst.getLong("user_id");
                String password = rst.getString("password");
                PreparedStatement stmt2 = conn.prepareStatement("Select * from public.\"student_info\"" +
                        " where user_id=?");
                stmt2.setObject(1, userID, Types.BIGINT);
                ResultSet rst2 = stmt2.executeQuery();
                while (rst2.next()){
                    long studentID = rst2.getLong("student_id");
                    String fName = rst2.getString("first_name");
                    String mName = rst2.getString("middle_name");
                    String lName = rst2.getString("last_name");
                    int age = rst2.getInt("age");
                    String gender = rst2.getString("gender");
                    String email = rst2.getString("email");
                    String phoneNumber = rst2.getLong("phone_number")+"";
                    String section = rst.getString("section");
                    String schoolName = rst.getString("school_name");
                    String schoolYear = rst.getString("school_year");
                    Department dep = Department.department(rst.getInt("dep_id"));


                    Student student = new Student(userID,password,
                            studentID, fName,mName,lName,age,gender,email,phoneNumber,section,dep,schoolName,schoolYear);
                    studentObservableList.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadProgress(){
        Task<Void> loadingData = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 1; i <= 100; i++){
                    updateProgress(i, 100);
                    if(i == 30){
                        init_student_list();
                    }
                    Thread.sleep(50);
                }

                return null;
            }

        };

        loadingData.setOnSucceeded( event -> {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("landingPage.fxml")));
                Parent root = loader.load();
                LandingController landingController = loader.getController();
                landingController.setStudentObservableList(studentObservableList);

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                StageTool.setOnMovable(root,stage);

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
