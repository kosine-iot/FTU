package com.kosine.ImageServer.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import com.kosine.ImageServer.model.FileDB;
import com.kosine.ImageServer.model.alertModel;
import com.kosine.ImageServer.model.smartrackmodel;
import com.kosine.ImageServer.repository.FileDBRepository;
import com.kosine.ImageServer.repository.alertRepo;
import com.kosine.ImageServer.repository.smartrackrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;

  @Autowired
  alertRepo alertrepo;

  @Autowired
  private smartrackrepo repo;
  public FileDB store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    List<String> elephantList = Arrays.asList(fileName.split("_"));
    String input = elephantList.get(1).substring(0, elephantList.get(1).length() - 4);
    input = input.replaceAll("-", "T");
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    LocalDateTime dateTime = LocalDateTime.parse(input, inputFormatter);
    String formattedDate = dateTime.format(outputFormatter);
    LocalDateTime Time = LocalDateTime.parse(formattedDate, outputFormatter);
    FileDB FileDB = new FileDB(elephantList.get(0),fileName, file.getContentType(), file.getBytes(),Time);

    return fileDBRepository.save(FileDB);
  }

  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }

  public List<smartrackmodel> getAllassets() {
    List<smartrackmodel> ls = new ArrayList<>();
    List<String> device = repo.findDistinctDevId();
    for(String st : device){
       ls.add(repo.findTopByDevIdOrderByIdDesc(st));
    }
    Map<String, String> map = new HashMap<String, String>();
    map.put("5ec6835877ec","Mumbai");
    map.put("16349821301b","Mumbai");
    map.put("023b9ba1a9ac","Mumbai");
    map.put("826545639885","Mumbai");
    map.put("aa48a372db76","Pune");
    map.put("e45f01c9f068","Pune");
    map.put("ae7e4e3eead5","Pune");
    map.put("72487d7b982c","Pune");
    map.put("e272d3b58870","Delhi");
    map.put("d6dcfd9b6ba3","Delhi");
    int epoch = (int) (System.currentTimeMillis()/1000);
    for (smartrackmodel li: ls) {
      if(epoch - li.getTimestamp() > 300){
        li.setStatus("OFFLINE");
      }else{
        li.setStatus("ONLINE");
      }
      li.setSiteLoc(map.get(li.getDevId()));
      li.setUser("kosine");
    }
      return ls;
  }

  public smartrackmodel getAssetById(String assetId) {
    return repo.findTopByDevIdOrderByIdDesc(assetId);
  }

  public List<Map> getAllassetlive(List<String> devId, List<String> fields, LocalDateTime startDate, LocalDateTime endDate) {
    if (!devId.isEmpty()) {
        ZonedDateTime zdtstart = ZonedDateTime.of(startDate, ZoneId.systemDefault());
        long start = zdtstart.toInstant().toEpochMilli();
        ZonedDateTime zdtend = ZonedDateTime.of(endDate, ZoneId.systemDefault());
        long end = zdtend.toInstant().toEpochMilli();
        long start1 = start / 1000;
        long end1 = end / 1000;
        System.out.println(start1);
        System.out.println(end1);
        List<Map> list = new ArrayList<>();
        ArrayList<smartrackmodel> ls = repo.graphResponse(devId, start1, end1);
        // ls.sort(Comparator.comparing(graphFields::getTimestamp));
        for (smartrackmodel grp : ls) {
          Map<String, Object> mp = new HashMap<>();
          for (String str : fields) {
            mp.put("timestamp", grp.getTimestamp());
            if (str.equals("internalTemp")) {
              mp.put("internalTemp", grp.getInternalTemp());
            }
            if (str.equals("externalTemp")) {
              mp.put("externalTemp", grp.getExternalTemp());
            }
            if (str.equals("supplyVoltage")) {
              mp.put("supplyVoltage", grp.getSupplyVoltage());
            }
            if (str.equals("humidity")) {
              mp.put("humidity", grp.getHumidity());
            }
          }
          list.add(mp);
        }
        return list;
    }
    return null;
  }

  public List<smartrackmodel> getLiveAssetById(String assetId) {
      List<smartrackmodel> list = repo.findTop30ByDevIdOrderByTimestampDesc(assetId);
    list.sort(Comparator.comparing(smartrackmodel::getTimestamp));
    return list;
  }


  public FileDB getLetest(String devId) {
    return fileDBRepository.findTopByDevIdOrderByTimestampDesc(devId);
  }



  public List<alertModel> getAlerts() {
    int epoch = (int) (System.currentTimeMillis()/1000);
    boolean check = false;
    List<alertModel> list = alertrepo.findAll();
    Collections.reverse(list);
//    list.sort(Collections.reverseOrder());
    return list;
  }
}
