package com.remoteworker.Client;

import javafx.scene.image.ImageView;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class WClient {
    private Socket fromServer = null;
    private ClientThreadReader clientThreadReader = null;
    private ClientThreadWriter clientThreadWriter = null;
    private ImageView imageView = null;
    private ArrayList<Integer> queue = null;

    protected BufferedReader keyboardInput = null;
    private String ip;

    public WClient(String ip, ImageView imageView) {
        this.ip = ip;
        this.imageView = imageView;
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
        clientThreadReader = new ClientThreadReader(fromServer, imageView);
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


   /* public static void main(String[] args) throws IOException {
        WClient client = new WClient("127.0.0.1");
        client.keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type in something and press enter.");
        System.out.println();

        while (true){
            client.queue.add(Integer.getInteger(client.keyboardInput.readLine()));
        }

    }*/
}
