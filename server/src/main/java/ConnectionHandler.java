import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class ConnectionHandler extends Thread {
    private final BlockingQueue<Message> broadcast;

    private final PrintWriter writer;
    private final BufferedReader reader;

    private String nickname;

    public ConnectionHandler(PrintWriter writer, BufferedReader reader, BlockingQueue<Message> broadcast) {
        this.broadcast = broadcast;
        this.writer = writer;
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            this.register();

            String message;
            while ((message = reader.readLine()) != null) {
                this.broadcast.put(new Message("message", this.nickname, message));
            }
        } catch (IOException|InterruptedException e) {
            Logger.error(e.getMessage());
            System.out.println("Что-то пошло не таааак... " + e);
        }
    }

    private void register() throws IOException, InterruptedException {
        Logger.info("Request client nickname");
        this.sendMessage("Пожалуйста, укажите ваш никнейм:");
        this.nickname = this.reader.readLine();
        Logger.info("New user nickname: " + this.nickname);
        this.broadcast.put(new Message("register", this.nickname, ""));
    }

    public void sendMessage(String message) {
        this.writer.println(message.trim());
    }

    public void close() throws IOException {
        this.writer.close();
        this.reader.close();
        Logger.info("User " + this.nickname + " disconnected");
    }
}
