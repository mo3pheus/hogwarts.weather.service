package domain;

import java.util.Properties;

public class WeatherConfig {
    private String weatherHost;
    private Double latitude;
    private Double longitude;
    private transient String apiKey;
    private String weatherDestination;
    private String weatherComamnd;
    private String weatherClientId;

    public WeatherConfig(Properties properties) {
        this.weatherHost = properties.getProperty("weather.host");
        this.apiKey = properties.getProperty("api.key");
        this.latitude = Double.parseDouble(properties.getProperty("latitude"));
        this.longitude = Double.parseDouble(properties.getProperty("longitude"));
        this.weatherComamnd = properties.getProperty("weather.command.channel");
        this.weatherDestination = properties.getProperty("weather.destination");
        this.weatherClientId = properties.getProperty("weather.client.id");
    }

    public String getWeatherClientId() {
        return weatherClientId;
    }

    public void setWeatherClientId(String weatherClientId) {
        this.weatherClientId = weatherClientId;
    }

    public String getWeatherHost() {
        return weatherHost;
    }

    public void setWeatherHost(String weatherHost) {
        this.weatherHost = weatherHost;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getWeatherDestination() {
        return weatherDestination;
    }

    public void setWeatherDestination(String weatherDestination) {
        this.weatherDestination = weatherDestination;
    }

    public String getWeatherComamnd() {
        return weatherComamnd;
    }

    public void setWeatherComamnd(String weatherComamnd) {
        this.weatherComamnd = weatherComamnd;
    }
}
