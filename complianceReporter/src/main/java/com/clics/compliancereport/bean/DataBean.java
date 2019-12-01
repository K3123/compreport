package com.clics.compliancereport.bean;


import com.clics.compliancereport.domain.DBInfo;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.List;

@JsonRootName(value = "DataBean")
public class DataBean {
    @JsonProperty(value="dbInfos")
    private List<DBInfo> dbInfos;

    public DataBean() {}

    public DataBean(List<DBInfo> dbInfos) {
        this.dbInfos = dbInfos;
    }


    public List<DBInfo> getDbInfos() {
        return dbInfos;
    }

    public void setDbInfos(List<DBInfo> dbInfos) {
        this.dbInfos = dbInfos;
    }
}
