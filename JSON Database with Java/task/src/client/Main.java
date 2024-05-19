package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    static final String address = "127.0.0.1";
    static final int port = 23456;
    public static final String SENT_LABEL = "Sent: ";

    public static final String REQUEST_DIR = "/Users" +
            "/mihail" +
            "/IdeaProjects" +
            "/JSON Database with Java" +
            "/JSON Database with Java" +
            "/task" +
            "/src" +
            "/client" +
            "/data" +
            "/";

    public static void main(String[] args) {

        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        Gson gson = new Gson();

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            RequestType requestType = null;
            Request request = null;
            String key = null;
            String value = null;

            if (arguments.getFile() != null) {
                String argFile = arguments.getFile();
                if (argFile != null) {
                    String filePath = REQUEST_DIR + argFile;
                    try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

                        request = gson.fromJson(reader, Request.class);
                        key = request.getKey();
                        value = request.getValue();
                        requestType = RequestType.valueOf(request.getType().toUpperCase());

                    }
                }
            } else {
                requestType = RequestType.valueOf(arguments.getRequestType().toUpperCase());
                key = arguments.getIndex();
                value = arguments.getValue();
            }


            switch (requestType) {

                case SET:
                    request = new Request(requestType, key, value);
                    break;
                case GET, DELETE:
                    request = new Request(requestType, key);
                    break;
                case IN:
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
