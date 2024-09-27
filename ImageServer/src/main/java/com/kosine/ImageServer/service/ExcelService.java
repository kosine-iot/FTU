package com.kosine.ImageServer.service;

import com.kosine.ImageServer.helper.ExcelHelper;
import com.kosine.ImageServer.model.ExcelModel;
import com.kosine.ImageServer.model.GraphModel;
import com.kosine.ImageServer.model.smartrackmodel;

import com.kosine.ImageServer.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ExcelService {
  @Autowired
  TutorialRepository repository;

  public ByteArrayInputStream load(GraphModel graphModel) {
//    LocalDate startDate1 = LocalDate.from(graphModel.getStartDate());
//    LocalDate endDate1 = LocalDate.from(graphModel.getEndDate());
    LocalDateTime startDate = graphModel.getStartDateTime();
    LocalDateTime endDate = graphModel.getEndDateTime();
    ZonedDateTime zdtstart = ZonedDateTime.of(startDate, ZoneId.systemDefault());
    long start = zdtstart.toInstant().toEpochMilli();
    ZonedDateTime zdtend = ZonedDateTime.of(endDate, ZoneId.systemDefault());
    long end = zdtend.toInstant().toEpochMilli();
    long start1 = start / 1000;
    long end1 = end / 1000;


    List<smartrackmodel> tutorials = repository.graphResponse(graphModel.getDevId(),start1,end1);

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(tutorials);
    return in;
  }

}
