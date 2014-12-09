<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理 (${ver?if_exists})</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script>
</script>
</head>
<body >
<#if loginuser?exists>
<#include "header.ftl" />
<div id="wrapper1" ><!--最外层div-->
	<div id="menu"> <#include "/menu.ftl" ></div><!--导航栏-->
	<div id="context">
<iframe id="rightframe1" src="<@spring.url "/mainlist.nsf?menuId=${menuId?if_exists}"/>" frameborder="0" width="100%" ></iframe>
	</div><!--内容栏-->
	<div id="footer"><#include "footer.ftl" ></div><!--版权-->
</div>
<#else>
<#include "index.ftl" >
  <font color="red">你还没登录！</font>
</#if>
</body>
</html>