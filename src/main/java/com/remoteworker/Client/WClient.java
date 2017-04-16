package com.remoteworker.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class WClient {
    private Socket fromServer = null;
    ClientThreadReader clientThreadReader = null;
    ClientThreadWriter clientThreadWriter = null;
    ArrayList<Integer> queue = null;

    protected BufferedReader keyboardInput = null;
    private String ip;

    WClient(String ip) {
        this.ip = ip;
        try {
            ConnecntToServer();
        } catch (IOException e) {
            System.out.println("Don`t connect to server!!!");
        }
    }

    void ConnecntToServer() throws IOException {
        InetAddress ipAddress = InetAddress.getByName(ip);
        fromServer = new Socket(ipAddress, 1996);
        queue = new ArrayList<Integer>();
        clientThreadReader = new ClientThreadReader(fromServer);
        clientThreadWriter = new ClientThreadWriter(fromServer,queue);
    }


    void close(){
        try {
            clientThreadReader.getIn().close();
            clientThreadReader.getClientReader().interrupt();
            clientThreadWriter.getOut().close();
            clientThreadWriter.getClientWriter().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        WClient client = new WClient("127.0.0.1");
        client.keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type in something and press enter.");
        System.out.println();

        while (true){
            client.queue.add(Integer.getInteger(client.keyboardInput.readLine()));
        }

    }
}
