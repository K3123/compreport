package com.clics.compliancereport.service.impl;

import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.exception.ServiceException;
import com.clics.compliancereport.repository.CompRequestRepository;
import com.clics.compliancereport.repository.RequestSearchRepository;
import com.clics.compliancereport.service.CompRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import javax.persistence.PersistenceException;
import java.util.List;

@Service(value = "compRequestService")
public class CompRequestServiceImpl implements CompRequestService {

    @Autowired
    private CompRequestRepository compRequestRepository;
    @Autowired
    private RequestSearchRepository requestSearchRepository;


    /**
     * This service saves a request
     * @param compRequest
     * @return true if sucessful and false is failed
     */
    @Override
    public boolean saveRequest(final CompRequest compRequest) {
        Assert.notNull(compRequest, "CompRequest can't be null");
        boolean bSuccess;

        try {
           compRequestRepository.save(compRequest);
           bSuccess = true;
        }catch(PersistenceException pe){
            throw new ServiceException(pe);
        }
        return bSuccess;
    }

    /**
     * This service saves a list of requests in a batch way
     * @param compRequests
     * @return true if sucessful and false is failed
     */
    @Override
    public boolean saveRequests(final List<CompRequest> compRequests) {
        Assert.notNull(compRequests, "CompRequests can't be null");
        boolean bSuccess;
        try {
            compRequestRepository.save(compRequests);
            bSuccess = true;
        }catch(PersistenceException pe){
            throw new ServiceException(pe);
        }
        return bSuccess;
    }

    /**
     * This service gets a list of requests
     * @return list of compRequests
     */
    @Override
    public List<CompRequest> getRequestsBySSOId(String ssoId) {
        return compRequestRepository.getRequestsBySSOId(ssoId);
    }

    /**
     * <p>
     * Query used to populate the DataTables that display the list of persons.
     *
     * @param criterias
     * The DataTables criterias used to filter the persons.
     * (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    @Override
    public DataSet<CompRequest> findRequestsWithDatatablesCriterias(DatatablesCriterias criterias) {

        List<CompRequest> requests;
        DataSet<CompRequest> dataSet;

        try {
           requests = requestSearchRepository.findRequestsWithDatatablesCriterias(criterias);
           Long count = requestSearchRepository.getTotalCount();
           Long countFiltered = requestSearchRepository.getFilteredCount(criterias);
           dataSet =  new DataSet<CompRequest>(requests, count, countFiltered);
        }catch(PersistenceException pe){
            throw new ServiceException(pe);
        }
        return dataSet;
    }
}
