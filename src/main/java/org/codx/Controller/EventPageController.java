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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.codx.Services.QRCodeService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class EventPageController implements Initializable {

    @FXML
    private ImageView scannerQR;

    private Webcam webcam;

    private boolean isStopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private QRCodeService qrDecoder = new QRCodeService();


//    private static String scanResult = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init_camera();
    }


    private void init_camera() {

        Task<Void> webCamTask = new Task<Void>() {
            @Override
            protected Void call() {

                webcam = Webcam.getDefault();
                System.out.println("[INFO]" + webcam);
                if (webcam != null) {
                    System.out.println("[INFO]: Initializing camera....");
                    webcam.setCustomViewSizes(new Dimension[]{new Dimension(300, 600)}); // register custom resolutions
                    webcam.setViewSize(new Dimension(300, 600));
                    webcam.open();
                    startWebCamStream();
                    System.out.println("[INFO]: Qr scanner is ready to use....");
                } else {
                    System.out.println("[ERROR]: No camera detect");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setHeaderText("Camera Status");
                            error.setContentText("There is no camera installed on the computer.");

                            Optional<ButtonType> result = error.showAndWait();
                            System.out.println(result);
                            if (result.get() == ButtonType.OK) {

                                Stage stage = (Stage) scannerQR.getScene().getWindow();
                                // do what you have to do

                                stage.close();
                            }


                        }
                    });

                }


                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    private void startWebCamStream() {

        Task<Void> streamTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage imgBuffered;

                while (!isStopCamera) {
                    try {
                        if ((imgBuffered = webcam.getImage()) != null) {
                            ref.set(SwingFXUtils.toFXImage(imgBuffered, ref.get()));
                            imgBuffered.flush();
                            Platform.runLater(() -> imageProperty.set(ref.get()));
                            String scanResult = qrDecoder.decodeQRCode(imgBuffered);

                            if (scanResult != null) {
                                isStopCamera = true;
                                if (isStopCamera) {

                                    webcam.close();
                                    System.out.println("[INFO] QR Code Result:" + scanResult);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {

                                            Alert qrStringAlert = new Alert(Alert.AlertType.INFORMATION);
                                            qrStringAlert.setContentText("Result: " + scanResult);


                                            Optional<ButtonType> result = qrStringAlert.showAndWait();

                                            if (result.get() == ButtonType.OK) {

                                                Stage stage = (Stage) scannerQR.getScene().getWindow();
                                                // do what you have to do

                                                stage.close();
                                            }
                                        }

                                    });


//                                System.out.println(scanResult);

//                                System.exit(0);
                                }
                            }

                        }
                    } catch (Exception e) {

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
    void back(ActionEvent event) {
        scannerQR.getScene().getWindow().hide();
    }

}
