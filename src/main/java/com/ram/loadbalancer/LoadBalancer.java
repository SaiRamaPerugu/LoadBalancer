package com.ram.loadbalancer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LoadBalancer {

    int port;

    public LoadBalancer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = bufferedReader.readLine();
                System.out.println("Message from client: " + message);
                out.println("Message received ok");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
