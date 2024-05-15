package Socket;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server đang lắng nghe kết nối....");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + clientSocket);
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        // Xử lý giao tiếp với client trong phương thức run()
    	try {
			PrintWriter out= new PrintWriter(clientSocket.getOutputStream(),true);
			for(int i=1;i<=100;i++) {
				out.print(i);
				out.flush();
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
}