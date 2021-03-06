package com.clics.compliancereport.service.impl;

import com.clics.compliancereport.common.Constants;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBInfo;
import com.clics.compliancereport.domain.DBProcessType;
import com.clics.compliancereport.exception.ServiceException;
import com.clics.compliancereport.repository.DBInfoRepository;
import com.clics.compliancereport.service.CompReportService;
import com.clics.compliancereport.service.CompRequestService;
import com.clics.compliancereport.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "compReportService")
public class CompReportServiceImpl implements CompReportService {

    private static final Logger LOG = LoggerFactory.getLogger(CompReportServiceImpl.class);
    @Autowired
    private DBInfoRepository dbInfoRepository;
    @Autowired
    private CompRequestService compRequestService;

    @Override
    @Transactional
    public Boolean processRequest(final CompRequest compRequest) {
        LOG.debug("Entering 'SERVICE:processRequest()' method...");
        Boolean bSuccess = Boolean.FALSE;

        //DBProcessType.ALL: proccess all databases
        if (compRequest.getDbProcessType() == DBProcessType.ALL) {
            bSuccess = processAllDatabases(compRequest);
        }
        //DBProcessType.SINGLE or DBProcessType.MULTIPLE: proccess single or multiple databases
        //NOTE: database name can't be null or empty:
        else if ((compRequest.getDbProcessType() == DBProcessType.SINGLE ||
            compRequest.getDbProcessType() == DBProcessType.MULTIPLE) &&
            StringUtils.isNotEmpty(compRequest.getDatabaseName())) {
            bSuccess = processDatabases(compRequest);
        }
        //missing params and should throw an error
        else {
            throw new ServiceException(Constants.MISSING_PARAMETERS);
        }
        return bSuccess;
    }

    /*
     *  This method handles a single or multiple  databases so it can create request for each.
     */
    private Boolean processDatabases(final CompRequest compRequest) {
        LOG.debug("<<<<< Entering method 'processDatabases(compRequest)'...");
        Boolean bSuccess = Boolean.FALSE;

        //compRequest should always be present
        Assert.notNull(compRequest);

        //remove all white spaces from database name before processing
        String dbname = StrUtil.removeSpaces(compRequest.getDatabaseName());

        //Tokenize dbname which is of the format "id1:name1,id2:name2,...idn:namen" or "id1:name1" by delimiter ","
        Object[] dbArray = StrUtil.tokenize(dbname, Constants.COMMA_DELIMITER);
        Object[] idArray;

        try {
            if (dbArray.length != 0) {

                for (Object dbId : dbArray) {

                    //Tokenize dbId which is of the format "id:name" by delimiter ":"
                    idArray = StrUtil.tokenize((String)dbId, Constants.COLON_DELIMITER);

                    //we only need the id token from "id:name"
                    if (StringUtils.isNotEmpty((String)idArray[0])) {
                        //convert string id to a Long
                        Long id = Long.valueOf((String)idArray[0]);
                        //get the entire DBInfo from the repository based on an id
                        DBInfo dbInfo = dbInfoRepository.findOne(id);
                        //make sure a DBInfo is returned
                        if (dbInfo != null) {

                            //make a copy of the original compRequest object
                            CompRequest cr = compRequest.duplicate();

                            //populate the compRequest with the necessary data it needs for persistence
                            cr.setId(null);
                            cr.setDatabaseName(dbInfo.getName());
                            cr.setUsage(dbInfo.getUsage());
                            cr.setVirtualSqlInstance(dbInfo.getVirtualSqlInstance());
                            cr.setReportDate(new Date());

                            //persist a single compRequest object
                            compRequestService.saveRequest(cr);

                            //save was successful
                            bSuccess = Boolean.TRUE;
                        }else {
                            LOG.debug(String.format("<<<<< No matching DBInfo found for Id: %s", id));
                        }
                    }else{
                        throw new ServiceException("ID id missing from the database name id:name");
                    }
                } // end of for
            } //end of outer if

        } catch (PersistenceException pe) {
            throw new ServiceException(pe);
        }
        return bSuccess;
    }


    /*
     *  This method handles the list for all databases so it can create request for each
     */
    private Boolean processAllDatabases(CompRequest compRequest) {
        LOG.debug("<<<<< Entering method 'processAllDatabases(compRequest)'...");
        Boolean bSuccess = Boolean.FALSE;

        try {
            //get the entire list of databases that are not retired
            List<DBInfo> dbInfos = dbInfoRepository.findByStatusNot(Constants.RETIRED);

            List<CompRequest> compRequests = new ArrayList<CompRequest>();

            //loop through all the databases,create a request for each and add to a list for batch processing
            for (DBInfo dbInfo : dbInfos) {
                CompRequest cr = compRequest.duplicate();
                cr.setDatabaseName(dbInfo.getName());
                cr.setUsage(dbInfo.getUsage());
                cr.setVirtualSqlInstance(dbInfo.getVirtualSqlInstance());
                cr.setReportDate(new Date());
                compRequests.add(cr);
            }
            //batch save the list of requests
            compRequestService.saveRequests(compRequests);

            //save was successful
            bSuccess = Boolean.TRUE;
        } catch (PersistenceException pe) {
            throw new ServiceException(pe);
        }
        return bSuccess;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DBInfo> findByNameLike(String name) {
        LOG.debug("<<<<< Entering method 'findByNameLike(\"{}\")...", name);
        return dbInfoRepository.findByStatusNotAndNameLikeOrderByNameAsc(Constants.RETIRED, name.toUpperCase() + "%");
    }

    @Override
    @Transactional(readOnly = true)
    public List<DBInfo> findByName(String name) {
        return dbInfoRepository.findByStatusNotAndName(Constants.RETIRED, name.toUpperCase());
    }

}
