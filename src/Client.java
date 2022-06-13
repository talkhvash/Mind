import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Scanner scanner;
    DataInputStream dis;
    DataOutputStream dos;

    public Client() {
        scanner = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            initStreams();
        } catch (IOException e) {
            e.printStackTrace();
            }
    }

    public void initStreams() {
        Thread reader = new Thread(() -> {
            while (true) {
                if (scanner.hasNext()) {
                    String command = scanner.nextLine();
                    try {
                        dos.writeUTF(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        reader.start();
        Thread writer = new Thread(() -> {
            while (true) {
                try {
                    String message = dis.readUTF();
                    System.out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        writer.start();
    }

    public static void main(String[] args) {
        new Client();
    }

}
