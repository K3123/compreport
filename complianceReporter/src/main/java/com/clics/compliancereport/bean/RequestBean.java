package com.clics.compliancereport.bean;

import com.clics.compliancereport.domain.CompRequest;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.List;

@JsonRootName(value = "RequestBean")
public class RequestBean {
    @JsonProperty(value="requests")
    private List<CompRequest> requests;

    public RequestBean() {}

    public RequestBean(List<CompRequest> requests) {
        this.requests = requests;
    }

    public List<CompRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<CompRequest> requests) {
        this.requests = requests;
    }
}
