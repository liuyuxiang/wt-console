<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>添加记录</title>
   ${componentConfig.jqueryPath}
	<link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />
	<script>
		$(function(){
			$("#systemCodeSaveForm").submit(function(){
				 var systemCode_rptCode=$("#systemCode_rptCode").val();
				 var systemCode_rptName=$("#systemCode_rptName").val();
				 var systemCode_rptValue=$("#systemCode_rptValue").val();
				if(systemCode_rptCode==''){
					alert("编码值不能为空!");
					return false;
				}
				if(systemCode_rptName==''){
					alert("注册名称不能为空!");
					return false;
				}
				if(systemCode_rptValue==''){
					alert("注册值不能为空!");
					return false;
				}
				return true;
			});
		});
	</script>
  </head>
 <body>
<html:form action="heaSystemCodeAction.hea?action=save" method="post" styleId="systemCodeSaveForm">

<input type="hidden" name="systemCode.parentId" value="${param.pid}"/>
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
