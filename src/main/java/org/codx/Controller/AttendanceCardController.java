package org.codx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceCardController implements Initializable {

    @FXML
    private HBox availabilityBox;

    @FXML
    private Label activeStatus;

    @FXML
    private Label absentStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void attend(ActionEvent event) {
        Parent root = absentStatus.getScene().getRoot();
        ColorAdjust adj = new ColorAdjust(0, 0, -0.8, 0);
        GaussianBlur blur = new GaussianBlur(10);
        adj.setInput(blur);
        root.setEffect(adj);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("eventPage.fxml"));
            Parent form = loader.load();
//            AnnounceFormController controller = loader.getController();
//            controller.setUserInformation(userinfo);
//            controller.SetFullName(userinfo.getFullName());
            Stage stage = new Stage();
            Scene scene = new Scene(form);
            form.requestFocus();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.showAndWait();

            root.setEffect(null);

        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
