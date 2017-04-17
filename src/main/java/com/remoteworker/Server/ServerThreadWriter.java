package com.remoteworker.Server;



import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThreadWriter implements Runnable{
    private Thread serverWriter = null;
    private Socket fromClient = null;
    private ImageOutputStream out = null;

    ServerThreadWriter(Socket fromClient){
        this.fromClient = fromClient;
        try {
            out = ImageIO.createImageOutputStream(new BufferedOutputStream(this.fromClient.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverWriter = new Thread(this);
        serverWriter.start();
    }

    void wServerWriter(BufferedImage bi) throws IOException {
        ImageIO.write(bi, "jpg", out);
        out.flush();
    }

    Thread getServerWriter(){
        return serverWriter;
    }

    ImageOutputStream getOut(){
        return out;
    }
    public void run() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage bi = null;
        while (true){
            bi = robot.createScreenCapture(screenRect);
            try {
                wServerWriter(bi);
                Thread.sleep(1000/30);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
