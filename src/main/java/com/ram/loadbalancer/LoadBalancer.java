package com.ram.loadbalancer;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadBalancer {

    String mode;
    String serverName;
    int port;
    List<String> backendServers;

    public LoadBalancer(String mode) {
        this.mode = mode;
        this.serverName = "localhost";
        this.port = 8080;
        this.backendServers = new ArrayList<>();

        //TODO:  Move this list to a configuration file, so code does not have to updated.
        backendServers.add("localhost:8081");
        backendServers.add("localhost:8082");
        backendServers.add("localhost:8083");
    }

    public void performOperation(String operation) {
        switch(OPERATION.valueOf(operation)) {
            case START: startLoadBalancer();
                        break;
            case STOP: stopLoadBalancer();
                        break;
            case ADD: addBackendServer(serverName);
                        break;
            case DELETE: deleteBackendServer(serverName);
                         break;
            case PING: pingBackendServers(backendServers);
                       break;
        }
    }

    private void stopLoadBalancer() {
    }

    private void startLoadBalancer() {
       while(true) {
           try (ServerSocket serverSocket = new ServerSocket(port)) {
               System.out.println("Load balancer started and listening on port " + port);
               Socket clientSocket = serverSocket.accept();
               System.out.println("Client connected from :" +
                       "Local address" + clientSocket.getLocalAddress() + ":" +
                       "Local Socket Address " + clientSocket.getLocalSocketAddress() + ":" +
                       " Inet address " + clientSocket.getInetAddress() + ":" +
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
           } catch (SocketException ex) {
               System.out.println("Client disconnected");
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

    }

    private void addBackendServer(String serverName) {

    }

    private void deleteBackendServer(String serverName) {

    }

    private void pingBackendServers(List<String> backendServers) {
        List<String> messages = List.of("Hello friend!", "Hello Amigo!", "Hello Nanba!");
        Random random = new Random();
        int messageNo = random.nextInt(messages.size());
        for(String backendServer: backendServers) {
            System.out.println("Pinging backend server " + backendServer);
            String server = backendServer.split(":")[0];
            int port = Integer.parseInt(backendServer.split(":")[1]);
            sendMessage(messages.get(messageNo), server, port);
        }
    }

    private void sendMessage(String message, String server, int port) {
        try {
            Socket socket = new Socket(serverName, port);
            System.out.println("Connected to the server at " + serverName + ":" + port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(message);
            // Close the connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("Waiting for response from Server...");
            System.out.println("Received response from server: " + response);
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

    enum OPERATION {
        START, ADD, DELETE, STOP, PING;
    }
}

