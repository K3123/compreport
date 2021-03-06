package com.clics.compliancereport.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;


public class UserCredential
{
    private String ssoid;
    private String email;
    private String name;
    private String groupid;

    public String getSsoid() {
        return ssoid;
    }

    public void setSsoid(String ssoid) {
        this.ssoid = ssoid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAutenticated() {
        return (StringUtils.isNotEmpty(this.ssoid) && StringUtils.isNotEmpty(this.email));
    }

    public boolean isAuthorized() {
       //return (StringUtils.isNotEmpty(this.getGroupid()));
    	return true;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("ssoid", ssoid).
                append("email", email).
                append("name", name).
                append("groupid", groupid).
                toString();
    }

    public static UserCredentialBuilder getBuilder(String ssoid, String email) {
        return new UserCredentialBuilder(ssoid, email);
    }

    public static class UserCredentialBuilder {

        private UserCredential built;

        private UserCredentialBuilder(){}

        public UserCredentialBuilder(String ssoid, String email) {
            built = new UserCredential();
            built.ssoid = ssoid;
            built.email = email;
        }

        public UserCredential build() {
            return built;
        }

        public UserCredentialBuilder name(String name) {
            built.name = name;
            return this;
        }
        public UserCredentialBuilder groupid(String groupid) {
            built.groupid = groupid;
            return this;
        }
     }
}
