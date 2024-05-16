package socket2;

import java.io.*;
import java.net.*;

public class chatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8000);
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter username: ");
            String username = userInput.readLine();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(username);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread receiver = new Thread(new Runnable() {
                public void run() {
                    try {
                        String message;
                        while ((message = in.readLine()) != null) {
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();

            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}