package bootstrap;

import domain.WeatherConfig;
import https.WeatherDataCollector;
import mqtt.HogwartsWeatherman;
import org.apache.log4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.*;

public class Driver {
    public static Properties projectProperties = new Properties();
    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) throws Exception {
        loadProjectProperties(args);

        Boolean debugLogEnabled = Boolean.parseBoolean(projectProperties.getProperty("debug.log.enabled"));
        configureLogging(debugLogEnabled, projectProperties.getProperty("log.file.path"));

        WeatherConfig weatherConfig = new WeatherConfig(projectProperties);
        logger.info("Weather destination = " + weatherConfig.getWeatherDestination());

        HogwartsWeatherman hogwartsWeatherman = new HogwartsWeatherman(weatherConfig);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(hogwartsWeatherman, 0l, 1l, TimeUnit.HOURS);
        //System.exit(0);
    }

    public static String configureLogging(boolean debug, String filePath) {
        FileAppender fa = new FileAppender();

        if (!debug) {
            fa.setThreshold(Level.toLevel(Priority.INFO_INT));
            fa.setFile(filePath + "weatherService.log");
        } else {
            fa.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            fa.setFile(filePath + "weatherServiceDebug.log");
        }

        fa.setLayout(new EnhancedPatternLayout("%-6d [%25.35t] %-5p %40.80c - %m%n"));

        fa.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(fa);
        return fa.getFile();
    }

    public static void loadProjectProperties(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("--")) {
                projectProperties.put(args[i].replaceAll("--", ""), args[i + 1]);
            }
        }
    }
}
