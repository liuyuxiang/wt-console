<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加应用第一步</title>
<link rel="stylesheet" type="text/css" href="../../css/subcss.css">
${componentConfig.jqueryPath}
<script type="text/javascript" src="${pageContext.request.contextPath }/heaconsole/script/appmanage/addapp1.js"></script>
${componentConfig.validationPath}
</head>
${message}
<body>
	 <div class="title">应用基础信息</div>
	 <form id="appmanageform" action="${pageContext.request.contextPath }/heaAppManageAction.hea" method="post" name="appmanageform">
	 <input name="action" type="hidden" value="addApp1">
	 <input name="url" type="hidden" id="url" value="">
	 <input name="id" type="hidden" value="${param.id }">
	 <table class="tab_2">
			<tr>
				<td class="td_1">系统名称：</td>
				<td><input name="appManage.appName" heavalidation="appName" class="validate[required,custom[noSpecialCaracters],length[1,20]] text-input ipt" id="appManage_appName"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">系统标识：</td>
				<td><input name="appManage.appNo" heavalidation="appNo" class="validate[required,custom[noSpecialCaracters],length[1,20]] text-input ipt" id="appManage_appNo"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">描述：</td>
				<td>
					<textarea name="appManage.appDesc" heavalidation="appdesc" class="validate[length[0,40]] ipt" rows="4" cols="40" class="ipt"></textarea>
				</td>
			</tr>
			<tr>
				<td class="td_1">系统ip地址：</td>
				<td>
				<input name="appManage.appAddr" heavalidation="appAddr" type="text" class="validate[custom[ip],length[0,40]] text-input ipt">
				</td>
			</tr>
			<tr>
				<td class="td_1">系统目录：</td>
				<td>
				<input name="appManage.appCataLog" heavalidation="cataLog" type="text" class="validate[length[0,40]] text-input ipt">
				</td>
			</tr>
			<tr>
				<td class="td_1">系统类型：</td>
				<td>
				<select name="appManage.appType">
					<option value="01">应用系统</option>
					<option value="02">门户系统</option>
				</select>
				</td>
			</tr>
			<tr>
				<td  colspan="3"  class="td_2" >
					<input class="btn" id="saveBtn" type="submit" value="保存"/>
					<input class="btn" id="nextBtn" type="submit" value="下一步"/>
					<input class="btn" id="backBtn" type="button" onclick="window.location.href='${pageContext.request.contextPath}/heaAppManageAction.hea?action=appList&treeNodeId=${param.id }'" value="返回"/>
				</td>
			</tr>
		</table>
		</form>
</body>
</html>