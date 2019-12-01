package com.clics.compliancereport.repository;


import com.clics.compliancereport.domain.CompRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompRequestRepository extends JpaRepository<CompRequest, Long> {

    @Transactional(readOnly = true)
    @Query("select cr from CompRequest cr where ssoId =?1 and upper(cr.requestStatus) <> 'DONE'")
    List<CompRequest> getRequestsBySSOId(String ssoId);

}
