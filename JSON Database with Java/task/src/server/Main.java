package server;

import client.Request;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import static server.ResponseType.*;


public class Main {


    private static final int SIZE_DATABASE = 1000;
    private static String[] database = new String[SIZE_DATABASE];

    static final String address = "127.0.0.1";
    static final int port = 23456;


    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        DataBase dataBase = new DataBase();
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String receivedMessage = input.readUTF();
                Request request = new Gson().fromJson(receivedMessage, (Type) Request.class);

                String method = request.getType();

                if (method.equals("exit")) {
                    socket.close();
                    serverSocket.close();
                    return;
                }
                Response response = dataBase.requestHandler(request);

                Gson gson = new Gson();
                output.writeUTF(gson.toJson(response).toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
