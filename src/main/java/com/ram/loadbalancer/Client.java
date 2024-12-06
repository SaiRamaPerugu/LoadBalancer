package com.ram.loadbalancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        if(args.length < 2) {
            throw new RuntimeException("Provide server name and port");
        }
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);
        try(Socket socket = new Socket(serverName, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("Hello from client 1");
            out.flush();

            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                String message = bufferedReader.readLine();
                System.out.println(message);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unkonwn host");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
