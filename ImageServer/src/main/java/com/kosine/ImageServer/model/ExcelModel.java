package com.kosine.ImageServer.model;

import java.time.LocalDate;
import java.util.List;

public class ExcelModel {

    private List<String> devId;
  private LocalDate startDate;
  private LocalDate endDate;

    public List<String> getDevId() {
        return devId;
    }

    public void setDevId(List<String> devId) {
        this.devId = devId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
