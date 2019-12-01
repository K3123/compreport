package com.clics.compliancereport.common;

public final class Constants {
    private Constants() {}

    public static final String RETIRED = "RETIRED";
    public static final String NOTAPPLICABLE = "NA";
    public static final String BLANK = "";

    public static final String MISSING_PARAMETERS = "Required parameter(s) are missing. ";
    public static final String SUBMISSION_ERROR = "A system error occurred and form submission failed!";

    public static final String SUCCESS_KEY = "success";
    public static final String ERROR_KEY = "error";

    public static final String COMMA_DELIMITER = ",";
    public static final String COLON_DELIMITER = ":";

    public static final String CREDENTIAL_KEY = "user_credential";

    //User authorization
    public static final String STANDARD_USER_GROUP = "@CAPITAL GECA_DBH_Security";
    public static final String ADMIN_USER_GROUP = "@Capital comprepadmin";

    //fake user credential
    public static final String FAKE_SSO_ID = "221011789";
    public static final String FAKE_EMAIL = "testuser12349@ge.com";
    public static final String FAKE_FULLNAME = "CompReport Pilot User";
}
