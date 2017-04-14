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
    private DataInputStream in = null;
    private DataOutputStream out = null;

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
        in = new DataInputStream(new BufferedInputStream(fromClient.getInputStream()));
        out = new DataOutputStream(fromClient.getOutputStream());
        System.out.println("Server is started!!!");
    }

    void wServerWriter(String data) throws IOException {
        out.writeUTF(data);
        out.flush();
    }

    String wServerReader() throws IOException {
        return in.readUTF();
    }

    void close(){
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(InetAddress.getLocalHost());
        WServer server = new WServer();
        String bufLine = null;

        while (true){
            bufLine = server.wServerReader();
            if(bufLine.equals("quit")){
                server.close();
                break;
            }
            System.out.println("Client message: "+bufLine);
            server.wServerWriter(bufLine);
            System.out.println("Waiting for the next line...");
            System.out.println();
        }
    }
}
