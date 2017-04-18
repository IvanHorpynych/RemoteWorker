package com.remoteworker.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

class ServerWriteImage implements Runnable {

    private Thread serverWriteImage;
    private final Robot robot;
    private final OutputStream os;
    private final Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    private final Socket serverSocket;

    private boolean isSended = false;
    private int clientW, clientH;
    
    private boolean isClosed = false;

    public ServerWriteImage(Robot robot, OutputStream os, Socket serverSocket) {
        this.robot = robot;
        this.os = os;
        this.serverSocket = serverSocket;
        serverWriteImage = new Thread(this);
        serverWriteImage.start();
    }

    public void sendScreen() {
        isSended = false;
    }
    
    public void sendScreen(int w, int h) {
        clientH = h;
        clientW = w;
        sendScreen();
    }

    public static BufferedImage scaleScreen(Image img, int w, int h) {

        if (w == 0 || h == 0) {
            return (BufferedImage) img;
        }

        img = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TRANSLUCENT);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public void close() {
        isClosed = true;
    }
    
    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
            serverSocket.setTcpNoDelay(true);
            while (!isClosed) {

                if (isSended) {
                    Thread.sleep(10);
                    continue;
                }

                BufferedImage writedImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

//                writedImage = scaleScreen(writedImage, clientW, clientH);
//                System.out.println("Image scaled " + clientW + "x" + clientH);

                ImageIO.write(writedImage, "jpg", baos);
                writer.println(Base64.getEncoder().encodeToString(baos.toByteArray()));
                writer.flush();
                System.out.println("Image sended");

                isSended = true;
            }
        } catch (Exception ex) {
            close();
        }
    }
}
