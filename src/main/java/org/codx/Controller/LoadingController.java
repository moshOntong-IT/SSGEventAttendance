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
import org.codx.Model.Student;
import org.codx.StageTool;

import java.io.IOException;
import java.net.URL;
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
        studentObservableList.add(new Student("1","password",1,"Muslimin","Banto",
                "Ontong",18,"Male","moshOntong@gmail.com","09777044903","Gentoo","SHS",
                "AMACC Davao","2019-2020"));
        loadProgress();
    }

    private void loadProgress(){
        Task<Void> loadingData = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 1; i <= 100; i++){
                    updateProgress(i, 100);
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
