<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理(${ver?if_exists})</title>

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
			</TABLE></td></form>
	</tr>
</table>

</div>

</body>
</html>