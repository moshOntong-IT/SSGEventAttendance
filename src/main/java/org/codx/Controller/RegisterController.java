package org.codx.Controller;

import animatefx.animation.FadeIn;
import com.google.zxing.WriterException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.validator.routines.EmailValidator;
import org.codx.Model.Department;
import org.codx.Model.Student;
import org.codx.Services.DbConnection;
import org.codx.Services.FileService;
import org.codx.Services.QRCodeService;
import org.codx.StageTool;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterController implements Initializable {


    private Student student;
    private ObservableList<Student> studentObservableList;
    private ObservableList<Department> departmentObservableList;
    private String qrFilePath;
    private String tempName;
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

    @FXML
    private Text schoolNameLBL;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private Text depLBL;

    @FXML
    private ComboBox<String> schoolYearCombox;

    @FXML
    private Text schoolYearlbl;

    @FXML
    private TextField sectionField;

    @FXML
    private Text sectionLBL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departmentObservableList = FXCollections.observableArrayList();
        student = new Student();
        hideMessage();
        init_combo_items();


    }

    private void init_combo_items() {

        if (schoolYearCombox != null && departmentComboBox != null) {
            Connection conn = DbConnection.connectDb();
            ObservableList<String> schoolYear = FXCollections.observableArrayList();
            for (int year = 2021; year < 2030; year++) {
                int schoolYearEnd = year + 1;
                schoolYear.add(year + "-" + schoolYearEnd);
            }
            schoolYearCombox.setItems(schoolYear);

            //fetching all department table from database
            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM public.\"department\"");
                ResultSet rst = stmt.executeQuery();
                while (rst.next()) {
                    Department dep = new Department(rst.getInt("dep_id"),
                            rst.getString("dep_name"));
                    departmentObservableList.add(dep);
                    departmentComboBox.getItems().add(dep.getName());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
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
        student.setfName(fNameField.getText());
        student.setmName(mNameField.getText());
        student.setlName(lNameField.getText());
        if (ageField.getText().equals("")) {
            student.setAge(0);
        } else {
            student.setAge(Integer.parseInt(ageField.getText()));
        }
        if (maleRadioBtn.isSelected()) {
            student.setGender("Male");
        } else {
            student.setGender("Female");
        }
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("registerPage1.fxml")));
            Parent root = loader.load();
            RegisterController regController = loader.getController();
            regController.setStudentObservableList(studentObservableList);
            regController.setStudent(student);
            regController.setIdField(student.getUserID() + "");
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
        student.setSchoolName(schoolNameField.getText());
