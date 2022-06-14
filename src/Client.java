import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Scanner scanner;


    public Client() {
        scanner = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8000);
            DataInputStream        dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            initStreams(dos, dis);
        } catch (IOException e) {
            e.printStackTrace();
            }
    }

    public void initStreams(DataOutputStream dos, DataInputStream dis) {
        Thread reader = new Thread(() -> {
            while (true) {
                if (scanner.hasNext()) {
                    String command = scanner.nextLine();
                    try {
                        dos.writeUTF(command);
                        System.out.println("dos.writeUTF(command); " + command);
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
                    System.out.println("message " + message);
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
