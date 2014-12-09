<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>add.jsp</title>
    <link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/common/jquery-1.4.2.js"></script>
  </head>
 <body>
<html:form action="heaSystemCodeAction.hea?action=update" method="post" >
<input name="systemCode.id" value="${systemCode.id}" type="hidden"/>
<div class="content_2">
 <div class="box_4">
 	<div class="tag_container"><div class="title">报表基本信息</div></div>
	<table class="tab_4">
		<tr>
			<td class="td_3">编码：</td>
			<td>
		 		<input id="systemCode_rptCode" name="systemCode.regCode" value="${systemCode.regCode}" class="ipt_3" heavalidation="rptCode" />
			</td>
			<tr>
			<td class="td_3">注册类型：</td>
			<td>
				<select class="ipt_3" name="systemCode.regType">
				  		<option value="0">线性关系</option>
				  		<option value="1">树状关系</option>				  	
			  	</select>
			 </td>
			 </tr>
		</tr>
		<tr>
			<td class="td_3">注册名称：</td>
			<td>
				<input id="systemCode_rptName" name="systemCode.regName" value="${systemCode.regName}" class="ipt_3" heavalidation="rptName" />*
			 </td>
			
		</tr>
		<tr>
			<td class="td_3">注册值：</td>
			<td><input id="systemCode_rptValue" name="systemCode.regValue" value="${systemCode.regValue}" class="ipt_3" heavalidation="rptValue" />*</td>
		</tr>
		<tr>
			<td class="td_3" colspan="2"  >
				 	<input type="reset"  class="btn btnPopDiv"  value="重置"/>
				 	<input type="submit"  class="btn"  value="保存编码" />
				 <input type="button"  class="btn" id='btnCancel'  value="返回" onclick="window.open('${ctx}/heaSystemCodeAction.hea?action=loadPage','_self')"/>
			</td>
		</tr>
	</table>
 </div>
</div> 
 ${message}
</html:form>
</body>
</html>
