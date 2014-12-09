<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
</head>
<body >
<script type="text/javascript">
//<![CDATA[
$(function(){
	$("#Submit").click(function(){
		$("#name").val($.trim($("#name").val()));
		$("#orderNum").val($.trim($("#orderNum").val()));
		$("#code").val($.trim($("#code").val()));
		if($("#name").val().length==0){
			alert("组名称不能为空");
			$("#name").focus();
			return false;
		}else{
			if($("#orderNum").val().length==0){
				alert("排序号不能为空");
				$("#orderNum").focus();
				return false;
			}else{
				if($("#code").val().length==0){
					alert("组标识不能为空");
					$("#code").focus();
					return false;
				}else{
					var url="<@spring.url "/group/confirmationgroup.nsf"/>?id=${groupChildren.uuid}&type=add";
					$.post(url,{code:$("#code").val(),name:$("#name").val()},function(data){
						data=checkAjaxData(data);
						switch (data){
						case "both":
							$("#confirmationdeptname").html("组名称已经存在!");
							$("#confirmationdept").html("组标识已经存在!");
							$("#name").focus();
							return false;
							break
						case "code":
							$("#confirmationdeptname").html("*");
							$("#confirmationdept").html("组标识已经存在!");
							$("#code").focus();
							return false;
							break
						case "name":
							$("#confirmationdeptname").html("组名称已经存在!");
							$("#confirmationdept").html("*");
							$("#name").focus();
							return false;
							break
						case "false":
							$("#confirmationdeptname").html("*");
							$("#confirmationdept").html("*");
							//$("#addgroupform").submit();
							//window.parent.parent.parent.location.reload();
							$.post("<@spring.url "/group/creategroup.nsf"/>",$("#addgroupform").serialize(),function(data){
								if(data=='false'){
									alert("创建失败,请联系管理员!");
									return false;
								}else{
									alert("创建成功!");
									window.parent.location.reload();
								}
								return false;
							});
							return false;
						default:
							break;
						}
					});
				}
			}
		}
	});
});
$(function(){
	$("#Submitadmin").click(function(){
		$("#suname").val($.trim($("#suname").val()));
		$("#suorderNum").val($.trim($("#suorderNum").val()));
		$("#sucode").val($.trim($("#sucode").val()));
		if($("#suname").val().length==0){
			alert("组名称不能为空");
			$("#suname").focus();
			return false;
		}else{
			if($("#suorderNum").val().length==0){
				alert("排序号不能为空");
				$("#suorderNum").focus();
				return false;
			}else{
				if($("#sucode").val().length==0){
					alert("组标识不能为空");
					$("#sucode").focus();
					return false;
				}else{
					var url="<@spring.url "/group/confirmationgroup.nsf"/>?type=add";
					$.post(url,{id:$("#suid").val(),code:$("#sucode").val(),name:$("#suname").val()},function(data){
						data=checkAjaxData(data);
						switch (data){
						case "false":
							$("#suconfirmationdeptname").html("*");
							$("#suconfirmationdept").html("*");
							$.post("<@spring.url "/group/creategroup.nsf"/>",$("#addgroupformadmin").serialize(),function(data){
								if(data=='false'){
									alert("创建失败,请联系管理员!");
									return false;
								}else{
									alert("创建成功!");
									window.parent.location.reload();
								}
								return false;
							});
							return false;
							break
						case "both":
							$("#suconfirmationdeptname").html("组名称已经存在!");
							$("#suconfirmationdept").html("组标识已经存在!");
							$("#suname").focus();
							return false;
							break
						case "code":
							$("#suconfirmationdeptname").html("*");
							$("#suconfirmationdept").html("组标识已经存在!");
							$("#sucode").focus();
							return false;
							break
						case "name":
							$("#suconfirmationdeptname").html("组名称已经存在!");
							$("#suconfirmationdept").html("*");
							$("#suname").focus();
							return false;
							break
						default:
							break
						}
					});
				}
			}
		}
	});
});
$(function(){
	$("#jssearchgroup").click(function(){
	var date=new Date;
		var time=parseInt(date.getSeconds());
		var url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#jsgroupuuid").val()+'&mygroupuuid=no&flag=${superstatus?string}&time='+time;
		var returnVal;
		returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');
		if(returnVal!=null){
			$("#jsgroupuuid").val(returnVal);
			var url="<@spring.url "/publicgroupdata.nsf"/>"
			$.post(url,{groupuuids:returnVal},function(data){
				data=checkAjaxData(data).replace(/,$/,"");
				$("#jsgroupAreName").text(data);
				fitheight(window);
			});
		}
	});
});

