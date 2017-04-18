package com.remoteworker;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import javax.imageio.ImageIO;


public class ServerFrame extends javax.swing.JFrame {

    static class ServerRunnable implements Runnable {

        private Robot robot;
        private OutputStream os;

        public ServerRunnable(Robot robot, OutputStream os) {
            this.robot = robot;
            this.os = os;
        }

        @Override
        public void run() {
            try {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));

                while (true) {
                    BufferedImage writedImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(writedImage, "jpg", baos);
                    writer.println(Base64.getEncoder().encodeToString(baos.toByteArray()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static class ServerReadKeys implements Runnable {

        private Robot robot;
        private InputStream is;

        public ServerReadKeys(Robot robot, InputStream is) {
            this.robot = robot;
            this.is = is;
        }

        @Override
        public void run() {
            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while (true) {
                    String line = reader.readLine();

                    if (line.startsWith("KEY")) {
                        int keyCode = Integer.parseInt(line.split(":")[1]);
                        robot.keyPress(keyCode);
                        robot.keyRelease(keyCode);
                        System.out.println("KeyPressed: " + keyCode);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    Robot robot;

    public ServerFrame() {

        initComponents();

        try {

            robot = new Robot();

            ServerSocket ss = new ServerSocket(MainFrame.PORT);

            Socket clientSocket = ss.accept();
            new Thread(new ServerRunnable(robot, clientSocket.getOutputStream())).start();
            new Thread(new ServerReadKeys(robot, clientSocket.getInputStream())).start();

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
