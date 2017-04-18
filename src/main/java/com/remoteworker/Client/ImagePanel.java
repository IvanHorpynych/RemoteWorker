package com.remoteworker.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImagePanel extends JComponent {
    private BufferedImage image;
    double scaleX = 1;
    double scaleY = 1;

    public void refreshImage(BufferedImage image) {
        this.image = image;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            return;
        }
        int frameWidth = g.getClipBounds().width;
        int frameHeight = g.getClipBounds().height;
        scaleX = image.getWidth() / (double) frameWidth;
        scaleY = image.getHeight() / (double) frameHeight;
        g.drawImage(image, 0, 0, frameWidth, frameHeight, this);
    }
}
