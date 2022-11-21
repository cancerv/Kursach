import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class InputReaderTest {

    @Test
    public void testRun_test() throws IOException {
        Logger.init("client test", "/tmp/test_client.log");
        String lines = "Line 1\nLine 2\n";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        BufferedReader buffReader = new BufferedReader(new StringReader(lines));
        PrintStream printStream = new PrintStream(outContent);

        SocketReader inputReader = new SocketReader(buffReader, printStream);
        inputReader.run();

        assertEquals(outContent.toString(), lines);
    }
}
