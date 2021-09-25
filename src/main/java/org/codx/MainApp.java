package org.codx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Services.DbConnection;
import org.codx.Services.EmailService;
import org.codx.Services.FontLoader;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage){
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
        String tempPath = "C:\\Users\\ACER\\IdeaProjects\\SSGEventAttendanec\\src\\main\\resources\\assets\\demoQR.png";
        EmailService emailService = new EmailService("montong_200000000677@uic.edu.ph","2O5NqRJKkjYI4xLf");
        emailService.sendMessage("moshontong@gmail.com","Muslimin Ontong",tempPath);
        DbConnection.connectDb();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
