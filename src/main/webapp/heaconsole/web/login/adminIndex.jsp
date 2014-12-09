<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hirisun.hea.common.util.WebComponentConfig"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>管理平台</title>
<link id="themePath" href="${ctx}/heaconsole/css/console.css"
	rel="stylesheet" type="text/css" />
<link type="text/css" href="${ctx}/heaconsole/css/bootstrap.css"
	rel="stylesheet" />
<link href="${ctx}/heaconsole/css/style.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/common/component/bootstrap-3.2.0/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/common/component/bootstrap-3.2.0/js/bootstrap.js" type="text/javascript"></script>

${componentConfig.jqueryPath}
${componentConfig.dhtmlTreePath}
${componentConfig.jerichotabPath}



<!-- 自己定义js -->
<script type="text/javascript">
function changeTheme(color){
	var cssVar =document.getElementById('themePath') ;
	cssVar.href = "${ctx}"+"/css/theme/"+"${sessionScope.theme}"+"/console.css";
}

function innitil(){
	
		changDialogURL("${ctx}/heaUserAction.hea?action=userGroup");
	
		loadConsoleTree();

}
//登出
function loginOut(){
	$("#clearSession").attr("src","/uum2/logout.nsf");
	window.location.href='${ctx}/heaUserAction.hea?action=logout';
}
</script>
<!-- 自定义js End: -->


<jsp:include page="../../script/adminIndex_js.jsp" >
	<jsp:param value="5" name="indexType"/>
</jsp:include>
</head>
<body onload="innitil();autoWindowHeight();">
	<div>
		<!-- 头部开始 -->
		<div class="head clearfix" id="header">
			<div class="header_basicinfor">
				<div class="logoimg">
					<img src="./heaconsole/images/hirisunlogo.png" />
				</div>
				<div class="logo">Hirisun</div>
				<div class="diveder_s"></div>
				<div class="headname">ADMIN CONSOLE</div>
				<div class="headinfor">
					<ul>
						<li><i class="icon-resize-full marginalign"></i></li>
						<li><i class="icon-fullscreen marginalign"></i></li>
						<li><i class="icon-th-large marginalign"></i></li>
						<li><i class="icon-bell marginalign"></i></li>
						<li><i class="icon-exclamation-sign marginalign"></i></li>
						<li>
							<p style="height: 40px;" align="center">
								<img align="middle" src="./heaconsole/images/4650a.png"
									class="img-circle marginalign02" />
							</p>
						</li>
						<li><a target="_black" href="w/index">${user.name}</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="loginOut()"><bean:message key="ui.loginout" /></a></li>
					</ul>
				</div>
			</div>
			<div class="dividerss"></div>
		</div>
		<!-- 头部结束 End; -->
		<div class="maincontainer clearfix" id="maincontent">
			<div class="left-nav">
				<div id="login">
				<!-- 
					<div class="login_content">
						<bean:message key="ui.welcome" arg0="${user.name}" />
					</div>
				 -->
				</div>
				<div class="siderbar_menu_1_content" id="sidebar"></div>
			</div>
			<!-- <div class="drawback" id="drawback">
				<div class="drawback_icon"></div>
			</div> -->
			<div id="rightContent" class="maincont"></div>
		</div>
		<div class="footer" id="footer">
			<address>版权所有 2008-${copyrightTime} 深圳海联讯科技股份有限公司</address>
		</div>
	</div>
	<div class="hiddenFrame">
		<iframe id="hiddenFrame"></iframe>
		<iframe src="${ctx}/heaconsole/web/keeper/eye.html"></iframe>
		<iframe id="clearSession" src=""></iframe>
	</div>

	<jsp:include page="../../web/rbac/include/dialogWindow.jsp"></jsp:include>
</body>
</html>