<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理数据维护界面</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
//<![CDATA[
$(function(){
	$("#submit1").click(function(){
    	$("#submit1").val("请等待").attr("disabled",true);
    	$("#loading").toggle();
		var url="<@spring.url "/domaintenance.nsf"/>";
		$.post(url,{},function(data){
			if(data=="false"){
				$("#message").toggle();
			}
		    $("#loading").toggle();
		    document.getElementById("submit1").disabled="";
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
				<TR height="24px" >
					<TD ><font size='6'>数据库信息维护</font></TD>
				</TR>
	<tr>
		<td width="100%" height="30">
		<form name="login" id="login" method="post" action="" >
			<TABLE width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
				<br>
				<TR height="24px" >
					<TD >部门路径维护:</TD>
				</TR>
				<TR>
					<TD id="loading" style="display:none;" ><img  height="24px" src="<@spring.url "/style/hirisun/images/loading29.gif" />"></img>数据维护中，请稍等！</TD>
				</TR>
				<TR>
					<TD ><font color="red" id="message" style="display:none;">*创建失败,请确认数据库状态或者联系管理员</font></TD>
				</TR>
				<TR>
					<TD ><input type="button" id="submit1" value="提交" />
					</TD>
				</TR>
			</TABLE></td></form>
	</tr>
</table>

</div>

</body>
</html>