<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>权限分配</title>
		<link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />
		${componentConfig.jqueryPath}
		${componentConfig.dhtmlTreePath}
		<jsp:include page="../../../script/rbac/index/indexGroupTree_js.jsp"/>
	</head>
	<body>
		<form name="updateIGForm"
			action="${pageContext.request.contextPath}/heaIndexAction.hea" onsubmit="return updateIGFormSubmit();" method="post">
			<input type="hidden" name="action" value="updateIG">
			<input type="hidden" name="bubs" >
			<input type="hidden" name="groupIdsStr" value="${groupIdsStr}">
			<input type="hidden" name="selectedGroupIdsStr" value="${groupIdsStr}">
			<input type="hidden" name="indexId" value="${indexId}">
			<div>
			<input class="btn" type="submit" value="提交">
			</div>
			<div id="sidebar"></div>
		</form>
	</body>
</html>