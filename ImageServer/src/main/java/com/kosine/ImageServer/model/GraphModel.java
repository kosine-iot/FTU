package com.kosine.ImageServer.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;


public class GraphModel {
    private static final Logger logger = LoggerFactory.getLogger(GraphModel.class);
    private List<String> devId;
    private List<String> fields;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public List<String> getDevId() {
        return devId;
    }

    public void setDevId(List<String> devId) {
        this.devId = devId;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
