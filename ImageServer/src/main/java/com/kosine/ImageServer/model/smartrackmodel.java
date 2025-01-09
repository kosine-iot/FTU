package com.kosine.ImageServer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;


@Document(collection = "smartrack")
public class smartrackmodel {
    @Id
    private String id;
    private String devId;

    private double internalTemp;
    private double externalTemp;
    private int supplyVoltage;
    private int fanDuty;
    private int TambSensorState;
    private int TintSensorState;
    private int supplyState;
    private int doorState;
    private int smokeState;
    private int hrtState;
    private int fansRunningState;
    private int fansState;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private double humidity;
    private int floodState;
    private String siteLoc;
    private String user;
    private int timestamp;

    public String getSiteLoc() {
        return siteLoc;
    }

    public String getUser() {
        return user;
    }

    public void setSiteLoc(String siteLoc) {
        this.siteLoc = siteLoc;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public double getInternalTemp() {
        return internalTemp;
    }

    public void setInternalTemp(double internalTemp) {
        this.internalTemp = internalTemp;
    }

    public double getExternalTemp() {
        return externalTemp;
    }

    public void setExternalTemp(double externalTemp) {
        this.externalTemp = externalTemp;
    }

    public int getSupplyVoltage() {
        return supplyVoltage;
    }

    public void setSupplyVoltage(int supplyVoltage) {
        this.supplyVoltage = supplyVoltage;
    }

    public int getFanDuty() {
        return fanDuty;
    }

    public void setFanDuty(int fanDuty) {
        this.fanDuty = fanDuty;
    }

    public int getTambSensorState() {
        return TambSensorState;
    }

    public void setTambSensorState(int tambSensorState) {
        TambSensorState = tambSensorState;
    }

    public int getTintSensorState() {
        return TintSensorState;
    }

    public void setTintSensorState(int tintSensorState) {
        TintSensorState = tintSensorState;
    }

    public int getSupplyState() {
        return supplyState;
    }

    public void setSupplyState(int supplyState) {
        this.supplyState = supplyState;
    }

    public int getDoorState() {
        return doorState;
    }

    public void setDoorState(int doorState) {
        this.doorState = doorState;
    }

    public int getSmokeState() {
        return smokeState;
    }

    public void setSmokeState(int smokeState) {
        this.smokeState = smokeState;
    }

    public int getHrtState() {
        return hrtState;
    }

    public void setHrtState(int hrtState) {
        this.hrtState = hrtState;
    }

    public int getFansRunningState() {
        return fansRunningState;
    }

    public void setFansRunningState(int fansRunningState) {
        this.fansRunningState = fansRunningState;
    }

    public int getFansState() {
        return fansState;
    }

    public void setFansState(int fansState) {
        this.fansState = fansState;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public int getFloodState() {
        return floodState;
    }

    public void setFloodState(int floodState) {
        this.floodState = floodState;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
