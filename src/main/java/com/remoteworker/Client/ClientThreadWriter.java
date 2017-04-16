package com.remoteworker.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThreadWriter implements Runnable {
    private Thread ClientWriter = null;
    private Socket fromServer = null;
    private DataOutputStream out = null;
    private ArrayList<Integer> queue = null;

    ClientThreadWriter(Socket fromServer, ArrayList<Integer> queue) {
        this.fromServer = fromServer;
        this.queue = queue;
        try {
            out = new DataOutputStream(this.fromServer.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientWriter = new Thread(this);
        ClientWriter.start();
    }

    void wClientWriter() throws IOException {
        synchronized (queue) {
            if (queue.size() != 0) {
                out.writeInt(queue.get(0));
                out.flush();
                queue.remove(0);
            }
        }
    }

    DataOutputStream getOut() {
        return out;
    }
    Thread getClientWriter(){
        return ClientWriter;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("ClientWriter start!!!");
                wClientWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
