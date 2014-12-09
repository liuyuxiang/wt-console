---------------------------------------------
-- Export file for user TESTSX             --
-- Created by liuyx on 2014/8/20, 18:24:25 --
---------------------------------------------

spool hea_create_table.log





create table BUSINESS_SYSTEM_CODE
(
  id         VARCHAR2(32 CHAR) not null,
  haschild   VARCHAR2(200),
  indexlevel VARCHAR2(200),
  level_code VARCHAR2(4000 CHAR),
  param1     VARCHAR2(255 CHAR),
  param2     VARCHAR2(255 CHAR),
  param3     VARCHAR2(255 CHAR),
  param4     VARCHAR2(255 CHAR),
  param5     VARCHAR2(255 CHAR),
  parent_id  VARCHAR2(32 CHAR),
  reg_code   VARCHAR2(50 CHAR) not null,
  reg_desc   VARCHAR2(500 CHAR),
  reg_label  VARCHAR2(200 CHAR),
  reg_name   VARCHAR2(200 CHAR) not null,
  reg_order  NUMBER(10) not null,
  reg_prop   VARCHAR2(50 CHAR),
  reg_type   VARCHAR2(50 CHAR) not null,
  reg_value  VARCHAR2(1000 CHAR)
)
;
alter table BUSINESS_SYSTEM_CODE
  add primary key (ID);





create table HEA_ACCESSLOG
(
  logid       VARCHAR2(32) not null,
  user_uuid   VARCHAR2(50),
  user_name   VARCHAR2(50),
  index_uuid  VARCHAR2(50),
  index_name  VARCHAR2(50),
  access_ip   VARCHAR2(50),
  access_time VARCHAR2(20) default sysdate,
  remark      VARCHAR2(1000),
  access_type VARCHAR2(2),
  app_id      VARCHAR2(100),
  app_name    VARCHAR2(200)
)
;





create table HEA_APPS
(
  app_id      VARCHAR2(32) not null,
  app_no      VARCHAR2(32),
  app_name    VARCHAR2(256),
  app_desc    VARCHAR2(256),
  app_type    VARCHAR2(16),
  app_addr    VARCHAR2(256),
  app_catalog VARCHAR2(1000),
  db_info     VARCHAR2(256),
  db_user     VARCHAR2(32),
  db_password VARCHAR2(32),
  app_status  VARCHAR2(16),
  run_status  VARCHAR2(16)
)
;
comment on table HEA_APPS
  is '?????';
alter table HEA_APPS
  add constraint PK_HEA_APPS primary key (APP_ID);





create table HEA_SYSTEMLOG
(
  logtime   VARCHAR2(100),
  num       NUMBER(38),
  priority  VARCHAR2(5),
  classname VARCHAR2(500),
  line      NUMBER(38),
  msg       VARCHAR2(4000),
  throw     VARCHAR2(4000)
)
;





create table HEA_SYSTEM_CODE
(
  id         VARCHAR2(255) not null,
  reg_code   VARCHAR2(255),
  reg_type   VARCHAR2(255),
  reg_name   VARCHAR2(200) not null,
  reg_desc   VARCHAR2(500),
  reg_prop   VARCHAR2(50),
  reg_order  NUMBER(10),
  reg_value  VARCHAR2(1000),
  parent_id  VARCHAR2(32),
  indexlevel NUMBER(10),
  haschild   NUMBER(10),
  level_code VARCHAR2(255),
  reg_label  VARCHAR2(255),
  app_id     VARCHAR2(32)
)
;
alter table HEA_SYSTEM_CODE
  add primary key (ID);
alter table HEA_SYSTEM_CODE
  add constraint FKB73CBD229DFCF1C3 foreign key (PARENT_ID)
  references HEA_SYSTEM_CODE (ID);





create table LFS_IG
(
  groupuuid VARCHAR2(32 CHAR) not null,
  indexuuid VARCHAR2(32 CHAR) not null
)
;
alter table LFS_IG
  add primary key (INDEXUUID, GROUPUUID);





