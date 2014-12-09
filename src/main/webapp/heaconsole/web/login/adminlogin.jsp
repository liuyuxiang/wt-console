<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Admin Console</title>
<link id="themePath" rel="stylesheet" href="${ctx}/heaconsole/css/css.css" />
<script>
function check(){
var pwd = document.getElementById('password').value;
var userid = document.getElementById('userid').value;
if(userid == ""){alert("用户名不能为空");return false};
if(pwd == ""){alert("密码不能为空");return false};
}
	
</script>
</head>
<body class="login_background" onload="document.getElementById('userid').focus()">
<form method="post" action="<html:rewrite action='heaUserAction' />" onsubmit="return check();">
	<input type="hidden" value="adminValidateUser" name="action">
	<div id="cont" class="login_content_background">
	  <div class="login_ipt">
	    <p>
	    	<bean:message key="ui.account"/>:
	      <input name="id" type="text" class="jpt_2" id="userid" size="12"/>
	    </p>
	    <p>
	    	<bean:message key="ui.password"/>:
	      <input name="password" type="password" class="jpt_2" id="password" size="12" />
	      </p>
	    <p>
	      <label>
	      <input name="button" type="submit" class="btn_1" id="button" value="<bean:message key='ui.ok'/>" />
	      </label>
	      <label>
	      <input name="button" type="button" class="btn_2" id="button" value="<bean:message key='ui.reset'/>" onclick="document.getElementById('userid').value='';document.getElementById('password').value='';document.getElementById('userid').focus();"/>
	      </label>
	    </p>
		<div class="focus">${message }</div>
	  </div>
	</div>
</form>
</body>
</html>