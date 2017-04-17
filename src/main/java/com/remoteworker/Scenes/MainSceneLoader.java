package com.remoteworker.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainSceneLoader {
    private Scene mainScene = null;
    public MainSceneLoader() throws IOException {
        String fxmlFile = "/fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        mainScene = new Scene(root,600,400);
        mainScene.getStylesheets().add("/css/main.css");
    }

    public Scene getMainScene(){
        return mainScene;
    }
}
