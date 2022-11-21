import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static final String ConfigPath = "./server_config.json";

    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            Config config = gson.fromJson(new FileReader(ConfigPath), Config.class);

            Logger.init(config.getAppName(), config.getLogFile());

            Server server = new Server(8000);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
