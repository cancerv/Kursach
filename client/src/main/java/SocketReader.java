import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

// Чтение сообщения из сокета
public class SocketReader extends Thread {
    private final BufferedReader reader;
    private final PrintStream out;

    public SocketReader(BufferedReader reader, PrintStream out) {
        this.reader = reader;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = this.reader.readLine()) != null) {
                Logger.info(message);
                this.out.println(message);
            }
        } catch (IOException e) {}
    }
}
