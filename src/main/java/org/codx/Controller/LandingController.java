package org.codx.Controller;

import animatefx.animation.FadeIn;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Model.Student;
import org.codx.StageTool;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LandingController implements Initializable {

    private ObservableList<Student> studentObservableList;
    private Stage stage = null;
    private double x = 0;
    private double y = 0;
    @FXML
    private HBox systemHeader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    void exitBTN(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) systemHeader.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    void registerAction(ActionEvent event) {
        Parent root = null;


        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("registerPage1.fxml")));
            root = loader.load();
            RegisterController regController = loader.getController();
            regController.setStudentObservableList(studentObservableList);

            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageTool.setOnMovable(root,stage);

            stage.show();
            systemHeader.getScene().getWindow().hide();

            new FadeIn(root).play();
        } catch (IOException exception) {
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }

    }

    @FXML
    void loginAction(ActionEvent event) {
        StageTool loginPanel = new StageTool("loginPage.fxml");
        loginPanel.hide((Stage) systemHeader.getScene().getWindow());
        loginPanel.setOnMovable();
    }


    public ObservableList<Student> getStudentObservableList() {
        return studentObservableList;
    }

    public void setStudentObservableList(ObservableList<Student> studentObservableList) {
        this.studentObservableList = studentObservableList;
    }
}
