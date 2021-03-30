package server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

enum Server {

    INSTANCE;

    public static final Path DATA_DIR_PATH = Paths.get(
            "src" + File.separator +
            "main" + File.separator +
            "java" + File.separator +
            "server" + File.separator +
            "data").toAbsolutePath();

    private static final String ADDRESS = "localhost";
    private static final int PORT = 9000;
    private static final int BACKLOG = 50;

    private final ExecutorService executor;

    {
        final int threads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(threads);
    }

    Server() {}

    public void start() {
        System.out.println("Server started!");
        try (ServerSocket socket = new ServerSocket(PORT,
                BACKLOG, InetAddress.getByName(ADDRESS))) {

            Database.INSTANCE.init();

            while (!executor.isShutdown()) {
                Session session = new Session(socket.accept());
                executor.submit(session);
            }
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
