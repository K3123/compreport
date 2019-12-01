drop sequence CMRPTRQT_SEQ if exists;

create sequence CMRPTRQT_SEQ;

drop table compliance_rpt_rqt if exists;

create table compliance_rpt_rqt (
    com_rptrqt_id bigint not null unique,
    com_report_type varchar(255),
    com_database_name varchar(255) not null,
    emailaddress varchar(255),
    com_inserted_by varchar(255),
    report_date timestamp,
    report_type varchar(255),
    req_status varchar(255),
    requestorsso varchar(255),
    com_updated_by varchar(255),
    db_usage varchar(255) not null,
    virtual_sql_instance varchar(255) not null,
    primary key (com_rptrqt_id)
);





