<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>主题列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
	${componentConfig.jqueryPath}
	<script type="text/javascript" src="${pageContext.request.contextPath }/heaconsole/script/appmanage/themelist.js"></script>
  </head>
  <body>
  <input type="hidden" id="appId" value="${param.appId }">
  <div class="title">主题信息</div>
  <div class="title_1"><input type="button" value="添加" id="addThemeInfo" class="btn"/></div>
    <table class="tab" >
						<tr class="tab_title">
						  <%--<td >选择</td>
						  --%><td>主题名称</td>
						  <td>主题地址</td>
						  <td>操作项</td>
			 			</tr>
			 			<c:forEach items="${themes.data}" var="theme">
							<tr class="tab_trcurrent_1" >
								<%--<td>
								<input type="hidden" name="appInfos.themeSystemId" id="themeSystemId" value="${appTheme.id }">
								<input type="radio" name="appInfos.themeId" value="${theme.themeCode }" class="themeInfo" ${appTheme.regValue eq theme.themeCode ?'checked=checked':'' }/>
								</td>
								--%><td>${theme.themeName }</td>
								<td>${theme.themePath }</td>
								<td>
									<a class="edit edittheme" tid="${theme.themeCode}" href="###">[编辑]</a>
									<a class="delete deletetheme" tid="${theme.themeCode}" href="###">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
  </body>
</html>
