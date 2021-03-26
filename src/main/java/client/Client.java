package client;

import server.cli.CommandLineArgs;
import server.requests.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 9000;

    public static void start(CommandLineArgs cla) {
        System.out.println("Client Started");

        try (
                Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            Request request = new Request(cla);
            output.writeUTF(request.toJson());
            System.out.println("Sent: " + request.toJson());
            System.out.println("Received: " + input.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
