package com.remoteworker.Scenes.Controller;

import com.remoteworker.Scenes.ClientScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Label IPLabel;
    @FXML
    Button ClientConnect;

    public void initialize(URL location, ResourceBundle resources) {


        String buf = null;
        try {
            buf = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
            IPLabel.setText(buf.substring(buf.lastIndexOf('/')+1));
        ClientConnect.setOnAction(event->{
            Stage stage = (Stage)IPLabel.getScene().getWindow();
            System.out.println(stage.hashCode());
        });

    }

}