create table LFS_INDEX
(
  indexuuid           VARCHAR2(32 CHAR) not null,
  indexname           VARCHAR2(100 CHAR),
  indexorder          NUMBER,
  indexurl            VARCHAR2(2000 CHAR),
  indexmappedurl      VARCHAR2(2000 CHAR),
  parentindexuuid     VARCHAR2(32 CHAR),
  target              VARCHAR2(50 CHAR),
  way                 VARCHAR2(255 CHAR),
  indextype           NUMBER(10),
  indexicon           VARCHAR2(255 CHAR),
  indexlevel          NUMBER(19),
  description         VARCHAR2(255 CHAR),
  haschild            NUMBER,
  createtime          DATE,
  indexicon_disk_path VARCHAR2(500),
  level_code          VARCHAR2(2000),
  app_id              VARCHAR2(32),
  wicket_class        VARCHAR2(500),
  index_icon_path     VARCHAR2(500),
  index_icon_no       VARCHAR2(500),
  style_class         VARCHAR2(500),
  style_class_on      VARCHAR2(500),
  index_icon_path_on  VARCHAR2(500),
  userid              VARCHAR2(100),
  nodeid              VARCHAR2(100),
  viewnodeid          VARCHAR2(200),
  ordernum            NUMBER(10),
  show_t              NUMBER(10)
)
;





create table LFS_INDEX_PERSONALIZATION
(
  personalizationuuid VARCHAR2(32) not null,
  userid              VARCHAR2(100),
  nodeid              VARCHAR2(100),
  viewnodeid          VARCHAR2(200),
  ordernum            NUMBER(10),
  show_t              NUMBER(10)
)
;
alter table LFS_INDEX_PERSONALIZATION
  add primary key (PERSONALIZATIONUUID);





create table LFS_INDIVIDUATION
(
  uuid      VARCHAR2(32) not null,
  portletid VARCHAR2(32),
  indexuuid VARCHAR2(32),
  userid    VARCHAR2(32),
  item      VARCHAR2(4000)
)
;
alter table LFS_INDIVIDUATION
  add constraint PRIMARY_KEY primary key (UUID);





create table LFS_PERSONALIZATION
(
  personalizationuuid VARCHAR2(50) not null,
  userid              VARCHAR2(100),
  nodeid              VARCHAR2(100),
  viewnodeid          VARCHAR2(200),
  ordernum            NUMBER(11),
  show_t              NUMBER(11)
)
;
alter table LFS_PERSONALIZATION
  add constraint PRIMARK_KEY primary key (PERSONALIZATIONUUID);





create table P_SITE_MANAGE
(
  site_id           VARCHAR2(32 CHAR) not null,
  site_no           VARCHAR2(255 CHAR),
  site_name         VARCHAR2(255 CHAR),
  p_site_no         VARCHAR2(255 CHAR),
  p_site_name       VARCHAR2(255 CHAR),
  disp_sn           NUMBER(10),
  site_status       VARCHAR2(255 CHAR),
  create_time       TIMESTAMP(6),
  user_no           VARCHAR2(255 CHAR),
  dept_no           VARCHAR2(255 CHAR),
  site_addr         VARCHAR2(255 CHAR),
  logo_path         VARCHAR2(255 CHAR),
  copyright_content VARCHAR2(255 CHAR),
  type_code         VARCHAR2(255 CHAR),
  navi_id           VARCHAR2(255 CHAR),
  app_id            VARCHAR2(255)
)
;
alter table P_SITE_MANAGE
  add primary key (SITE_ID);
alter table P_SITE_MANAGE
  add constraint FK21A8AFEECB2F2617 foreign key (P_SITE_NO)
  references P_SITE_MANAGE (SITE_ID);





create table P_BASE_INFO
(
  top_id        VARCHAR2(32) not null,
  site_id       VARCHAR2(255),
  site_name     VARCHAR2(255),
  site_no       VARCHAR2(255),
  suspen_addr   VARCHAR2(255),
  suspen_status VARCHAR2(255),
  suspen_url    VARCHAR2(255),
  top_type      VARCHAR2(255),
  res_code      VARCHAR2(255),
  suspen_path   VARCHAR2(255),
  res_id        VARCHAR2(255),
  res_height    NUMBER(10),
  res_width     NUMBER(10)
)
;
alter table P_BASE_INFO
  add primary key (TOP_ID);
alter table P_BASE_INFO
  add constraint FK6A4A882D946DECFA foreign key (SITE_ID)
  references P_SITE_MANAGE (SITE_ID);
alter table P_BASE_INFO
  add constraint FK6A4A882D946DEDA0 foreign key (SITE_NO)
  references P_SITE_MANAGE (SITE_ID);





create table P_LAYOUT_DEFINITION
(
  layout_code    VARCHAR2(32 CHAR) not null,
  layout_name    VARCHAR2(256 CHAR),
  layout_content VARCHAR2(256 CHAR),
  layout_picpath VARCHAR2(256 CHAR),
  create_date    DATE,
  modify_date    DATE
)
;
alter table P_LAYOUT_DEFINITION
  add primary key (LAYOUT_CODE);





