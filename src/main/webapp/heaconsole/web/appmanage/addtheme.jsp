<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>主题</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
  </head>
  ${message }
  <body>
    <div class="title">资源信息</div>
	<form action="${pageContext.request.contextPath }/heaAppResourceAction.hea" method="post">
		<input type="hidden" name="action" value="saveTheme">
		<input type="hidden" name="themeDef.themeCode" value="${themeDef.themeCode }">
		<input type="hidden" name="themeDef.appId" value="${empty param.appId ? themeDef.appId : param.appId}"/>
		<input type="hidden" name="forward" value="${empty param.forward ? forward : param.forward}">
		<table class="tab_2">
			<tr>
				<td class="td_1">资源名称：</td>
				<td><input name="themeDef.themeName" class="ipt" value="${themeDef.themeName }"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">资源地址：</td>
				<td><input name="themeDef.themePath" class="ipt" value="${themeDef.themePath }"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="保存" class="btn">
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>
