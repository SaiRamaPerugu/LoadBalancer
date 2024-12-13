# Implementation of Load balancer 
1. Create  a server to send a message to a client - Done 
2. Create a client to send a message to a server - Done
3. Separate the project to a different repository in GIT - Done

## To Do

1. Run multiple instances of a client.
2. Have three instances of a server running.
   ```
   One on port 80 - the load balancer
   Second one on port 81 and third on port 82
   Forward request 1 to Second server, request 2 to Third server.
   ```
3. Handle multiple connections, using threads?