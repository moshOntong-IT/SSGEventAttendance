package org.codx.Controller;

import animatefx.animation.FadeIn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
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

    @FXML
    private TextField fNameField;

    @FXML
    private Text fNameLbl;

    @FXML
    private TextField mNameField;

    @FXML
    private Text mNamelbl;

    @FXML
    private TextField lNameField;

    @FXML
    private Text lNameLbl;

    @FXML
    private TextField ageField;

    @FXML
    private Text ageLabel;

    @FXML
    private RadioButton maleRadioBtn;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton femaleRadioBtn;

    @FXML
    private TextField schoolNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student = new Student();
        hideMessage();


    }

    @FXML
    void backButton(ActionEvent event) {
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
            password_label.getScene().getWindow().hide();

            new FadeIn(root).play();
        } catch (IOException exception) {
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }
    }

    @FXML
    void backPage2(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("registerPage1.fxml")));
            Parent root = loader.load();
            RegisterController regController = loader.getController();
            regController.setStudentObservableList(studentObservableList);
            regController.setStudent(student);
            regController.setIdField(student.getUserID());
            regController.setEmailField(student.getEmail());
            regController.setPhoneField(student.getPhoneNumber());
            regController.setPasswordField(student.getPassword());
            regController.setConfirmPasswordField(student.getPassword());

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageTool.setOnMovable(root, stage);

            stage.show();
            fNameField.getScene().getWindow().hide();

            new FadeIn(root).play();
        } catch (IOException exception) {
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }
    }

    @FXML
    void backPage3(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("registerPage2.fxml")));
            Parent root = loader.load();
            RegisterController regController = loader.getController();
            regController.setStudentObservableList(studentObservableList);
            regController.setStudent(student);
            regController.setfNameField(student.getfName());
            regController.setmNameField(student.getmName());
            regController.setlNameField(student.getlName());
            regController.setAgeField(student.getAge() + "");
            if (student.getGender().equals("Male")) {
                regController.setMaleRadioBtn(true);
            } else {
                regController.setFemaleRadioBtn(true);
            }

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageTool.setOnMovable(root, stage);

            stage.show();
            schoolNameField.getScene().getWindow().hide();

            new FadeIn(root).play();
        } catch (IOException exception) {
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }
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
    void nextPage(ActionEvent event) {


        if (validatorPage1()) {
            student.setUserID(idField.getText());
            student.setEmail(emailField.getText());
            student.setPhoneNumber(phoneField.getText());
            student.setPassword(confirmPasswordField.getText());
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("registerPage2.fxml")));
                Parent root = loader.load();
                RegisterController regController = loader.getController();
                regController.setStudentObservableList(studentObservableList);
                regController.setStudent(student);
                if (student.getfName() != null && student.getmName() != null &&
                        student.getlName() != null && student.getAge() != 0 &&
                        student.getGender() != null) {
                    regController.setfNameField(student.getfName());
                    regController.setmNameField(student.getmName());
                    regController.setlNameField(student.getlName());
                    regController.setAgeField(student.getAge() + "");
                    if (student.getGender().equals("Male")) {
                        regController.setMaleRadioBtn(true);
                    } else {
                        regController.setFemaleRadioBtn(true);
                    }
                }

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                StageTool.setOnMovable(root, stage);

                stage.show();
                password_label.getScene().getWindow().hide();

                new FadeIn(root).play();
            } catch (IOException exception) {
                Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
            }

        }


    }

    @FXML
    void nextPage2(ActionEvent event) {


        if (validatorPage2()) {
            student.setfName(fNameField.getText());
            student.setmName(mNameField.getText());
            student.setlName(lNameField.getText());
            student.setAge(Integer.parseInt(ageField.getText()));
            if (maleRadioBtn.isSelected()) {
                student.setGender("Male");
            } else {
                student.setGender("Female");
            }


            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("registerPage3.fxml")));
                Parent root = loader.load();
                RegisterController regController = loader.getController();
                regController.setStudentObservableList(studentObservableList);
                regController.setStudent(student);

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                StageTool.setOnMovable(root, stage);

                stage.show();
                fNameField.getScene().getWindow().hide();

                new FadeIn(root).play();
            } catch (IOException exception) {
                Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
            }

        }
    }

