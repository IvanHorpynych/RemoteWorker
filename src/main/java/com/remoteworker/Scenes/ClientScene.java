package com.remoteworker.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Created by Иван on 4/16/2017.
 */
public class ClientScene {

    private Scene clientScene = null;
    public ClientScene() throws IOException {
        String fxmlFile = "/fxml/client.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        clientScene = new Scene(root);
        //clientScene.getStylesheets().add("/css/main.css");
    }
}
