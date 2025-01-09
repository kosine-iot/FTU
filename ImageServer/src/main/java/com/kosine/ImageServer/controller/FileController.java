package com.kosine.ImageServer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kosine.ImageServer.model.GraphModel;
import com.kosine.ImageServer.model.alertModel;
import com.kosine.ImageServer.model.smartrackmodel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import com.kosine.ImageServer.message.ResponseFile;
import com.kosine.ImageServer.message.ResponseMessage;
import com.kosine.ImageServer.model.FileDB;
import com.kosine.ImageServer.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("*")
@Controller
@RequestMapping("/iot/data")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
public class FileController {

  @Autowired
  private FileStorageService storageService;

  public static String UPLOAD_DIRECTORY = "/home/kosine/Service/service/uploads";

  @PostMapping("/image")
  public ResponseEntity<ResponseMessage> uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
    String message = "";
    try {
      StringBuilder fileNames = new StringBuilder();
      Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
      fileNames.append(file.getOriginalFilename());
      Files.write(fileNameAndPath, file.getBytes());
      model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

//  @PostMapping("/upload")
//  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
//    String message = "";
//    try {
//      storageService.store(file);
//      message = "Uploaded the file successfully: " + file.getOriginalFilename();
//      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//    } catch (Exception e) {
//      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//    }
//  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }

  @GetMapping("/last/{devId}")
  public ResponseEntity<?> getDevFile(@PathVariable String devId) {
    try {


      FileDB fileDB = storageService.getLetest(devId);
      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
              .body(fileDB.getData());
    }catch (Exception e){
      String message = "Image not found" ;
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }



  @GetMapping("/assets")
  public ResponseEntity<List<smartrackmodel>> getAsset(){
    return ResponseEntity.status(HttpStatus.OK).body(storageService.getAllassets());
  }

  @GetMapping("/asset/{assetId}")
  public ResponseEntity<smartrackmodel> getAsset(@PathVariable("assetId") String assetId) {
    return ResponseEntity.status(HttpStatus.OK).body(storageService.getAssetById(assetId));
  }

  @PostMapping("/graph")
  public ResponseEntity<?> getAssetlive(@RequestBody GraphModel graphModel){
      LocalDateTime startDate1 = graphModel.getStartDateTime();
      LocalDateTime endDate1 = graphModel.getEndDateTime();
//            LocalDateTime startDate = startDate1.atTime(0, 0, 0);
//            LocalDateTime endDate = endDate1.atTime(0, 0, 0);
      return ResponseEntity.status(HttpStatus.OK).body(storageService.getAllassetlive(graphModel.getDevId(),graphModel.getFields(),startDate1,endDate1));

  }
  @GetMapping("/live/{assetId}")
  public ResponseEntity<?> getLiveAsset(@PathVariable("assetId") String assetId) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(storageService.getLiveAssetById(assetId));
    }catch (Exception e){
      String message = "asset not found" ;
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

//  @GetMapping("/getAlertcount")
//  public ResponseEntity
@GetMapping("/alerts")
public ResponseEntity<List<alertModel>> alerts() {
  return ResponseEntity.status(HttpStatus.OK).body(storageService.getAlerts());
}

}
