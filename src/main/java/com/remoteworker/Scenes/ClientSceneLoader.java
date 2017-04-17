package com.remoteworker.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientSceneLoader {

    private Scene clientScene = null;
    public ClientSceneLoader(Stage primaryStage) throws IOException {
        String fxmlFile = "/fxml/client.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        clientScene = new Scene(root);
        ImageView imageView =(ImageView) clientScene.lookup("#imgView");
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        ((BorderPane)root).setCenter(imageView);
        //clientScene.getStylesheets().add("/css/main.css");
    }

   public Scene getClientScene(){
        return clientScene;
    }
}
