package Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String []args) {
		try {
			Socket clientSocket = new Socket("localhost",12345);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String message;
			while((message=in.readLine())!=null) {
			System.out.println("Received from server "+ message);
			}
			clientSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}