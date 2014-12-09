<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>

<#include "/style/style.ftl" >

<#include "/script/jquery.ftl" >

<script type="text/javascript">
//<![CDATA[

$(function(){
	$("a.new-window").click(function(){
  		window.open(this.href);
  		return false;
  	});
});
 $(function(){
  });
//]]>
</script>

</head>
<body style="background:#FFFFFF;">

<div id="login" >

<!--<h3>首页</h3>-->
<table width="838" height="394" align="center" border="0" cellspacing="0" cellpadding="0" background="<@spring.url "/style/default/images/welcomeuser.jpg" />" >
	<tr>
		<td width="100%" valign="bottom" align="center" height="364"></td>
	</tr>
	<tr>
		<td width="100%" height="30">
		<form name="login" id="login" method="post" action="<@spring.url "/login.nsf" />" >
			<TABLE width="100%" align="right" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD align="right" >用户名：<input type="text" id="userid" name="u" value="" /></TD>
					<TD align="center" >密&nbsp;&nbsp;码：<input type="password" id="password" name="p" value="" /></TD>
					<TD align="left" ><input type="submit" id="submit" value="提交" /> <input type="reset" value="重置" /> 
					</TD>
				</TR>
				<#if Due?exists>
				<TR>
					<TD align="center" colspan="6" style="color:red" >
						<label id="pwdDueTime"><#if pwdDueTime?exists>密码已过期，过期时间为：${pwdDueTime?date}</#if></label>
					</TD>
				</TR>
				<TR>
					<TD align="center" colspan="6" style="color:red" >
						<a href="<@spring.url "/user/updateuserpwd.nsf"/>?userid=${userid}" target="_blank">请点击进入密码维护页面</a>
					</TD>
				</TR>
				<#else>
				<TR>
					<TD align="center" colspan="6" style="color:red">用户名或者密码错误，请重新输入！</TD>
				</TR>
				</#if>
			</TABLE></td></form>
	</tr>
</table>

</div>

</body>
</html>