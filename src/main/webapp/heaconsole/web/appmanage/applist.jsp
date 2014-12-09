<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>应用列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/common/component/dialog/css/default.css">
	${componentConfig.jqueryPath}
	<script type="text/javascript" src="${pageContext.request.contextPath}/heaconsole/script/appmanage/applist.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/component/dialog/script/lhgcore.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/component/dialog/script/lhgdialog.min.js" ></script>
  </head>
  ${message }
  <body>
    <div class="title">多应用管理</div><div class="title_1"><input type="button" indexId="${empty param.treeNodeId ? indexId : param.treeNodeId}" class="btn" id="addapp" value='<bean:message key="ui.add" />'/></div>
    <table class="tab" >
			<tr class="tab_title">
			  <td >系统名称</td>
			  <td>系统地址</td>
			  <td>目录</td>
			  <td>系统状态</td>
			  <td>运行状态</td>
			  <td>操作项</td>
 			</tr>
 			<c:forEach items="${apps.data}" var="app">
				<tr class="tab_trcurrent_1" >
					<td>${app.appName }</td>
					<td>${app.appAddr }</td>
					<td>${app.appCataLog }</td>
					<td>${app.appStatus }</td>
					<td>${app.runStatus }</td>
					<td>
						<a class="btn_5 editapp" appId="${app.appId }" id="editapp" href="${ctx}/heaAppManageAction.hea?action=toUpdate&id=${app.appId }&indexId=${empty param.treeNodeId ? indexId : param.treeNodeId}">[编辑]</a>
						<a class="btn_6 deleteapp" appId="${app.appId }" id="deleteapp" href="###">[删除]</a>						
					</td>
		 		</tr>
 			</c:forEach>
 			<tr class="tab_trcurrent">
 				<td colspan="6">
                   	<div class="changepage1 position_r" >
					共${apps.totalPage }页，当前第${apps.currPageNum }页 
					
					<!--<a href="heaAppManageAction.hea?action=appList&currPage=1&perPage=10&treeNodeId=${empty param.treeNodeId ? indexId : param.treeNodeId}">首页</a>
					--><c:if test="${apps.hasPreviouPage}">
						<a class="pre_1" href="heaAppManageAction.hea?action=appList&currPage=${ apps.currPageNum -1}&perPage=10&treeNodeId=${empty param.treeNodeId ? indexId : param.treeNodeId}">上一页</a> 
					</c:if>
					<c:if test="${!apps.hasPreviouPage}">
						<bean:message key="ui.prev_page"/>
					</c:if>
					<c:if test="${apps.hasNextPage}">
						<a class="next_1" href="heaAppManageAction.hea?action=appList&currPage=${ apps.currPageNum +1}&perPage=10&treeNodeId=${empty param.treeNodeId ? indexId : param.treeNodeId}">下一页</a>          
					</c:if>
					<c:if test="${!apps.hasNextPage}">
						<bean:message key="ui.next_page"/>
					</c:if>
					<!--<a  href="heaAppManageAction.hea?action=appList&currPage=${ apps.totalPage}&perPage=10&treeNodeId=${empty param.treeNodeId ? indexId : param.treeNodeId}">尾页</a>
           			--></div>
 				</td>
 			</tr>
		</table>

  </body>
</html>
