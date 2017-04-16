package com.remoteworker.Client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThreadReader implements Runnable {
    Thread ClientReader = null;
    private Socket fromServer = null;
    private DataInputStream in = null;

    ClientThreadReader(Socket fromServer) {
        this.fromServer = fromServer;
        try {
            in = new DataInputStream(new BufferedInputStream(this.fromServer.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientReader = new Thread(this);
        ClientReader.start();
    }

    byte wClientReader() throws IOException {
        return in.readByte();
    }

    DataInputStream getIn() {
        return in;
    }

    Thread getClientReader(){
        return ClientReader;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("ClientReader start!!!");
                wClientReader();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
