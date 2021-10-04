package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import org.codx.Model.Student;
import org.codx.Services.EmailService;

import java.io.*;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLConnection;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class SignUpSuccessfullController implements Initializable {
    @FXML
    private ImageView qrImage;

    private String pathImage;
    private Student student;
    private String fileName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setQrImage(String path) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
            Image image = new Image(stream);
            qrImage.setImage(image);
            this.pathImage = path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stream.close();
                Files.delete(new File(path).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    @FXML
    void sendEmail(ActionEvent event) {
        if (netIsAvailable()) {
            String emailTo = student.getEmail();
            String name = student.getfName() + " " + student.getlName();
            EmailService emailService = new EmailService("montong_200000000677@uic.edu.ph", "2O5NqRJKkjYI4xLf");

            emailService.sendMessage(emailTo, name, pathImage);

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Sent Successfully");
            message.setContentText("Please check your email(" + emailTo + ")");
            message.showAndWait();


        } else {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Internet Status");
            message.setContentText("Internet access is not available");
            message.showAndWait();
        }

    }


    @FXML
    void done(ActionEvent event) {
        qrImage.setImage(null);
        qrImage.getScene().getWindow().hide();
    }

    @FXML
    void saveToFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(qrImage.getScene().getWindow());
        if (selectedDirectory != null) {
            try {
                Files.copy(Paths.get(pathImage), Paths.get(selectedDirectory.getPath()+"/"+fileName), StandardCopyOption.REPLACE_EXISTING);

                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setTitle("Saved");
                message.setContentText("Qr code image have been moved.");
                message.showAndWait();


            } catch (IOException ex) {
                System.out.println("[ERROR]:" + ex);
                ex.printStackTrace();
            }


        }
    }


    public void setStudent(Student student) {
        this.student = student;
    }

    public void setFileName(String name){
        this.fileName = name;
    }
}
