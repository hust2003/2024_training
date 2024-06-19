import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server1 {
    private static List<ClientHandler1> clients = new ArrayList<>();

    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler1 clientHandler = new ClientHandler1(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getClientNames() {
        List<String> names = new ArrayList<>();
        for (ClientHandler1 client : clients) {
            names.add(client.getClientName());
        }
        return names;
    }

    private static class ClientHandler1 extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String name;

    public String getClientName() { 

        return name;
    }

    public ClientHandler1(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Get the client's name
            name = in.readLine();
            broadcastMessage(name + " has joined the chat.");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/list")) {
                    out.println("Current users: " + String.join(", ", Server1.getClientNames()));
                } else if (message.startsWith("@")) {
                    // 私聊消息，格式为 "@接收者 消息内容"
                    String[] parts = message.split("\\s+", 2);
                    if (parts.length == 2) {
                        String recipientName = parts[0].substring(1); // 去掉@符号
                        String privateMessage = parts[1];
                        sendPrivateMessage(recipientName, privateMessage);
                    } else {
                        out.println("Invalid private message format.");
                    }
                } else {
                    // 广播消息给其他客户端，但不包括自己
                    broadcastMessage(name + ": " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 清理资源
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // 客户端断开连接时从列表中移除
            clients.remove(this);
        }
    }

    private void sendPrivateMessage(String recipientName, String message) {
        for (ClientHandler1 client : clients) {
            if (client.getClientName().equals(recipientName)) {
                // 找到接收者，发送消息
                try {
                    client.out.println(name + ": " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return; // 消息已发送，退出循环
            }
        }
        // 如果没有找到接收者，可以发送一个错误消息给发送者
        try {
            out.println("Recipient not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    private static void broadcastMessage(String message) {
        for (ClientHandler1 client : clients) {
            client.out.println(message);
        }
    }


}
