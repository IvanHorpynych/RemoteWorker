package com.remoteworker.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThreadWriter implements Runnable{
    private Thread serverWriter = null;
    private Socket fromClient = null;
    private DataOutputStream out = null;

    ServerThreadWriter(Socket fromClient){
        this.fromClient = fromClient;
        try {
            out = new DataOutputStream(this.fromClient.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverWriter = new Thread(this);
        serverWriter.start();
    }

    void wServerWriter(byte b) throws IOException {
        out.writeByte(b);
        out.flush();
    }

    Thread getServerWriter(){
        return serverWriter;
    }

    DataOutputStream getOut(){
        return out;
    }
    public void run() {
        while (true){
            try {
                wServerWriter(((byte)0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