//        System.out.println(schoolNameField.getText());
        if (departmentComboBox.getValue() != null) {
            departmentObservableList.forEach((department -> {
                if (department.getName().equals(departmentComboBox.getValue())) {
                    student.setDepartment(department);
                }
            }));

        }
        if (schoolYearCombox.getValue() != null) {
            student.setSchoolYear(schoolYearCombox.getValue());
        }

        student.setSection(sectionField.getText());

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
            student.setUserID(Long.parseLong(idField.getText()));
            student.setEmail(emailField.getText());
            student.setPhoneNumber(phoneField.getText());
            student.setPassword(confirmPasswordField.getText());
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("registerPage2.fxml")));
                Parent root = loader.load();
                RegisterController regController = loader.getController();
                regController.setStudentObservableList(studentObservableList);
                regController.setStudent(student);
                if (student.getfName() != null) {
                    regController.setfNameField(student.getfName());
                }
                if (student.getmName() != null) {
                    regController.setmNameField(student.getmName());
                }
                if (student.getlName() != null) {
                    regController.setlNameField(student.getlName());
                }
                if (student.getAge() != 0) {
                    regController.setAgeField(student.getAge() + "");
                }

                if (student.getGender() != null) {
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
                if (student.getSchoolName() != null) {
                    regController.setSchoolNameField(student.getSchoolName());
                }
                if (student.getDepartment() != null) {
                    regController.setDepartmentComboBox(student.getDepartment().getName());
                }
                if (student.getSchoolYear() != null) {
                    regController.setSchoolYearCombox(student.getSchoolYear());
                }
                if (student.getSection() != null) {
                    regController.setSectionField(student.getSection());
                }


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

    @FXML
    void submit(ActionEvent event) {


        if (validatorPage3()) {
            Connection conn = DbConnection.connectDb();
            student.setSchoolName(schoolNameField.getText());
            departmentObservableList.forEach((department -> {
                if (department.getName().equals(departmentComboBox.getValue())) {
                    student.setDepartment(department);
                }
            }));

            student.setSchoolYear(schoolYearCombox.getValue());
            student.setSection(sectionField.getText());

            try {
                PreparedStatement stmt = conn.prepareStatement("Insert into public.\"user_info\" (user_id,password)" +
                        " VALUES (?,?)");
                stmt.setObject(1, student.getUserID(), Types.BIGINT);
                stmt.setString(2, student.getPassword());

                int status = stmt.executeUpdate();
                if (status > 0) {
                    PreparedStatement stmtStudentInfo = conn.prepareStatement("Insert into \"student_info\" " +
                            "(user_id,dep_id,first_name,middle_name,last_name,age,gender,email,phone_number," +
                            "section,school_name,school_year) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                    stmtStudentInfo.setLong(1, student.getUserID());
                    stmtStudentInfo.setLong(2, student.getDepartment().getId());
                    stmtStudentInfo.setString(3, student.getfName());
                    stmtStudentInfo.setString(4, student.getmName());
                    stmtStudentInfo.setString(5, student.getlName());
                    stmtStudentInfo.setInt(6, student.getAge());
                    stmtStudentInfo.setString(7, student.getGender());
                    stmtStudentInfo.setString(8, student.getEmail());
                    stmtStudentInfo.setObject(9, student.getPhoneNumber(), Types.BIGINT);
                    stmtStudentInfo.setString(10, student.getSection());
                    stmtStudentInfo.setString(11, student.getSchoolName());
                    stmtStudentInfo.setString(12, student.getSchoolYear());

                    int statusStudent = stmtStudentInfo.executeUpdate();
                    if (statusStudent > 0) {

                        PreparedStatement stmtStudentID = conn.prepareStatement("Select" +
                                " student_id from student_info where user_id = ?");
                        stmtStudentID.setLong(1,student.getUserID());
                        ResultSet rstId = stmtStudentID.executeQuery();

                        while (rstId.next()){
                            try {
                                tempName = QRCodeService.generateName(student.getUserID() + "");
                                qrFilePath = FileService.defaultPath(tempName);
                                String qrID = student.getUserID() +
                                        "\n" +
                                        student.getPassword()+"\n"+
                                        rstId.getLong("student_id");
                                QRCodeService.generateQRCode(qrID, 350, 350, qrFilePath);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            Parent root = schoolYearCombox.getScene().getRoot();
                            ColorAdjust adj = new ColorAdjust(0, 0, -0.8, 0);
                            GaussianBlur blur = new GaussianBlur(10);
                            adj.setInput(blur);
                            root.setEffect(adj);

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("signUpSuccessfully.fxml"));
                                Parent form = loader.load();
                                SignUpSuccessfullController message = loader.getController();
                                message.setQrImage(qrFilePath);
                                message.setStudent(student);
                                message.setFileName(tempName);
                                Stage stage = new Stage();
                                Scene scene = new Scene(form);
                                form.requestFocus();
                                stage.setScene(scene);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.initStyle(StageStyle.UNDECORATED);

                                stage.showAndWait();

                                root.setEffect(null);


                            } catch (IOException ex) {
                                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }





        }

        switchWindow();

    }

    private void switchWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("landingPage.fxml")));
            root = loader.load();
            LandingController controller = loader.getController();
            controller.setStudentObservableList(studentObservableList);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageTool.setOnMovable(root, stage);

            stage.show();
            schoolYearCombox.getScene().getWindow().hide();


            new FadeIn(root).play();
        } catch (IOException exception) {
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
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

        if (schoolNameLBL != null && depLBL != null && schoolYearlbl != null && sectionLBL != null) {
            schoolNameLBL.setVisible(false);
            depLBL.setVisible(false);
            schoolYearlbl.setVisible(false);
            sectionLBL.setVisible(false);
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
            if (idField.getText().matches("[0-9]+")) {
                idField.getStyleClass().add("field");
                studentID_lbl.setVisible(false);
                if (!studentObservableList.isEmpty()) {
                    studentObservableList.forEach(student1 -> {
//                        System.out.println(student1.getUserID());
                        if (idField.getText().equals(student1.getUserID() + "")) {
                            isReady = false;

                            idField.getStyleClass().add("field-wrong");
                            studentID_lbl.setText("ID is already exist*");
                            studentID_lbl.setVisible(true);

                        }
                    });
                }

            } else {
                isReady = false;
                idField.getStyleClass().add("field-wrong");
                studentID_lbl.setText("ID field is should be number");
                studentID_lbl.setVisible(true);
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
            EmailValidator validator = EmailValidator.getInstance();
            if (validator.isValid(emailField.getText())) {
                emailField.getStyleClass().add("field");
                email_lbl.setVisible(false);
            } else {
                isReady = false;

                emailField.getStyleClass().add("field-wrong");
                email_lbl.setText("Invalid Email");
                email_lbl.setVisible(true);
            }
        }
/////////////////phone Number field
        if (phoneField.getText().equals("")) {
            isReady = false;

            phoneField.getStyleClass().add("field-wrong");
            phone_lbl.setText("Phone Number field is empty*");
            phone_lbl.setVisible(true);
        } else {
            if (idField.getText().matches("[0-9]+")) {
                phoneField.getStyleClass().add("field");
                phone_lbl.setVisible(false);
            } else {
                isReady = false;

                phoneField.getStyleClass().add("field-wrong");
                phone_lbl.setText("Phone Number should be a number*");
                phone_lbl.setVisible(true);
            }

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
            if (!confirmPasswordField.getText().equals(passwordField.getText())) {
                isReady = false;
//                System.out.println(confirmPasswordField.getText());
                confirmPasswordField.getStyleClass().add("field-wrong");
                confirmPasswordLabel.setText("Password not match*");
                confirmPasswordLabel.setVisible(true);
            } else {
                confirmPasswordField.getStyleClass().add("field");
                confirmPasswordLabel.setVisible(false);
            }

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
            mNamelbl.setText("Middle Name field is empty*");
            mNamelbl.setVisible(true);
        } else {
            mNameField.getStyleClass().add("field");
            mNamelbl.setVisible(false);
        }

//////////////////middle name field
        if (lNameField.getText().equals("")) {
            isReady = false;

            lNameField.getStyleClass().add("field-wrong");
            lNameLbl.setText("Last Name field is empty*");
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

    private boolean validatorPage3() {
        isReady = true;
        schoolNameField.getStyleClass().clear();
        departmentComboBox.getStyleClass().removeAll("field", "field-wrong");
        schoolYearCombox.getStyleClass().removeAll("field", "field-wrong");
        sectionField.getStyleClass().clear();


//////////////////school name
        if (schoolNameField.getText().equals("")) {
            isReady = false;

            schoolNameField.getStyleClass().add("field-wrong");
            schoolNameLBL.setText("School Name field is empty*");
            schoolNameLBL.setVisible(true);
        } else {
            schoolNameField.getStyleClass().add("field");
            schoolNameLBL.setVisible(false);
        }
/////////////////department
//        System.out.println(departmentComboBox.getValue());
        if (departmentComboBox.getValue() == null) {
            isReady = false;

            departmentComboBox.getStyleClass().add("field-wrong");
            depLBL.setText("Department field is empty*");
            depLBL.setVisible(true);
        } else {
            departmentComboBox.getStyleClass().add("field");
            depLBL.setVisible(false);
        }

//////////////////school year
        if (schoolYearCombox.getValue() == null) {
            isReady = false;

            schoolYearCombox.getStyleClass().add("field-wrong");
            schoolYearlbl.setText("School Year field is empty*");
            schoolYearlbl.setVisible(true);
        } else {
            schoolYearCombox.getStyleClass().add("field");
            schoolYearlbl.setVisible(false);
        }
/////////////////////section
        if (sectionField.getText().equals("")) {
            isReady = false;

            sectionField.getStyleClass().add("field-wrong");
            sectionLBL.setText("Section field is empty*");
            sectionLBL.setVisible(true);
        } else {

            sectionField.getStyleClass().add("field");
            sectionLBL.setVisible(false);

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

    public void setSchoolNameField(String schoolNameField) {
        this.schoolNameField.setText(schoolNameField);
    }

    public void setDepartmentComboBox(String depValue) {
        this.departmentComboBox.setValue(depValue);
    }

    public void setSchoolYearCombox(String yearValue) {
        this.schoolYearCombox.setValue(yearValue);
    }

    public void setSectionField(String sectionField) {
        this.sectionField.setText(sectionField);
    }
}
