import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static final String ConfigPath = "./client_config.json";

    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            Config config = gson.fromJson(new FileReader(ConfigPath), Config.class);

            Logger.init(config.getAppName(), config.getLogFile());

            Client client = new Client(config.getHost(), config.getPort());
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
