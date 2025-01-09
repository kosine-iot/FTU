package com.kosine.ImageServer.shutdown;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ShutdownHook implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    @Autowired
    private MqttClient client;

    @Override
    public void run(String... args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (client.isConnected()) {
                    client.disconnect();
                    logger.info("Disconnected from MQTT broker.");
                }
            } catch (MqttException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }));
    }
}