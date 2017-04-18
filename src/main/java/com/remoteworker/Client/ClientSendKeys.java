package com.remoteworker.Client;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

class ClientSendKeys implements Runnable {

    Thread clientSendKeys;
    private final Queue<String> eventQueue;
    private final PrintWriter writer;

    private boolean isClosed = false;
    
    public ClientSendKeys(PrintWriter writer) {
        this.eventQueue = new ArrayBlockingQueue<>(30);
        this.writer = writer;
        clientSendKeys = new Thread(this);
        clientSendKeys.start();
    }

    public void postEvent(String event) {
        eventQueue.offer(event);
    }

    protected void formKeyPressed(KeyEvent evt) {
        System.out.println("KeyCode: " + evt.getKeyCode());
        System.out.println("KeyChar: " + evt.getKeyChar());
        if (clientSendKeys != null) {
            postEvent("KEY-PRESSED:" + evt.getKeyCode());
        }
    }

    protected void formKeyReleased(java.awt.event.KeyEvent evt) {
        System.out.println("KeyCode: " + evt.getKeyCode());
        System.out.println("KeyChar: " + evt.getKeyChar());
        if (clientSendKeys != null) {
            postEvent("KEY-RELEASED:" + evt.getKeyCode());
        }
    }

    protected void formMousePressed(MouseEvent evt) {
        if (clientSendKeys != null) {
            System.out.println("MOUSE-PRESSED:" + evt.getButton());
            postEvent("MOUSE-PRESSED:" + evt.getButton());
        }
    }

    protected void formMouseReleased(MouseEvent evt) {
        if (clientSendKeys != null) {
            System.out.println("MOUSE-RELEASED:" + evt.getButton());
            postEvent("MOUSE-RELEASED:" + evt.getButton());
        }
    }

    public void close() {
        isClosed = true;
    }

    @Override
    public void run() {
        while (!isClosed) {
            String peak = eventQueue.poll();
            if (peak != null) {
                writer.println(peak);
                writer.flush();
                System.out.println(peak);
            }
        }
    }
}
