package com.clics.compliancereport.util;

import com.clics.compliancereport.bean.FormBean;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBProcessType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public final class MiscUtil {

    private MiscUtil() {}

    public static CompRequest populateCompRequest(FormBean formBean){
        Assert.notNull(formBean, "a valid formBean is required");
        CompRequest compRequest = new CompRequest();
        if(formBean != null){
            compRequest.setDatabaseName(formBean.getName());
            compRequest.setSsoId(formBean.getSsoId());
            compRequest.setEmailAddress(formBean.getEmail());
            compRequest.setComReportType(formBean.getReportType());
            compRequest.setbAllDatabases(formBean.isbAllDatabases());
            if(compRequest.isbAllDatabases()) {
                compRequest.setDbProcessType(DBProcessType.ALL);
            }else if(StringUtils.isNotEmpty(compRequest.getDatabaseName()) && compRequest.getDatabaseName().contains(",")) {
                compRequest.setDbProcessType(DBProcessType.MULTIPLE);
            }else if(StringUtils.isNotEmpty(compRequest.getDatabaseName())){
                compRequest.setDbProcessType(DBProcessType.SINGLE);
            }else{
                compRequest.setDbProcessType(DBProcessType.NONE);
            }
        }
        return compRequest;
    }
}
