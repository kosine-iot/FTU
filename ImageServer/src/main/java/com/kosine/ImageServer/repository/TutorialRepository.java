package com.kosine.ImageServer.repository;




import com.kosine.ImageServer.model.smartrackmodel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface TutorialRepository extends MongoRepository<smartrackmodel, String> {
    @Query(value = "{devId: {$in: ?0}, timestamp: {$gte: ?1, $lte: ?2}}",fields = "{\"_id\": 0}",sort = "{\"timestamp\": -1}")
    ArrayList<smartrackmodel> graphResponse(List<String> devId, long startdate, long endDate);

    List<smartrackmodel> findTop1000ByDevIdOrderByTimestampDesc(String device);
}
