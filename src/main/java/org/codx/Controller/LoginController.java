package org.codx.Controller;

import animatefx.animation.FadeIn;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

public class LoginController implements Initializable {

    private ObservableList<Student> studentObservableList;


    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView eye;

    @FXML
    private Button loginBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void submitButton(ActionEvent event) {
        StageTool mainPage = new StageTool("mainPage.fxml");
        mainPage.hide((Stage)loginBTN.getScene().getWindow());
        mainPage.setOnMovable();
    }


    @FXML
    void exitBTN(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) loginBTN.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("landingPage.fxml")));
            Parent root = loader.load();
            LandingController controller = loader.getController();
            controller.setStudentObservableList(studentObservableList);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageTool.setOnMovable(root,stage);

            stage.show();
            loginBTN.getScene().getWindow().hide();

            new FadeIn(root).play();
        } catch (IOException exception) {
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }

    }

    public void setStudentObservableList(ObservableList<Student> studentObservableList) {
        this.studentObservableList = studentObservableList;
    }
}
