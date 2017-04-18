package com.remoteworker.Client;

import com.remoteworker.MainFrame;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClientFrame extends javax.swing.JFrame {

    ImagePanel imagePanel;
    ClientSendKeys clientSendKeys;
    ClientReadImage clientReadImage;
    ClientSendMouse clientSendMouse;
    PrintWriter clientWriter;
    Socket clientSocket;

    public ClientFrame() {

        initComponents();
        imagePanel = new ImagePanel();
        this.setContentPane(imagePanel);
        initConnection();
        initListeners(imagePanel);
        setLocationRelativeTo(null);
    }

    void initListeners(ImagePanel imagePanel) {
        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evt) {
                clientSendMouse.formMouseMoved(evt);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                clientSendMouse.formMouseMoved(e);
            }
        });
        imagePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                clientSendKeys.formMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clientSendKeys.formMouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void initConnection() {
        try {
            clientSocket = new Socket(MainFrame.IP, MainFrame.PORT);
            clientWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            clientSendKeys = new ClientSendKeys(clientWriter);
            clientReadImage = new ClientReadImage(imagePanel, clientSocket, clientSendKeys);
            clientSendMouse = new ClientSendMouse(imagePanel, clientWriter);
        } catch (IOException ex) {
            closeMe();
        }
    }

    public void closeMe() {
        setVisible(false); //you can't see me!
        MainFrame.instance.setVisible(true);
        dispose(); //Destroy the JFrame object
        clientReadImage.close();
        clientSendKeys.close();
        clientSendMouse.close();
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeMe();
            }
        });

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                clientSendKeys.formKeyPressed(evt);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                clientSendKeys.formKeyReleased(e);
            }

        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 814, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        pack();
    }
}
