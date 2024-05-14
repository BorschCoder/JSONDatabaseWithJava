package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class Main {
    static final String address = "127.0.0.1";
    static final int port = 23456;
    public static final String SENT_LABEL = "Sent: ";

    public static void main(String[] args) throws IOException {

        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        Gson gson = new Gson();

        try {
            Socket socket = new Socket(InetAddress.getByName(address), port);
            System.out.println("Client started!");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            RequestType requestType = RequestType.valueOf(arguments.getRequestType().toUpperCase());
            String key = arguments.getIndex();



            Request request;
            switch (requestType) {

                case SET:
                    request = new Request(requestType, key, arguments.getValue());
                    break;
                case GET, DELETE:
                    request = new Request(requestType, key);
                    break;
                default:
                    request = new Request(requestType);
                    break;
            }
            String message = gson.toJson(request).toString();
            System.out.println(SENT_LABEL + message);
            output.writeUTF(message);

            String answer = input.readUTF();
            System.out.println("Received: " + answer);
            if (arguments.getRequestType().equals("exit")) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