$(function(){
	$("#sujssearchgroup").click(function(){
	var date=new Date;
		var time=parseInt(date.getSeconds());
		var url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#sujsgroupuuid").val()+'&mygroupuuid=no&flag=${superstatus?string}&time='+time;
		var returnVal;
		returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');
		if(returnVal!=null){
			$("#sujsgroupuuid").val(returnVal);
			var url="<@spring.url "/publicgroupdata.nsf"/>"
			$.post(url,{groupuuids:returnVal},function(data){
				data=checkAjaxData(data).replace(/,$/,"");
				$("#sujsgroupAreName").text(data);
				fitheight(window);
			});
		}
	});
});
$(function(){
	$("#restgroup").click(function(){
		$("#jsgroupuuid").val("");
		$("#jsgroupAreName").text("无");
	});
});
$(function(){
	$("#surestgroup").click(function(){
		$("#sujsgroupuuid").val("");
		$("#sujsgroupAreName").text("无");
	});
});
//]]>
</script>
<table><tr><td>
<form action="<@spring.url "/group/addgroupform.nsf" />" method="post"  id="addgroupform">
<table width="600" align="left">
		<tr>
			<td>
			<table cellspacing="1" cellpadding="0" width="100%" border="0">
				<tr>
					<td align="right" width="45%">组标识：</td>
					<td align="left">
					<input name="code" id="code" value="" onkeyup="matchEnChNum_(this);" maxlength="25" onpaste="return !clipboardData.getData('text').match(/\D/)"/></td><td width="50%" align="left"><font id="confirmationdept" color="#FF0000">*</font></td>
				</tr>
				<tr>
					<td width="45%" align="right">组名称：</td>
					<td align="left"><input name="name" id="name" onkeyup="matchEnChNum_(this);" maxlength="20" onpaste="return !clipboardData.getData('text').match(/\D/)"/></td><td  width="50%" align="left"><font id="confirmationdeptname" color="#FF0000">*</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">排序号：</td>
					<td align="left">
					<input name="orderNum" id="orderNum" value="0" maxlength="5" onkeyup="matchNum(this);" onpaste="return !clipboardData.getData('text').match(/\D/)"/></td><td  width="50%" align="left"><font id="" color="#FF0000">*</font></td>
				</tr>
				<#if appgroup="1">
				<tr>
					<td align="right" width="45%">加载应用系统同步：</td>
					<td align="left" colspan="2">
					<input type="checkbox" name="appgrouptype" id="appgrouptype" onpaste="return !clipboardData.getData('text').match(/\D/)"/><font color="red">*该属性控制应用系统的同步信息是否同步到ED中</font>
					</td>
				</tr>
				</#if>
				
				<tr height="24px">
				<td width="45%" align="right">上级组：</td>
			      <td align="left">
			        <span>${groupChildren.name}</span></td><td width="50%" align="left">
				    <input name="id" type="hidden" id="id" value="${groupChildren.uuid}" />
				</td></tr>
				
				<TR>
					<TD align="right"  >可管理该组的组：</TD>
					<TD align=left  colspan="3"  >
					    <span name="jsgroupAreName" id="jsgroupAreName" >无</span>
						<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="" style="width:70%">
						<input type="Button" id="jssearchgroup" value="选择"  class="button" /></TD>
				</TR>
				<#if attributelist?exists>
						<#list attributelist as attribute0>
		<TR class='tb1' <#if attribute0.id="dataCameFrom">style="display:none"</#if>>
			<TD align="right">${attribute0.name}</TD>
			<TD align=left colspan="3">
				<#switch attribute0.pageType >
				   <#case "select" >
			          <select  name="${attribute0.id}" id="${attribute0.id}" style="width=50%" > 
			                <#if attribute0.candidateItems ??>
			             	<#list attribute0.candidateItems as a0>
							  <option value="${a0.value}" <#if a0.isDefault??&&a0.isDefault>selected</#if>>${a0.caption}</option>
						    </#list>
						    </#if>
					</select>
			       <#break>
			        <#case "checkbox" >
			        		<#--if attribute0.candidateItems ??>
			        		<#list attribute0.candidateItems as a0>
							  <input type="checkbox" id="${a0.value}" value="${a0.value}" name="${attribute0.id}" <#if a0.isDefault??&&a0.isDefault>checked</#if>/><label for="${a0.value}">${a0.value}</label>
						    </#list>
						    </#if-->
			       <#break>
			       <#case "radio" >
			        		<#if attribute0.candidateItems ??>
			        		<#list attribute0.candidateItems as a0>
							  <input type="radio" id="${a0.value}" value="${a0.value}" name="${attribute0.id}" <#if a0.isDefault??&&a0.isDefault>checked</#if>/><label for="${a0.value}">${a0.caption}</label>
						    </#list>
						    </#if>
			       <#break>
			         <#case "textarea" >
			           <textarea name="${attribute0.id}" id="${attribute0.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)" value=""></textarea>&nbsp;&nbsp;长度不能超过100个字符
			       <#break>
					 <#default>
					 <#if attribute0.id!="dataCameFrom">
					  <input id="${attribute0.id}" name="${attribute0.id}" <#if attribute0.rule?exists && attribute0.rule!="0"> onblur="checkType(this);"</#if> onkeypress="" attvalue="${attribute0.value?if_exists}" style="width:70%" value="" maxlength="<#if attribute0.length?exists>${attribute0.length}<#else>100</#if>"  />
					  <label></label><!--input id="${attribute0.id}" name="${attribute0.id}" onkeypress="checkEvent();" value="" style="width:70%" maxlength="50"/-->
					<#else>
					  <input id="${attribute0.id}" name="${attribute0.id}" value="1" style="width:70%"/>
					  </#if>
			   </#switch>
			</TD>
		</TR>
	 </#list></#if>
				<TR>
					<td align="right" width="45%">&nbsp;</td>
					<td align="left">
					 <input type="button" id="Submit" value="确定"  class="button" /> 
					 <input  type="reset"   value="重置" class="button" id="restgroup"/> 
					 <input  type="button"   value="返回" onclick="history.back()" class="button" />
					 </td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
