package com.remoteworker.Client;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;


class ClientReadImage implements Runnable {
    Thread clientReadImage;
    ImagePanel imagePanel;
    InputStream is;

    public ClientReadImage(ImagePanel imagePanel, InputStream is) {
        this.imagePanel = imagePanel;
        this.is = is;
        clientReadImage = new Thread(this);
        clientReadImage.start();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while (true) {
                String imageBase64 = reader.readLine();
                byte[] buf = Base64.getDecoder().decode(imageBase64);
                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                BufferedImage readedImage = ImageIO.read(bais);
                imagePanel.refreshImage(readedImage);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

