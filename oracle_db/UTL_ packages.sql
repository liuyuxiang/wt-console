--一、开通UTL_ packages的sql是：（注：使用DBA权限的用户操作）

--用dba登录oracle
connect / as sysdba;

--创建Resolve_Access.xml
begin
dbms_network_acl_admin.create_acl (
--一个数据库下只能存在一个xml,如果需要多个用户同时开通需要自定义xml名称
acl => 'Resolve_Access.xml',
description => 'Resolve Network Access using UTL_INADDR',
--指定用户名,必须大写
principal => 'UUM',
is_grant => TRUE,
privilege => 'resolve',
start_date => null,
end_date => null
);
commit;
end;

--启动Resolve_Access.xml
begin
dbms_network_acl_admin.assign_acl (
acl => 'Resolve_Access.xml',
host => '*',
lower_port => null,
upper_port => null);
commit;
end;
/


--二、关闭UTL_ packages的sql是：

--用dba登录oracle
connect / as sysdba

--关闭Resolve_Access.xml
begin
dbms_network_acl_admin.unassign_acl (
acl => 'Resolve_Access.xml',
host => '*',
lower_port => null,
upper_port => null);
commit;
end;

--删除Resolve_Access.xml
BEGIN
DBMS_NETWORK_ACL_ADMIN.drop_acl (
acl => 'Resolve_Access.xml');
COMMIT;
END;
/

