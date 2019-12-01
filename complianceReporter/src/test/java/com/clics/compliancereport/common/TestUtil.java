package com.clics.compliancereport.common;


import com.clics.compliancereport.bean.FormBean;
import com.clics.compliancereport.bean.LabelValueBean;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBInfo;
import com.clics.compliancereport.domain.DBProcessType;
import com.clics.compliancereport.domain.UserCredential;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import java.util.ArrayList;
import java.util.List;

public final class TestUtil {
    private TestUtil() {
    }

    public static DatatablesCriterias getSampleDatatablesCriterias() {
        ColumnDef filteredColumDef = new ColumnDef();
        filteredColumDef.setFiltered(true);
        filteredColumDef.setFilterable(true);
        filteredColumDef.setName("databaseName");
        List<ColumnDef> filteredColumDefs = new ArrayList<ColumnDef>();
        filteredColumDefs.add(filteredColumDef);

        ColumnDef sortedColumDef = new ColumnDef();
        sortedColumDef.setSortable(true);
        sortedColumDef.setName("databaseName");
        sortedColumDef.setSortDirection(ColumnDef.SortDirection.ASC);
        List<ColumnDef> sortedColumDefs = new ArrayList<ColumnDef>();
        sortedColumDefs.add(sortedColumDef);
        return new DatatablesCriterias("ACB", 0, 10, filteredColumDefs, sortedColumDefs,  1);

    }


    public static DatatablesCriterias getSampleDatatablesCriterias2() {
        ColumnDef filteredColumDef = new ColumnDef();
        filteredColumDef.setFiltered(true);
        filteredColumDef.setFilterable(true);
        filteredColumDef.setName("databaseName");
        ColumnDef filteredColumDef2 = new ColumnDef();
        filteredColumDef2.setFiltered(true);
        filteredColumDef2.setFilterable(true);
        filteredColumDef2.setName("databaseName");
        List<ColumnDef> filteredColumDefs = new ArrayList<ColumnDef>();
        filteredColumDefs.add(filteredColumDef);
        filteredColumDefs.add(filteredColumDef2);

        ColumnDef sortedColumDef = new ColumnDef();
        sortedColumDef.setSortable(true);
        sortedColumDef.setName("databaseName");
        sortedColumDef.setSortDirection(ColumnDef.SortDirection.ASC);
        ColumnDef sortedColumDef2 = new ColumnDef();
        sortedColumDef2.setSortable(true);
        sortedColumDef2.setName("requestStatus");
        sortedColumDef2.setSortDirection(ColumnDef.SortDirection.ASC);
        List<ColumnDef> sortedColumDefs = new ArrayList<ColumnDef>();
        sortedColumDefs.add(sortedColumDef);
        sortedColumDefs.add(sortedColumDef2);

        return new DatatablesCriterias("ACB", 0, 10, filteredColumDefs, sortedColumDefs,  1);

    }


    public static List<DBInfo> getTestDBInfo(){
        DBInfo builder = DBInfo.getBuilder(TestConstants.DB_TEST_NAME, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI).build();
        builder.setId(TestConstants.DB_TEST_ID);
        List<DBInfo> dbInfos = new ArrayList<DBInfo>();
        dbInfos.add(builder);
        return dbInfos;
    }


    public static List<DBInfo> getTestDBInfoList(){
        DBInfo builder1 = DBInfo.getBuilder(TestConstants.DB_TEST_NAME, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI).build();
        DBInfo builder2 = DBInfo.getBuilder(TestConstants.DB_TEST_NAME2, TestConstants.DB_TEST_USAGE2, TestConstants.DB_TEST_VSI).build();
        DBInfo builder3 = DBInfo.getBuilder(TestConstants.DB_TEST_NAME3, TestConstants.DB_TEST_USAGE3, TestConstants.DB_TEST_VSI).build();
        List<DBInfo> dbInfos = new ArrayList<DBInfo>();
        dbInfos.add(builder1);
        dbInfos.add(builder2);
        dbInfos.add(builder3);
        return dbInfos;
    }

    public static List<DBInfo> getTestDBInfoListLike(){
        DBInfo builder = DBInfo.getBuilder(TestConstants.DB_TEST_NAME, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI).build();
        List<DBInfo> dbInfos = new ArrayList<DBInfo>();
        dbInfos.add(builder);
        return dbInfos;
    }


    public static List<CompRequest> getTestRequests() {

        List<CompRequest> compRequests;


        CompRequest compRequest1 =  CompRequest.getBuilder(DBProcessType.SINGLE)
                                               .ssoId(TestConstants.TEST_SSO)
                                               .emailAddress(TestConstants.TEST_EMAIL)
                                               .databaseName(TestConstants.DB_TEST_NAME1)
                                               .usage(TestConstants.DB_TEST_USAGE1)
                                               .virtualSqlInstance(TestConstants.DB_TEST_VSI)
                                               .build();


        CompRequest compRequest2 =  CompRequest.getBuilder(DBProcessType.SINGLE)
                                               .ssoId(TestConstants.TEST_SSO)
                                               .emailAddress(TestConstants.TEST_EMAIL)
                                               .databaseName(TestConstants.DB_TEST_NAME2)
                                               .usage(TestConstants.DB_TEST_USAGE2)
                                               .virtualSqlInstance(TestConstants.DB_TEST_VSI)
                                               .build();


        CompRequest compRequest3 =  CompRequest.getBuilder(DBProcessType.SINGLE)
                                               .ssoId(TestConstants.TEST_SSO)
                                               .emailAddress(TestConstants.TEST_EMAIL)
                                               .databaseName(TestConstants.DB_TEST_NAME3)
                                               .usage(TestConstants.DB_TEST_USAGE3)
                                               .virtualSqlInstance(TestConstants.DB_TEST_VSI)
                                               .build();

        compRequests = new ArrayList<CompRequest>();
        compRequests.add(compRequest1);
        compRequests.add(compRequest2);
        compRequests.add(compRequest3);
        return compRequests;
    }

