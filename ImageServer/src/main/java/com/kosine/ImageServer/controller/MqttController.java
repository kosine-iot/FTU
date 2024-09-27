package com.kosine.ImageServer.controller;


import com.kosine.ImageServer.config.Mqtt;
import com.kosine.ImageServer.exceptions.ExceptionMessages;
import com.kosine.ImageServer.message.ResponseMessage;
import com.kosine.ImageServer.model.MqttPublishModel;
import com.kosine.ImageServer.model.MqttSubscribeModel;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin("*")
@RestController
@RequestMapping("/iot/data")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")


public class MqttController {
    private static final Logger logger = LoggerFactory.getLogger(MqttController.class);

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

    private static boolean messagesEqual = false;
    @GetMapping("/publish/{devId}/{comp}/{state}")
    public ResponseEntity<ResponseMessage> publishMessage(@PathVariable String devId, @PathVariable String comp, @PathVariable String state) throws JSONException {

        String response = "";
        try {
            IMqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            client.connect(options);

            String topic = "kosine/psu/smartrack/" + devId + "/cmd";
            JSONObject json = new JSONObject("{" + comp + ":" + state + "}");
            byte[] payload = json.toString().getBytes();

            MqttMessage message = new MqttMessage(payload);
            message.setQos(qos);
            message.setRetained(retained);

            // Set callback to handle messages
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    logger.error("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String receivedPayload = new String(message.getPayload());


                    // Compare the received message with the published message
                    messagesEqual = receivedPayload.equals(json.toString());
                    if (messagesEqual) {
                        logger.info("Published and subscribed messages are the same.");
                    } else {
                        logger.info("Published and subscribed messages are different.");
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    logger.info("Delivery complete");
                }
            });

            // Subscribe to the topic
            client.subscribe(topic, qos);

            // Publish the message
            client.publish(topic, message);
            logger.info("JSON message published");

            // Keep the client connected to receive messages
            Thread.sleep(5000);

            client.disconnect();
            logger.info("Disconnected from broker");
            logger.info("Messages equal: " + messagesEqual);
            if(messagesEqual){
                response="Command found";
            }else{
                response="Command Not found";
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(response));
    }





}
