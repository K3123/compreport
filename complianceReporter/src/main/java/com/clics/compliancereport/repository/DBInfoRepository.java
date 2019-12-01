package com.clics.compliancereport.repository;


import com.clics.compliancereport.domain.DBInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DBInfoRepository extends JpaRepository<DBInfo, Long> {

    @Query("select db from DBInfo db where upper(db.status) <> ?1 and upper(db.name) like ?2 order by db.name asc")
    List<DBInfo> findByStatusNotAndNameLikeOrderByNameAsc(String status, String name);

    @Query("select db from DBInfo db where upper(db.status) <> ?1 and upper(db.name) = ?2")
    List<DBInfo> findByStatusNotAndName(String status, String name);

    @Query("select db from DBInfo db where upper(db.status) <> ?1")
    List<DBInfo> findByStatusNot(String status);

    @Query("select db from DBInfo db where upper(db.status) <> ?1 and db.id = ?2")
    List<DBInfo> findByStatusNotAndIdIn(String status, Long ids);
}
