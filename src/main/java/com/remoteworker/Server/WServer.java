package com.remoteworker.Server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import java.net.Socket;

public class WServer {
    private int port = 1996;
    private ServerSocket serverSocket = null;
    private Socket fromClient = null;
    private ServerThreadWriter serverThreadWriter = null;
    private ServerThreadReader serverThreadReader = null;

    WServer(){
        try {
            createConnect();
        } catch (IOException e) {
            System.out.println("Don`t enable server!!!");
        }
    }

    void createConnect() throws IOException {
        serverSocket = new ServerSocket(port);
        fromClient = serverSocket.accept();
        serverThreadReader = new ServerThreadReader(fromClient);
        serverThreadWriter = new ServerThreadWriter(fromClient);
        System.out.println("Server is started!!!");
    }


    void close(){
        try {
            serverThreadReader.getIn().close();
            serverThreadReader.getServerReader().interrupt();
            serverThreadWriter.getOut().close();
            serverThreadWriter.getServerWriter().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(InetAddress.getLocalHost());
        WServer server = new WServer();
    }
}
