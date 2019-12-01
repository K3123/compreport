drop table DBINVENTORY if exists;

drop sequence dbinventory_seq if exists;

create sequence dbinventory_seq;

create table dbinventory (
  dbid bigint not null unique,
  description varchar(255),
  location varchar(255),
  database_name varchar(255),
  port varchar(255),
  server_name varchar(255),
  db_status varchar(255),
  db_usage varchar(255),
  version varchar(255),
  virtual_sql_instance varchar(255),
  primary key (dbid)
);
