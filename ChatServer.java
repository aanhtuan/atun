package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static List<PrintWriter> clientWriters = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server đang lắng nghe kết nối....");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + clientSocket);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                getClientWriters().add(out);

                Thread clientThread = new Thread(new ClientHandler(clientSocket, out));
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message) {
        for (PrintWriter writer : getClientWriters()) {
            writer.println(message);
            writer.flush();
        }
    }

    public static List<PrintWriter> getClientWriters() {
        return clientWriters;
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket clientSocket, PrintWriter out) {
        this.clientSocket = clientSocket;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Nhập username của bạn:");
            username = in.readLine();

            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                ChatServer.broadcastMessage(username + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                in.close();
                out.close();
                ChatServer.getClientWriters().remove(out);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}