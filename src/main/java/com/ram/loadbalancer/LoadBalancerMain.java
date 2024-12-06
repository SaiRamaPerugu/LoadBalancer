package com.ram.loadbalancer;

public class LoadBalancerMain {
    public static void main(String[] args) {
        if(args.length == 0 || args[0] == null || args[0].isEmpty()) {
            throw new RuntimeException("Enter a port number as the first argument");
        }
        int port = Integer.parseInt(args[0]);
        LoadBalancer loadBalancer = new LoadBalancer(port);
        while(true) {
            loadBalancer.start();
        }
    }
}
