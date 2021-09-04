package org.codx.Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import org.codx.SwitchPanel;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {

    @FXML
    private ProgressBar progressBarIndicator;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadProgress();
    }

    private void loadProgress(){
        Task<Void> loadingData = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 1; i <= 100; i++){
                    updateProgress(i, 100);
                    Thread.sleep(100);
                }

                return null;
            }

        };

        loadingData.setOnSucceeded( event -> {
            SwitchPanel landingPanel = new SwitchPanel();
            landingPanel.setPanel("landingPage.fxml");
            landingPanel.setCurrentStageHide(progressBarIndicator);
            landingPanel.setMovable(true);
        });

        progressBarIndicator.progressProperty().unbind();
        progressBarIndicator.progressProperty().bind(loadingData.progressProperty());

        Thread runProgress = new Thread(loadingData);
        runProgress.setDaemon(true);
        runProgress.start();
    }
}
