import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    DataInputStream dis;
    DataOutputStream dos;


    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                Socket socket = serverSocket.accept();
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                String command = dis.readUTF();
                System.out.println(command);
                handleCommand(command);
            }
        } catch (IOException e) {e.printStackTrace();}

    }

    private void handleCommand(String command) throws IOException {

    }

    public static void main(String[] args) {
        new Server();
    }
}
