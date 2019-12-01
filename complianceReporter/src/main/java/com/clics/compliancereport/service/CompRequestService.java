package com.clics.compliancereport.service;

import com.clics.compliancereport.domain.CompRequest;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import java.util.List;

public interface CompRequestService {
    boolean saveRequest(CompRequest compRequest);
    boolean saveRequests(List<CompRequest> compRequests);
    List<CompRequest> getRequestsBySSOId(String ssoId);
    DataSet<CompRequest> findRequestsWithDatatablesCriterias(DatatablesCriterias criterias);
}
