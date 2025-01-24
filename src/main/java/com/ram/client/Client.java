package com.ram.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args)  {
        if(args.length < 2) {
            throw new RuntimeException("Provide server name and port");
        }
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            Socket socket = new Socket(serverName, port);
            System.out.println("Connected to the server at " + serverName + ":" + port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            // Send a message to the server
            String[] messages = {"Hello, server! This is a message from the client.", "This is message 2", "bye!"};
            for(String message: messages) {
                writer.println(message);
                System.out.println("Message sent: " + message);
            }
            // Close the connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = reader.readLine();
            System.out.println("Waiting for response from Server...");
            System.out.println("Received response from server: " + message);
            reader.close();
            writer.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
