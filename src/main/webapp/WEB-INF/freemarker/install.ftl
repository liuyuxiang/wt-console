<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理安装界面</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
//<![CDATA[
$(function(){
	$("#submit1").click(function(){
    	$("#submit1").val("请等待").attr("disabled",true);
    	$("#loading").toggle();
		var url="<@spring.url "/setup.nsf"/>";
		$.post(url,{deptname:$("#deptname").val(),username:$("#username").val(),password:$("#password").val()},function(data){
			if(data=="false"){
				$("#message").toggle();
			}
	    	//$("#submit1").val("提交").attr("disabled",false);
		    $("#loading").toggle();
		    window.location.reload();
		});
  	});
});
//]]>
</script>

</head>
<body style="background:#FFFFFF;">

<div id="login" >

<!--<h3>首页</h3>-->
<table width="838" height="394" align="center" border="1" cellspacing="0" cellpadding="0" >
	<tr>
		<td width="100%" height="30">
		<form name="login" id="login" method="post" action="" >
			<TABLE width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
				<TR height="24px" >
					<TD ><font size='8'>创建数据库基本信息</font></TD>
				</TR>
				<br>
				<TR height="24px" >
					<TD >根部门名称:<input style="width:70%" id="deptname" name="deptname" value="${rootdept.name}" /></TD>
				</TR>
				<br>
				<TR height="24px" >
					<TD >系统管理员ID:<input type="hidden" name="userid" value="${superuser.id}"><span>${superuser.id}</span></TD>
				</TR>
				<TR height="24px" >
					<TD >系统管理员中文名称:<input id="username" name="username" value="${superuser.name}"></TD>
				</TR>
				<TR height="24px" >
					<TD >系统管理员登录密码:<input id="password" name="password" value="${password}"></TD>
				</TR>
				<br>
				<TR>
					<TD ><img id="loading" height="24px" style="display:none;" src="<@spring.url "/style/hirisun/images/loading29.gif" />"></img></TD>
				</TR>
				<TR>
					<TD ><font color="red" id="message" style="display:none;">*创建失败,请确认数据库状态或者联系管理员</font></TD>
				</TR>
				<br>
				
				<TR>
					<TD ><input type="button" id="submit1" value="提交" /> <input type="reset" value="重置" /> 
					</TD>
				</TR>
			</TABLE></td></form>
	</tr>
</table>

</div>

</body>
</html>