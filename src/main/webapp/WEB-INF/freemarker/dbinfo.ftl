<#import "/spring.ftl" as spring />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>统一用户管理</title>
  <#include "/script/jquery.ftl" >
  <#include "/style/style.ftl" >
 </head>

 <body>
 <script type="text/javascript">
//<![CDATA[

  
	$(function(){
	$("tr").css("background-color","#fffff0");
	$("td").css("text-align","center");
	});
 //]]>
</script>
  <table class="tableborder1" id="tt" width="70%" align="center" border="0" cellspacing="2"
		cellpadding="0">
  <tr>
	<td colspan=4>数据库连接信息</td>
  </tr>
  <tr>
	<td width="12%" >服务器名</td>
	<td><span>${serverHost}</span>(<span>${serverIp})</span></td>
	<td  width="12%" bgcolor="#fffff0">实例名</td>
	<td><span>${instanceName}</span></td>
  </tr>
  <tr>
	
	<td  width="12%">用户</td>
	<td><span>${user}</span></td>
	<td  width="12%">角色</td>
	<td><span>${roles}</span></td>
  </tr>
    <tr>
	<td  width="12%">表空间</td>
	<td><span>${tablespace}</span></td>
	<td  width="12%">连接者ip</td>
	<td><span>${clientIp}</span></td>
  </tr>   
  </table>
  <#if dbaWarning>
  <br />
  <font color=red size="3" ><strong>当前用户有DBA角色的权限，请撤消此角色！</strong></font>
  </#if>
 </body>
</html>
