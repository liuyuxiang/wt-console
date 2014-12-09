<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script language="javascript" type="text/javascript">
	
</script>
	<form action="<@spring.url "/application/modifyAppUserSuc.nsf"/>" method="post">
	<input type="hidden" name="uuid" id="uuid" value="${ua.uuid}"/>
	<input type="hidden" name="useruuid" id="useruuid" value="${ua.useruuid}"/>
	<input type="hidden" name="appuuid" id="appuuid" value="${ua.applicationuuid}"/>
	<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" border="0" cellspacing="2" cellpadding="0">
			<tr >
				<td height="10px" align="right" colspan="3"   bgcolor="#fffff0">用户ID</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="userid" disabled="disabled" id="userid" value="${ua.user.id}"/></td>
			</tr>
			<tr >
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">用户姓名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="username" disabled="disabled" id="username" value="${ua.user.name}"/></td>
			</tr>
			<tr >
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">允许登录</td>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="checkbox" name="loginable" id="loginable" <#if ua.loginable>checked</#if>/></td>
			</tr>
			<tr >
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">系统登录ID</td>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="mappenduserid" id="mappenduserid" value="${ua.mappendUserid}"/></td>
			</tr>
			<tr >
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">系统密码</td>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="password" name="mappendpassword" id="mappendpassword" value="${ua.mappendPassword}"/></td>
			</tr>
			<tr>
			<td align="center" colspan="6"  bgcolor="#fffff0">
				<input type="submit" class="button" value="提交"/>
	<input type="reset" class="button" value="重置"/>
	<input type="button" class="button" value="返回" onclick="history.back()"/>
			</td>
			</tr>
			</table>
	</form>
