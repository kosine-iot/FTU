package com.kosine.ImageServer.repository;


import com.kosine.ImageServer.model.alertModel;
import com.kosine.ImageServer.model.smartrackmodel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface alertRepo extends MongoRepository<alertModel,String> {

    @Query(value = "{check:?0}",sort = "{\"intimestamp\": -1}")
    ArrayList<alertModel> findByCheck(boolean check);

    @Query(value = "{timestamp: {$gt: ?0}}",sort = "{\"intimestamp\": -1}")
    ArrayList<alertModel> findByTime(long time);

    @Query(value = "{timestamp: {$lte: ?0}}",sort = "{\"timestamp\": -1}")
    ArrayList<alertModel> findByTimeread(int time);

    @Query(value = "{check:?0}",count = true)
    int findByChecktrue(boolean check);


}
