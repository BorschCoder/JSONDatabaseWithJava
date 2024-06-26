package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static server.ResponseType.OK;

public class Server {
    private final ExecutorService executor;
    private final EntityService entityService;
    private ServerSocket server;
    private final int port;

    private EntityController controller;

    public Server(int port) {
        this.entityService = new EntityService();
        this.port = port;
        this.executor = Executors.newCachedThreadPool();
        this.controller = new EntityController();

    }

    public void run() {
        try {
            server = new ServerSocket(port);
            while (!server.isClosed()) {
                Socket socket = server.accept();

                executor.submit(() -> {

                    try (DataInputStream input = new DataInputStream(socket.getInputStream());
                         DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                        String receivedMessage = input.readUTF();

                        Entity entity = new Gson().fromJson(receivedMessage, Entity.class);
                        String method = entity.getType();
                        Response response = null;

                        if (method.equals("exit")) {
                            response = close();
                        } else {
                            response = controller.handle(entity);
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
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response(OK);
    }

}


