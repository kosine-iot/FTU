package com.kosine.ImageServer.controller;


import com.kosine.ImageServer.message.ResponseMessage;
import com.kosine.ImageServer.service.MQTTService;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/iot/data")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")


public class MqttController {

    @Autowired
    private MQTTService mqttService;

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


    @GetMapping("/command/{devId}/{key}")
    public ResponseEntity<ResponseMessage> sendMessage(@PathVariable String devId, @PathVariable String key) {
        String response = "";
        try {
            String topic = "kosine/psu/smartrack/key/"+devId;
            String jsonString = "{\"key\":"+key+"}";

            mqttService.subscribe(topic);
            JSONObject json = new JSONObject(jsonString);
            byte[] payload = json.toString().getBytes();
            MqttMessage message = new MqttMessage(payload);
            mqttService.publish(topic, payload);
            String msg = mqttService.receiveMessage();
            if (message.toString().equals(msg)) {
                response = "Command found";
            } else {
                response = "Command Not found";
            }
            mqttService.clearMessages();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
        }catch (Exception e){
            mqttService.clearMessages();
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(response));
        }
    }

    @GetMapping("/publish/{devId}/{comp}/{state}")
    public ResponseEntity<ResponseMessage> publishMessage(@PathVariable String devId, @PathVariable String comp, @PathVariable String state) throws JSONException {

        String response = "";
        try {
            String topic = "kosine/psu/smartrack/lock/"+devId;
            String jsonString = "";
            if(state.equals("open")){
                jsonString = "{\"lock\":1}";
//
            }
            if(state.equals("close")){
                jsonString = "{\"lock\":0}";
            }

            mqttService.subscribe(topic);
            JSONObject json = new JSONObject(jsonString);
            byte[] payload = json.toString().getBytes();
            MqttMessage message = new MqttMessage(payload);
            mqttService.publish(topic, payload);
            String msg = mqttService.receiveMessage();
            if (message.toString().equals(msg)) {
                response = "Command found";
            } else {
                response = "Command Not found";
            }
            mqttService.clearMessages();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
        }catch (Exception e){
            mqttService.clearMessages();
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(response));
        }
    }


}
