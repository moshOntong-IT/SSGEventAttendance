package org.codx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpSuccessfullController implements Initializable {
    @FXML
    private ImageView qrImage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setQrImage(String path){

        try {
            InputStream stream = new FileInputStream(path);
            Image image = new Image(stream);
            qrImage.setImage(new Image(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
