package org.codx.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.codx.Model.Student;
import org.codx.StageTool;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private Student student;
    private ObservableList<Student> studentObservableList;
    private boolean isReady = true;
    @FXML
    private TextField idField;

    @FXML
    private Text studentID_lbl;

    @FXML
    private TextField emailField;

    @FXML
    private Text email_lbl;

    @FXML
    private TextField phoneField;

    @FXML
    private Text phone_lbl;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text password_label;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Text confirmPasswordLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideMessage();
    }

    @FXML
    void backButton(ActionEvent event) {

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) password_label.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    void nextPage2(ActionEvent event) {
        if (validatorPage1()) {
            StageTool next = new StageTool("registerPage2.fxml");
            next.hide((Stage)password_label.getScene().getWindow());
            next.setOnMovable();
        }
    }


//
//    non-injectable
//            void/function

    private void hideMessage() {
        studentID_lbl.setVisible(false);
        email_lbl.setVisible(false);
        phone_lbl.setVisible(false);
        password_label.setVisible(false);
        confirmPasswordLabel.setVisible(false);

    }

    private boolean validatorPage1() {
        isReady = true;
        idField.getStyleClass().clear();
        emailField.getStyleClass().clear();
        phoneField.getStyleClass().clear();
        passwordField.getStyleClass().clear();
        confirmPasswordField.getStyleClass().clear();

/////////////////id Field



        if (!idField.getText().equals("")) {

            if (!studentObservableList.isEmpty()) {
                studentObservableList.forEach(student1 -> {
                    if (idField.getText().equals(student1.getUserID().toString())) {
                        isReady = false;

                        idField.getStyleClass().add("field-wrong");
                        studentID_lbl.setText("ID is already exist*");
                        studentID_lbl.setVisible(true);
                    }else{
                        idField.getStyleClass().add("field");
                        studentID_lbl.setVisible(false);
                    }
                });
            }



        } else {
            isReady = false;

            idField.getStyleClass().add("field-wrong");
            studentID_lbl.setText("ID is empty*");
            studentID_lbl.setVisible(true);
        }
//////////////////email field
        if (emailField.getText().equals("")) {
            isReady = false;

            emailField.getStyleClass().add("field-wrong");
            email_lbl.setText("Email field is empty*");
            email_lbl.setVisible(true);
        } else {
            emailField.getStyleClass().add("field");
            email_lbl.setVisible(false);
        }
/////////////////phone Number field
        if (phoneField.getText().equals("")) {
            isReady = false;

            phoneField.getStyleClass().add("field-wrong");
            phone_lbl.setText("Phone Number field is empty*");
            phone_lbl.setVisible(true);
        } else {
            phoneField.getStyleClass().add("field");
            phone_lbl.setVisible(false);
        }

//////////////////password Number field
        if (passwordField.getText().equals("")) {
            isReady = false;

            passwordField.getStyleClass().add("field-wrong");
            password_label.setText("Password field is empty*");
            password_label.setVisible(true);
        } else {
            passwordField.getStyleClass().add("field");
            password_label.setVisible(false);
        }
/////////////////////confirm password field
        if (confirmPasswordField.getText().equals("")) {
            isReady = false;

            confirmPasswordField.getStyleClass().add("field-wrong");
            confirmPasswordLabel.setText("Confirm Password field is empty*");
            confirmPasswordLabel.setVisible(true);
        } else {
            confirmPasswordField.getStyleClass().add("field");
            confirmPasswordLabel.setVisible(false);
        }

        return isReady;
    }

    public ObservableList<Student> getStudentObservableList() {
        return studentObservableList;
    }

    public void setStudentObservableList(ObservableList<Student> studentObservableList) {
        this.studentObservableList = studentObservableList;
    }
}
