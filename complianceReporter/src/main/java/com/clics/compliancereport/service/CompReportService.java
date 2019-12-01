package com.clics.compliancereport.service;

import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBInfo;

import java.util.List;

public interface CompReportService {
    List<DBInfo> findByNameLike(String name);
    Boolean processRequest(CompRequest compRequest);
    List<DBInfo> findByName(String name);
}
