insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_root', '系统结构', 0, null, null, 'index_root', '_blank', '1', 5, null, 1, null, 1, to_date('17-05-2011 11:14:18', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hes_system', '系统管理', 1, null, null, 'hea_root', null, '1', 5, null, 2, null, 1, to_date('11-05-2011 07:36:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_resources', '资源管理', 1, null, null, 'hes_system', null, '1', 5, null, 3, null, 1, to_date('17-05-2011 11:14:18', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_group_resources', '组与指标绑定', 0, 'heaconsole/web/rbac/group/groupIndexManager.jsp', null, 'hea_resources', null, '1', 5, null, 4, null, 1, null, null, null, 'jixiaoappid', null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_systemresource', '系统资源', 0, 'heaconsole/web/rbac/index/indexManagerTree.jsp?type=5', null, 'hea_resources', null, '0', 5, null, 4, null, 1, null, null, null, 'jixiaoappid', null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_appresource', '应用资源', 0, 'heaconsole/web/rbac/index/indexManagerTree.jsp?type=0', null, 'hea_resources', null, '0', 5, null, 4, null, 1, null, null, null, 'jixiaoappid', null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_rootpower', '权限管理', -2, null, null, 'hes_system', null, '1', 5, null, 3, null, 1, to_date('17-05-2011 11:14:18', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_rootdept', '人员组织管理', 0, 'w/userlist', null, 'hea_rootpower', null, '1', 5, null, 3, null, 0, null, null, null, 'jixiaoappid', null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_rootsys', '系统配置', 3, 'w/syslist', null, 'hea_rootpower', null, '1', 5, null, 3, null, 0, null, null, null, 'jixiaoappid', null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_rootgroup', '角色管理', 0, 'mainlist.nsf?menuId=1', null, 'hea_rootpower', null, '1', 5, null, 4, null, 0, null, null, null, 'jixiaoappid', null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_rootuser', '属性管理', -2, 'mainlist.nsf?menuId=3', null, 'hea_rootpower', null, '1', 5, null, 4, null, null, to_date('17-05-2011 11:14:18', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_appmanager', '应用管理', 0, 'heaAppManageAction.hea?action=appList', null, 'hes_system', 'mainContent', '1', 5, null, 3, null, 1, null, null, null, 'jixiaoappid', null, null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('hea_indexroot_0', 'root', 1, null, null, 'index_root', 'mainContent', '1', 0, null, 1, 'd', 1, to_date('10-09-2013', 'dd-mm-yyyy'), null, null, 'pfl', 'com.hirisun.hea.wsc.web.content.FramePage', null, null, null, null, null, null, null, null, null, null);
insert into LFS_INDEX (INDEXUUID, INDEXNAME, INDEXORDER, INDEXURL, INDEXMAPPEDURL, PARENTINDEXUUID, TARGET, WAY, INDEXTYPE, INDEXICON, INDEXLEVEL, DESCRIPTION, HASCHILD, CREATETIME, INDEXICON_DISK_PATH, LEVEL_CODE, APP_ID, WICKET_CLASS, INDEX_ICON_PATH, INDEX_ICON_NO, STYLE_CLASS, STYLE_CLASS_ON, INDEX_ICON_PATH_ON, USERID, NODEID, VIEWNODEID, ORDERNUM, SHOW_T)
values ('index_root', '根节点', 1, null, null, null, null, '1', 0, null, 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
commit;
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('4028d381321a11f001321a2f292d0002', 'regtype', '01', '线性关系', null, null, null, '01', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('4028d381321a11f001321a2faa9c0003', 'regtype', '02', '树状关系', null, null, null, '02', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('1', 'app__link1', '01', 'logo', null, null, 1, 'heaAppResourceAction.hea?action=resourceList=05', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('2', 'app__link1', '01', 'banner', null, null, 2, 'heaAppResourceAction.hea?action=resourceList=07', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('3', 'app__link1', '01', '主题', null, null, 3, 'heaAppResourceAction.hea?action=themeList', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('4', 'app_link', '01', '应用资源', null, null, 4, 'heaconsole/web/rbac/index/indexManagerTree.jsp?type=0', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('5', 'app__link1', '01', '链接资源', null, null, 5, 'heaconsole/web/rbac/index/indexManagerTree.jsp?type=0', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('6', 'app__link1', '01', '插件资源', null, null, 6, 'http://www.google.com.hk', null, null, null, null, null, null);
insert into HEA_SYSTEM_CODE (ID, REG_CODE, REG_TYPE, REG_NAME, REG_DESC, REG_PROP, REG_ORDER, REG_VALUE, PARENT_ID, INDEXLEVEL, HASCHILD, LEVEL_CODE, REG_LABEL, APP_ID)
values ('7', 'site_link', '01', '站点管理', null, null, 7, 'heaconsole/web/webbuild/flex/ViewTreeTable.html', null, null, null, null, null, null);
commit;