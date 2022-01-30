package client;

import server.cli.CommandLineArgs;
import server.utils.GsonUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;


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

            String request = cla.filename != null
                    ? new String(Files.readAllBytes(Path.of(cla.filename)))
                    : GsonUtils.prettyPrint(cla);

            if (request.isBlank() || "{}".equals(request)) {
                System.out.println("No request given !");
                return;
            }

            output.writeUTF(request);
            System.out.println("Sent: " + request);
            System.out.println("Received: " + input.readUTF());
        } catch (NoSuchFileException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
