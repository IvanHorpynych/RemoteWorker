package com.remoteworker.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Base64;

class ServerWriteImage implements Runnable {
    Thread serverWriteImage;
    private Robot robot;
    private OutputStream os;

    public ServerWriteImage(Robot robot, OutputStream os) {
        this.robot = robot;
        this.os = os;
        serverWriteImage = new Thread(this);
        serverWriteImage.start();
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));

            while (true) {
                BufferedImage writedImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(writedImage, "jpg", baos);
                writer.println(Base64.getEncoder().encodeToString(baos.toByteArray()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
