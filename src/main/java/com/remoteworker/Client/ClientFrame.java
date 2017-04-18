package com.remoteworker.Client;

import com.remoteworker.MainFrame;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.*;

public class ClientFrame extends javax.swing.JFrame {

    ImagePanel imagePanel;
    ClientSendKeys clientSendKeys;
    ClientReadImage clientReadImage;

    public ClientFrame() {

        initComponents();

         imagePanel = new ImagePanel();
        this.setContentPane(imagePanel);

        try {
            Socket s = new Socket(MainFrame.IP, MainFrame.PORT);
            clientSendKeys = new ClientSendKeys(s.getOutputStream());
            clientReadImage = new ClientReadImage(imagePanel, s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void formKeyPressed(KeyEvent evt) {
        System.out.println("KeyCode: " + evt.getKeyCode());
        System.out.println("KeyChar: " + evt.getKeyChar());
        if (clientSendKeys != null) {
            clientSendKeys.postEvent("KEY:" + evt.getKeyCode());
        }
    }

    private void formMouseMoved(MouseEvent evt) {
        System.out.println("MousePos: " + evt.getX() + " " + evt.getY());
        System.out.println("MousePos(Scaled): " + (int) (evt.getX() * imagePanel.scaleX) + " " + (int) (evt.getY() * imagePanel.scaleY));
        if (clientSendKeys != null) {
            clientSendKeys.postEvent("MOUSE-POS:" + (int) (evt.getX() * imagePanel.scaleX) + "," + (int) (evt.getY() * imagePanel.scaleY));
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
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
    }

}
