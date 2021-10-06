package org.codx.Controller;

import Builder.EventConvert;
import animatefx.animation.FadeIn;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Model.LoginHistory;
import org.codx.Model.Student;
import org.codx.Model.SystemInfo;
import org.codx.Services.DbConnection;
import org.codx.StageTool;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    private ObservableList<Student> studentObservableList;
    private LoginHistory info;

    private  Connection conn;

    private String loginDate;
    private static String logoutDate;


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
        if (validator()) {
           conn = DbConnection.connectDb();
            try {
                PreparedStatement stmt = conn.prepareStatement("Select u.user_id, u.password, \n" +
                        "sys.admin_pos, sys.admin_id\n" +
                        "from user_info u\n" +
                        "inner join public.\"system_info\" sys\n" +
                        "on u.user_id = sys.user_id\n" +
                        "where sys.user_id = ? and u.password = ? ;");
                stmt.setLong(1, Long.parseLong(loginField.getText()));
                stmt.setString(2, passwordField.getText());
                ResultSet rst = stmt.executeQuery();
                if (rst.next()) {
                    EventConvert.parseDate((date) -> this.loginDate = date);
                    info = new LoginHistory(Long.parseLong(loginField.getText()));
                    info.setAdminID(rst.getLong("admin_id"));
                    info.setPosition(rst.getString("admin_pos"));
                    info.setLoginDate(loginDate);


                    PreparedStatement stmt2 = conn.prepareStatement("" +
                            "Insert into login_history (login_date,admin_id) VALUES (?,?)" +
                            " RETURNING history_id");
//                    System.out.println(info.getLoginDate());
                    stmt2.setObject(1, info.getLoginDate(), Types.TIMESTAMP);
                    stmt2.setObject(2, info.getAdminID());

                    stmt2.execute();


                    ResultSet rst2 = stmt2.getResultSet();
                    if (rst2.next()) {
                        info.setHistoryId(rst2.getLong("history_id"));
//                        System.out.println(info.getHistoryId());
                        switchWindow();


                    }
                } else {
                    Alert invalid = new Alert(Alert.AlertType.ERROR);
                    invalid.setTitle("Invalid Account");
                    invalid.setContentText("This account is invalid");
                    invalid.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    public static void setLogout(LoginHistory adminLog, Connection conn) throws SQLException {
        EventConvert.parseDate((date) -> logoutDate = date);
        PreparedStatement stmt = conn.prepareStatement("UPDATE login_history" +
                " SET logout_date = ? where history_id = ?");

        stmt.setObject(1,logoutDate, Types.TIMESTAMP);
        stmt.setLong(2, adminLog.getHistoryId());
        stmt.execute();
    }

    private boolean validator() {
        boolean isReady = true;
        Alert message = new Alert(Alert.AlertType.WARNING);
        message.setTitle("Message");
        String contentMessage = "Invalid:";
        if (loginField.getText().equals("")) {
            contentMessage += "\nUser field is empty";
            isReady = false;
        }

        if (passwordField.getText().equals("")) {
            contentMessage += "\nPassword field is empty";
            isReady = false;
        }
        if (!isReady) {
            message.setContentText(contentMessage);
            message.showAndWait();
        }
        return isReady;
    }

    private void switchWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("mainPage.fxml")));
            Parent root = loader.load();
            MainPageController controller = loader.getController();
            controller.setConnection(this.conn);
            controller.setAdminLog(info);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageTool.setOnMovable(root, stage);

            stage.show();

            loginField.getScene().getWindow().hide();


            new FadeIn(root).play();
        } catch (IOException exception) {
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }

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


            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageTool.setOnMovable(root, stage);

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
