package com.remoteworker.Server;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class ServerReadKeys implements Runnable {

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
