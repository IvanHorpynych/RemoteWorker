package com.remoteworker.Client;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

class ClientSendKeys implements Runnable {
    Thread clientSendKeys;
    private ConcurrentLinkedQueue<String> eventQueue;
    private OutputStream os;

    public ClientSendKeys(OutputStream os) {
        this.eventQueue = new ConcurrentLinkedQueue<>();
        this.os = os;
        clientSendKeys = new Thread(this);
        clientSendKeys.start();
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