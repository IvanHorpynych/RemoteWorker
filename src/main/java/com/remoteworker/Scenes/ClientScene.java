package com.remoteworker.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientScene {

    private Scene clientScene = null;
    public ClientScene(Stage primaryStage) throws IOException {
        String fxmlFile = "/fxml/client.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        clientScene = new Scene(root);
        ImageView imageView = new ImageView();
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        ((BorderPane)root).setCenter(imageView);
        //clientScene.getStylesheets().add("/css/main.css");
    }

   public Scene getClientScene(){
        return clientScene;
    }
}
