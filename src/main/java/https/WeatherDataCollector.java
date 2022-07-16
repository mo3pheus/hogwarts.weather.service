package https;

import domain.WeatherConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;

public class WeatherDataCollector implements Callable<String> {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private WeatherConfig weatherConfig;
    private Logger logger = LoggerFactory.getLogger(WeatherDataCollector.class);

    public WeatherDataCollector(WeatherConfig weatherConfig) {
        this.weatherConfig = weatherConfig;
    }

    @Override
    public String call() {
        Thread.currentThread().setName("weather-data-collector");

        String fullUrl = BASE_URL + "?lat=" + weatherConfig.getLatitude() +
                "&lon=" + weatherConfig.getLongitude() + "&appid=" + weatherConfig.getApiKey();

        logger.debug(fullUrl);

        HttpGet httpGet = new HttpGet(fullUrl);
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String weatherReport = EntityUtils.toString(httpResponse.getEntity());
            EntityUtils.consumeQuietly(httpResponse.getEntity());

            logger.info(weatherReport);
            return weatherReport;
        } catch (IOException e) {
            logger.error("Could not execute http request/ process response.", e);
            return "error";
        }
    }
}
