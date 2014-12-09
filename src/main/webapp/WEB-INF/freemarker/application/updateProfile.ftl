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
  		 if($("#name").val().length<=0){
  		  alert("组中文显示名不能为空");
  		  return false;
  		 }else{
  		  if($("#orderNum").val().length<=0){
  		  alert("组排序号不能为空");
  		  return false;
  		 }else{
  		 if($("#code").val().length<=0){
  		  alert("组名不能为空");
  		  return false;
  		 }else{
  		 if($("#pid").val().length<=0){
  		     alert("上级组不能为空");
  		  }else{
  		   var url="<@spring.url "/application/confirmationgroup.nsf"/>?id=${application.uuid}&type=update";
			       $.post(url,{code:$("#code").val(),name:$("#name").val()},
			       function(data){
data=checkAjaxData(data);
			       if(data=="false"){
			       $("#updategroupform").submit();
		  		     window.parent.location.reload();
			        }else{
			        $("#confirmationdept").html("已经存在!");
			        $("#name").focus();
				     return false;
			        }
			        });
  		   }
  		  }
  		 }
  		}
  	});
  });
  $(function(){
     $("#jssearchgroup").click(function(){
     var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#jsgroupuuid").val()+'&mygroupuuid=no&flag=${superstatus?string}&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#jsgroupuuid").val(returnVal);
	  	   var url="<@spring.url "/publicgroupdata.nsf"/>"
	       $.post(url,{groupuuids:returnVal},
	       function(data){
data=checkAjaxData(data);
	        $("#jsgroupAreName").val(data);
	       });
       }
  	 });
  });
  $(function(){
     $("#pidgroup").click(function(){
     var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#pid").val()+'&mygroupuuid=${application.uuid}&flag=${superstatus?string}&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#pid").val(returnVal);
	  	   var url="<@spring.url "/publicgroupdata.nsf"/>"
	       $.post(url,{groupuuids:returnVal},
	       function(data){
data=checkAjaxData(data);
	        $("#groupname").val(data);
	       });
       }
  	 });
  });
  
$(function(){
     $("#grouplog").click(function(){
     	$("#Table22").show();
		 document.getElementById('underframe').height="1px";
    	 document.getElementById('Table23').action="<@spring.url "/application/grouplog.nsf"/>";
    	 $("#Table23").submit();
    	 });
  });
       $(function(){
     $("#grouplogclose").click(function(){
     		$("#Table22").hide();
    	 });
  });
    function bk(){
      window.location.href='<@spring.url "/application/content.nsf"/>?id=${application.uuid}';
  }
  
