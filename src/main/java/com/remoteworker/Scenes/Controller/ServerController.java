package com.remoteworker.Scenes.Controller;

import com.remoteworker.Scenes.MainScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    @FXML
    Button ServerDisconnBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServerDisconnBtn.setOnAction(event -> {
            Stage primaryStage = (Stage) ServerDisconnBtn.getScene().getWindow();
            try {
                primaryStage.setScene(new MainScene().getMainScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setWidth(600);
            primaryStage.setHeight(400);
            primaryStage.show();
        });
    }
}

