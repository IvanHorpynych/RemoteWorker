package com.remoteworker.Server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThreadReader implements Runnable {
    private Thread serverReader = null;
    private Socket fromClient = null;
    private DataInputStream in = null;

    ServerThreadReader(Socket fromClient){
        this.fromClient = fromClient;
        try {
            in = new DataInputStream(new BufferedInputStream(this.fromClient.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverReader = new Thread(this);
        serverReader.start();
    }

    int wServerReader() throws IOException {
        return in.readInt();
    }

    Thread getServerReader(){
        return serverReader;
    }

    DataInputStream getIn(){
        return in;
    }


    public void run() {
        while (true){
            try {
                wServerReader();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
