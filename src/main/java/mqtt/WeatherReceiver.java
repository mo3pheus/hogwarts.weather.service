package mqtt;

import domain.WeatherConfig;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherReceiver implements MqttCallback {
    private Logger logger = LoggerFactory.getLogger(WeatherReceiver.class);
    private WeatherConfig weatherConfig;

    public WeatherReceiver(WeatherConfig weatherConfig) {
        this.weatherConfig = weatherConfig;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.debug("Connection timedout reconnecting");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        logger.info("Message arrived on " + s);
        logger.info("Message => " + new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("Delivery complete");
    }
}
