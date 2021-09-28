package org.codx;

import animatefx.animation.FadeIn;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StageTool {

    private double x;
    private double y;

    private static double xStatic;
    private static  double yStatic;
    private Stage stage;
    private Parent root;



    public StageTool(){

    }
    public StageTool(String fxmlPath){


        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(fxmlPath)));
            root = loader.load();


            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene (root);
            stage.setScene(scene);

            stage.show();

            new FadeIn(root).play();
        }catch (IOException exception){
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }
    }

    public void setOnMovable (){

            this.root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    x = mouseEvent.getSceneX();
                    y = mouseEvent.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - x);
                    stage.setY(mouseEvent.getScreenY()- y);

                }
            });

    }

    public static void setOnMovable (Parent root, Stage stage){

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xStatic = mouseEvent.getSceneX();
                yStatic = mouseEvent.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xStatic);
                stage.setY(mouseEvent.getScreenY()- yStatic);

            }
        });

    }


    public void hide(Stage stage){
        stage.hide();
    }

    //method for Stop all running including stage
    public void setSystemStop(){
        System.exit(0);
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage= stage;
    }



    public void setFullSized (Boolean maximized){
        try {
            stage.setMaximized(maximized);
        }catch (NullPointerException x){
            Logger.getLogger(StageTool.class.getName()).log(Level.SEVERE, "Stage is not initialized");
        }
    }

}