create table P_PLACEHOLDER_GROUP
(
  pg_id          VARCHAR2(32 CHAR) not null,
  placeholder_id VARCHAR2(255 CHAR),
  group_id       VARCHAR2(255 CHAR),
  tmpl_id        VARCHAR2(255 CHAR)
)
;
alter table P_PLACEHOLDER_GROUP
  add primary key (PG_ID);





create table P_POP_INFO
(
  pop_id      VARCHAR2(32) not null,
  site_id     VARCHAR2(32),
  top_id      VARCHAR2(32),
  pop_title   VARCHAR2(256),
  pop_content VARCHAR2(4000),
  pop_status  VARCHAR2(16),
  start_time  VARCHAR2(50),
  end_time    VARCHAR2(50),
  pop_url     VARCHAR2(1000),
  height      NUMBER(5),
  width       NUMBER(5)
)
;





create table P_PORTLET_PROPERTY
(
  portlet_id   VARCHAR2(32 CHAR) not null,
  portlet_name VARCHAR2(256 CHAR),
  func_action  VARCHAR2(256 CHAR),
  portlet_type VARCHAR2(16 CHAR),
  edit_action  VARCHAR2(256 CHAR),
  title_url    VARCHAR2(256 CHAR),
  portlet_desc VARCHAR2(1024 CHAR),
  width        NUMBER(10),
  height       NUMBER(10),
  html_code    VARCHAR2(4000 CHAR)
)
;
alter table P_PORTLET_PROPERTY
  add primary key (PORTLET_ID);





create table P_RESOURCES
(
  res_id     VARCHAR2(32 CHAR) not null,
  res_type   VARCHAR2(255 CHAR),
  res_name   VARCHAR2(255 CHAR),
  res_path   VARCHAR2(255 CHAR),
  res_status VARCHAR2(255 CHAR),
  res_width  NUMBER(10),
  res_height NUMBER(10),
  file_size  NUMBER(19),
  site_id    VARCHAR2(255 CHAR),
  site_name  VARCHAR2(255 CHAR),
  res_code   VARCHAR2(255 CHAR),
  app_id     VARCHAR2(255)
)
;
alter table P_RESOURCES
  add primary key (RES_ID);





create table P_SYS_PARAM
(
  code_id    VARCHAR2(32 CHAR) not null,
  code_type  VARCHAR2(255 CHAR),
  code_value VARCHAR2(255 CHAR),
  code_name  VARCHAR2(255 CHAR),
  p_code     VARCHAR2(255 CHAR),
  disp_sn    NUMBER(10),
  content1   VARCHAR2(255 CHAR),
  content2   VARCHAR2(255 CHAR),
  content3   VARCHAR2(255 CHAR),
  content4   VARCHAR2(255 CHAR),
  content5   VARCHAR2(255 CHAR)
)
;
alter table P_SYS_PARAM
  add primary key (CODE_ID);





create table P_THEME_DEFINITION
(
  theme_code    VARCHAR2(32 CHAR) not null,
  theme_name    VARCHAR2(256 CHAR),
  theme_picpath VARCHAR2(256 CHAR),
  theme_content VARCHAR2(256 CHAR),
  create_date   DATE,
  modify_date   DATE,
  theme_path    VARCHAR2(255 CHAR),
  app_id        VARCHAR2(255)
)
;
alter table P_THEME_DEFINITION
  add primary key (THEME_CODE);





create table P_TMPL
(
  tmpl_id         VARCHAR2(32 CHAR) not null,
  tmpl_name       VARCHAR2(100 CHAR),
  tmpl_addr       VARCHAR2(4000 CHAR),
  tmpl_status     VARCHAR2(10 CHAR),
  tmpl_code       VARCHAR2(8 CHAR),
  tmpl_pic_addr   VARCHAR2(255 CHAR),
  disp_sn         NUMBER(10),
  create_time     TIMESTAMP(6),
  update_time     TIMESTAMP(6),
  tmpl_style_addr VARCHAR2(255 CHAR),
  app_id          VARCHAR2(255)
)
;
alter table P_TMPL
  add primary key (TMPL_ID);





create table P_TMPL_ATTR
(
  tmpl_attr_id     VARCHAR2(32) not null,
  tmpl_id          VARCHAR2(32),
  placeholder_id   VARCHAR2(32),
  placeholder_name VARCHAR2(255),
  placeholder_type VARCHAR2(255),
  content_code     VARCHAR2(255),
  portlet_id       VARCHAR2(255),
  portlet_name     VARCHAR2(255),
  func_action      VARCHAR2(255),
  edit_action      VARCHAR2(255),
  title_url        VARCHAR2(255)
)
;
alter table P_TMPL_ATTR
  add primary key (TMPL_ATTR_ID);
