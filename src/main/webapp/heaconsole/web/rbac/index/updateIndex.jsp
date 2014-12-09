<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加菜单项</title>
<link id="themePath" rel="Stylesheet" href="${ctx}/heaconsole/css/subindex.css" />
<script type="text/javascript" src="${ctx}/common/script/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctx}/heaconsole/script/rbac/index/updateIndex.js"></script>
${componentConfig.validationPath}
</head>
<body>
	<html:form action="/heaIndexAction.hea" onsubmit="return indexUpdate();">
		<input type="hidden" name="action" value="updateIndex">
		<input type="hidden" name="creatDate" value="${index.createTime }" />
		<input type="hidden" name="index.appId" value="${empty param.appId ? 'jixiaoappid' : param.appId}" />

		<html:hidden property="index.indexuuid" />
		<html:hidden property="index.indexicon" />
		<html:hidden property="index.indexlevel" />
		<html:hidden property="index.indextype" />
		<html:hidden property="index.hasChild" />
		<c:if test="${indexForm.index.indexuuid == null}">
			<html:hidden property="index.parentindexuuid" value="${parentUuid}" />
		</c:if>
		<c:if test="${indexForm.index.indexuuid != null}">
			<html:hidden property="index.parentindexuuid" />
		</c:if>
		<table class="tab_4">
			<tr>
				<td align="right" width="80px">名称：</td>
				<td>
				<input type="text" id="indexName" class="ipt_8" value="${index.indexname }" name="index.indexname" autocomplete="off"  >
				</td>
			</tr>
			<tr>
				<td align="right">目标：</td>
				<td>
					<label for="target0">主窗口</label><input id="target0" type="radio" value="mainContent" name="index.target" ${empty index.target ? "checked=checked" : ""} ${index.target eq 'mainContent' ? "checked=checked" : ""} >
					<label for="target1">新窗口</label><input id="target1" type="radio" value="_blank" name="index.target" ${index.target eq '_blank' ? "checked=checked" : ""} >
				</td>
			</tr>
			<tr>
				<td align="right">是否启用：</td>
				<td><html:select styleClass="ipt_2_1" property="index.way">
						<option value="1" ${index.way== '1'?'selected=selected':''}>是</option>
						<option value="0" ${index.way== '0'?'selected=selected':''}>否</option>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td align="right">排序号：</td>
				<td><html:text styleClass="ipt_8" property="index.indexorder"></html:text></td>
			</tr>
			<tr>
				<td align="right" valign="top">URL：</td>
				<td>
				<textarea id="indexurl" rows="6" heavalidation="indexurl" name="index.indexurl" class="validate[length[0,1000]] ipt_8" autocomplete="off">${index.indexurl }</textarea>
				</td>
			</tr>
			<tr>
				<td align="right" width="80px">Wicket_Class：</td>
				<td>
					<div id="selectDiv">
						<select id="select" name="index.wicketClass" class="ipt_8">
							<option value="com.hirisun.hea.wsc.web.content.FramePage">外部页面</option>
							<option value="">请选择</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right" valign="top">默认图片：</td>
				<td>
				<input type="text" id="indexIconPath" heavalidation="indexIconPath" class="validate[length[0,255]] ipt_8" value="${index.indexIconPath }" name="index.indexIconPath" autocomplete="off"  >
				</td>
			</tr>
			<tr>
				<td align="right" valign="top">选中图片：</td>
				<td>
				<input type="text" id="indexIconOnPath" heavalidation="indexIconOnPath" class="validate[length[0,255]] ipt_8" value="${index.indexIconOnPath }" name="index.indexIconOnPath" autocomplete="off"  >
				</td>
			</tr>
			<tr>
				<td align="right" valign="top">默认样式：</td>
				<td>
				<input type="text" id="styleClass" heavalidation="styleClass" class="validate[length[0,255]] ipt_8" value="${index.styleClass }" name="index.styleClass" autocomplete="off"  >
				</td>
			</tr>
			<tr>
				<td align="right" valign="top">选中样式：</td>
				<td>
				<input type="text" id="styleClassOn" heavalidation="styleClassOn" class="validate[length[0,255]] ipt_8" value="${index.styleClassOn }" name="index.styleClassOn" autocomplete="off"  >
				</td>
			</tr>
			<c:if test="${index.createTime != null}">
			<tr>
				<td align="right">创建时间：</td>
				<td><bean:write property="index.createTime" name="indexForm" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:if>
			<tr>
				<td align="right" valign="top">备注：</td>
				<td>
				<html:textarea styleClass="validate[length[0,255]] ipt_8" rows="4" property="index.description" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="left"><html:submit styleClass="btn">提交</html:submit></td>
			</tr>
		</table>
	</html:form>
</body>
</html>