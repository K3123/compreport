package com.clics.compliancereport.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "DBH.DBINVENTORY")
public class DBInfo implements Serializable {

    private Long id;
    private String name;
    private String usage;
    private String location;
    private String version;
    private String desc;
    private String serverName;
    private String virtualSqlInstance;
    private String port;
    private String status;

    public DBInfo(){
    }

    @Id
    @Column(name="DBID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name="DATABASE_NAME", nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="DB_USAGE", nullable = true)
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Column(name="LOCATION", nullable = true)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name="VERSION", nullable = true)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name="DESCRIPTION", nullable = true)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Column(name="SERVER_NAME", nullable = true)
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Column(name="VIRTUAL_SQL_INSTANCE", nullable = true)
    public String getVirtualSqlInstance() {
        return virtualSqlInstance;
    }

    public void setVirtualSqlInstance(String virtualSqlInstance) {
        this.virtualSqlInstance = virtualSqlInstance;
    }

    @Column(name="PORT", nullable = true)
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Column(name="DB_STATUS", nullable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static DBInfoBuilder getBuilder(String name, String usage, String virtualSqlInstance) {
        return new DBInfoBuilder(name, usage, virtualSqlInstance);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DBInfo");
        sb.append("{Id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", usage='").append(usage).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", serverName='").append(serverName).append('\'');
        sb.append(", virtualSqlInstance='").append(virtualSqlInstance).append('\'');
        sb.append(", port='").append(port).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class DBInfoBuilder {

        private DBInfo built;

        public DBInfoBuilder(){}

        public DBInfoBuilder(String name, String usage, String virtualSqlInstance) {
            built = new DBInfo();
            built.name = name;
            built.usage = usage;
            built.virtualSqlInstance = virtualSqlInstance;
        }

        public DBInfo build() {
            return built;
        }

        public DBInfoBuilder id(Long id) {
            built.id = id;
            return this;
        }
        public DBInfoBuilder location(String location) {
            built.location = location;
            return this;
        }
        public DBInfoBuilder status(String status) {
            built.status = status;
            return this;
        }
        public DBInfoBuilder serverName(String serverName) {
            built.serverName = serverName;
            return this;
        }
        public DBInfoBuilder port(String port) {
            built.port = port;
            return this;
        }
    }


}
