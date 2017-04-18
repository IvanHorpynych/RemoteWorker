package com.remoteworker.Server;

import com.remoteworker.MainFrame;
import java.awt.Robot;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerFrame extends javax.swing.JFrame {

    Robot robot;
    Socket serverSocket;
    ServerWriteImage sendThread;
    ServerReadKeys readThread;

    public ServerFrame() {
        initComponents();
        initConnectoin();
    }

    void initConnectoin() {
        try {
            robot = new Robot();
            ServerSocket ss = new ServerSocket(MainFrame.PORT);
            serverSocket = ss.accept();
            sendThread = new ServerWriteImage(robot, serverSocket.getOutputStream(), serverSocket);
            readThread = new ServerReadKeys(robot, serverSocket.getInputStream(), sendThread);
        } catch (Exception ex) {
            closeMe();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        BlockKeysBtn = new javax.swing.JButton();
        DisconnBnt1 = new javax.swing.JButton();
        BlockMouseBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BlockKeysBtn.setFont(new java.awt.Font("Bodoni MT", 0, 15)); // NOI18N
        BlockKeysBtn.setText("Block Keys");
        BlockKeysBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockKeysBtnActionPerformed(evt);
            }
        });

        DisconnBnt1.setFont(new java.awt.Font("Bodoni MT", 0, 15)); // NOI18N
        DisconnBnt1.setText("Disconnect");
        DisconnBnt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnBnt1ActionPerformed(evt);
            }
        });

        BlockMouseBtn.setFont(new java.awt.Font("Bodoni MT", 0, 15)); // NOI18N
        BlockMouseBtn.setText("Block Mouse");
        BlockMouseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockMouseBtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(DisconnBnt1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BlockMouseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BlockKeysBtn)
                                .addGap(2, 2, 2))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(BlockKeysBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DisconnBnt1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BlockMouseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>                        

    private void BlockKeysBtnActionPerformed(java.awt.event.ActionEvent evt) {
        readThread.setKeysActive(!readThread.isKeyActive());
        BlockKeysBtn.setText(readThread.isKeyActive() ? "Block Keys" : "Unblock Keys");
    }

    public void closeMe() {
        sendThread.close();
        readThread.close();
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setVisible(false); //you can't see me!
        MainFrame.instance.setVisible(true);
        dispose(); //Destroy the JFrame object        
    }
    
    private void DisconnBnt1ActionPerformed(java.awt.event.ActionEvent evt) {
        closeMe();
    }

    private void BlockMouseBtn1ActionPerformed(java.awt.event.ActionEvent evt) {
        readThread.setMouseActive(!readThread.isMouseActive());
        BlockMouseBtn.setText(readThread.isMouseActive() ? "Block Mouse" : "Unblock Mouse");
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton BlockKeysBtn;
    private javax.swing.JButton BlockMouseBtn;
    private javax.swing.JButton DisconnBnt1;
    // End of variables declaration 

}
