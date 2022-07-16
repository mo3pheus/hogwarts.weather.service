package mqtt;

import domain.WeatherConfig;
import https.WeatherDataCollector;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HogwartsWeatherman implements Runnable {
    Logger logger = LoggerFactory.getLogger(HogwartsWeatherman.class);
    private IMqttClient weatherMan;
    private IMqttClient commandClient;
    private String weatherHost;
    private WeatherDataCollector weatherDataCollector;
    private WeatherConfig weatherConfig;
    private WeatherReceiver weatherReceiver;

    public HogwartsWeatherman(WeatherConfig weatherConfig) throws MqttException {
        this.weatherConfig = weatherConfig;
        this.weatherHost = weatherConfig.getWeatherHost();
        this.weatherMan = new MqttClient(weatherHost, MqttAsyncClient.generateClientId());

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setConnectionTimeout(3000);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setMaxInflight(10);

        this.weatherReceiver = new WeatherReceiver(weatherConfig);
        weatherMan.connect(mqttConnectOptions);
        weatherMan.subscribe(weatherConfig.getWeatherComamnd());
        weatherMan.setCallback(weatherReceiver);
    }

    public void run() {
        Thread.currentThread().setName("weather-man");

        weatherDataCollector = new WeatherDataCollector(weatherConfig);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> weatherFuture = executorService.submit(weatherDataCollector);

        try {
            String weatherReport = weatherFuture.get();

            weatherMan.publish(weatherConfig.getWeatherDestination()
                    , new MqttMessage(weatherReport.getBytes()));
        } catch (Exception e) {
            logger.error("Could not send weather data.", e);
        }
    }
}