//]]>
</script>
<form action="<@spring.url "/application/updategroupfrom.nsf" />" method="post"  id="updategroupform">
<table width="100%" align="center">
		<tr>
			<td>
			<table cellspacing="1" cellpadding="0" width="100%" border="0">
				<tr>
					<td width="45%" align="right">组中文显示名：</td>
					<td align="left"><input name="name" id="name" onkeypress="checkEventName();" value="${application.name}" maxlength="30" /></td><td  width="50%" align="left"><font id="confirmationdept" color="#FF0000" >*</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">组排序号：</td>
					<td align="left">
					<input name="orderNum" id="orderNum" value="${application.order}" maxlength="5" id="orderNum" onkeypress="checkEventNUM();"/></td>
				</tr>
				<tr>
					<td align="right" width="45%">组名称：</td>
					<td align="left">
					<input name="code" id="code" readOnly value="${application.code!""}" onkeypress="checkEventID();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="30"/></td><td width="50%" align="left"><!--font id="confirmationdept" color="#FF0000">*(请使用英文小写字母)</font--></td>
				</tr>
				<#if appgroup="1">
				<tr>
					<td align="right" width="45%">是否默认加载同步：</td>
					<td align="left">
					<input type="checkbox" name="appgrouptype" id="appgrouptype" <#if application.appGroupType??&application.appGroupType="1">checked</#if>/>
					</td><td width="50%" align="left"></td>
				</tr>
				</#if>
				<tr>
				<td width="45%" align="right">上级组：</td>
			      <td align="left">
			        <input name="groupname" id="groupname" value="<#if  uumService.getParentsGroupsByGroup(application)  ??><#list uumService.getParentsGroupsByGroup(application) as grouplist>${grouplist.name},</#list></#if>" maxlength="10" disabled /></td><td  width="50%" align="left">
				    <input name="pid" type="hidden" id="pid" value="<#if  uumService.getParentsGroupsByGroup(application)  ??><#list uumService.getParentsGroupsByGroup(application) as grouplist>${grouplist.uuid},</#list></#if>" />
				    <input  type="button" id="pidgroup"  value="选择" class="button" /> </td>
				</td></tr>
					<TR>
						<TD align="right"  >可管理该组的组</TD>
						<TD align=left  colspan="3"  >
						<textarea name="jsgroupAreName" id="jsgroupAreName" rows="5" style="width:70%"
				         value="" disabled><#if  groupgroup ?? ><#list groupgroup as grouplist><#compress>${grouplist.name},</#compress></#list></#if></textarea>
						<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="<#if  groupgroup ?? ><#list groupgroup as grouplist><#compress>${grouplist.uuid},</#compress></#list></#if>" style="width:70%">
						<input type="Button" id="jssearchgroup" value="选择"  class="button" /></TD>
		          </TR>
				<#if appgroup="0">
		<#list attlist as attribute1>
		<TR>
			<TD align="right" >${attribute1.type.name}</TD>
		    <TD align="left" colspan="2">
				<#switch attribute1.type.pageType >
				<#case "select" >
					<select  name="${attribute1.type.id}" id="${attribute1.type.id}" style="width=50%" >
						<#if attribute1.attValues[0]?exists&&attribute1.attValues[0].getValue()?? >
							<option value="<#list attribute1.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list>" ><#list attribute1.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list></option>
							<#if attribute1.type.candidateItems ??>
								<#list attribute1.type.candidateItems as a1>
									<#list attribute1.attValues as att>
										<#if a1.value != att.getValue() >
											<option value="${a1.value}" >${a1.value}</option>
										</#if> 
									</#list>
								</#list>
							</#if>
						<#else>  
							<#list attribute1.type.candidateItems as a1>
								<option value="${a1.value}" >${a1.value}</option>
							</#list>
						</#if> 
					</select>
					<#break>
				<#case "checkbox" >
						<#--if attribute1.type.candidateItems ??>
							<#list attribute1.type.candidateItems as a1>
								<input type="checkbox" id="${a1.value}" value="${a1.value}" <#if a1.isDefault??&&a1.isDefault>checked</#if> name="${attribute1.type.id+a0_index}"/><label for="${a1.value}">${a1.value}</label>
							</#list>
						</#if-->
					<#break>
				<#case "radio" >
					<#if attribute1.attValues[0]?exists&&attribute1.attValues[0].getValue()?? >
						<input type="radio" id="${attribute1.type.id}" value="${attribute1.attValues[0].getValue()}" checked name="${attribute1.type.id}"/><label for="${attribute1.attValues[0].getValue()}">${attribute1.attValues[0].getValue()}</label>
						<#if attribute1.type.candidateItems ??>
							<#list attribute1.type.candidateItems as a1>
								<#list attribute1.attValues as att>
									<#if att.getValue()!=a1.value>
										<input type="radio" id="${a1.value}" value="${a1.value}" name="${attribute1.type.id}"/><label for="${a1.value}">${a1.value}</label>
									</#if>
								</#list>
							</#list>
						</#if>
					<#else>
						<#if attribute1.type.candidateItems ??>
							<#list attribute1.type.candidateItems as a1>
								<input type="radio" id="${a1.value}" value="${a1.value}" name="${attribute1.type.id}"/><label for="${a1.value}">${a1.value}</label>
							</#list>
						</#if>
					</#if>
					<#break>
				<#case "textarea" >
					<textarea   name="${attribute1.type.id}" id="${attribute1.type.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)"><#list uumService.getStringValuesUnderAttribute(attribute0) as att><#if att.getValue() ??>${att.getValue()}</#if></#list></textarea>&nbsp;&nbsp;长度不能超过100个字符
					<#break>
				<#default>
					<input id="${attribute1.type.id}" name="${attribute1.type.id}" onkeypress="checkEvent();" value="<#list attribute1.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list>" style="width:70%" maxlength="50"  />
				</#switch>
			</TD>
		</TR>
	 </#list>
	 				</#if>
<tr>                 <td align="right" width="45%">&nbsp;</td>
					<td align="left">
					 <input type="button" id="Submit" value="确定"  class="button" /> 
					 <input  type="reset"   value="重置" class="button" /> 
					 <input  type="button"   value="返回" onclick="bk()" class="button" />
					 <input type="button"  value="查看记录" id="grouplog" class="button" />
		             <input type="button"  value="关闭记录" id="grouplogclose" class="button" />
					 <input name="id" type="hidden" id="id" value="${application.uuid}" />
					 </td>
				</tr>
				<tr width="100%">
		
		</tr>
			</table>
			</td>
		</tr>
		
	</table>
	<table style="display:none" cellspacing="1" border="0" align="left" width="100%" class="tableborder1" id="Table22">
	   <tbody><tr><td>
	   <iframe name="underframe" id="underframe" src="" frameborder="0" width="100%" scrolling=no></iframe>
	   </td></tr></tbody>
    </table>
</form>
<form id="Table23" target="underframe" method="post" >
<input type="hidden" name="group" value="${application.uuid}">
<input type="hidden" name="page" value="1">
<input type="hidden" name="pagesize" value="3">
</form>
</body>
</html>