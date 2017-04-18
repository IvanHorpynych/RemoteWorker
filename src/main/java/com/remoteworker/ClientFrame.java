package com.remoteworker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ClientFrame extends javax.swing.JFrame {

    class ImagePanel extends JComponent {

        private BufferedImage image;

        public void refreshImage(BufferedImage image) {
            this.image = image;
            repaint();
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

    static class ClientReadImage implements Runnable {

        ImagePanel imagePanel;
        InputStream is;

        public ClientReadImage(ImagePanel imagePanel, InputStream is) {
            this.imagePanel = imagePanel;
            this.is = is;
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
                    //BufferedImage readedImage = ImageIO.read(ImageIO.createImageInputStream(clientSocket.getInputStream()));
                    imagePanel.refreshImage(readedImage);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    static class ClientSendKeys implements Runnable {

        private ConcurrentLinkedQueue<String> eventQueue;
        private OutputStream os;

        public ClientSendKeys(OutputStream os) {
            this.eventQueue = new ConcurrentLinkedQueue<>();
            this.os = os;
        }

        public void postEvent(String event) {
            eventQueue.offer(event);
        }

        @Override
        public void run() {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
            while (true) {
                String poll = eventQueue.poll();
                writer.println(poll);
            }
        }

    }

    ImagePanel imagePanel;
    ClientSendKeys clientSendKeys;
    double scaleX = 1, scaleY = 1;

    public ClientFrame() {

        initComponents();

        imagePanel = new ImagePanel();
        this.setContentPane(imagePanel);

        try {
            Socket s = new Socket(MainFrame.IP, MainFrame.PORT);

            clientSendKeys = new ClientSendKeys(s.getOutputStream());

            new Thread(new ClientReadImage(imagePanel, s.getInputStream())).start();
            new Thread(clientSendKeys).start();
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        System.out.println("KeyCode: " + evt.getKeyCode());
        System.out.println("KeyChar: " + evt.getKeyChar());
        if (clientSendKeys != null) {
            clientSendKeys.postEvent("KEY:" + evt.getKeyCode());
        }
    }//GEN-LAST:event_formKeyPressed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {
        System.out.println("MousePos: " + evt.getX() + " " + evt.getY());
        System.out.println("MousePos(Scaled): " + (int) (evt.getX() * scaleX) + " " + (int) (evt.getY() * scaleY));
        if (clientSendKeys != null) {
            clientSendKeys.postEvent("MOUSE-POS:" + (int) (evt.getX() * scaleX) + "," + (int) (evt.getY() * scaleY));
        }
    }
}
