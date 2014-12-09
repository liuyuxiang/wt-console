<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'updateIndex.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />
	<!-- ajax树状结构所需文件 -->
	<link   href="${pageContext.request.contextPath}/common/css/theme/${sessionScope.theme}/component/dhtmlxtree/dhtmlxtree.css" rel="stylesheet" type="text/css" >
	<script src="${pageContext.request.contextPath}/common/component/dhtmlxtree/script/dhtmlxcommon_ST.js"></script>
	<script src="${pageContext.request.contextPath}/common/component/dhtmlxtree/script/dhtmlxtree_ST.js"></script>
	<!-- ajax树状结构所需文件 End:-->
	${componentConfig.jqueryPath}
	<%@include file="../../../script/rbac/index/updateIndexParent_js.jsp" %>
  </head>
  
  <body onload="initilTree();">
	<div>
		<form action="${pageContext.request.contextPath}/heaIndexAction.hea" method="post">
			<input type="hidden" name="action" value="updateIndexPid"/>
			<input type="hidden" name="indexId" value="${param.indexuuid}"/>
			<input type="hidden" name="indexPId"/>
			<input type="hidden" name="appId" id="appId" value="${param.appId }"/>
			<input type="submit" class="btn" onclick="" value="修改"/>
		 	<div id="divTree"></div>
			<input type="submit" class="btn" onclick="" value="修改"/>
		</form>
	</div>
  </body>
</html>
