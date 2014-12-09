<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>指标管理</title>
<link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />

<link href="${pageContext.request.contextPath }/css/treeWin.css" rel="stylesheet" type="text/css" />

${componentConfig.jqueryPath}
${componentConfig.dhtmlTreePath}
<jsp:include page="../../../../heaconsole/script/rbac/index/indexManagerTree_js.jsp">
	<jsp:param value="${param.type}" name="indexType"/>
	<jsp:param value="${param.appId}" name="appId"/>
</jsp:include>

</head>
<body>
	<table width="100%" height="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td id="tdSidebar" valign="top" width="200px;">
				<div id="sidebar"  style="vertical-align: top;"></div>
			</td>
			<td>
				<iframe name="iframeContent" frameborder="0" width="100%" height="100%" src=""></iframe>
			</td>
		</tr>
	</table>
</body>
</html>