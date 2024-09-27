package com.kosine.ImageServer.controller;

import com.kosine.ImageServer.model.ExcelModel;
import com.kosine.ImageServer.model.GraphModel;
import com.kosine.ImageServer.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/iot/data")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
public class ExcelController {


  @Autowired
  ExcelService fileService;

  @PostMapping("/download")
  public ResponseEntity<Resource> getFile(@RequestBody GraphModel graphModel) {

    String filename = "FTUData.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.load(graphModel));

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
  }

}
