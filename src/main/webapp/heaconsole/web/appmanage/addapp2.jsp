<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加应用第二步</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/common/component/dialog/css/default.css">
${componentConfig.jqueryPath}
<script type="text/javascript" src="${pageContext.request.contextPath}/common/component/dialog/script/lhgcore.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/component/dialog/script/lhgdialog.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/heaconsole/script/appmanage/addapp2.js"></script>
</head>
${message }
<body>
<div class="title">资源信息</div>
	<form action="${pageContext.request.contextPath }/heaAppManageAction.hea" method="post">
		<input type="hidden" name="action" value="saveApp2">
		<input type="hidden" id="appId" name="appManage.appId" value="${am.appId }"/>
		<input type="hidden" id="indexId" name="id"	value="${id }">
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
									<input type="hidden" id="logoSystemId" name="appInfos.logoSystemId" value="${appLogo.id }"/>
									<input type="radio" name="appInfos.logoId" class="logoinfo" ${appLogo.regValue eq logoInfo.resId ?'checked=checked':'' } value="${logoInfo.resId }">
								</td>
								<td>${logoInfo.resName }</td>
								<td>${logoInfo.resPath }</td>
								<td>
									<a class="edit editresource" sid="${logoInfo.resId }" resType="${logoInfo.resType }" href="###">[编辑]</a>
									<a class="delete deletelogo" sid="${logoInfo.resId }" resType="${logoInfo.resType }" href="###">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
				</td>
	            <td class="star">*</td>
				<td><input type="button" value="添加" id="addLogoInfo" class="btn"/></td>
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
									<input type="hidden" name="appInfos.bannerSystemId" id="bannerSystemId" value="${appBanner.id }"/>
									<input type="radio" name="appInfos.bannerId" class="bannerInfo" id="yes" value="${banner.resId }" ${appBanner.regValue eq banner.resId ?'checked=checked':'' }>
								</td>
								<td>${banner.resName }</td>
								<td>${banner.resPath }</td>
								<td>
									<a class="edit editresource" sid="${banner.resId }" resType="${banner.resType }"  href="###">[编辑]</a>
									<a class="delete deletelogo" sid="${banner.resId }" resType="${banner.resType }" href="###">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
				</td>
	            <td class="star">*</td>
	            <td><input type="button" id="addBannerInfo" value="添加" class="btn"/></td>
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
								<td>
								<input type="hidden" name="appInfos.themeSystemId" id="themeSystemId" value="${appTheme.id }">
								<input type="radio" name="appInfos.themeId" value="${theme.themeCode }" class="themeInfo" ${appTheme.regValue eq theme.themeCode ?'checked=checked':'' }/>
								</td>
								<td>${theme.themeName }</td>
								<td>${theme.themePath }</td>
								<td>
									<a class="edit edittheme" tid="${theme.themeCode}" href="###">[编辑]</a>
									<a class="delete deletetheme" tid="${theme.themeCode}" href="###">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
				</td>
				<td class="star">*</td>
				<td><input type="button" value="添加" id="addThemeInfo"  class="btn"/></td>
			</tr>
			<tr>
				<td>系统信息状态：</td>
				<td>
					<input type="radio" name="appManage.appStatus" id="active" value="01" ${am.appStatus eq '01' ?' checked=checked ':'' }><label for="active">使用</label>
					<input type="radio" name="appManage.appStatus" id="stop" value="02" ${empty am.appStatus ? "checked=checked" : ""} ${am.appStatus eq '02' ?' checked=checked ':'' }><label for="stop">停用</label>
				</td>
			</tr>
			<tr>
				<td  colspan="4" class="td_2" >
					<input class="btn"  type="button" onclick="window.location='heaAppManageAction.hea?action=toUpdate&id=${am.appId}&indexId=${id }'" value="上一步"/>
				<!-- 
					<input class="btn"  type="button" value="保存"/>
				-->
					<input class="btn"  type="submit" value="完成" onclick="window.open('${ctx}/heaAppManageAction.hea?action=appList','_self')"/>
					<!-- -->
					<input class="btn"  type="button" onclick="window.location='${ctx }/heaAppManageAction.hea?action=appList&treeNodeId=${indexId }'" value="返回"/>
				</td>
			</tr>
		</table>
		</form>
	<div id="dialog"></div>
</body>
</html>