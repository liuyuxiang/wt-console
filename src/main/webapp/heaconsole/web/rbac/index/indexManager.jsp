<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'indexManager.jsp' starting page</title>
		<link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />
		<jsp:include page="../../../script/rbac/index/indexManager_js.jsp"/>
	</head>

	<body onload="updateIndex('${param.indexId}','${param.appId }');">
		<div>
			<table>
				<tr>
					<td>
						<c:if test="${consoleIsAdmin}"></c:if>
							<input type="button" class="btn" value="添加" onclick="addIndex('${param.indexId}','${param.appId }');">
						<c:if test="${ param.indexId ne 'hea_indexroot_0'}">
							<input type="button" class="btn" value="修改" onclick="updateIndex('${param.indexId}','${param.appId }');">
						</c:if>
						<c:if test="${ param.indexId ne 'hea_indexroot_0'}">
							<input type="button" class="btn" value="删除" onclick="deleteIndex('${param.indexId}','${param.appId }');">
						</c:if>
						<c:if test="${ param.indexId ne 'hea_indexroot_0'}">
							<input type="button" class="btn" value="修改父节点" onclick="updateIndexParent('${param.indexId}','${param.appId }');" style="width: 80px;">
						</c:if>
						<input type="button" class="btn" value="权限分配" onclick="updateIGTree('${param.indexId}','${param.appId }');" style="width: 80px;">
					</td>
				</tr>
			</table>
		</div>
		<iframe id="iframeIndex" name="iframeIndex" width="100%" frameborder="0" height="450px"></iframe>
	</body>
</html>
