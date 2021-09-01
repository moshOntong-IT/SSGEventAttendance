package org.codx;

import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwitchPanel {

    private Parent root;
    private Stage stage;
    private StageTool stageTool;

    public Boolean isMovable = false;
    public void setPanel(String path)
    {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene (root);
            stage.setScene(scene);

            stageTool = new StageTool();
            stageTool.move(root, isMovable);
            stageTool.setStage(stage);
            stage.show();
            new FadeIn(root).play();
        }catch (IOException exception){
            Logger.getLogger(SwitchPanel.class.getName()).log(Level.SEVERE, "Cannot switch to another Page", exception);
        }
    }
    public void setCurrentStageHide(Node node){
        node.getScene().getWindow().hide();
    }
    public void setMovable(Boolean isMovable){
        this.isMovable = isMovable;
        stageTool.move(root, isMovable);

    }
    public void setFullSized (Boolean maximized){
        try {
            stage.setMaximized(maximized);
        }catch (NullPointerException x){
           Logger.getLogger(SwitchPanel.class.getName()).log(Level.SEVERE, "Stage is not initialized");
        }
    }
}
