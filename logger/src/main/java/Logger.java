import java.io.IOException;
import java.io.InputStream;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

public class Logger {
    private static java.util.logging.Logger logger;

    public static void init(String appName, String logFile) throws IOException {
        InputStream stream = Logger.class.getClassLoader().getResourceAsStream("logging.properties");
        LogManager.getLogManager().readConfiguration(stream);

        logger = java.util.logging.Logger.getLogger(appName);
        FileHandler fileHandler = new FileHandler(logFile, true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.severe(message);
    }
}
