package org.codx;

import com.google.zxing.WriterException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Services.*;


import java.io.IOException;
import java.net.URISyntaxException;


public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage){

        String tempName = QRCodeService.generateName();
         String qrID = "200000000402";
        try {
            QRCodeService.generateQRCode(qrID,300,300,FileService.defaultPath(tempName));
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }



        EmailService emailService = new EmailService("montong_200000000677@uic.edu.ph","2O5NqRJKkjYI4xLf");
        try {
            emailService.sendMessage("jgacote_200000000402@uic.edu.ph","James Gacote",FileService.defaultPath(tempName));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.exit(0);
//        Parent root = null;
//        try {
//            root = FXMLLoader.load(getClass().getClassLoader().getResource("eventPage.fxml"));
//        }catch (IOException e){
//            e.printStackTrace();
//            System.out.println(e);
//        }
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        Scene scene = new Scene(root);
//
////        scene.setFill(Color.TRANSPARENT);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        primaryStage.setScene(scene);
//        primaryStage.show();


//        DbConnection.connectDb();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
