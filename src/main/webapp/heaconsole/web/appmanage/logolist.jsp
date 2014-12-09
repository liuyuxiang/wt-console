<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>logo信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
	${componentConfig.jqueryPath}
	<script type="text/javascript" src="${pageContext.request.contextPath }/heaconsole/script/appmanage/logolist.js"></script>
  </head>
  
  <body>
  <input type="hidden" id="appId" value="${param.appId }">
  <div class="title">logo信息列表</div><div class="title_1"><input type="button" value="添加" id="addLogoInfo" class="btn"/></div>
   <table class="tab" >
						<tr class="tab_title">
						  <%--<td >选择</td>
						  --%><td>logo名称</td>
						  <td>logo地址</td>
						  <td>操作项</td>
			 			</tr>
			 			<c:forEach items="${resources.data}" var="logoInfo">
							<tr class="tab_trcurrent_1" >
								<%--<td>
									<input type="hidden" id="logoSystemId" name="appInfos.logoSystemId" value="${appLogo.id }"/>
									<input type="radio" name="appInfos.logoId" class="logoinfo" ${appLogo.regValue eq logoInfo.resId ?'checked=checked':'' } value="${logoInfo.resId }">
								</td>
								--%><td>${logoInfo.resName }</td>
								<td>${logoInfo.resPath }</td>
								<td>
									<a class="edit editresource" sid="${logoInfo.resId }" resType="${logoInfo.resType }" href="###">[编辑]</a>
									<a class="delete deletelogo" sid="${logoInfo.resId }" resType="${logoInfo.resType }" href="###">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
  </body>
</html>
