package org.codx.Controller;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.codx.Services.QRCodeService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class EventPageController implements Initializable {

    @FXML
    private ImageView scannerQR;

    private Webcam webcam;

    private boolean isStopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private QRCodeService qrDecoder = new QRCodeService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init_camera();
    }


    private void init_camera(){

        Task<Void> webCamTask = new Task<Void>() {
            @Override
            protected Void call() {
                webcam = Webcam.getDefault();
                webcam.setCustomViewSizes(new Dimension(300,600)); // register custom resolutions
                webcam.setViewSize(new Dimension(300,600));
                webcam.open();
                startWebCamStream();


                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    private void startWebCamStream(){
        Task<Void> streamTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final AtomicReference<WritableImage> ref =new AtomicReference<>();
                BufferedImage imgBuffered;

                while(!isStopCamera){
                    try {
                        if ((imgBuffered = webcam.getImage()) != null){
                            ref.set(SwingFXUtils.toFXImage(imgBuffered, ref.get()));
                            imgBuffered.flush();
                            Platform.runLater(() -> imageProperty.set(ref.get()));
                            String scanResult = qrDecoder.decodeQRCode(imgBuffered);
                            System.out.println(scannerQR.getFitHeight());
                            if (scanResult!=null){

                                System.out.println(webcam.getCustomViewSizes());
                                webcam.close();
//                                System.exit(0);
                            }

                        }
                    }catch (Exception e){

                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        Thread th = new Thread(streamTask);
        th.setDaemon(true);
        th.start();

        scannerQR.imageProperty().bind(imageProperty);
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) scannerQR.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }
}
