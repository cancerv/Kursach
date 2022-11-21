import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    private final int serverPort = 6667;
    private Thread serverThread;

    private void runServer() {
        this.serverThread = new Thread(() -> {
            Server server = new Server(this.serverPort);
            server.run();
        });
        this.serverThread.start();
    }

    @Test
    public void testServer() throws IOException, InterruptedException {
        Logger.init("server test", "/tmp/test_server.log");

        this.runServer();

        Thread.sleep(2000);

        Socket socketClient = new Socket("localhost", this.serverPort);

        PrintWriter writer = new PrintWriter(socketClient.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime testStartTime = LocalDateTime.now();

        StringBuilder out = new StringBuilder();
        out.append(reader.readLine());
        writer.println("Test nickname");
        out.append(reader.readLine());
        writer.println("My message");
        out.append(reader.readLine());

        Socket socketClient2 = new Socket("localhost", this.serverPort);

        PrintWriter writer2 = new PrintWriter(socketClient2.getOutputStream(), true);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(socketClient2.getInputStream()));

        out.append(reader2.readLine());
        writer2.println("Second user");
        out.append(reader2.readLine());
        writer2.println("Second user message");
        out.append(reader2.readLine());

        assertEquals(
                out.toString(),
                "Пожалуйста, укажите ваш никнейм:===> Test nickname ["+testStartTime.format(formatter)+"]: присоединился к чату <===Test nickname ["+testStartTime.format(formatter)+"]: My messageПожалуйста, укажите ваш никнейм:===> Second user ["+testStartTime.format(formatter)+"]: присоединился к чату <===Second user ["+testStartTime.format(formatter)+"]: Second user message"
        );

        this.serverThread.interrupt();
    }
}
