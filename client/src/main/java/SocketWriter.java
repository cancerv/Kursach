import java.io.*;

// читает ввод от пользователя
public class SocketWriter {
    public final static String ExitCommand = "/выход";

    private final PrintWriter socketWriter;
    private final BufferedReader consoleIn;

    public SocketWriter(PrintWriter writer, BufferedReader consoleIn) {
        this.socketWriter = writer;
        this.consoleIn = consoleIn;
    }

    public void run() throws IOException {
        String message;
        while ((message = this.consoleIn.readLine()) != null) {
            if (message.equals(ExitCommand)) {
                return;
            } else {
                this.socketWriter.println(message);
            }
        }
    }
}
