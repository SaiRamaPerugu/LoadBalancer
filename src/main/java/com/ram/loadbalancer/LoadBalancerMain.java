package com.ram.loadbalancer;

public class LoadBalancerMain {
    public static void main(String[] args) {
        if(args.length == 0 || args[0] == null || args[0].isEmpty()) {
            throw new RuntimeException("Enter a port number as the first argument");
        }

        String mode = args[0].toUpperCase();
        String host = args[1].toLowerCase();
        int port = Integer.parseInt(args[2]);

        LoadBalancer loadBalancer = new LoadBalancer(mode, host, port);
        loadBalancer.performOperation(mode);
    }
}
