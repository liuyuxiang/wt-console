<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'bannerlist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/heaconsole/css/subcss.css">
	${componentConfig.jqueryPath}
	<script type="text/javascript" src="${pageContext.request.contextPath}/heaconsole/script/appmanage/bannerlist.js"></script>
  </head>
  
  <body>
  <input type="hidden" id="appId" value="${param.appId}">
  <div class="title">横幅信息</div>
  <div class="title_1"><input type="button" id="addBannerInfo" value="添加" class="btn"/></div>
    <table class="tab" >
						<tr class="tab_title">
						  <%--<td >选择</td>
						  --%><td>横幅名称</td>
						  <td>横幅地址</td>
						  <td>操作项</td>
			 			</tr>
			 			<c:forEach items="${resources.data}" var="banner">
							<tr class="tab_trcurrent_1" >
								<%--<td>
									<input type="hidden" name="appInfos.bannerSystemId" id="bannerSystemId" value="${appBanner.id }"/>
									<input type="radio" name="appInfos.bannerId" class="bannerInfo" id="yes" value="${banner.resId }" ${appBanner.regValue eq banner.resId ?'checked=checked':'' }>
								</td>
								--%><td>${banner.resName }</td>
								<td>${banner.resPath }</td>
								<td>
									<a class="edit editresource" sid="${banner.resId }" resType="${banner.resType }" href="###">[编辑]</a>
									<a class="delete deletelogo" sid="${banner.resId }" resType="${banner.resType }" href="###">[删除]</a>						
								</td>
					 		</tr>
			 			</c:forEach>
					</table>
  </body>
</html>
