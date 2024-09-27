package com.kosine.ImageServer.helper;

import com.kosine.ImageServer.model.smartrackmodel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  static String[] HEADERs = {"devId","internalTemp","externalTemp","supplyVoltage","fanDuty","TambSensorState","TintSensorState","supplyState","doorState","smokeState","hrtState","fansRunningState","fansState","humidity","floodState","timestamp"};
  static String SHEET = "Ftu_DataSheet";
  List<String> list = new ArrayList<>();
  ArrayList<String> ls = new ArrayList<>();





  public static ByteArrayInputStream tutorialsToExcel(List<smartrackmodel> tutorials) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (smartrackmodel tutorial : tutorials) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(tutorial.getDevId());
        row.createCell(1).setCellValue(tutorial.getInternalTemp());
        row.createCell(2).setCellValue(tutorial.getExternalTemp());
        row.createCell(3).setCellValue(tutorial.getSupplyVoltage());
        row.createCell(4).setCellValue(tutorial.getFanDuty());
        row.createCell(5).setCellValue(tutorial.getTambSensorState());
        row.createCell(6).setCellValue(tutorial.getTintSensorState());
        row.createCell(7).setCellValue(tutorial.getSupplyState());
        row.createCell(8).setCellValue(tutorial.getDoorState());
        row.createCell(9).setCellValue(tutorial.getSmokeState());
        row.createCell(10).setCellValue(tutorial.getHrtState());
        row.createCell(11).setCellValue(tutorial.getFansRunningState());
        row.createCell(12).setCellValue(tutorial.getFansState());
        row.createCell(13).setCellValue(tutorial.getHumidity());
        row.createCell(14).setCellValue(tutorial.getFloodState());
        row.createCell(15).setCellValue(tutorial.getTimestamp());

      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

}