alter table P_TMPL_ATTR
  add constraint FK22E4208CC1B40E84 foreign key (TMPL_ID)
  references P_TMPL (TMPL_ID);





create table P_TMPL_PAGE
(
  page_id      VARCHAR2(32) not null,
  site_no      VARCHAR2(255),
  site_name    VARCHAR2(255),
  page_no      VARCHAR2(32),
  page_name    VARCHAR2(255),
  site_id      VARCHAR2(255),
  tmpl_id      VARCHAR2(255),
  page_type    VARCHAR2(16),
  page_title   VARCHAR2(256),
  layout_code  VARCHAR2(16),
  theme_code   VARCHAR2(16),
  page_status  VARCHAR2(255),
  if_default   NUMBER(10),
  disp_sn      NUMBER(10),
  page_addr    VARCHAR2(255),
  pub_time     DATE,
  user_no      VARCHAR2(255),
  pub_addr     VARCHAR2(255),
  content_type VARCHAR2(1)
)
;
alter table P_TMPL_PAGE
  add primary key (PAGE_ID);
alter table P_TMPL_PAGE
  add constraint FK22EAA92A946DECFA foreign key (SITE_ID)
  references P_SITE_MANAGE (SITE_ID);
alter table P_TMPL_PAGE
  add constraint FK22EAA92AC1B40E84 foreign key (TMPL_ID)
  references P_TMPL (TMPL_ID);





create table P_TMPL_PORTLETINFO
(
  tp_id            VARCHAR2(32) not null,
  page_id          VARCHAR2(255),
  portlet_id       VARCHAR2(255),
  placeholder_id   VARCHAR2(255),
  placeholder_name VARCHAR2(255),
  placeholder_type VARCHAR2(255),
  show_name        VARCHAR2(255),
  func_id          VARCHAR2(255),
  func_name        VARCHAR2(255),
  func_action      VARCHAR2(255),
  edit_action      VARCHAR2(255),
  title_url        VARCHAR2(255),
  portlet_status   VARCHAR2(255),
  portlet_type     VARCHAR2(255),
  html_code        VARCHAR2(255),
  sort_no          NUMBER(10),
  row_no           NUMBER(10),
  disp_sn          NUMBER(10)
)
;
alter table P_TMPL_PORTLETINFO
  add primary key (TP_ID);
alter table P_TMPL_PORTLETINFO
  add constraint FK5367368D244CD88F foreign key (PAGE_ID)
  references P_TMPL_PAGE (PAGE_ID);
alter table P_TMPL_PORTLETINFO
  add constraint FK5367368DA9C443F4 foreign key (PORTLET_ID)
  references P_PORTLET_PROPERTY (PORTLET_ID);





create table P_USER_PAGE
(
  page_id     VARCHAR2(32 CHAR) not null,
  page_no     VARCHAR2(32 CHAR),
  page_title  VARCHAR2(256 CHAR),
  disp_sn     NUMBER(10),
  layout_code VARCHAR2(32 CHAR),
  theme_code  VARCHAR2(32 CHAR),
  user_id     VARCHAR2(256 CHAR),
  user_no     VARCHAR2(256 CHAR),
  user_name   VARCHAR2(256 CHAR),
  if_default  NUMBER(10)
)
;
alter table P_USER_PAGE
  add primary key (PAGE_ID);





create table P_USER_PORTLETINFO
(
  up_id          VARCHAR2(32 CHAR) not null,
  page_id        VARCHAR2(255 CHAR),
  portlet_id     VARCHAR2(32 CHAR),
  show_name      VARCHAR2(256 CHAR),
  user_id        VARCHAR2(256 CHAR),
  user_no        VARCHAR2(256 CHAR),
  user_name      VARCHAR2(256 CHAR),
  func_id        VARCHAR2(16 CHAR),
  func_name      VARCHAR2(256 CHAR),
  func_action    VARCHAR2(256 CHAR),
  portlet_status VARCHAR2(8 CHAR),
  sort_no        NUMBER(10),
  row_no         NUMBER(10),
  disp_sn        NUMBER(10),
  edit_action    VARCHAR2(256 CHAR),
  title_url      VARCHAR2(256 CHAR)
)
;
alter table P_USER_PORTLETINFO
  add primary key (UP_ID);
alter table P_USER_PORTLETINFO
  add constraint FK_USER_POR_PORTLET_P_PORTLET_ foreign key (PORTLET_ID)
  references P_PORTLET_PROPERTY (PORTLET_ID);
alter table P_USER_PORTLETINFO
  add constraint FK_USER_POR_USER_PERS_USER_PAG foreign key (PAGE_ID)
  references P_USER_PAGE (PAGE_ID);

