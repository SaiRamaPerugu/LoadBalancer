package com.ram.loadbalancer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class LoadBalancer {

    String mode;
    String serverName;
    int port;

    public LoadBalancer(String mode, String serverName, int port) {
        this.mode = mode;
        this.serverName = serverName;
        this.port = port;
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
            case PING: boolean response = pingBackendServer(serverName);
                        if(response) {
                            System.out.println("Backend server responded fine");
                        } else {
                            System.out.println("NO response from Backend server");
                        }
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

    private boolean pingBackendServer(String serverName) {
        return false;
    }

    enum OPERATION {
        START, ADD, DELETE, STOP, PING;
    }
}

