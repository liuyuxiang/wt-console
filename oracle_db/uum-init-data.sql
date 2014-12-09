
insert into U2_RESOURCE (UUID, TYPE, STATUS, CREATEDTIME, MODIFIEDTIME)
values ('4028a7a147ec3e570147ec3eee260001', 1, 1, to_timestamp('19-08-2014 11:10:52.965000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('19-08-2014 11:10:52.966000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into U2_RESOURCE (UUID, TYPE, STATUS, CREATEDTIME, MODIFIEDTIME)
values ('4028a7a147ec3e570147ec3eee350002', 1, 1, to_timestamp('19-08-2014 11:10:52.981000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('19-08-2014 11:10:52.981000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into U2_RESOURCE (UUID, TYPE, STATUS, CREATEDTIME, MODIFIEDTIME)
values ('4028a7a147ec3e570147ec3eee420003', 1, 1, to_timestamp('19-08-2014 11:10:52.994000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('19-08-2014 11:10:52.994000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into U2_RESOURCE (UUID, TYPE, STATUS, CREATEDTIME, MODIFIEDTIME)
values ('4028a7a147ec3e570147ec3eee640004', 2, 1, to_timestamp('19-08-2014 11:10:52.998000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('19-08-2014 11:10:52.961000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into U2_RESOURCE (UUID, TYPE, STATUS, CREATEDTIME, MODIFIEDTIME)
values ('4028a7a147ec3e570147ec3eee800006', 2, 1, to_timestamp('19-08-2014 11:10:53.037000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('19-08-2014 11:10:52.961000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into U2_RESOURCE (UUID, TYPE, STATUS, CREATEDTIME, MODIFIEDTIME)
values ('4028a7a147ec3e570147ec3eee950009', 0, 1, to_timestamp('19-08-2014 11:10:53.073000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('19-08-2014 11:10:53.080000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into U2_DEPARTMENT (UUID, PARENTUUID, ORDERNUM, NAME, CODE, HASCHILDREN, PATH, DEPTCODE, ORGCODE, DEPTPARENTCODE, LASTUPDATETIME, CURRENTAUTHORLEVEL, RTXCODE, LEVEL_)
values ('4028a7a147ec3e570147ec3eee640004', null, 1, '深圳海联讯科技股份有限公司', 'rootuuid', 1, '深圳海联讯科技股份有限公司', 'rootuuid', 'rootuuid', 'rootuuid', to_timestamp('19-08-2014 11:10:52.961000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, 0, 0);
insert into U2_DEPARTMENT (UUID, PARENTUUID, ORDERNUM, NAME, CODE, HASCHILDREN, PATH, DEPTCODE, ORGCODE, DEPTPARENTCODE, LASTUPDATETIME, CURRENTAUTHORLEVEL, RTXCODE, LEVEL_)
values ('4028a7a147ec3e570147ec3eee800006', '4028a7a147ec3e570147ec3eee640004', 2, 'dummy', 'dummy', 1, '深圳海联讯科技股份有限公司→dummy', 'dummy', 'dummy', 'rootuuid', to_timestamp('19-08-2014 11:10:52.961000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, 1, 1);
commit;

insert into U2_DEPARTMENTPATH (UUID, ELDERUUID, JUNIORUUID)
values ('4028a7a147ec3e570147ec3eee650005', '4028a7a147ec3e570147ec3eee640004', '4028a7a147ec3e570147ec3eee640004');
insert into U2_DEPARTMENTPATH (UUID, ELDERUUID, JUNIORUUID)
values ('4028a7a147ec3e570147ec3eee810007', '4028a7a147ec3e570147ec3eee800006', '4028a7a147ec3e570147ec3eee800006');
insert into U2_DEPARTMENTPATH (UUID, ELDERUUID, JUNIORUUID)
values ('4028a7a147ec3e570147ec3eee8a0008', '4028a7a147ec3e570147ec3eee640004', '4028a7a147ec3e570147ec3eee800006');
commit;

insert into U2_GROUP (UUID, CODE, ORDERNUM, NAME, HASCHILDREN, GROUPTYPE, APPGROUPTYPE)
values ('4028a7a147ec3e570147ec3eee260001', 'root', 0, 'cqroot', 1, '0', '0');
insert into U2_GROUP (UUID, CODE, ORDERNUM, NAME, HASCHILDREN, GROUPTYPE, APPGROUPTYPE)
values ('4028a7a147ec3e570147ec3eee350002', 'wpsadmins', 0, '超级管理员组', 1, '0', '0');
insert into U2_GROUP (UUID, CODE, ORDERNUM, NAME, HASCHILDREN, GROUPTYPE, APPGROUPTYPE)
values ('4028a7a147ec3e570147ec3eee420003', 'appGroup', 0, '应用系统授权', 1, '0', '0');
commit;

insert into U2_GROUPRELATIONSHIP (CHILDUUID, PARENTUUID)
values ('4028a7a147ec3e570147ec3eee350002', '4028a7a147ec3e570147ec3eee260001');
insert into U2_GROUPRELATIONSHIP (CHILDUUID, PARENTUUID)
values ('4028a7a147ec3e570147ec3eee420003', '4028a7a147ec3e570147ec3eee260001');
commit;
insert into U2_USER (UUID, PRIMARYDEPARTMENTUUID, PRIMARYUSERUUID, ORDERNUM, CURRENTAUTHORLEVEL, ID, NAME, LASTUPDATETIME, PASSWORD)
values ('4028a7a147ec3e570147ec3eee950009', '4028a7a147ec3e570147ec3eee640004', null, 1, null, 'wpsadmin', '超级管理员', to_timestamp('19-08-2014 11:10:53.080000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'vToh9QcKcWPy6jbK6LmoBg==');
commit;

insert into U2_USERDEPARTMENTS (USERUUID, DEPARTMENTUUID)
values ('4028a7a147ec3e570147ec3eee950009', '4028a7a147ec3e570147ec3eee640004');
commit;

insert into U2_USERGROUPS (USERUUID, GROUPUUID)
values ('4028a7a147ec3e570147ec3eee950009', '4028a7a147ec3e570147ec3eee350002');
commit;
