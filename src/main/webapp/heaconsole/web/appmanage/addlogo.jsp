<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
</head>
${message }
<body>
	<div class="title">资源信息</div>
	<form action="${pageContext.request.contextPath }/heaAppResourceAction.hea" method="post">
		<input type="hidden" name="action" value="saveResource">
		<input type="hidden" name="resource.resId" value="${resource.resId }">
		<input type="hidden" name="resource.appId" value="${empty param.appId ? resource.appId : param.appId}"/>
		<input type="hidden" name="resource.resType" value="${empty param.resType ? resource.resType : param.resType}"/>
		<input type="hidden" name="forward" value="${empty param.forward ? forward : param.forward }">
		<table class="tab_2">
			<tr>
				<td class="td_1">资源名称：</td>
				<td><input name="resource.resName" class="ipt" value="${resource.resName }"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">资源地址：</td>
				<td><input name="resource.resPath" class="ipt" value="${resource.resPath }"/></td>
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