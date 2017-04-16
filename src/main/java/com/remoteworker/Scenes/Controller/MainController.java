package com.remoteworker.Scenes.Controller;

import com.remoteworker.Scenes.ClientScene;
import com.remoteworker.Scenes.ServerScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Label IPLabel;
    @FXML
    Button ClientConnBtn;
    @FXML
    Button ServerConnBtn;

    public void initialize(URL location, ResourceBundle resources) {
        String buf;
        try {
            buf = InetAddress.getLocalHost().toString();
            IPLabel.setText(buf.substring(buf.lastIndexOf('/') + 1));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
                ClientConnBtn.setOnAction(event -> {
            Stage primaryStage = (Stage) ClientConnBtn.getScene().getWindow();
            try {
                ClientScene clientScene = new ClientScene(primaryStage);
                primaryStage.setScene(clientScene.getClientScene());

                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                //set Stage boundaries to visible bounds of the main screen
                primaryStage.setX(primaryScreenBounds.getMinX());
                primaryStage.setY(primaryScreenBounds.getMinY());
                primaryStage.setWidth(primaryScreenBounds.getWidth());
                primaryStage.setHeight(primaryScreenBounds.getHeight());
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ServerConnBtn.setOnAction(event -> {
            ServerScene serverScene = null;
            Stage primaryStage = (Stage) ServerConnBtn.getScene().getWindow();
            try {
                serverScene = new ServerScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(serverScene.getserverScene());
            primaryStage.setWidth(205);
            primaryStage.setHeight(68);
            primaryStage.show();
        });
    }

}
