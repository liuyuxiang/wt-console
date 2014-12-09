------------------------------------------------------
-- Export file for user UUM                         --
-- Created by Administrator on 2012-12-12, 18:31:01 --
------------------------------------------------------






create table U2_ATTRIBUTETYPE
(
  UUID         VARCHAR2(32 CHAR) not null,
  ORDERNUM     NUMBER(19) not null,
  NAME         VARCHAR2(500 CHAR) not null,
  CATAGORY     VARCHAR2(500 CHAR) not null,
  ID           VARCHAR2(500 CHAR) not null,
  RESOURCETYPE NUMBER(10) not null,
  MULTIVALUED  NUMBER(1) not null,
  HIDDEN       NUMBER(1) not null,
  PAGETYPE     VARCHAR2(255 CHAR),
  LENGTH       NUMBER(19),
  RULE         VARCHAR2(2 CHAR),
  VALUE        VARCHAR2(100 CHAR)
)
;
alter table U2_ATTRIBUTETYPE
  add primary key (UUID);





create table U2_RESOURCE
(
  UUID         VARCHAR2(32 CHAR) not null,
  TYPE         NUMBER(10) not null,
  STATUS       NUMBER(10) not null,
  CREATEDTIME  TIMESTAMP(6) not null,
  MODIFIEDTIME TIMESTAMP(6) not null
)
;
alter table U2_RESOURCE
  add primary key (UUID);





create table U2_ADMINGROUPATTRIBUTETYPE
(
  ATTRIBUTETYPEUUID VARCHAR2(32 CHAR) not null,
  GROUPUUID         VARCHAR2(32 CHAR) not null
)
;
alter table U2_ADMINGROUPATTRIBUTETYPE
  add primary key (ATTRIBUTETYPEUUID, GROUPUUID);
alter table U2_ADMINGROUPATTRIBUTETYPE
  add constraint FKDA1A984CC2F4BF foreign key (ATTRIBUTETYPEUUID)
  references U2_ATTRIBUTETYPE (UUID);
alter table U2_ADMINGROUPATTRIBUTETYPE
  add constraint FKDA1A984E24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_ADMINGROUPRESOURCE
(
  RESOURCEUUID VARCHAR2(32 CHAR) not null,
  GROUPUUID    VARCHAR2(32 CHAR) not null
)
;
alter table U2_ADMINGROUPRESOURCE
  add primary key (RESOURCEUUID, GROUPUUID);
alter table U2_ADMINGROUPRESOURCE
  add constraint FKB30ED9C0E02D319F foreign key (RESOURCEUUID)
  references U2_RESOURCE (UUID);
alter table U2_ADMINGROUPRESOURCE
  add constraint FKB30ED9C0E24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_TASKLIST
(
  UUID      VARCHAR2(32 CHAR) not null,
  ID        VARCHAR2(100 CHAR),
  LINKORDER NUMBER(10),
  LINKNAME  VARCHAR2(1000 CHAR),
  LINKURL   VARCHAR2(1000 CHAR)
)
;
alter table U2_TASKLIST
  add primary key (UUID);





create table U2_ADMINGROUPTASKLIST
(
  TASKLISTUUID VARCHAR2(32 CHAR) not null,
  GROUPUUID    VARCHAR2(32 CHAR) not null
)
;
alter table U2_ADMINGROUPTASKLIST
  add primary key (TASKLISTUUID, GROUPUUID);
alter table U2_ADMINGROUPTASKLIST
  add constraint FKAEED2575DFE88269 foreign key (TASKLISTUUID)
  references U2_TASKLIST (UUID);
alter table U2_ADMINGROUPTASKLIST
  add constraint FKAEED2575E24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_APPLICATION
(
  UUID              VARCHAR2(32 CHAR) not null,
  ORDERNUM          NUMBER(19) not null,
  NAME              VARCHAR2(500 CHAR),
  APPID             VARCHAR2(500 CHAR),
  APPREMARK         VARCHAR2(500 CHAR),
  APPURL            VARCHAR2(500 CHAR),
  APPSINGLELOGINURL VARCHAR2(500 CHAR),
  APPUSERNAME       VARCHAR2(500 CHAR),
  APPPASSWORD       VARCHAR2(500 CHAR),
  APPDBURL          VARCHAR2(500 CHAR),
  APPDBPORT         VARCHAR2(500 CHAR),
  APPDBNAME         VARCHAR2(500 CHAR),
  APPDBUSERNAME     VARCHAR2(500 CHAR),
  APPDBPASSWORD     VARCHAR2(500 CHAR),
  APPDBMIDTABLENAME VARCHAR2(500 CHAR),
  ACCESSGROUPUUID   VARCHAR2(32 CHAR),
  APPNAME           VARCHAR2(500 CHAR),
  CODE              VARCHAR2(500 CHAR) not null,
  REMARK            VARCHAR2(500 CHAR)
)
;
alter table U2_APPLICATION
  add primary key (UUID);
alter table U2_APPLICATION
  add constraint FK2F35426E112F743 foreign key (UUID)
  references U2_RESOURCE (UUID);
