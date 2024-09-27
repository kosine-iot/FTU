package com.kosine.ImageServer.repository;

import com.kosine.ImageServer.model.FileDB;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface FileDBRepository extends JpaRepository<FileDB, String> {



    FileDB findTopByDevIdOrderByTimestampDesc(@Param("devId") String devId);
}