</td></tr>
				<#if appgroup="1">
				<#else>
<tr><td>
<form action="" method="post"  id="addgroupformadmin">
<#if  uumService.isUserinSuperGroup(uumService.getLoginUser()) >
<table cellspacing="1" cellpadding="0" width="600" border="0">
<tr><td colspan="3">建立一级角色</td></tr>
<tr><td colspan="3"><hr></td></tr>
		<tr>
			<td>
			<table cellspacing="1" cellpadding="0" width="100%" border="0">
			<tr>
				<td width="45%" align="right">父级组：</td>
				<td align="left">
					根组
				</td></tr>
				<tr>
					<td align="right" width="45%">组标识：</td>
					<td align="left">
					<input name="code" id="sucode" value="" onkeyup="matchEnChNum_(this);" onpaste="return !clipboardData.getData('text').match(/\D/)" maxlength="25"/></td><td width="50%" align="left"><font id="suconfirmationdept" color="#FF0000" >*</font></td>
				</tr>
				<tr>
					<td width="45%" align="right">组名称：</td>
					<td align="left"><input name="name" id="suname" value="" onkeyup="matchEnChNum_(this);" onpaste="return !clipboardData.getData('text').match(/\D/)" maxlength="20" /></td><td  width="50%" align="left"><font id="suconfirmationdeptname" color="#FF0000" >*</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">排序号：</td>
					<td align="left">
					<input name="orderNum" id="suorderNum" value="0" maxlength="5" onkeyup="matchNum(this);" onpaste="return !clipboardData.getData('text').match(/\D/)"/></td><td  width="50%" align="left"><font id="" color="#FF0000" >*</font></td>
				</tr>
				<TR>
					<TD align="right"  >可管理该组的组：</TD>
					<TD align=left  colspan="3"  >
						<span name="jsgroupAreName" id="sujsgroupAreName" style="width:70%" >无</span>
						<input name="jsgroupuuid" id="sujsgroupuuid" type="hidden" value="" style="width:70%">
						<input type="Button" id="sujssearchgroup" value="选择"  class="button" /></TD>
				</TR>
				<#if attributelist?exists>
		<#list attributelist as attribute0>
		<TR class='tb1' <#if attribute0.id="dataCameFrom">style="display:none"</#if>>
			<TD align="right">${attribute0.name}</TD>
			<TD align=left colspan="3">
				<#switch attribute0.pageType >
				   <#case "select" >
			          <select  name="${attribute0.id}" id="${attribute0.id}" style="width=50%" > 
			                <#if attribute0.candidateItems ??>
			             	<#list attribute0.candidateItems as a0>
							  <option value="${a0.value}" <#if a0.isDefault??&&a0.isDefault>selected</#if>>${a0.caption}</option>
						    </#list>
						    </#if>
					</select>
			       <#break>
			        <#case "checkbox" >
			        		<#--if attribute0.candidateItems ??>
			        		<#list attribute0.candidateItems as a0>
							  <input type="checkbox" id="${a0.value}" value="${a0.value}" name="${attribute0.id}" <#if a0.isDefault??&&a0.isDefault>checked</#if>/><label for="${a0.value}">${a0.value}</label>
						    </#list>
						    </#if-->
			       <#break>
			       <#case "radio" >
			        		<#if attribute0.candidateItems ??>
			        		<#list attribute0.candidateItems as a0>
							  <input type="radio" id="${a0.value}" value="${a0.value}" name="${attribute0.id}" <#if a0.isDefault??&&a0.isDefault>checked</#if>/><label for="${a0.value}">${a0.caption}</label>
						    </#list>
						    </#if>
			       <#break>
			         <#case "textarea" >
			           <textarea name="${attribute0.id}" id="${attribute0.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)" value=""></textarea>&nbsp;&nbsp;长度不能超过100个字符
			       <#break>
					 <#default>
					 <#if attribute0.id!="dataCameFrom">
					  <input id="${attribute0.id}" name="${attribute0.id}" <#if attribute0.rule?exists && attribute0.rule!="0"> onblur="checkType(this);"</#if> onkeypress="" attvalue="${attribute0.value?if_exists}" style="width:70%" value="" maxlength="<#if attribute0.length?exists>${attribute0.length}<#else>100</#if>"  />
					  <label></label><!--input id="${attribute0.id}" name="${attribute0.id}" onkeypress="checkEvent();" value="" style="width:70%" maxlength="50"/-->
					<#else>
					  <input id="${attribute0.id}" name="${attribute0.id}" value="1" style="width:70%"/>
					  </#if>
			   </#switch>
			</TD>
		</TR>
	 </#list></#if>
				<tr>
					<td align="right" width="45%">&nbsp;</td>
					<td align="left">
					<input name="id" type="hidden" id="suid" value="${uumService.getRootGroup().getUuid()}" />
					 <input type="button" id="Submitadmin" value="确定" class="button" /> 
					 <input type="reset" value="重置" class="button" id="surestgroup"/> 
					 </td>
				</tr>
				
			</table>
			</td>
		</tr>
	</table>
 </#if>
</form>

</td></tr>
</#if>
</table>
</body>
</html>