package com.kosine.ImageServer.repository;

import com.kosine.ImageServer.model.smartrackmodel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface smartrackrepo extends MongoRepository<smartrackmodel,String> {
    smartrackmodel findTopByDevIdOrderByIdDesc(String devid);

    @Aggregation(pipeline = {"{ '$group': { '_id' : '$devId' } }","{ $sort: { id: 1 } }"})
    Set<String> findDistinctDevId();

    @Query(value = "{devId: {$in: ?0}, timestamp: {$gte: ?1, $lte: ?2}}",sort = "{\"timestamp\": 1}")
    ArrayList<smartrackmodel> graphResponse(List<String> devId, long startdate, long endDate);

    ArrayList<smartrackmodel> findTop30ByDevIdOrderByTimestampDesc(String device);
}
