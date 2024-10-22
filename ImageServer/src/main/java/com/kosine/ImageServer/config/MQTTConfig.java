package com.kosine.ImageServer.config;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MQTTConfig {

    private static final Logger logger = LoggerFactory.getLogger(MQTTConfig.class);

    @Value("${kosine.mqtt.broker}")
    private String broker;
    @Value("${kosine.mqtt.client}")
    private String clientId;
    @Value("${kosine.mqtt.username}")
    private String username;
    @Value("${kosine.mqtt.password}")
    private String password;
    @Value("${kosine.mqtt.qos}")
    private int qos;
    @Value("${kosine.mqtt.retained}")
    private boolean retained;


    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        client.connect(options);
        logger.info("Connected to mqtt",clientId);
        return client;
    }
}

