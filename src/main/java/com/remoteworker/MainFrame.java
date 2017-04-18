
package com.remoteworker;

import com.remoteworker.Client.ClientFrame;
import com.remoteworker.Server.ServerFrame;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainFrame extends javax.swing.JFrame {

    public static int PORT = 1488;
    public static String IP = "127.0.0.1";

    public MainFrame() {
        initComponents();
        try {
            txtAddress.setText(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLocationRelativeTo(null);
    }


    private void initComponents() {

        btnClient = new javax.swing.JButton();
        btnServer = new javax.swing.JButton();
        txtAddress = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnClient.setText("Client");
        btnClient.addActionListener(evt-> {
                btnClientActionPerformed(evt);
            });

        btnServer.setText("Server");
        btnServer.addActionListener(evt-> {
                btnServerActionPerformed(evt);
        });

        txtAddress.setText("127.0.0.1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAddress)
                    .addComponent(btnClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnServer, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnServer)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pack();
    }

    private void btnClientActionPerformed(java.awt.event.ActionEvent evt) {
        IP = txtAddress.getText();
        new ClientFrame().setVisible(true);
        this.setVisible(false);
    }

    private void btnServerActionPerformed(java.awt.event.ActionEvent evt) {
        new ServerFrame().setVisible(true);
        this.setVisible(false);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnClient;
    private javax.swing.JButton btnServer;
    private javax.swing.JTextField txtAddress;
}
