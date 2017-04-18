package com.remoteworker.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

class ClientReadImage implements Runnable {

    Thread clientReadImage;
    ClientSendKeys clientSendKeys;
    ImagePanel imagePanel;
    Socket clientSocket;       
    
    private boolean isClosed = false;

    public ClientReadImage(ImagePanel imagePanel, Socket clientSocket, ClientSendKeys clientSendKeys) {
        this.imagePanel = imagePanel;
        this.clientSocket = clientSocket;
        this.clientSendKeys = clientSendKeys;
        clientReadImage = new Thread(this);
        clientReadImage.start();
    }
    
    public void close() {
        isClosed = true;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientSocket.setTcpNoDelay(true);
            while (!isClosed) {
                String imageBase64 = reader.readLine();
                byte[] buf = Base64.getDecoder().decode(imageBase64);
                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                BufferedImage readedImage = ImageIO.read(bais);
                imagePanel.refreshImage(readedImage);                
                System.out.println("Image readed");
                
                //clientSendKeys.postEvent("GIVE-SCREEN:" + imagePanel.getClientW() + ":" + imagePanel.getClientH());                
                clientSendKeys.postEvent("GIVE-SCREEN");
                System.out.println("Image asked");
                
                Thread.sleep(1000 / 30);
            }
        } catch (Exception ex) {
            close();
        }
    }
}