alter table U2_APPLICATION
  add constraint FK2F35426E2CB0AC8D foreign key (ACCESSGROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_APPLICATIONGROUPS
(
  APPLICATIONUUID VARCHAR2(32 CHAR) not null,
  GROUPUUID       VARCHAR2(32 CHAR) not null
)
;
alter table U2_APPLICATIONGROUPS
  add primary key (APPLICATIONUUID, GROUPUUID);
alter table U2_APPLICATIONGROUPS
  add constraint FKC94BFDE2B693D493 foreign key (APPLICATIONUUID)
  references U2_RESOURCE (UUID);
alter table U2_APPLICATIONGROUPS
  add constraint FKC94BFDE2E24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_ATTRIBUTE
(
  UUID              VARCHAR2(32 CHAR) not null,
  OWNERRESOURCEUUID VARCHAR2(32 CHAR) not null,
  TYPEUUID          VARCHAR2(32 CHAR) not null,
  VALUE             VARCHAR2(500)
)
;
comment on column U2_ATTRIBUTE.VALUE
  is '扩展属性值';
alter table U2_ATTRIBUTE
  add primary key (UUID);
alter table U2_ATTRIBUTE
  add constraint FKC221973AA3C3AFD2 foreign key (OWNERRESOURCEUUID)
  references U2_RESOURCE (UUID);
alter table U2_ATTRIBUTE
  add constraint FKC221973AF791A623 foreign key (TYPEUUID)
  references U2_ATTRIBUTETYPE (UUID);





create table U2_ATTRIBUTETYPEGROUPS
(
  ATTRIBUTETYPEUUID VARCHAR2(32 CHAR) not null,
  GROUPUUID         VARCHAR2(32 CHAR) not null
)
;
alter table U2_ATTRIBUTETYPEGROUPS
  add primary key (ATTRIBUTETYPEUUID, GROUPUUID);
alter table U2_ATTRIBUTETYPEGROUPS
  add constraint FKFB8EB488CC2F4BF foreign key (ATTRIBUTETYPEUUID)
  references U2_ATTRIBUTETYPE (UUID);
alter table U2_ATTRIBUTETYPEGROUPS
  add constraint FKFB8EB488E24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_ATTRIBUTEVALUE
(
  UUID          VARCHAR2(32 CHAR) not null,
  TYPE          NUMBER(10) not null,
  ATTRIBUTEUUID VARCHAR2(32 CHAR) not null
)
;
alter table U2_ATTRIBUTEVALUE
  add primary key (UUID);
alter table U2_ATTRIBUTEVALUE
  add constraint FK7E277E57A89C72B foreign key (ATTRIBUTEUUID)
  references U2_ATTRIBUTE (UUID) on delete cascade;





create table U2_AUTHENTICATIONPROFILE
(
  UUID                   VARCHAR2(32 CHAR) not null,
  PROFILENAME            VARCHAR2(100 CHAR) not null,
  PROFILEDESCRIPTION     VARCHAR2(255 CHAR),
  FORMNAME               VARCHAR2(10 CHAR),
  PREACCESSURLSERVERSIDE VARCHAR2(500 CHAR),
  ACTIONURLSERVERSIDE    VARCHAR2(500 CHAR),
  PREACCESSURL           VARCHAR2(500 CHAR),
  ACTIONURL              VARCHAR2(500 CHAR) not null,
  TARGET                 VARCHAR2(10 CHAR),
  SUBMITMETHOD           VARCHAR2(10 CHAR),
  INPUTNAMEUSERID        VARCHAR2(50 CHAR) not null,
  INPUTNAMEPASSWORD      VARCHAR2(50 CHAR) not null,
  CHARSET                VARCHAR2(10 CHAR),
  APPLICATIONUUID        VARCHAR2(32 CHAR) not null,
  ERRORKEYWORD           VARCHAR2(500 CHAR)
)
;
alter table U2_AUTHENTICATIONPROFILE
  add primary key (UUID);
alter table U2_AUTHENTICATIONPROFILE
  add unique (PROFILENAME, APPLICATIONUUID);
alter table U2_AUTHENTICATIONPROFILE
  add constraint FK5B1FF7AFB693D493 foreign key (APPLICATIONUUID)
  references U2_RESOURCE (UUID);





create table U2_BUSYHOURLIST
(
  UUID       VARCHAR2(32 CHAR) not null,
  ORDERNUM   NUMBER(10),
  ACTIVATION NUMBER(1),
  STARTTIME  VARCHAR2(10 CHAR),
  ENDTIME    VARCHAR2(10 CHAR)
)
;
alter table U2_BUSYHOURLIST
  add primary key (UUID);





create table U2_CANDIDATEITEM
(
  UUID              VARCHAR2(32 CHAR) not null,
  VALUE             VARCHAR2(100 CHAR),
  SORTNUMBER        NUMBER(19),
  ISDEFAULT         NUMBER(1),
  ATTRIBUTETYPEUUID VARCHAR2(32 CHAR) not null,
  CAPTION           VARCHAR2(255 CHAR)
)
;
alter table U2_CANDIDATEITEM
  add primary key (UUID);
alter table U2_CANDIDATEITEM
  add constraint FK3C0E78D4CC2F4BF foreign key (ATTRIBUTETYPEUUID)
  references U2_ATTRIBUTETYPE (UUID);





create table U2_COMPARE
(
  UUID        VARCHAR2(32 CHAR) not null,
  TYPE        VARCHAR2(255 CHAR) not null,
  TOTALNUMBER NUMBER(19),
  ERRORNUMBER NUMBER(19),
  MODIFYTIME  TIMESTAMP(6) not null,
  ENDTIME     TIMESTAMP(6),
  OPERATOR    VARCHAR2(255 CHAR),
  HASFINISHED NUMBER(1),
  REMARK      VARCHAR2(500 CHAR)
)
;
alter table U2_COMPARE
  add primary key (UUID);





create table U2_COMPARECONTENT
(
  UUID      VARCHAR2(32 CHAR) not null,
  ID        VARCHAR2(255 CHAR) not null,
  NAME      VARCHAR2(255 CHAR),
  TYPE      VARCHAR2(255 CHAR),
  REMARK    VARCHAR2(255 CHAR),
  OWNERUUID VARCHAR2(32 CHAR)
)
;
alter table U2_COMPARECONTENT
  add primary key (UUID);
alter table U2_COMPARECONTENT
  add constraint FK4FF179F6303D9688 foreign key (OWNERUUID)
  references U2_COMPARE (UUID);





create table U2_COMPAREDETAIL
(
  UUID      VARCHAR2(32 CHAR) not null,
  NAME      VARCHAR2(255 CHAR),
  DBVALUE   VARCHAR2(255 CHAR),
  LDAPVALUE VARCHAR2(255 CHAR),
  OWNERUUID VARCHAR2(32 CHAR)
)
;
alter table U2_COMPAREDETAIL
  add primary key (UUID);
alter table U2_COMPAREDETAIL
  add constraint FK1442AF34AD6F3F8A foreign key (OWNERUUID)
  references U2_COMPARECONTENT (UUID);





create table U2_DEPARTMENT
(
  UUID               VARCHAR2(32 CHAR) not null,
  PARENTUUID         VARCHAR2(32 CHAR),
  ORDERNUM           NUMBER(19) not null,
  NAME               VARCHAR2(120) not null,
  CODE               VARCHAR2(50),
  HASCHILDREN        NUMBER(1) not null,
  PATH               VARCHAR2(500) not null,
  DEPTCODE           VARCHAR2(50),
  ORGCODE            VARCHAR2(50),
  DEPTPARENTCODE     VARCHAR2(50),
  LASTUPDATETIME     TIMESTAMP(6),
  CURRENTAUTHORLEVEL NUMBER(19),
  RTXCODE            NUMBER(19) not null,
  LEVEL_             NUMBER(10) default 0 not null
)
;
comment on column U2_DEPARTMENT.RTXCODE
  is 'RTX编码,必须有值,而且唯一';
comment on column U2_DEPARTMENT.LEVEL_
  is 'department path';
alter table U2_DEPARTMENT
  add primary key (UUID);
alter table U2_DEPARTMENT
  add unique (NAME, PATH, CODE);
alter table U2_DEPARTMENT
  add constraint FK9C71EA74C63B357F foreign key (PARENTUUID)
  references U2_RESOURCE (UUID);
alter table U2_DEPARTMENT
  add constraint FK9C71EA74D4F891D5 foreign key (UUID)
  references U2_RESOURCE (UUID);





create table U2_DEPARTMENTAUTHOR
(
  UUID             VARCHAR2(32 CHAR) not null,
  DEPARTMENTLEVELS VARCHAR2(1 CHAR),
  DEPARTMENTUUID   VARCHAR2(32 CHAR) not null,
  GROUPUUID        VARCHAR2(32 CHAR) not null
)
;
alter table U2_DEPARTMENTAUTHOR
  add primary key (UUID);
alter table U2_DEPARTMENTAUTHOR
  add constraint FKA3BF511FC530DC27 foreign key (DEPARTMENTUUID)
  references U2_RESOURCE (UUID);
alter table U2_DEPARTMENTAUTHOR
  add constraint FKA3BF511FE24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_DEPARTMENTPATH
(
  UUID       VARCHAR2(32 CHAR) not null,
  ELDERUUID  VARCHAR2(32 CHAR) not null,
  JUNIORUUID VARCHAR2(32 CHAR) not null
)
;
alter table U2_DEPARTMENTPATH
  add primary key (UUID);
alter table U2_DEPARTMENTPATH
  add unique (ELDERUUID, JUNIORUUID);
alter table U2_DEPARTMENTPATH
  add constraint FKE3F568B983865EDF foreign key (ELDERUUID)
  references U2_RESOURCE (UUID);
alter table U2_DEPARTMENTPATH
  add constraint FKE3F568B9D7881DFE foreign key (JUNIORUUID)
  references U2_RESOURCE (UUID);





create table U2_DEPARTMENTTEMP
(
  UUID           VARCHAR2(32 CHAR) default sys_guid() not null,
  DEPTID         VARCHAR2(32 CHAR),
  DEPTCODE       VARCHAR2(255 CHAR),
  DEPTNAME       VARCHAR2(255 CHAR) default '新建部门' not null,
  DEPTORDER      VARCHAR2(255 CHAR),
  DEPTORGCODE    VARCHAR2(255 CHAR),
  PARENTDEPTCODE VARCHAR2(255 CHAR),
  ISVIRTUAL      VARCHAR2(255 CHAR),
  MODIFYTYPE     VARCHAR2(255 CHAR),
  LASTUPDATETIME TIMESTAMP(6) default sysdate,
  BEGINDATE      TIMESTAMP(6),
  ENDDATE        TIMESTAMP(6),
  RECORDSTATE    VARCHAR2(255 CHAR),
  DUMMY          VARCHAR2(255 CHAR)
)
;
comment on column U2_DEPARTMENTTEMP.DEPTID
  is '部门ID';
comment on column U2_DEPARTMENTTEMP.DEPTCODE
  is '部门编码';
comment on column U2_DEPARTMENTTEMP.DEPTNAME
  is '部门名称';
comment on column U2_DEPARTMENTTEMP.DEPTORDER
  is '部门排序号';
comment on column U2_DEPARTMENTTEMP.DEPTORGCODE
  is '部门公司编码';
comment on column U2_DEPARTMENTTEMP.PARENTDEPTCODE
  is '上级部门编码';
alter table U2_DEPARTMENTTEMP
  add primary key (UUID);





create table U2_DEPARTMENTTEMPLOG
(
  UUID           VARCHAR2(32 CHAR) not null,
  DEPTID         VARCHAR2(500 CHAR),
  DEPTNAME       VARCHAR2(500 CHAR),
  DEPTORDER      NUMBER(19),
  DEPTCODE       VARCHAR2(4000 CHAR),
  DEPTORGCODE    VARCHAR2(500 CHAR),
  PARENTDEPTCODE VARCHAR2(500 CHAR),
  MODIFYTYPE     VARCHAR2(500 CHAR),
  LASTUPDATETIME TIMESTAMP(6),
  DUMMY          VARCHAR2(255 CHAR),
  MODIFYSTATUS   NUMBER(19) not null,
  ISVIRTUAL      VARCHAR2(255 CHAR),
  BEGINDATE      TIMESTAMP(6),
  ENDDATE        TIMESTAMP(6),
  RECORDSTATE    VARCHAR2(255 CHAR),
  REMARK         VARCHAR2(255 CHAR)
)
;
comment on column U2_DEPARTMENTTEMPLOG.DEPTID
  is '部门ID';
comment on column U2_DEPARTMENTTEMPLOG.DEPTNAME
  is '部门名称';
comment on column U2_DEPARTMENTTEMPLOG.DEPTORDER
  is '部门排序号';
comment on column U2_DEPARTMENTTEMPLOG.DEPTCODE
  is '部门编码';
comment on column U2_DEPARTMENTTEMPLOG.DEPTORGCODE
  is '部门公司编码';
comment on column U2_DEPARTMENTTEMPLOG.PARENTDEPTCODE
  is '上级部门编码';
comment on column U2_DEPARTMENTTEMPLOG.MODIFYSTATUS
  is '变更状态:0未处理;1异常;2成功;3忽略';
comment on column U2_DEPARTMENTTEMPLOG.REMARK
  is '描述';
alter table U2_DEPARTMENTTEMPLOG
  add primary key (UUID);





create table U2_DEPTLOG
(
  UUID        VARCHAR2(32 CHAR) not null,
  LOGID       VARCHAR2(100 CHAR),
  FIELDNAME   VARCHAR2(100 CHAR),
  DEPTUUID    VARCHAR2(100 CHAR),
  BEFOREVALUE VARCHAR2(100 CHAR),
  AFTERVALUE  VARCHAR2(100 CHAR),
  EDITPERSON  VARCHAR2(100 CHAR),
  REMARK      VARCHAR2(100 CHAR),
  EDITDATE    TIMESTAMP(6) not null
)
;
alter table U2_DEPTLOG
  add primary key (UUID);





create table U2_EVENT
(
  UUID              VARCHAR2(32 CHAR) not null,
  TYPE              VARCHAR2(30 CHAR) not null,
  UPDATEDATE        TIMESTAMP(6) not null,
  RESOURCEUUID      VARCHAR2(32 CHAR) not null,
  RESOURCENAME      VARCHAR2(100 CHAR),
  OPERATOR          VARCHAR2(32 CHAR),
  OPERATORNAME      VARCHAR2(100 CHAR),
  OPERATORDEPT      VARCHAR2(200 CHAR),
  OPERUUID          VARCHAR2(32 CHAR),
  DEPENDEVENTUUID   VARCHAR2(32 CHAR),
  SEQ               NUMBER(19),
  OPERATORIPADDERSS VARCHAR2(255),
  RESOURCEID        VARCHAR2(100),
  RESOURCETYPE      VARCHAR2(10),
  STATUS            NUMBER(10)
)
;
comment on column U2_EVENT.OPERATORIPADDERSS
  is '操作者IP地址';
comment on column U2_EVENT.RESOURCETYPE
  is '资源类型';
comment on column U2_EVENT.STATUS
  is '处理状态,0为处理中,1为异常,2为正常结束';
alter table U2_EVENT
  add primary key (UUID);
alter table U2_EVENT
  add constraint FK78DB5E383C0A273B foreign key (DEPENDEVENTUUID)
  references U2_EVENT (UUID);





create table U2_EVENTPARAM
(
  UUID          VARCHAR2(32 CHAR) not null,
  EVENTUUID     VARCHAR2(32 CHAR) not null,
  KEY           VARCHAR2(50 CHAR) not null,
  ORIGINALVALUE VARCHAR2(255 CHAR),
  VALUE         VARCHAR2(255 CHAR)
)
;
alter table U2_EVENTPARAM
  add primary key (UUID);
alter table U2_EVENTPARAM
  add constraint FKA3B3ED353C760427 foreign key (EVENTUUID)
  references U2_EVENT (UUID) on delete cascade;





create table U2_GROUP
(
  UUID         VARCHAR2(32 CHAR) not null,
  CODE         VARCHAR2(500 CHAR),
  ORDERNUM     NUMBER(19) not null,
  NAME         VARCHAR2(500 CHAR) not null,
  HASCHILDREN  NUMBER(1) not null,
  GROUPTYPE    VARCHAR2(255 CHAR),
  APPGROUPTYPE VARCHAR2(255 CHAR)
)
;
alter table U2_GROUP
  add primary key (UUID);
alter table U2_GROUP
  add constraint FK78F5E21D3AD8F272 foreign key (UUID)
  references U2_RESOURCE (UUID);





create table U2_GROUPLOG
(
  UUID        VARCHAR2(32 CHAR) not null,
  LOGID       VARCHAR2(100 CHAR),
  FIELDNAME   VARCHAR2(100 CHAR),
  GROUPUUID   VARCHAR2(100 CHAR),
  BEFOREVALUE VARCHAR2(100 CHAR),
  AFTERVALUE  VARCHAR2(100 CHAR),
  EDITPERSON  VARCHAR2(100 CHAR),
  REMARK      VARCHAR2(100 CHAR),
  EDITDATE    TIMESTAMP(6) not null
)
;
alter table U2_GROUPLOG
  add primary key (UUID);





create table U2_GROUPRELATIONSHIP
(
  CHILDUUID  VARCHAR2(32 CHAR) not null,
  PARENTUUID VARCHAR2(32 CHAR) not null
)
;
alter table U2_GROUPRELATIONSHIP
  add primary key (CHILDUUID, PARENTUUID);
alter table U2_GROUPRELATIONSHIP
  add constraint FK5C3A68F52C1B961C foreign key (PARENTUUID)
  references U2_RESOURCE (UUID);
alter table U2_GROUPRELATIONSHIP
  add constraint FK5C3A68F53504F98E foreign key (CHILDUUID)
  references U2_RESOURCE (UUID);





create table U2_LONGVALUE
(
  UUID  VARCHAR2(32 CHAR) not null,
  VALUE NUMBER(19)
)
;
alter table U2_LONGVALUE
  add primary key (UUID);
alter table U2_LONGVALUE
  add constraint FKC87574F36764A848 foreign key (UUID)
  references U2_ATTRIBUTEVALUE (UUID) on delete cascade;





create table U2_RESOURCELOG
(
  UUID              VARCHAR2(32 CHAR) not null,
  LOGID             VARCHAR2(100 CHAR),
  FIELDNAME         VARCHAR2(100 CHAR),
  BEFOREVALUE       VARCHAR2(1000 CHAR),
  AFTERVALUE        VARCHAR2(1000 CHAR),
  EDITPERSON        VARCHAR2(100 CHAR),
  REMARK            VARCHAR2(1000 CHAR),
  EDITDATE          TIMESTAMP(6) not null,
  RESOURCEUUID      VARCHAR2(100 CHAR) not null,
  OPERATORIPADDERSS VARCHAR2(255 CHAR)
)
;
comment on column U2_RESOURCELOG.OPERATORIPADDERSS
  is '操作者IP地址';
alter table U2_RESOURCELOG
  add primary key (UUID);





create table U2_RESOURCEMAPPING
(
  UUID         VARCHAR2(32 CHAR) not null,
  MAPPINGID    VARCHAR2(255 CHAR) not null,
  RESOURCEUUID VARCHAR2(255 CHAR) not null
)
;
alter table U2_RESOURCEMAPPING
  add primary key (UUID);
alter table U2_RESOURCEMAPPING
  add unique (MAPPINGID, RESOURCEUUID);
alter table U2_RESOURCEMAPPING
  add constraint FKD7165EFEE02D319F foreign key (RESOURCEUUID)
  references U2_RESOURCE (UUID);





create table U2_RESOURCETEMPDETAILS
(
  UUID          VARCHAR2(32 CHAR) not null,
  KEY           VARCHAR2(50 CHAR) not null,
  NAME          VARCHAR2(50 CHAR),
  EVENTUUID     VARCHAR2(50 CHAR) not null,
  ORIGINALVALUE VARCHAR2(255 CHAR),
  VALUE         VARCHAR2(255 CHAR),
  STATUS        NUMBER(1) default 0 not null,
  TYPE          NUMBER(10)
)
;
comment on column U2_RESOURCETEMPDETAILS.STATUS
  is '处理状态位';
alter table U2_RESOURCETEMPDETAILS
  add primary key (UUID);





create table U2_RESOURCE_SYNC
(
  UUID      VARCHAR2(32 CHAR) not null,
  TYPE      NUMBER(10) not null,
  PROPTYPE  VARCHAR2(30 CHAR),
  PROPNAME  VARCHAR2(30 CHAR) not null,
  TRANSFUNC VARCHAR2(30 CHAR)
)
;
alter table U2_RESOURCE_SYNC
  add primary key (UUID);





create table U2_ROLE
(
  UUID      VARCHAR2(32 CHAR) not null,
  NAME      VARCHAR2(100 CHAR),
  OWNERUUID VARCHAR2(32 CHAR) not null
)
;
alter table U2_ROLE
  add primary key (UUID);
alter table U2_ROLE
  add unique (NAME, OWNERUUID);
alter table U2_ROLE
  add constraint FKEB25A918D5DDF308 foreign key (OWNERUUID)
  references U2_RESOURCE (UUID);





create table U2_RTXCODE
(
  DEPTCODE VARCHAR2(50) not null,
  DEPTID   VARCHAR2(50) not null,
  DEPTNAME VARCHAR2(50),
  DEPTPATH VARCHAR2(100),
  RTXCODE  NUMBER(19) not null
)
;





create table U2_SERVERLOG
(
  UUID          VARCHAR2(32 CHAR) not null,
  OS_ARCH       VARCHAR2(255 CHAR),
  OS_NAME       VARCHAR2(255 CHAR),
  OS_VERSION    VARCHAR2(255 CHAR),
  JAVA_HOME     VARCHAR2(500 CHAR),
  JAVA_VERSION  VARCHAR2(255 CHAR),
  USER_NAME     VARCHAR2(255 CHAR),
  USER_DIR      VARCHAR2(500 CHAR),
  USER_HOME     VARCHAR2(500 CHAR),
  FILE_ENCODING VARCHAR2(255 CHAR),
  MAC           VARCHAR2(500 CHAR),
  IP            VARCHAR2(500 CHAR) not null,
  HANDLETIME    TIMESTAMP(6),
  STATUS        VARCHAR2(255 CHAR) default 'UNKNOWN',
  PROJECT_VER   VARCHAR2(255 CHAR) default 'UNKNOWN',
  PROJECT_ID    VARCHAR2(255 CHAR) default 'UNKNOWN'
)
;
comment on column U2_SERVERLOG.OS_ARCH
  is 'os_arch';
comment on column U2_SERVERLOG.OS_NAME
  is 'os_name';
comment on column U2_SERVERLOG.OS_VERSION
  is 'os_version';
comment on column U2_SERVERLOG.JAVA_HOME
  is 'java_home';
comment on column U2_SERVERLOG.JAVA_VERSION
  is 'java_version';
comment on column U2_SERVERLOG.USER_NAME
  is 'user_name';
comment on column U2_SERVERLOG.USER_DIR
  is 'user_dir';
comment on column U2_SERVERLOG.USER_HOME
  is 'user_home';
comment on column U2_SERVERLOG.FILE_ENCODING
  is 'file_encoding';
comment on column U2_SERVERLOG.MAC
  is 'mac';
comment on column U2_SERVERLOG.IP
  is 'ip';
comment on column U2_SERVERLOG.HANDLETIME
  is 'start time';
comment on column U2_SERVERLOG.STATUS
  is 'status';
comment on column U2_SERVERLOG.PROJECT_VER
  is 'project_ver';
comment on column U2_SERVERLOG.PROJECT_ID
  is 'project_id';
alter table U2_SERVERLOG
  add primary key (UUID);





create table U2_STRINGVALUE
(
  UUID  VARCHAR2(32 CHAR) not null,
  VALUE VARCHAR2(500 CHAR)
)
;
alter table U2_STRINGVALUE
  add primary key (UUID);
alter table U2_STRINGVALUE
  add constraint FK7803E0FE49E195D3 foreign key (UUID)
  references U2_ATTRIBUTEVALUE (UUID) on delete cascade;





create table U2_SYNCED
(
  UUID             VARCHAR2(32 CHAR) not null,
  LDAPFACTORY      VARCHAR2(1000 CHAR),
  LDAPUSERNAME     VARCHAR2(1000 CHAR),
  LDAPUSERPASSWORD VARCHAR2(1000 CHAR),
  LDAPURL          VARCHAR2(1000 CHAR),
  BASEDN           VARCHAR2(1000 CHAR),
  USERDN           VARCHAR2(1000 CHAR),
  GROUPDN          VARCHAR2(1000 CHAR),
  DEPTDN           VARCHAR2(1000 CHAR),
  USERKEY          VARCHAR2(1000 CHAR),
  GROUPKEY         VARCHAR2(1000 CHAR),
  DEPTKEY          VARCHAR2(1000 CHAR),
  USEROBJECTCLASS  VARCHAR2(1000 CHAR),
  DEPTOBJECTCLASS  VARCHAR2(1000 CHAR),
  GROUPOBJECTCLASS VARCHAR2(1000 CHAR),
  DBDEPTROOT       VARCHAR2(1000 CHAR)
)
;
alter table U2_SYNCED
  add primary key (UUID);





create table U2_SYNCLOG
(
  UUID       VARCHAR2(32 CHAR) not null,
  EVENTUUID  VARCHAR2(32 CHAR) not null,
  APPID      VARCHAR2(32 CHAR) not null,
  HANDLETIME TIMESTAMP(6) not null,
  STATUS     NUMBER(1) not null
)
;
comment on column U2_SYNCLOG.EVENTUUID
  is 'event uuid';
comment on column U2_SYNCLOG.APPID
  is 'synchronous application id';
comment on column U2_SYNCLOG.HANDLETIME
  is 'handle time';
comment on column U2_SYNCLOG.STATUS
  is 'processing status';
alter table U2_SYNCLOG
  add primary key (UUID);





create table U2_SYNCERRORLOG
(
  UUID         VARCHAR2(32 CHAR) not null,
  ERRORMESSAGE CLOB not null
)
;
comment on column U2_SYNCERRORLOG.ERRORMESSAGE
  is 'error message';
alter table U2_SYNCERRORLOG
  add primary key (UUID);
alter table U2_SYNCERRORLOG
  add constraint FKF992D679A0FA149C foreign key (UUID)
  references U2_SYNCLOG (UUID);





create table U2_TEXTVALUE
(
  UUID  VARCHAR2(32 CHAR) not null,
  VALUE CLOB
)
;
alter table U2_TEXTVALUE
  add primary key (UUID);
alter table U2_TEXTVALUE
  add constraint FK82FA7CE221E9B037 foreign key (UUID)
  references U2_ATTRIBUTEVALUE (UUID);





create table U2_USER
(
  UUID                  VARCHAR2(32 CHAR) not null,
  PRIMARYDEPARTMENTUUID VARCHAR2(32 CHAR) not null,
  PRIMARYUSERUUID       VARCHAR2(32 CHAR),
  ORDERNUM              NUMBER(19) not null,
  CURRENTAUTHORLEVEL    NUMBER(19),
  ID                    VARCHAR2(50) not null,
  NAME                  VARCHAR2(100),
  LASTUPDATETIME        TIMESTAMP(6),
  PASSWORD              VARCHAR2(100)
)
;
alter table U2_USER
  add primary key (UUID);
alter table U2_USER
  add unique (ID);
alter table U2_USER
  add constraint FKEB27146DB7CEF04E foreign key (UUID)
  references U2_RESOURCE (UUID);
alter table U2_USER
  add constraint FKEB27146DEE9CEA9B foreign key (PRIMARYUSERUUID)
  references U2_RESOURCE (UUID);
alter table U2_USER
  add constraint FKEB27146DF7343EA9 foreign key (PRIMARYDEPARTMENTUUID)
  references U2_RESOURCE (UUID);





create table U2_USERAPPLICATION
(
  UUID            VARCHAR2(32 CHAR) not null,
  TYPE            VARCHAR2(2 CHAR) not null,
  MAPPENDUSERID   VARCHAR2(50 CHAR),
  MAPPENDPASSWORD VARCHAR2(1000 CHAR),
  USERUUID        VARCHAR2(32 CHAR) not null,
  APPLICATIONUUID VARCHAR2(32 CHAR) not null,
  LOGINABLE       NUMBER(1),
  REMARK          VARCHAR2(1000 CHAR),
  LASTMODIFYTIME  TIMESTAMP(6) not null
)
;
alter table U2_USERAPPLICATION
  add primary key (UUID);
ALTER TABLE U2_USERAPPLICATION
  DROP PRIMARY KEY CASCADE;
alter table U2_USERAPPLICATION
  add primary key (USERUUID, APPLICATIONUUID);
alter table U2_USERAPPLICATION
  add constraint FK4E1582A3A7C4FE99 foreign key (USERUUID)
  references U2_RESOURCE (UUID);
alter table U2_USERAPPLICATION
  add constraint FK4E1582A3B693D493 foreign key (APPLICATIONUUID)
  references U2_RESOURCE (UUID);



create table U2_USERAUTHOR
(
  UUID       VARCHAR2(32 CHAR) not null,
  USERLEVELS VARCHAR2(1 CHAR),
  USERUUID   VARCHAR2(32 CHAR) not null,
  GROUPUUID  VARCHAR2(32 CHAR) not null
)
;
alter table U2_USERAUTHOR
  add primary key (UUID);
alter table U2_USERAUTHOR
  add constraint FKAA215C58A7C4FE99 foreign key (USERUUID)
  references U2_RESOURCE (UUID);
alter table U2_USERAUTHOR
  add constraint FKAA215C58E24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_USERDEPARTMENTS
(
  USERUUID       VARCHAR2(32 CHAR) not null,
  DEPARTMENTUUID VARCHAR2(32 CHAR) not null
)
;
alter table U2_USERDEPARTMENTS
  add primary key (USERUUID, DEPARTMENTUUID);
alter table U2_USERDEPARTMENTS
  add constraint FK10ABA4B4A7C4FE99 foreign key (USERUUID)
  references U2_RESOURCE (UUID);
alter table U2_USERDEPARTMENTS
  add constraint FK10ABA4B4C530DC27 foreign key (DEPARTMENTUUID)
  references U2_RESOURCE (UUID);





create table U2_USERGROUPS
(
  USERUUID  VARCHAR2(32 CHAR) not null,
  GROUPUUID VARCHAR2(32 CHAR) not null
)
;
alter table U2_USERGROUPS
  add primary key (USERUUID, GROUPUUID);
alter table U2_USERGROUPS
  add constraint FKB43214A1A7C4FE99 foreign key (USERUUID)
  references U2_RESOURCE (UUID);
alter table U2_USERGROUPS
  add constraint FKB43214A1E24C9171 foreign key (GROUPUUID)
  references U2_RESOURCE (UUID);





create table U2_USERIDMAPPING
(
  UUID            VARCHAR2(32 CHAR) not null,
  USERUUID        VARCHAR2(32 CHAR) not null,
  APPLICATIONUUID VARCHAR2(32 CHAR) not null,
  APPUSERID       VARCHAR2(500 CHAR) not null
)
;
alter table U2_USERIDMAPPING
  add primary key (UUID);
alter table U2_USERIDMAPPING
  add constraint FK6A005566A7C4FE99 foreign key (USERUUID)
  references U2_RESOURCE (UUID);
alter table U2_USERIDMAPPING
  add constraint FK6A005566B693D493 foreign key (APPLICATIONUUID)
  references U2_RESOURCE (UUID);





create table U2_USERLOG
(
  UUID        VARCHAR2(32 CHAR) not null,
  LOGID       VARCHAR2(100 CHAR),
  PROPUUID    VARCHAR2(100 CHAR),
  USERUUID    VARCHAR2(100 CHAR),
  BEFOREVALUE VARCHAR2(100 CHAR),
  AFTERVALUE  VARCHAR2(100 CHAR),
  EDITPERSON  VARCHAR2(100 CHAR),
  REMARK      VARCHAR2(100 CHAR),
  EDITDATE    TIMESTAMP(6) not null
)
;
alter table U2_USERLOG
  add primary key (UUID);





create table U2_USERTEMP
(
  UUID             VARCHAR2(32 CHAR) default sys_guid() not null,
  USERID           VARCHAR2(32 CHAR) not null,
  USERCODE         VARCHAR2(255 CHAR),
  USERNAME         VARCHAR2(255 CHAR),
  DEPTCODE         VARCHAR2(255 CHAR),
  DEPTNAME         VARCHAR2(255 CHAR),
  IDCARD           VARCHAR2(255 CHAR),
  USERORDER        VARCHAR2(255 CHAR),
  USERNAMEUSED     VARCHAR2(255 CHAR),
  GENDER           VARCHAR2(255 CHAR),
  MOBIEPHONE       VARCHAR2(255 CHAR),
  WORKPLACE        VARCHAR2(255 CHAR),
  JOBSTATUS        VARCHAR2(255 CHAR),
  JOBPOSITION      VARCHAR2(255 CHAR),
  TECHNAME         VARCHAR2(255 CHAR),
  NATIONALITY      VARCHAR2(255 CHAR),
  MARRIGED         VARCHAR2(255 CHAR),
  HOMEPHONE        VARCHAR2(255 CHAR),
  FAX              VARCHAR2(255 CHAR),
  EMAIL            VARCHAR2(255 CHAR),
  OFFICEPHONE      VARCHAR2(255 CHAR),
  USERSTATUS       VARCHAR2(255 CHAR),
  DUTYCODE         VARCHAR2(255 CHAR),
  DUTYNAME         VARCHAR2(255 CHAR),
  OFFICE           VARCHAR2(255 CHAR),
  JOINELECTRICDATE TIMESTAMP(6),
  WORKDATE         TIMESTAMP(6),
  BIRTHDATE        TIMESTAMP(6),
  BIRTHPLACE       VARCHAR2(255 CHAR),
  LIVEPLACE        VARCHAR2(255 CHAR),
  SUPPORTNAME      VARCHAR2(255 CHAR),
  DEPTORGCODE      VARCHAR2(255 CHAR),
  PASSWORD         VARCHAR2(255 CHAR),
  RETIREDATE       VARCHAR2(255 CHAR),
  RECORDSTATE      VARCHAR2(255 CHAR),
  REDUCINGCODE     VARCHAR2(255 CHAR),
  MODIFYTYPE       VARCHAR2(255 CHAR) not null,
  LASTUPDATETIME   TIMESTAMP(6) default sysdate,
  DATAFROM         VARCHAR2(255 CHAR)
)
;
comment on column U2_USERTEMP.USERID
  is '用户登录ID';
comment on column U2_USERTEMP.USERCODE
  is '用户编码';
comment on column U2_USERTEMP.USERNAME
  is '用户姓名';
comment on column U2_USERTEMP.DEPTCODE
  is '部门编码';
comment on column U2_USERTEMP.RETIREDATE
  is '退休，离职时间(yyyy-mm-dd)';
comment on column U2_USERTEMP.RECORDSTATE
  is '记录状态';
comment on column U2_USERTEMP.REDUCINGCODE
  is '减员标识';
comment on column U2_USERTEMP.MODIFYTYPE
  is '修改类型:insert,update,delete';
alter table U2_USERTEMP
  add primary key (UUID);





create table U2_USERTEMPLOG
(
  UUID             VARCHAR2(32 CHAR) not null,
  USERID           VARCHAR2(255 CHAR),
  USERNAME         VARCHAR2(255 CHAR),
  IDCARD           VARCHAR2(255 CHAR),
  USERORDER        NUMBER(19),
  USERCODE         VARCHAR2(500 CHAR),
  USERNAMEUSED     VARCHAR2(500 CHAR),
  GENDER           VARCHAR2(500 CHAR),
  BIRTHDATE        TIMESTAMP(6),
  BIRTHPLACE       VARCHAR2(255 CHAR),
  LIVEPLACE        VARCHAR2(500 CHAR),
  WORKDATE         TIMESTAMP(6),
  MOBIEPHONE       VARCHAR2(255 CHAR),
  WORKPLACE        VARCHAR2(255 CHAR),
  JOBSTATUS        VARCHAR2(255 CHAR),
  JOBPOSITION      VARCHAR2(500 CHAR),
  TECHNAME         VARCHAR2(500 CHAR),
  NATIONALITY      VARCHAR2(255 CHAR),
  MARRIGED         VARCHAR2(255 CHAR),
  HOMEPHONE        VARCHAR2(500 CHAR),
  FAX              VARCHAR2(255 CHAR),
  EMAIL            VARCHAR2(255 CHAR),
  OFFICEPHONE      VARCHAR2(500 CHAR),
  DEPTCODE         VARCHAR2(255 CHAR),
  DEPTNAME         VARCHAR2(255 CHAR),
  USERSTATUS       VARCHAR2(255 CHAR),
  MODIFYTYPE       VARCHAR2(255 CHAR),
  LASTUPDATETIME   TIMESTAMP(6),
  DUTYCODE         VARCHAR2(255 CHAR),
  DUTYNAME         VARCHAR2(255 CHAR),
  OFFICE           VARCHAR2(500 CHAR),
  JOINELECTRICDATE TIMESTAMP(6),
  SUPPORTNAME      VARCHAR2(255 CHAR),
  DEPTORGCODE      VARCHAR2(255 CHAR),
  PASSWORD         VARCHAR2(255 CHAR),
  MODIFYSTATUS     NUMBER(19) not null,
  RETIREDATE       VARCHAR2(255 CHAR),
  RECORDSTATE      VARCHAR2(255 CHAR),
  REDUCINGCODE     VARCHAR2(255 CHAR),
  REMARK           VARCHAR2(255 CHAR),
  DATAFROM         VARCHAR2(255)
)
;
comment on column U2_USERTEMPLOG.USERID
  is '用户登录ID';
comment on column U2_USERTEMPLOG.USERNAME
  is '用户姓名';
comment on column U2_USERTEMPLOG.USERCODE
  is '用户编码';
comment on column U2_USERTEMPLOG.DEPTCODE
  is '部门编码';
comment on column U2_USERTEMPLOG.MODIFYTYPE
  is '修改类型:insert,update,delete';
comment on column U2_USERTEMPLOG.MODIFYSTATUS
  is '变更状态:0未处理;1异常;2成功;3忽略';
comment on column U2_USERTEMPLOG.RETIREDATE
  is '退休，离职时间(yyyy-mm-dd)';
comment on column U2_USERTEMPLOG.RECORDSTATE
  is '记录状态';
comment on column U2_USERTEMPLOG.REDUCINGCODE
  is '减员标识';
comment on column U2_USERTEMPLOG.REMARK
  is '描述';
alter table U2_USERTEMPLOG
  add primary key (UUID);


create table U2_USERDEPARTMENTTEMP
(
  uuid     VARCHAR2(50) default sys_guid() not null,
  userid   VARCHAR2(50) not null,
  orgcode  VARCHAR2(50),
  orgname  VARCHAR2(50),
  deptcode VARCHAR2(50) not null,
  deptname VARCHAR2(50),
  dutycode VARCHAR2(50),
  dutyname VARCHAR2(50),
  postflag VARCHAR2(1)
)
;

-- Add comments to the columns 
comment on column U2_USERDEPARTMENTTEMP.uuid
  is '主键';
comment on column U2_USERDEPARTMENTTEMP.userid
  is '用户编码';
comment on column U2_USERDEPARTMENTTEMP.orgcode
  is '公司编码';
comment on column U2_USERDEPARTMENTTEMP.orgname
  is '公司名称';
comment on column U2_USERDEPARTMENTTEMP.deptcode
  is '部门编码';
comment on column U2_USERDEPARTMENTTEMP.deptname
  is '部门名称';
comment on column U2_USERDEPARTMENTTEMP.dutycode
  is '职务编码';
comment on column U2_USERDEPARTMENTTEMP.dutyname
  is '职务名称';
comment on column U2_USERDEPARTMENTTEMP.postflag
  is '是否兼职';


create table U2_DUTY
(
  uuid   VARCHAR2(32 CHAR) not null,
  name   VARCHAR2(255 CHAR) not null,
  id     VARCHAR2(50 CHAR),
  level_ NUMBER(10) default 0 not null
);

comment on column U2_DUTY.level_ is 'duty levle';
  
alter table U2_DUTY add primary key (UUID);
alter table U2_DUTY
  add constraint FKEB1F6378B7C73F59 foreign key (UUID)
  references U2_RESOURCE (UUID);
  
create table U2_USERDUTYS
(
  useruuid VARCHAR2(32 CHAR) not null,
  dutyuuid VARCHAR2(32 CHAR) not null
);

alter table U2_USERDUTYS
  add primary key (USERUUID, DUTYUUID);
  
alter table U2_USERDUTYS
  add constraint FKD41AD7F045911F2F foreign key (DUTYUUID)
  references U2_RESOURCE (UUID);
alter table U2_USERDUTYS
  add constraint FKD41AD7F0A7C4FE99 foreign key (USERUUID)
  references U2_RESOURCE (UUID);
  
  
create sequence U2_EVENT_SEQUENCE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;


