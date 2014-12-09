<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改用户密码</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
</head>
<script LANGUAGE="JavaScript">
function doSubmit()
{
	if((doForm.password.value=="")||(doForm.password.value==null))
	{
		alert('请输入密码');
		//doForm.password.focus();
		return false;
	}
	
	if((doForm.repassword.value=="")||(doForm.repassword.value==null)){
		alert('请输入确认密码');
		//doFrom.repassword.focus();
		return false;
	}
	if(doForm.password.value!=doForm.repassword.value)
	{
		alert('两次输入的密码不一致');
		return false;
	}

	if (doForm.password.value.length<6 || doForm.repassword.value.length<6) {
		alert('密码长度小于六位');
		return false;
	}
}
</script>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="overflow-y:scroll;overflow-x:hidden;height:458px;">
<form action="<@spring.url "/editUserPwdform.nsf" />?id=${id}&userid=${user.uuid}"   method="post" id="doForm" name="doForm" onsubmit="return doSubmit()" >
<table  class="tableborder1" width="100%" border="0" cellspacing="1" cellpadding="0">
<tr>
					<td width="250" height="25" valign="middle" bgcolor="#FFFFFF" background="<@spring.url "/style/default/images/back2.gif" />">
					<table width="251" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="12%">&nbsp;</td>
							<td width="88%" ><b>修改密码</b></td>
						</tr>
					</table>
					</td>

					<td height="21" align="right" bgcolor="#FFFFFF" width="100%" >&nbsp;</td>
</tr>
<TR>
	<TD align="right"  bgcolor="#FFFFF">ID:</TD>
	<TD align=left  bgcolor="#FFFFF">
       <input name=""  readonly="true" size="20" value="${user.id}" style="border:0px" maxlength="20" /> 
	</TD>
</TR>
<TR class='tb1'>
	<TD  bgcolor="#FFFFF" align=right>新密码:</TD>
	<TD  bgcolor="#FFFFF" align=left>	
		<input type="password" id="password" name="password" onkeyup='this.value=this.value.replace(/\s+/g,"");' value="" />
	</TD>
</TR>
<TR class='tb1'>
	<TD align="right"  bgcolor="#FFFFF">确认密码:</TD>
	<TD align=left  bgcolor="#FFFFF">
		<input type="password" id="repassword" name="repassword" onkeyup='this.value=this.value.replace(/\s+/g,"");' value="" />
	</TD>
</TR>
<#list attlist as list>
<TR class='tb1'>
	<TD align="right"  bgcolor="#FFFFF">${list.type.name}</TD>
	<TD align=left  bgcolor="#FFFFF" height="25px">
		<label id="${list.type.id}"><#list list.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list></label>
	</TD>
</TR>
</#list>
<TR class='tb1'>
	<TD align=center colspan=4  bgcolor="#FFFFF"> 
			<input type="submit" name="submit" class="button" value="确定">
			<input type="button" name="button" class="button" value="返回" onclick="history.go(-1)">
	</TD>
</TR>
</table>
</form>
</body>
</html>