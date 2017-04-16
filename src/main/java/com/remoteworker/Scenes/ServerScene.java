package com.remoteworker.Scenes;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ServerScene {
    private Scene serverScene = null;
    public ServerScene() throws IOException {
        String fxmlFile = "/fxml/server.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        serverScene = new Scene(root);
        //serverScene.getStylesheets().add("/css/main.css");
    }

    public Scene getserverScene(){
        return serverScene;
    }
}
