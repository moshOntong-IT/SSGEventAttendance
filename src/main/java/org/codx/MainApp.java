package org.codx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.codx.Services.FontLoader;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("loadingPage.fxml"));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e);
        }
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
//        scene.setFill(Color.TRANSPARENT);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        FontLoader fontLoader = new FontLoader();

        System.out.println("[INFO]: Avanti is now running....");
        System.out.println("[INFO]: Fetching all available font on your system....");
        fontLoader.load(Constant.fontString,Constant.fontStringPath);
        fontLoader.load(Constant.fontString1,Constant.fontStringPath1);
        fontLoader.load(Constant.fontString2,Constant.fontStringPath2);
        fontLoader.load(Constant.fontString3,Constant.fontStringPath3);

        launch(args);
    }
}
