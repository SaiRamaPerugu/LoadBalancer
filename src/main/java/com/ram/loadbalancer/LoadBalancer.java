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
        try(ServerSocket serverSocket = new ServerSocket(port))  {
            System.out.println("Load balancer listening on port " + port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from :" +
                    "Local address" + clientSocket.getLocalAddress() + ":" +
                    "Local Socket Address " + clientSocket.getLocalSocketAddress()  + ":" +
                    " Inet address " + clientSocket.getInetAddress()  + ":" +
                    " Port " + clientSocket.getPort());

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            System.out.println("Waiting for messages...");
            while ((message = reader.readLine()) != null) {
                System.out.println("Received message: " + message);
            }

            reader.close();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Message received from server ok");
            clientSocket.close();
            System.out.println("Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
