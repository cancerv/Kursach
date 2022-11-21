import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int QueueCapacity = 10;

    private final int port;

    private ServerSocket serverSocket;
    private final List<ConnectionHandler> connections = new ArrayList<>();
    private final BlockingQueue<Message> broadcastQueue = new ArrayBlockingQueue<>(QueueCapacity, true);
    private Thread queueThread;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        Logger.info("Run server...");

        this.runQueue();

        try {
            serverSocket = new ServerSocket(this.port);
            Logger.info("Server ran");
            ExecutorService pool = Executors.newCachedThreadPool();

            while (true) {
                Logger.info("Waiting for a client...");
                Socket client = serverSocket.accept();
                Logger.info("New client connected");

                PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                ConnectionHandler connectionHandler = new ConnectionHandler(writer, reader, broadcastQueue);
                connections.add(connectionHandler);
                pool.execute(connectionHandler);
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
            shutdown();
        }
    }

    private void runQueue() {
        Logger.info("Initializing message queue...");
        this.queueThread = new Thread(() -> {
            try {
                while (!this.queueThread.isInterrupted()) {
                    Message message = this.broadcastQueue.take();
                    this.broadcast(message.toString());
                }
            } catch (InterruptedException e) {}
        });
        this.queueThread.start();
        Logger.info("Message queue initialized");
    }

    public void broadcast(String message) {
        Logger.info("New message: " + message);
        for (ConnectionHandler ch : connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        Logger.info("Terminating server...");
        try {
            Logger.info("Stopping message queue");
            queueThread.interrupt();
            Logger.info("Message queue stopped");

            Logger.info("Disconnecting clients...");
            for (ConnectionHandler ch : connections) {
                ch.close();
            }
            Logger.info("Clients disconnected");

            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
            Logger.info("Server terminated");
        } catch (IOException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
