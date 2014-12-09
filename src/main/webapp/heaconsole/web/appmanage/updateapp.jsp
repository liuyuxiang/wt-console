<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改应用第一步</title>
<link rel="stylesheet" type="text/css" href="${ctx}/heaconsole/css/subcss.css">
${componentConfig.jqueryPath}
${componentConfig.validationPath}
<script type="text/javascript" src="${ctx }/heaconsole/script/appmanage/editapp1.js"></script>
</head>
<body>
	 <div class="title">应用基本信息</div>
	 <form id="appmanageform" action="${ctx }/heaAppManageAction.hea" method="post" name="appmanageform">
	 <input name="action" type="hidden" value="addApp1">
	 <input name="url" type="hidden" id="url" value="">
	 <input name="appManage.appId" type="hidden" value="${am.appId }">
	 <input name="id" type="hidden" value="${empty param.indexId ? indexId : param.indexId}">
	 <table class="tab_2">
			<tr>
				<td class="td_1">系统名称：</td>
				<td><input name="appManage.appName" heavalidation="appName" class="validate[required,custom[noSpecialCaracters],length[1,20]] text-input ipt" value="${am.appName }"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">系统标识：</td>
				<td><input name="appManage.appNo" heavalidation="appName" class="validate[required,custom[noSpecialCaracters],length[1,20]] text-input ipt" value="${am.appNo }"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">描述：</td>
				<td>
					<textarea class="validate[length[0,40]] ipt" name="appManage.appDesc" rows="4" cols="40">${am.appDesc }</textarea>
				</td>
			</tr>
			<tr>
				<td class="td_1">系统ip地址：</td>
				<td>
				<input name="appManage.appAddr" type="text" class="validate[custom[ip],length[0,40]] ipt" value="${am.appAddr }">
				</td>
			</tr>
			<tr>
				<td class="td_1">系统目录：</td>
				<td>
				<input name="appManage.appCataLog" type="text" class="validate[length[0,40]] ipt" value="${am.appCataLog }">
				</td>
			</tr>
			<tr>
				<td class="td_1">系统类型：</td>
				<td>
				<select name="appManage.appType" disabled="disabled">
					<option value="01" ${am.appType eq '01' ? 'selected':''}>应用系统</option>
					<option value="02" ${am.appType eq '02' ? 'selected':''}>门户系统</option>
				</select>
				</td>
			</tr>
			<tr>
				<td  colspan="3"  class="td_2" >
					<input class="btn" id="saveBtn" type="submit" value="保存"/>
					<input class="btn" id="nextBtn" type="submit" value="下一步"/>
					<input class="btn" id="backBtn" type="button" onclick="window.location.href='${pageContext.request.contextPath}/heaAppManageAction.hea?action=appList&treeNodeId=${indexId}'" value="返回"/>
				</td>
			</tr>
		</table>
		</form>
</body>
</html>