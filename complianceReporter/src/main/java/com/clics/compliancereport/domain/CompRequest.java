package com.clics.compliancereport.domain;


import com.clics.compliancereport.common.Constants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "DBH.COMPLIANCE_RPT_RQT")
public class CompRequest {
    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final String UPDATED_BY = "INFOAPP";
    public static final String REPORT_TYPE = "MANUAL";
    public static final String INSERTED_BY = "INFOAPP";
    public static final String REQUEST_STATUS = "OPEN";
    public static final String DUS_REPORT = "Database User Scan Reports";
    private Long id;
    private String databaseName;
    private Date reportDate;
    private String updatedBy = UPDATED_BY;
    private String reportType = REPORT_TYPE;
    private String insertedBy = INSERTED_BY;
    private String comReportType;
    private String requestStatus = REQUEST_STATUS;
    private String emailAddress;
    private String ssoId;
    private String usage;
    private boolean bAllDatabases;
    private String virtualSqlInstance;
    private DBProcessType dbProcessType;
    private List<String> databaseNames = new ArrayList<String>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DBH.cmrptrqt_seq")
    @SequenceGenerator(name = "DBH.cmrptrqt_seq", sequenceName = "DBH.CMRPTRQT_SEQ")
    @Column(name="COM_RPTRQT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="COM_DATABASE_NAME", nullable = false)
    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Column(name="REPORT_DATE", nullable = true)
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Column(name="COM_UPDATED_BY", nullable = true)
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name="REPORT_TYPE", nullable = true)
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @Column(name="COM_INSERTED_BY", nullable = true)
    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    @Column(name="COM_REPORT_TYPE", nullable = true)
    public String getComReportType() {
        return comReportType;
    }

    public void setComReportType(String comReportType) {
        this.comReportType = comReportType;
    }

    @Column(name="REQ_STATUS", nullable = true)
    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Column(name="EMAILADDRESS", nullable = true)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name="REQUESTORSSO", nullable = true)
    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    @Column(name="DB_USAGE", nullable = false)
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Transient
    public boolean isbAllDatabases() {
        return bAllDatabases;
    }

    public void setbAllDatabases(boolean bAllDatabases) {
        this.bAllDatabases = bAllDatabases;
    }


    @Column(name="VIRTUAL_SQL_INSTANCE", nullable = false)
    public String getVirtualSqlInstance() {
        return virtualSqlInstance;
    }

    public void setVirtualSqlInstance(String virtualSqlInstance) {
        this.virtualSqlInstance = virtualSqlInstance;
    }


    @Transient
    public DBProcessType getDbProcessType() {
        return dbProcessType;
    }

    public void setDbProcessType(DBProcessType dbProcessType) {
        this.dbProcessType = dbProcessType;
    }


    @Transient
    public List<String> getDatabaseNames() {
        return databaseNames;
    }

    public void setDatabaseNames(List<String> databaseNames) {
        this.databaseNames = databaseNames;
    }

    @Transient
    public String getReportDateStr() {
        return formatDate(this.reportDate);
    }


    public CompRequest duplicate() {
        CompRequest cr = new CompRequest();
        cr.setComReportType(getComReportType());
        cr.setUsage(getUsage());
        cr.setDatabaseName(getDatabaseName());
        cr.setEmailAddress(getEmailAddress());
        cr.setId(getId());
        cr.setInsertedBy(getInsertedBy());
        cr.setUpdatedBy(getUpdatedBy());
        cr.setbAllDatabases(isbAllDatabases());
        cr.setReportDate(getReportDate());
        cr.setReportType(getReportType());
        cr.setRequestStatus(getRequestStatus());
        cr.setSsoId(getSsoId());
        cr.setVirtualSqlInstance(getVirtualSqlInstance());
        cr.setDbProcessType(getDbProcessType());
        return cr;
    }

    public static CompRequestBuilder getBuilder(DBProcessType dbProcessType) {
        return new CompRequestBuilder(dbProcessType);
    }


    public static class CompRequestBuilder {

        private CompRequest built;

        private CompRequestBuilder(){}

        public CompRequestBuilder(DBProcessType dbProcessType) {
            built = new CompRequest();
            built.dbProcessType = dbProcessType;
            built.reportDate = new Date();
        }

        public CompRequest build() {
            return built;
        }

        public CompRequestBuilder id(Long id) {
            built.id = id;
            return this;
        }
        public CompRequestBuilder databaseName(String databaseName) {
            built.databaseName = databaseName;
            return this;
        }
        public CompRequestBuilder reportType(String reportType) {
            built.reportType = reportType;
            return this;
        }
        public CompRequestBuilder requestStatus(String requestStatus) {
            built.requestStatus = requestStatus;
            return this;
        }
        public CompRequestBuilder comReportType(String comReportType) {
            built.comReportType = comReportType;
            return this;
        }
        public CompRequestBuilder emailAddress(String emailAddress) {
            built.emailAddress = emailAddress;
            return this;
        }
        public CompRequestBuilder ssoId(String ssoId) {
            built.ssoId = ssoId;
            return this;
        }
        public CompRequestBuilder usage(String usage) {
            built.usage = usage;
            return this;
        }
        public CompRequestBuilder virtualSqlInstance(String virtualSqlInstance) {
            built.virtualSqlInstance = virtualSqlInstance;
            return this;
        }
        public CompRequestBuilder dbProcessType(DBProcessType dbProcessType) {
            built.dbProcessType = dbProcessType;
            return this;
        }
        public CompRequestBuilder bAllDatabases(boolean bAllDatabases) {
            built.bAllDatabases = bAllDatabases;
            return this;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CompRequest");
        sb.append("{id=").append(id);
        sb.append(", databaseName='").append(databaseName).append('\'');
        sb.append(", reportDate=").append(reportDate);
        sb.append(", reportType='").append(reportType).append('\'');
        sb.append(", comReportType='").append(comReportType).append('\'');
        sb.append(", requestStatus='").append(requestStatus).append('\'');
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append(", ssoId='").append(ssoId).append('\'');
        sb.append(", usage='").append(usage).append('\'');
        sb.append(", virtualSqlInstance='").append(virtualSqlInstance).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private String formatDate(Date date) {
        if(date == null) {
            return Constants.BLANK;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_FORMAT);
        return formatter.print(date.getTime());
    }

}
