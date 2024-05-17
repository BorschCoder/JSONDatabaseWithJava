package server;

import client.Request;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import static server.ResponseType.OK;

public class Server {
    private final ExecutorService executor;
    private final FileDB fileDB;
    private volatile boolean running;
    private final int port;

    public Server(int port) {
        this.fileDB = new FileDB();
        this.running = true;
        this.port = port;
        this.executor = Executors.newCachedThreadPool();

    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {

                Socket socket = serverSocket.accept();
                executor.submit(() -> {

                    try (DataInputStream input = new DataInputStream(socket.getInputStream());
                         DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                        String receivedMessage = input.readUTF();
                        Request request = new Gson().fromJson(receivedMessage, (Type) Request.class);

                        String method = request.getType();
                        Response response = null;

                        if (method.equals("exit")) {
                            response = close();
                        } else {
                            response = fileDB.requestHandler(request);
                        }

                        String textResponse = new Gson().toJson(response).toString();
                        output.writeUTF(textResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response close() {
        this.executor.shutdown();
        this.running = false;
        return new Response(OK);
    }

}


