package org.codx;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StageTool {

    private double x;
    private double y;
    private Stage stage;

    public void move (Parent root, Boolean movable){
        if (movable){
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
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
    }

    public void setMinimize(){
        stage.setIconified(true);
    }
    //method for Stop all running including stage
    public void setSystemStop(){
        System.exit(0);
    }
    public void setExitModal(){
        stage.close();
    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
