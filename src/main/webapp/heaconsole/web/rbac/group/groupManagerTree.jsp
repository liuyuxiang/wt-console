<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>指标管理</title>
<link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />
<link href="${ctx}/css/rbac/treeWin.css" rel="stylesheet" type="text/css" />
${componentConfig.jqueryPath}
${componentConfig.dhtmlTreePath}

<!-- 自己定义js -->

<!-- 自定义js End: -->
<jsp:include page="../../../script/rbac/group/groupManagerTree_js.jsp">
	<jsp:param value="0" name="indexType"/>
</jsp:include>


</head>
<body>
	<table width="100%" height="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td id="tdSidebar">
				<div id="sidebar"></div>
			</td>
			<td>
				<iframe name="iframeContent" width="100%" height="100%" src=""></iframe>
			</td>
		</tr>
	</table>
</body>
</html>