import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Scanner scanner;
    private String authTaken;

    public Client() {
        scanner = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8000);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // first message
            authTaken = dis.readUTF();

            // other messages
            initStreams(dos, dis);

        } catch (IOException e) {e.printStackTrace();}
    }

    public void initStreams(DataOutputStream dos, DataInputStream dis) {
        Thread reader = new Thread(() -> {
            while (true) {
                if (scanner.hasNext()) {
                    try {
                        String command = scanner.nextLine();
                        command = authTaken + " " + command;
                        dos.writeUTF(command);

                    } catch (IOException e) {e.printStackTrace();}
                }
            }
        });
        Thread writer = new Thread(() -> {
            while (true) {
                try {
                    String message = dis.readUTF();
                    message = message.substring(authTaken.length());
                    System.out.println(message);

                } catch (IOException e) {e.printStackTrace();}
            }
        });
        reader.start();
        writer.start();
    }

    public static void main(String[] args) {
        new Client();
    }

}
