package com.remoteworker.Server;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class ServerReadKeys implements Runnable {

    private Thread serverReadKeys;
    private ServerWriteImage sendThread;
    private Robot robot;
    private InputStream is;

    private boolean ketActive = true;
    private boolean mouseActive = true;
    
    private boolean isClosed = false;

    public ServerReadKeys(Robot robot, InputStream is, ServerWriteImage sendThread) {
        this.robot = robot;
        this.is = is;
        this.sendThread = sendThread;
        serverReadKeys = new Thread(this);
        serverReadKeys.start();
    }

    public void setKeysActive(boolean flag) {
        ketActive = flag;
    }

    public void setMouseActive(boolean flag) {
        mouseActive = flag;
    }

    public boolean isKeyActive() {
        return ketActive;
    }

    public boolean isMouseActive() {
        return mouseActive;
    }

    public void close() {
        isClosed = true;
    }
    
    @Override
    public void run() {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while (!isClosed) {
                String line = reader.readLine();
//                if (line.startsWith("GIVE-SCREEN")) {
//                    int w = Integer.parseInt(line.split(":")[1]);
//                    int h = Integer.parseInt(line.split(":")[2]);
//                    sendThread.sendScreen(w, h);
//                }
                if (line.startsWith("GIVE-SCREEN")) {
                    sendThread.sendScreen();
                }

                if (ketActive) {
                    if (line.startsWith("KEY-PRESSED")) {
                        int keyCode = Integer.parseInt(line.split(":")[1]);
                        robot.keyPress(keyCode);
                        System.out.println("KeyPressed: " + keyCode);
                    }
                    if (line.startsWith("KEY-RELEASED")) {
                        int keyCode = Integer.parseInt(line.split(":")[1]);
                        robot.keyRelease(keyCode);
                        System.out.println("KeyReleased: " + keyCode);
                    }
                }

                if (mouseActive) {
                    if (line.startsWith("MOUSE-POS")) {
                        int x = Integer.parseInt(line.split(":")[1]);
                        int y = Integer.parseInt(line.split(":")[2]);
                        robot.mouseMove(x, y);
                    }
                    if (line.startsWith("MOUSE-PRESSED")) {
                        int keyCode = Integer.parseInt(line.split(":")[1]);
                        switch (keyCode) {
                            case 1:
                                keyCode = MouseEvent.BUTTON1_MASK;
                                break;
                            case 2:
                                keyCode = MouseEvent.BUTTON2_MASK;
                                break;
                            case 3:
                                keyCode = MouseEvent.BUTTON3_MASK;
                                break;
                        }
                        System.out.println("MousePressed: " + keyCode);
                        robot.mousePress(keyCode);
                    }
                    if (line.startsWith("MOUSE-RELEASED")) {
                        int keyCode = Integer.parseInt(line.split(":")[1]);
                        switch (keyCode) {
                            case 1:
                                keyCode = MouseEvent.BUTTON1_MASK;
                                break;
                            case 2:
                                keyCode = MouseEvent.BUTTON2_MASK;
                                break;
                            case 3:
                                keyCode = MouseEvent.BUTTON3_MASK;
                                break;
                        }
                        System.out.println("MouseReleased: " + keyCode);
                        robot.mouseRelease(keyCode);
                    }
                }
            }
        } catch (Exception ex) {
            close();
        }
    }
}
