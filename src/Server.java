import Logic.CommandHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;


public class Server {
    private final HashMap<String, DataOutputStream> doses = new HashMap<>();
    private String host;
    private CommandHandler commandHandler;

    public Server() {
        initializeSocket();
    }

    public void initializeSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                initializeAuthTaken(dos);
                initializeReaderThread(dis);
            }
        } catch (IOException e) {e.printStackTrace();}
    }

    public static void main(String[] args) {
        new Server();
    }

    private void initializeAuthTaken(DataOutputStream dos) {
        Random random = new SecureRandom();
        String authTaken = random.nextInt() + "";

        if (host == null) host = authTaken;

        try { dos.writeUTF(authTaken);
        } catch (IOException e) {e.printStackTrace();}

        doses.put(authTaken, dos);
        if (commandHandler == null) commandHandler = new CommandHandler(doses, host);
    }

    private void initializeReaderThread(DataInputStream dis) {
        Thread reader = new Thread(() -> {
            try {
                while (true) {
                    commandHandler.handle(dis.readUTF());
                }
            } catch (IOException e) {e.printStackTrace();}
        });
        reader.start();
    }


}
