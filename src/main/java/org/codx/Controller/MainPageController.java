package org.codx.Controller;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private Button eventButton;

    @FXML
    private ImageView eventIcon;

    @FXML
    private Button systemRoleButton;

    @FXML
    private ImageView systemRoleIcon;

    @FXML
    private Button studentButton;

    @FXML
    private ImageView studentIcon;

    @FXML
    private Button departmentButton;

    @FXML
    private ImageView departmentIcon;

    @FXML
    private StackPane stackLayout;

    @FXML
    private Pane pink;

    @FXML
    private Pane red;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void menuNavAction(ActionEvent event) {



        ArrayList<Image> imagePath = new ArrayList<>();
        imagePath.add(new Image("assets/icons8-event-24 (2).png"));
        imagePath.add(new Image("assets/system-pink.png"));
        imagePath.add(new Image("assets/users-pink.png"));
        imagePath.add(new Image("assets/department-icon.png"));

        ArrayList<Image> imageHighlightPath = new ArrayList<>();
        imageHighlightPath.add(new Image("assets/icons8-event-24.png"));
        imageHighlightPath.add(new Image("assets/system-white.png"));
        imageHighlightPath.add(new Image("assets/users-white.png"));
        imageHighlightPath.add(new Image("assets/department-white.png"));

        eventButton.getStyleClass().clear();
        systemRoleButton.getStyleClass().clear();
        studentButton.getStyleClass().clear();
        departmentButton.getStyleClass().clear();

        eventButton.getStyleClass().add("menu-nav-left-btn");
        systemRoleButton.getStyleClass().add("menu-nav-left-btn");
        studentButton.getStyleClass().add("menu-nav-left-btn");
        departmentButton.getStyleClass().add("menu-nav-left-btn");

        eventIcon.setImage(imagePath.get(0));
        systemRoleIcon.setImage(imagePath.get(1));
        studentIcon.setImage(imagePath.get(2));
        departmentIcon.setImage(imagePath.get(3));

        if (event.getSource() == eventButton) {
            pink.toFront();
            eventIcon.setImage(imageHighlightPath.get(0));
            eventButton.getStyleClass().clear();
            eventButton.getStyleClass().add("menu-nav-left-btn-highlight");

        } else if (event.getSource() == systemRoleButton) {
            red.toFront();
            systemRoleIcon.setImage(imageHighlightPath.get(1));
            systemRoleButton.getStyleClass().clear();
            systemRoleButton.getStyleClass().add("menu-nav-left-btn-highlight");

        } else if (event.getSource() == studentButton) {
            studentIcon.setImage(imageHighlightPath.get(2));
            studentButton.getStyleClass().clear();
            studentButton.getStyleClass().add("menu-nav-left-btn-highlight");

        } else if (event.getSource() == departmentButton) {
            departmentIcon.setImage(imageHighlightPath.get(3));
            departmentButton.getStyleClass().clear();
            departmentButton.getStyleClass().add("menu-nav-left-btn-highlight");

        }

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) eventButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

}
