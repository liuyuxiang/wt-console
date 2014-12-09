<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.input_dl {
	TEXT-ALIGN: left; LINE-HEIGHT: 20px; TEXT-INDENT: 2px; WIDTH: 83px; FONT-FAMILY: "????"; HEIGHT: 15px; COLOR: #006f69; FONT-SIZE: 12px; TEXT-DECORATION: none
}
.titleborder {
	BORDER-BOTTOM: #dfdfdf 1px solid; BORDER-LEFT: #dfdfdf 1px solid; BORDER-TOP: #dfdfdf 1px; BORDER-RIGHT: #dfdfdf 1px solid
}
</style>
<script language="javascript">
function trim(str) {
	for ( var i = 0; i < str.length && str.charAt(i) == ' '; i++)
		;
	for ( var j = str.length; j > 0 && str.charAt(j - 1) == ' '; j--)
		;
	if (i > j)
		return '';
	return str.substring(i, j);
}
function checkname() {
	if (document.logon.username.value == ""
			&& document.logon.password.value == "") {
		alert("请输入用户名和密码！");
		return false;
	}else 
	if (document.logon.username.value == ""
			|| document.logon.password.value == "") {
		alert("请输入用户名或密码！");
		return false;
	}else 
	document.logon.username.value = trim(document.logon.username.value);
            document.logon.submit();
}
</script>
</head>
<body>
		<table width="190" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<td><img src="style/bj/images/yhdl1.jpg" /></td>
			</tr>
			<tr>
				<td class="titleborder">
				<form name="IDPLogin" enctype="application/x-www-form-urlencoded" method="POST" action="http://novellids.bj.sgcc.com.cn:8080/nidp/idff/sso?sid=0" AUTOCOMPLETE="off"
					onsubmit="checkname();return false;" name="logon">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td height="9" colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td width="33%" height="25" style="COLOR: #be0610; FONT-SIZE: 12px; FONT-WEIGHT: bold" nowrap="nowrap">用户名：</td>
						<td width="39%"><input name="Ecom_User_ID" type="text"
							class="input_dl" id="textfield" tabindex="1" /></td>
						<td width="28%" rowspan="2"><input type=image name=submit
							src="style/bj/images/yhdl2.jpg" tabindex="3" /></td>
					</tr>
					<tr>
						<td height="25" style="COLOR: #be0610; FONT-SIZE: 12px; FONT-WEIGHT: bold" nowrap="nowrap">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
						<td width="39%"><input name="Ecom_Password" type="password"
							class="input_dl" id="textfield2" tabindex="2" /></td>
					</tr>
				</table>
				<input name="login-form-type" type="hidden" value="pwd"></form>
				</td>
			</tr>
		</table>
<input type="hidden" value="http://novellids.bj.sgcc.com.cn:8080/nidp/app/logout"/>
</body>
</html>