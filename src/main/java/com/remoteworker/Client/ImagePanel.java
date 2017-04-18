package com.remoteworker.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImagePanel extends JComponent {

    private BufferedImage image;
    private double scaleX = 1;
    private double scaleY = 1;
    private int clientW, clientH;

    public void refreshImage(BufferedImage image) {
        this.image = image;
        this.repaint();
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public int getClientW() {
        return clientW;
    }

    public int getClientH() {
        return clientH;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            return;
        }
        clientW = g.getClipBounds().width;
        clientH = g.getClipBounds().height;
        scaleX = image.getWidth() / (double) clientW;
        scaleY = image.getHeight() / (double) clientH;
        g.drawImage(image, 0, 0, clientW, clientH, this);
    }

}
