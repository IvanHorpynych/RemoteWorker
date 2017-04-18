package com.remoteworker.Server;

import com.remoteworker.MainFrame;
import java.awt.Robot;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFrame extends javax.swing.JFrame {
    Robot robot;
    ServerWriteImage serverWriteImage;
    ServerReadKeys serverReadKeys;
    public ServerFrame() {
        initComponents();
        try {
            robot = new Robot();
            ServerSocket ss = new ServerSocket(MainFrame.PORT);
            Socket clientSocket = ss.accept();
            serverReadKeys = new ServerReadKeys(robot, clientSocket.getInputStream());
            serverWriteImage = new ServerWriteImage(robot, clientSocket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 341, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 46, Short.MAX_VALUE)
        );

        pack();
    }
}