    public static CompRequest testCompRequestData(DBProcessType dbProcessType) {
        CompRequest.CompRequestBuilder builder = CompRequest.getBuilder(dbProcessType)
                                    .comReportType(CompRequest.DUS_REPORT)
                                    .ssoId("221011704")
                                    .emailAddress("paul.garvey@ge.com")
                                    .dbProcessType(dbProcessType);

        if(dbProcessType == DBProcessType.SINGLE){
            builder.databaseName("219:ACBSCONV").bAllDatabases(false);
        }else if(dbProcessType == DBProcessType.MULTIPLE){
            builder.databaseName("219:ACBSCONV, 3:LB04DB15").bAllDatabases(false);
        }else if(dbProcessType == DBProcessType.ALL){
            builder.databaseName(Constants.BLANK).bAllDatabases(true);
        }
        return builder.build();
    }

    public static FormBean createFormBean() {
        FormBean form = new FormBean();
        form.setUsage(TestConstants.DB_TEST_USAGE1);
        form.setName(TestConstants.DB_TEST_NAME1);
        form.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);
        form.setSsoId(TestConstants.TEST_SSO);
        form.setReportType(CompRequest.DUS_REPORT);
        form.setEmail(TestConstants.TEST_EMAIL);
        form.setbAllDatabases(false);
        return form;
    }

    public static FormBean createFormBeanNone() {
        FormBean form = new FormBean();
        form.setUsage(TestConstants.DB_TEST_USAGE1);
        form.setName(null);
        form.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);
        form.setSsoId(TestConstants.TEST_SSO);
        form.setReportType(CompRequest.DUS_REPORT);
        form.setEmail(TestConstants.TEST_EMAIL);
        form.setbAllDatabases(false);
        return form;
    }

    public static FormBean createFormBeanMultiple() {
        FormBean form = new FormBean();
        form.setUsage(TestConstants.DB_TEST_USAGE1);
        form.setName(TestConstants.MULTIPLE_DB_NAMES);
        form.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);
        form.setSsoId(TestConstants.TEST_SSO);
        form.setReportType(CompRequest.DUS_REPORT);
        form.setEmail(TestConstants.TEST_EMAIL);
        form.setbAllDatabases(false);
        return form;
    }


    public static FormBean createFormBeanAllDB() {
        FormBean form = new FormBean();
        form.setUsage(null);
        form.setName(null);
        form.setVirtualSqlInstance(null);
        form.setSsoId(TestConstants.TEST_SSO);
        form.setReportType(CompRequest.DUS_REPORT);
        form.setEmail(TestConstants.TEST_EMAIL);
        form.setbAllDatabases(true);
        return form;
    }




    public static FormBean testFormBeanWithManditoryFields() {
        FormBean form = new FormBean();
        form.setUsage(null);
        form.setName(TestConstants.DB_TEST_NAME);
        form.setVirtualSqlInstance(null);
        form.setSsoId(TestConstants.TEST_SSO);
        form.setReportType(CompRequest.DUS_REPORT);
        form.setEmail(TestConstants.TEST_EMAIL);
        form.setbAllDatabases(false);
        return form;
    }

    public static FormBean testFormBeanWithMalformEmail() {
        FormBean form = new FormBean();
        form.setUsage(null);
        form.setName(null);
        form.setVirtualSqlInstance(null);
        form.setSsoId(TestConstants.TEST_SSO);
        form.setReportType(CompRequest.DUS_REPORT);
        form.setEmail(TestConstants.TEST_MALFORM_EMAIL);
        form.setbAllDatabases(false);
        return form;
    }

    public static FormBean testFormBeanWithBadSSOId() {
        FormBean form = new FormBean();
        form.setUsage(null);
        form.setName(TestConstants.DB_TEST_NAME);
        form.setVirtualSqlInstance(null);
        form.setSsoId(TestConstants.TEST_BAD_SSO);
        form.setReportType(CompRequest.DUS_REPORT);
        form.setEmail(TestConstants.TEST_EMAIL);
        form.setbAllDatabases(false);
        return form;
    }

    public static FormBean testFormBeanWithAllDBFalseAndNameMissing() {
        FormBean form = new FormBean();
        form.setUsage(null);
        form.setName(TestConstants.DB_TEST_NAME);
        form.setVirtualSqlInstance(null);
        form.setSsoId(TestConstants.TEST_SSO);
        form.setReportType(null);
        form.setEmail(TestConstants.TEST_MALFORM_EMAIL);
        form.setbAllDatabases(false);
        return form;
    }

    public static UserCredential createUserCredential() {
        return UserCredential.getBuilder(TestConstants.TEST_SSO, TestConstants.TEST_EMAIL)
                                                  .name("CompReport Pilot User")
                                                  .groupid(Constants.ADMIN_USER_GROUP)
                                                  .build();

    }


    public static List<LabelValueBean> getLabelValueBeans() {
        LabelValueBean valueBean1 = new LabelValueBean("label1", "value1");
        LabelValueBean valueBean2 = new LabelValueBean("label2", "value2");
        LabelValueBean valueBean3 = new LabelValueBean("label3", "value3");
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(valueBean1);
        list.add(valueBean2);
        list.add(valueBean3);
        return list;
    }

}
