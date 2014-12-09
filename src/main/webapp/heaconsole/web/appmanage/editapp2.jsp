<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改应用第二步</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
${componentConfig.jqueryPath}
<script type="text/javascript" src="${pageContext.request.contextPath}/script/heaconsole/script/editapp2.js"></script>
</head>
<body>
<div class="title">资源信息</div>
	<form action="${pageContext.request.contextPath }/heaAppManageAction.hea" method="post">
		<input type="hidden" name="action" value="">
	 <table class="tab_2">
			<tr>
				<td class="td_1" width="100px">logo信息：</td>
				<td>
					<table class="tab" >
						<tr class="tab_title">
						  <td >选择</td>
						  <td>logo名称</td>
						  <td>logo地址</td>
						  <td>操作项</td>
			 			</tr>
			 			<c:forEach items="${logoInfos}" var="logoInfo">
							<tr class="tab_trcurrent_1" >
								<td>
									
									<input type="radio" name="logoInfo" value="${logoInfo.regId }">
								</td>
								<td>${logoInfo.resName }</td>
								<td>${logoInfo.resPath }</td>
								<td>
									<a class="edit" href="#">[编辑]</a>
									<a class="delete" href="#">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
				</td>
	            <td class="star">*</td>
				<td><input type="button" value="添加" onclick="addLogoInfo('${appId}')" class="btn"/></td>
			</tr>
			<tr>
				<td class="td_1" width="100px">横幅信息：</td>
				<td>
					<table class="tab" >
						<tr class="tab_title">
						  <td >选择</td>
						  <td>横幅名称</td>
						  <td>横幅地址</td>
						  <td>操作项</td>
			 			</tr>
			 			<c:forEach items="${banners}" var="banner">
							<tr class="tab_trcurrent_1" >
								<td>
									<input type="radio" name="banner" id="yes" value="${banner.regId }">
								</td>
								<td>${banner.resName }</td>
								<td>${banner.resPath }</td>
								<td>
									<a class="edit" href="#">[编辑]</a>
									<a class="delete" href="#">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
				</td>
	            <td class="star">*</td>
	            <td><input type="button" value="添加" onclick="addBanner('${appId}')" class="btn"/></td>
			</tr>
			<tr>
				<td class="td_1" width="100px">主题信息：</td>
				<td>
					<table class="tab" >
						<tr class="tab_title">
						  <td >选择</td>
						  <td>主题名称</td>
						  <td>主题地址</td>
						  <td>操作项</td>
			 			</tr>
			 			<c:forEach items="${themes}" var="theme">
							<tr class="tab_trcurrent_1" >
								<td><input type="radio" name="themePath"/></td>
								<td>${theme.resName }</td>
								<td>${theme.resPath }</td>
								<td>
									<a class="edit" href="#">[编辑]</a>
									<a class="delete" href="#">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
				</td>
				<td class="star">*</td>
				<td><input type="button" value="添加" onclick="addTheme('${appId}')" class="btn"/></td>
			</tr>
			<tr>
				<td>系统信息状态：</td>
				<td>
					<input type="radio" name="appManage.appStatus" id="active" value="01"><label for="active">使用</label>
					<input type="radio" name="appManage.appStatus" id="stop" value="02"><label for="stop">停用</label>
				</td>
			</tr>
			<tr>
				<td  colspan="4" class="td_2" >
					<input class="btn"  type="button" value="上一步"/>
					<input class="btn"  type="button" value="保存"/>
					<input class="btn"  type="button" value="完成"/>
					<input class="btn"  type="button" value="返回"/>
				</td>
			</tr>
		</table>
		
		</form>
</body>
</html>