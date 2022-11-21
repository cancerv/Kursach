import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionHandlerTest {
    private final String TestMessage = "Test message\n";

    @Test
    public void testSendMessage() throws IOException {
        Logger.init("server test", "/tmp/test_server.log");

        ByteArrayOutputStream socketBuffer = new ByteArrayOutputStream();
        PrintWriter socketWriter = new PrintWriter(socketBuffer, true);

        ConnectionHandler connectionHandler = new ConnectionHandler(
                socketWriter,
                new BufferedReader(Reader.nullReader()),
                new ArrayBlockingQueue<>(10)
        );

        connectionHandler.sendMessage(TestMessage);

        assertEquals(socketBuffer.toString(), TestMessage);
    }

    @Test
    public void testMessageBroadcasting() throws InterruptedException {
        ByteArrayOutputStream socketBuffer = new ByteArrayOutputStream();
        PrintWriter socketWriter = new PrintWriter(socketBuffer, true);

        String myMessages = "Test nickname\nMy first message\n";
        BufferedReader myBuffReader = new BufferedReader(new StringReader(myMessages));

        ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<>(10, true);

        ConnectionHandler connectionHandler = new ConnectionHandler(
                socketWriter,
                myBuffReader,
                queue
        );

        connectionHandler.run();

        Message message1 = queue.take();
        Message message2 = queue.take();

        assertEquals(message1.getType(), "register");
        assertEquals(message1.getFrom(), "Test nickname");
        assertEquals(message1.getMessage(), "");

        assertEquals(message2.getType(), "message");
        assertEquals(message2.getFrom(), "Test nickname");
        assertEquals(message2.getMessage(), "My first message");

        assertEquals(
                socketBuffer.toString(),
                "Пожалуйста, укажите ваш никнейм:\n"
        );
    }
}
