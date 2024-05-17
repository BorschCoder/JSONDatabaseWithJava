package server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(23456);
        System.out.println("Server started!");
        server.run();
    }
}
