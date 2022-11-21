import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    @Test
    public void testClient_getMessages() throws IOException {
        Logger.init("client test", "/tmp/test_client.log");
        String myMessages = "My message 1\nMy message 2\n";
        BufferedReader myBuffReader = new BufferedReader(new StringReader(myMessages + SocketWriter.ExitCommand + "\n"));

        ByteArrayOutputStream socketBuffer = new ByteArrayOutputStream();
        PrintWriter socketWriter = new PrintWriter(socketBuffer, true);

        SocketWriter client = new SocketWriter(socketWriter, myBuffReader);
        client.run();

        assertEquals(myMessages, socketBuffer.toString());
    }
}
