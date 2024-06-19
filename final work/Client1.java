import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the server port number: ");
            int serverPort = Integer.parseInt(scanner.nextLine());

            Socket socket = new Socket(SERVER_ADDRESS, serverPort);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            out.println(username);

            Thread readThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();

            String message;
            while ((message = scanner.nextLine()) != null) {
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}