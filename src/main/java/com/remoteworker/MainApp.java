package com.remoteworker;

import com.remoteworker.Scenes.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{
    public static void main(String[] args) {
       launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("RemoteWorker");
        MainScene mainScene = new MainScene();
        primaryStage.setScene(mainScene.getMainScene());
        primaryStage.setResizable(false);
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.show();
    }
}
