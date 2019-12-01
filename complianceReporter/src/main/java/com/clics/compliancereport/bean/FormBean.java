package com.clics.compliancereport.bean;


import com.clics.compliancereport.common.Constants;

import java.util.List;

public class FormBean {
    private boolean bAllDatabases = false;
    private String search;
    private String name;
    private String usage;
    private String location;
    private String version;
    private String desc;
    private String serverName;
    private String virtualSqlInstance;
    private String port;
    private String email;
    private String reportType;
    private String debug = Constants.NOTAPPLICABLE;
    private String ssoId;
    private List<LabelValueBean> reportTypes;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getVirtualSqlInstance() {
        return virtualSqlInstance;
    }

    public void setVirtualSqlInstance(String virtualSqlInstance) {
        this.virtualSqlInstance = virtualSqlInstance;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }


    public boolean isbAllDatabases() {
        return bAllDatabases;
    }

    public void setbAllDatabases(boolean bAllDatabases) {
        this.bAllDatabases = bAllDatabases;
    }


    public List<LabelValueBean> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(List<LabelValueBean> reportTypes) {
        this.reportTypes = reportTypes;
    }

}
