import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final String serverHost;
    private final int serverPort;

    public Client(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    public void run() {
        try {
            Logger.info("Connecting to the server...");
            Socket socketClient = new Socket(this.serverHost, this.serverPort);
            Logger.info("Connected");

            PrintWriter writer = new PrintWriter(socketClient.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

            SocketReader socketRead = new SocketReader(reader, System.out);
            Thread socketReaderThread = new Thread(socketRead);
            socketReaderThread.start();

            SocketWriter socketWriter = new SocketWriter(writer, new BufferedReader(new InputStreamReader(System.in)));
            socketWriter.run();

            Logger.info("Disconnecting...");
            socketReaderThread.interrupt();
            socketClient.close();
            Logger.info("Disconnected");
        } catch (IOException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
