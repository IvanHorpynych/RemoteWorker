package com.remoteworker.Client;

import java.awt.event.MouseEvent;
import java.io.PrintWriter;

public class ClientSendMouse implements Runnable {

    Thread sendMouse;
    private final int INTERVAL = 1000 / 5;
    private final PrintWriter writer;
    ImagePanel imagePanel;
    private int mouseX, mouseY;
    private int lastX, lastY;

    private boolean isClosed = false;

    public ClientSendMouse(ImagePanel imagePanel, PrintWriter writer) {
        this.writer = writer;
        this.imagePanel = imagePanel;
        sendMouse = new Thread(this);
        sendMouse.start();
    }

    public void postMouse(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    protected void formMouseMoved(MouseEvent evt) {
        //System.out.println("MousePos: " + evt.getX() + " " + evt.getY());
        //System.out.println("MousePos(Scaled): " + (int) (evt.getX() * imagePanel.getScaleX()) + " " + (int) (evt.getY() * imagePanel.getScaleY()));
        if (sendMouse != null) {
            postMouse((int) (evt.getX() * imagePanel.getScaleX()), (int) (evt.getY() * imagePanel.getScaleY()));
        }
    }

    public void close() {
        isClosed = true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (lastX != mouseX || lastY != mouseY) {
                    writer.println("MOUSE-POS:" + mouseX + ":" + mouseY);
                    writer.flush();
                    System.out.println("mouse-sended:" + mouseX + ":" + mouseY);
                }
                lastX = mouseX;
                lastY = mouseY;
                Thread.sleep(INTERVAL);
            } catch (InterruptedException ex) {
                close();
            }
        }
    }
}
