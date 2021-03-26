package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum Server {
    INSTANCE;

    private static final String ADDRESS = "localhost";
    private static final int PORT = 9000;
    private static final int BACKLOG = 50;
    private static final int THREAD_COUNT;
    private static final ExecutorService EXECUTOR_SERVICE;

    static {
        THREAD_COUNT = Runtime.getRuntime().availableProcessors();
        EXECUTOR_SERVICE = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    private boolean exit = false;

    Server() {}

    public void start() {
        System.out.println("Server started!");
        try (ServerSocket socket = new ServerSocket(PORT,
                BACKLOG, InetAddress.getByName(ADDRESS))) {

            while (!exit) {
                Session session = new Session(socket.accept());
                EXECUTOR_SERVICE.submit(session);
            }
            EXECUTOR_SERVICE.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        exit = true;
    }
}