//
//    non-injectable
//            void/function

    private void hideMessage() {

        if (studentID_lbl != null && email_lbl != null && phone_lbl != null
                && password_label != null && confirmPasswordLabel != null) {

            studentID_lbl.setVisible(false);
            email_lbl.setVisible(false);
            phone_lbl.setVisible(false);
            password_label.setVisible(false);
            confirmPasswordLabel.setVisible(false);
        }

        if (fNameField != null && mNameField != null && lNameField != null && ageField != null) {
            fNameLbl.setVisible(false);
            mNamelbl.setVisible(false);
            lNameLbl.setVisible(false);
            ageLabel.setVisible(false);
        }


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
                    } else {
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

    private boolean validatorPage2() {
        isReady = true;
        fNameField.getStyleClass().clear();
        mNameField.getStyleClass().clear();
        lNameField.getStyleClass().clear();
        ageField.getStyleClass().clear();


//////////////////first name field
        if (fNameField.getText().equals("")) {
            isReady = false;

            fNameField.getStyleClass().add("field-wrong");
            fNameLbl.setText("First name field is empty*");
            fNameLbl.setVisible(true);
        } else {
            fNameField.getStyleClass().add("field");
            fNameLbl.setVisible(false);
        }
/////////////////middle name field
        if (mNameField.getText().equals("")) {
            isReady = false;

            mNameField.getStyleClass().add("field-wrong");
            mNamelbl.setText("Phone Number field is empty*");
            mNamelbl.setVisible(true);
        } else {
            mNameField.getStyleClass().add("field");
            mNamelbl.setVisible(false);
        }

//////////////////middle name field
        if (lNameField.getText().equals("")) {
            isReady = false;

            lNameField.getStyleClass().add("field-wrong");
            lNameLbl.setText("Password field is empty*");
            lNameLbl.setVisible(true);
        } else {
            lNameField.getStyleClass().add("field");
            lNameLbl.setVisible(false);
        }
/////////////////////age Field
        if (ageField.getText().equals("")) {
            isReady = false;

            ageField.getStyleClass().add("field-wrong");
            ageLabel.setText("Age field is empty*");
            ageLabel.setVisible(true);
        } else {
            if (ageField.getText().matches("[0-9]+")) {
                ageField.getStyleClass().add("field");
                ageLabel.setVisible(false);
            } else {
                isReady = false;
                ageField.getStyleClass().add("field-wrong");
                ageLabel.setText("Age field is should be number");
                ageLabel.setVisible(true);
            }
        }

        return isReady;
    }

    public ObservableList<Student> getStudentObservableList() {
        return studentObservableList;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setStudentObservableList(ObservableList<Student> studentObservableList) {
        this.studentObservableList = studentObservableList;
    }

    public void setIdField(String idField) {
        this.idField.setText(idField);
    }

    public void setEmailField(String emailField) {
        this.emailField.setText(emailField);
    }

    public void setPhoneField(String phoneField) {
        this.phoneField.setText(phoneField);
    }

    public void setPasswordField(String passwordField) {
        this.passwordField.setText(passwordField);
    }

    public void setConfirmPasswordField(String confirmPasswordField) {
        this.confirmPasswordField.setText(confirmPasswordField);
    }

    public void setfNameField(String fNameField) {
        this.fNameField.setText(fNameField);
    }

    public void setmNameField(String mNameField) {
        this.mNameField.setText(mNameField);
    }

    public void setlNameField(String lNameField) {
        this.lNameField.setText(lNameField);
    }

    public void setAgeField(String ageField) {
        this.ageField.setText(ageField);
    }

    public void setMaleRadioBtn(boolean isActive) {
        this.maleRadioBtn.setSelected(isActive);
    }

    public void setFemaleRadioBtn(boolean isActive) {
        this.femaleRadioBtn.setSelected(isActive);
    }
}
