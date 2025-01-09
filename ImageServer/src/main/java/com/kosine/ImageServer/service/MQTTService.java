package com.kosine.ImageServer.service;

import com.kosine.ImageServer.controller.MqttController;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MQTTService {

    private static final Logger logger = LoggerFactory.getLogger(MQTTService.class);
    @Autowired
    private MqttClient mqttClient;
    @Value("${kosine.mqtt.qos}")
    private int qos;
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    @PostConstruct
    public void init() {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                logger.info("Connection lost. Reconnecting...");
                reconnect();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String receivedMessage = new String(message.getPayload());
                messageQueue.put(receivedMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Handle delivery complete if needed
            }
        });
    }

    public void publish(String topic, byte[] payload) {
        try {
            MqttMessage message = new MqttMessage(payload);
            message.setQos(qos);
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) throws MqttException {
        mqttClient.subscribe(topic);
        logger.error("Subscribed to topic: " + topic);
    }

    public String receiveMessage() {
        try {
            return messageQueue.take(); // Blocking call to take a message
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            e.printStackTrace();
            return null;
        }
    }

    public void clearMessages() {
        messageQueue.clear(); // Optional method to clear messages if needed
    }

    private void reconnect() {
        try {
            mqttClient.connect();
            System.out.println("Reconnected to MQTT broker.");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
