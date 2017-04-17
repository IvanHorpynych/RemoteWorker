package com.remoteworker;

import com.remoteworker.Scenes.MainSceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{
    public static void main(String[] args) {
       launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("RemoteWorker");
        MainSceneLoader mainScene = new MainSceneLoader();
        primaryStage.setScene(mainScene.getMainScene());
        primaryStage.setResizable(false);
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.show();
    }
}
