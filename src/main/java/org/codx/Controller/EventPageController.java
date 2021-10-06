package org.codx.Controller;

import Builder.EventConvert;
import com.github.sarxos.webcam.Webcam;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.codx.Model.AttendanceSchedule;
import org.codx.Model.EventInfo;
import org.codx.Model.LoginHistory;
import org.codx.Services.DbConnection;
import org.codx.Services.EmailService;
import org.codx.Services.QRCodeService;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class EventPageController implements Initializable {

    private Connection conn;
    private String studentName;
    private LoginHistory adminLog;
    private EventInfo eventInfo;
    private AttendanceSchedule sched;

    private Thread th;
    private Thread webCamThread;

    private boolean isMorningActive;
    private boolean isNoonActive;

    private String timeNow;
    private String dateNow;
    private String eventTimeTarget;
    private boolean schedResult;

    private String activeColor = "#1F28CF";
    private String unActiveColor = "#969696";


    @FXML
    private Label morningLabel;

    @FXML
    private Line morningEntranceLine;

    @FXML
    private Text morningEntranceLabel;

    @FXML
    private Line morningExitLine;

    @FXML
    private Text morningExitLabel;

    @FXML
    private Line afternoonLine;

    @FXML
    private Label afternoonLabel;

    @FXML
    private Line entranceNoonLine;

    @FXML
    private Text entranceNoonLabel;

    @FXML
    private Line exitNoonLine;

    @FXML
    private Text exitNoonLabel;

    @FXML
    private ImageView scannerQR;

    @FXML
    private Label title;



    private Webcam webcam;

    private boolean isStopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private QRCodeService qrDecoder = new QRCodeService();


//    private static String scanResult = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = DbConnection.connectDb();


        init_camera();
    }


    private void day_line() {
        isMorningActive = false;
        isNoonActive = false;
//        System.out.println(eventInfo);
        if (!eventInfo.getMorning_begin().equals("")) {
            morningLabel.setTextFill(Color.web("#FE6174"));
//            System.out.println("true");
            isMorningActive = true;

        } else {
            morningLabel.setTextFill(Color.web(unActiveColor));
        }

        if (!eventInfo.getAfternoon_begin().equals("")) {
            afternoonLabel.setTextFill(Color.web("#FE6174"));
            isNoonActive = true;
        } else {
            afternoonLabel.setTextFill(Color.web(unActiveColor));
        }

        if (!isMorningActive) {
            morningEntranceLine.setStroke(Color.web(unActiveColor));
            morningEntranceLabel.setFill(Color.web(unActiveColor));

            morningExitLine.setStroke(Color.web(unActiveColor));
            morningExitLabel.setFill(Color.web(unActiveColor));

            afternoonLine.setStroke(Color.web(unActiveColor));
        }

        if (!isNoonActive) {
            entranceNoonLine.setStroke(Color.web(unActiveColor));
            entranceNoonLabel.setFill(Color.web(unActiveColor));

            exitNoonLine.setStroke(Color.web(unActiveColor));
            exitNoonLabel.setFill(Color.web(unActiveColor));
        }

        setSched();


    }

    private void setSched() {
        if (isMorningActive) {
            //pag karung oras is less than sa morning end then
            //ang sched is Morning entrance pag dli it means morning exit
            getSched(eventInfo.getMorning_end());
//            System.out.println(schedResult);
            if (schedResult) {
                sched = AttendanceSchedule.MorningEntrance;
                morningEntranceLabel.setFill(Color.web(activeColor));
                morningEntranceLine.setStroke(Color.web(activeColor));
            } else {
                morningEntranceLabel.setFill(Color.web(activeColor));
                morningEntranceLabel.setStrikethrough(true);
                morningEntranceLine.setStroke(Color.web(unActiveColor));

                //pero pag mag morning exit lang if less than pa sya sa afternoon begin
                // if active ang afternoon
                if(isNoonActive){
                    getSched(eventInfo.getAfternoon_begin());
                    if(schedResult){
                        sched = AttendanceSchedule.MorningExit;
                        morningExitLabel.setFill(Color.web(activeColor));
                        morningExitLine.setStroke(Color.web(activeColor));
                    }else{
                        morningExitLabel.setFill(Color.web(activeColor));
                        morningExitLabel.setStrikethrough(true);
                        morningExitLine.setStroke(Color.web(unActiveColor));
                        afternoonLine.setStroke(Color.web(activeColor));
                        //pag si afternoon begin less than pa sa afternoon end therefore
                        //ang attencance karun is afternoon entrance pag dli kay afternoon exit
                        afternoonProccessSched();
                    }
                }
            }
        }else{
            afternoonProccessSched();
        }
    }

    private void afternoonProccessSched(){
        getSched(eventInfo.getAfternoon_end());
        if(schedResult){
            sched = AttendanceSchedule.NoonEntrance;
            entranceNoonLabel.setFill(Color.web(activeColor));
            entranceNoonLine.setStroke(Color.web(activeColor));
        }else{
            sched = AttendanceSchedule.NoonExit;
            entranceNoonLabel.setFill(Color.web(activeColor));
            entranceNoonLabel.setStrikethrough(true);
            entranceNoonLine.setStroke(Color.web(unActiveColor));

            exitNoonLabel.setFill(Color.web(activeColor));
            exitNoonLine.setStroke(Color.web(activeColor));
        }
    }

    private void getSched(String eventTime) {
        try {
            EventConvert.parseDate((date) -> this.dateNow = date);
            EventConvert.parseAATime(dateNow, (timeNow) -> this.timeNow = timeNow);
            EventConvert.parseAATime(eventTime,(target)-> this.eventTimeTarget = target);

            schedResult = EventConvert.lessThan(timeNow, eventTimeTarget);
//            System.out.println(timeNow +" < "+ eventTimeTarget+":"+ schedResult);
        }catch (ParseException ex){
            ex.printStackTrace();
        }
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

        webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    private void startWebCamStream() {

        Task<Void> streamTask = new Task<Void>() {
            @Override
            protected Void call() {
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
                                    Platform.runLater(() -> {
                                        String preReplace = scanResult.replaceAll("[^0-9]", "");
                                        String replace = preReplace.replaceAll("[\\t\\n\\r]+", " ");

                                        System.out.println("[INFO] QR Code Number formatted Result:" + replace);
                                        long userID = Long.parseLong(replace.equals("") ? "0" : replace);

                                        present(userID);

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

        th = new Thread(streamTask);
        th.setDaemon(true);
        th.start();


        scannerQR.imageProperty().bind(imageProperty);
    }

    private void present(long userId) {

//        System.out.println("run...");
        if (checkStudent(userId)) {
            Parent root = scannerQR.getScene().getRoot();
            ColorAdjust adj = new ColorAdjust(0, 0, -0.8, 0);
            GaussianBlur blur = new GaussianBlur(10);
            adj.setInput(blur);
            root.setEffect(adj);
            FXMLLoader loader = new FXMLLoader(EventPageController.class.getClassLoader().getResource("eventMessageDone.fxml"));
            try {
                Parent form = loader.load();
                form.requestFocus();

                EventMessageDoneController controller = loader.getController();
                controller.setText(studentName);

                Scene scene = new Scene(form);

                Stage stage = new Stage();
                stage.setScene(scene);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);

                stage.showAndWait();
                root.setEffect(null);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showErrorMessage();
        }


        scannerQR.getScene().getWindow().hide();

    }

    private void showErrorMessage() {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Invalid");
        message.setContentText("Qr code is invalid");

        message.showAndWait();
    }

    private boolean checkStudent(long userId) {
        boolean isExist = false;
        try {
            PreparedStatement stmt = conn.prepareStatement("Select u.user_id, s.first_name," +
                    " s.last_name from user_info u" +
                    " inner join student_info s " +
                    " on u.user_id = s.user_id " +
                    "where u.user_id =?");
            stmt.setLong(1, userId);
            ResultSet rst = stmt.executeQuery();
            if (rst.next()) {
                studentName = rst.getString("first_name") + " " + rst.getString("last_name");
                isExist = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    @FXML
    void back(ActionEvent event) {
        scannerQR.getScene().getWindow().hide();

        if (webcam != null) {
            webcam.close();
        }

//        webCamThread.interrupt();
//        th.interrupt();
    }

    public void setAdminLog(LoginHistory adminLog) {
        this.adminLog = adminLog;
    }


    public void setEventInfo(EventInfo eventInfo) {
        this.eventInfo = eventInfo;
        title.setText(eventInfo.getEvent_name());

        Platform.runLater(() -> {
            day_line();
        });
    }
}
