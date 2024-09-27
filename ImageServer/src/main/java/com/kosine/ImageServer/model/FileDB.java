package com.kosine.ImageServer.model;



import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class FileDB {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String devId;
  private String fieldeng;
  private LocalDateTime timestamp;
  private String name;

  private String type;

  @Lob
  private byte[] data;

  public FileDB() {
  }

  public FileDB(String devId,String name, String type, byte[] data,LocalDateTime timestamp) {
    this.devId = devId;
  //  this.fieldeng = fieldeng;
    this.name = name;
    this.type = type;
    this.data = data;
    this.timestamp = timestamp;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getDevId() {
    return devId;
  }

  public void setDevId(String devId) {
    this.devId = devId;
  }

  public String getFieldeng() {
    return fieldeng;
  }

  public void setFieldeng(String fieldeng) {
    this.fieldeng = fieldeng;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
