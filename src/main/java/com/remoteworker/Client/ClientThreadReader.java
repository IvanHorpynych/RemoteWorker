package com.remoteworker.Client;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThreadReader implements Runnable {
    private Thread ClientReader = null;
    private Socket fromServer = null;
    private ImageView imageView = null;
    private BufferedInputStream in = null;

    ClientThreadReader(Socket fromServer, ImageView imageView) {
        this.fromServer = fromServer;
        this.imageView = imageView;
        try {
            in = new BufferedInputStream(this.fromServer.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientReader = new Thread(this);
        ClientReader.start();
    }

    BufferedImage wClientReader() throws IOException {
        return ImageIO.read(in);
    }

    BufferedInputStream getIn() {
        return in;
    }

    Thread getClientReader(){
        return ClientReader;
    }

    public void run() {
        BufferedImage bufferImage = null;
        while (true) {
            try {
                System.out.println("ClientReader start!!!");
                bufferImage = wClientReader();
                imageView.setImage(SwingFXUtils.toFXImage(bufferImage, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
