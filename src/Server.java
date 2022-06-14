import Logic.CommandHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Server {
    private HashMap<String, DataOutputStream> doses = new HashMap<>();
    private int lastId = 1;
    private int hostId;
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
                SecureRandom secureRandom = new SecureRandom();
                String authTaken = secureRandom.getAlgorithm();
                doses.put(authTaken, dos);
                Thread thread = new Thread(() -> {
                    try {
                        while (true) {
                            commandHandler.handle(dis.readUTF());
                        }
                    } catch (IOException e) {e.printStackTrace();}
                });
                thread.start();
            }
        } catch (IOException e) {e.printStackTrace();}
    }

    public static void main(String[] args) {
//        new Server();
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(Integer.parseInt(s));



    }
}
