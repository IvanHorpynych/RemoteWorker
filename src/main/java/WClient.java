import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class WClient {
    private Socket fromServer = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
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
        in = new DataInputStream(new BufferedInputStream(fromServer.getInputStream()));
        out = new DataOutputStream(fromServer.getOutputStream());
    }

    void wClientWriter(String data) throws IOException {
        out.writeUTF(data);
        out.flush();
    }

    String wClientReader() throws IOException {
        return in.readUTF();
    }

    void close(){
        try {
            keyboardInput.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        WClient client = new WClient("127.0.0.1");
        client.keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
        System.out.println();
        String bufLine;

        while (true) {
            bufLine =client.keyboardInput.readLine();
            if(bufLine.equals("quit")){
                client.wClientWriter(bufLine);
                client.close();
                break;
            }
            System.out.println("Sending this line to the server...");
            client.wClientWriter(bufLine);
            bufLine = client.wClientReader();
            System.out.println("Server send: "+bufLine);
            System.out.println();
        }
    }
}
