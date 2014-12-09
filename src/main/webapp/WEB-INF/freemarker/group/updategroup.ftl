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
			alert("组名称不能为空");
			return false;
		}
		if($("#orderNum").val().length<=0){
			alert("排序号不能为空");
			return false;
		}
		if($("#code").val().length<=0){
			alert("组标识不能为空");
			return false;
		}
		if($("#pid").val().length<=0){
			alert("上级组不能为空");
			return false;
		}
		var url="<@spring.url "/group/confirmationgroup.nsf"/>?id=${groupChildren.uuid}&type=update";
		$.post(url,{code:$("#code").val(),name:$("#name").val()},function(data){
			data=checkAjaxData(data);
			if(data=="false"){
				$.post($("#updategroupform").attr("action"),$("#updategroupform").serialize(),function(data){
					window.parent.location.reload();
				});
			}else{
				$("#confirmationdept").html("已经存在!");
				$("#name").focus();
				return false;
			}
		});
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
data=checkAjaxData(data).replace(/,$/,"");
	        $("#jsgroupAreName").val(data);
	       });
       }
  	 });
  });
  $(function(){
     $("#pidgroup").click(function(){
     var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#pid").val()+'&mygroupuuid=${groupChildren.uuid}&flag=${superstatus?string}&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#pid").val(returnVal);
	  	   var url="<@spring.url "/publicgroupdata.nsf"/>"
	       $.post(url,{groupuuids:returnVal},
	       function(data){
data=checkAjaxData(data).replace(/,$/,"");
	        $("#groupname").val(data);
	       });
       }
  	 });
  });
  
$(function(){
     $("#grouplog").click(function(){
     	$("#Table22").show();
		 document.getElementById('underframe').height="1px";
    	 document.getElementById('Table23').action="<@spring.url "/audit/resourceLog.nsf"/>";
    	 $("#Table23").submit();
    	 });
  });
       $(function(){
     $("#grouplogclose").click(function(){
     		$("#Table22").hide();
    	 });
  });
    function bk(){
      window.location.href='<@spring.url "/group/content.nsf"/>?id=${groupChildren.uuid}';
  }
  
//]]>
</script>
<form action="<@spring.url "/group/updategroupfrom.nsf" />" method="post"  id="updategroupform">
<table width="100%" align="center">
		<tr>
			<td>
			<table cellspacing="1" cellpadding="0" width="100%" border="0">
				<tr>
					<td align="right" width="45%">组标识：</td>
					<td align="left">
					<input disabled value="${groupChildren.code!""}"/></td><td width="50%" align="left"></td>
					<input name="code" id="code" value="${groupChildren.code!""}" type="hidden"></td><td width="50%" align="left"></td>
				</tr>
				<tr>
					<td width="45%" align="right">组名称：</td>
					<td align="left"><input name="name" id="name" onkeypress="checkEventName();" onkeyup='this.value=this.value.replace(/\s+/g,"");' value="${groupChildren.name}" maxlength="30" /></td><td  width="50%" align="left"><font id="confirmationdept" color="#FF0000" >*</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">排序号：</td>
					<td align="left">
					<input name="orderNum" id="orderNum" value="${groupChildren.order}" maxlength="5" id="orderNum" onkeypress="checkEventNUM();"/></td><td  width="50%" align="left"><font id="" color="#FF0000" >*</font></td>
				</tr>
				<#if appgroup="1">
				<tr>
					<td align="right" width="45%">是否默认加载同步：</td>
					<td align="left">
					<input type="checkbox" name="appgrouptype" id="appgrouptype" <#if groupChildren.appGroupType??&groupChildren.appGroupType="1">checked</#if>/>
					</td><td width="50%" align="left"></td>
				</tr>
				</#if>
				<tr>
				<td width="45%" align="right">上级组：</td>
				<#assign parentGroup=uumService.getParentsGroupsByGroup(groupChildren)>
			      <td align="left">
			        <input name="groupname" id="groupname" value="<#if parentGroup??><#list parentGroup as grouplist>${grouplist.name}<#if grouplist_has_next>,</#if></#list></#if>" maxlength="10" disabled /></td><td  width="50%" align="left">
				    <input name="pid" type="hidden" id="pid" value="<#if parentGroup??><#list parentGroup as grouplist>${grouplist.uuid}<#if grouplist_has_next>,</#if></#list></#if>" />
				    <input  type="button" id="pidgroup"  value="选择" class="button" /> </td>
				</td></tr>
					<TR>
						<TD align="right"  >可管理该组的组</TD>
						<TD align=left  colspan="3"  >
						<textarea name="jsgroupAreName" id="jsgroupAreName" rows="5" style="width:70%"
				         value="" disabled><#if  groupgroup ?? ><#list groupgroup as grouplist>${grouplist.name}<#if grouplist_has_next>,</#if></#list></#if></textarea>
						<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="<#if  groupgroup ?? ><#list groupgroup as grouplist><#compress>${grouplist.uuid},</#compress></#list></#if>" style="width:70%">
						<input type="Button" id="jssearchgroup" value="选择"  class="button" /></TD>
		          </TR>
				<#if appgroup="0">
		<#list attlist as attribute1>
		<#assign attributetype=attribute1.type>
		<TR>
			<TD align="right" >${attribute1.type.name}</TD>
		    <TD align="left" colspan="2">
				<#switch attributetype.pageType >
				<#case "select" >
					<select name="${attributetype.id}" id="${attributetype.id}" style="width=50%" >
						<#list attributetype.candidateItems as a0>
							<option value="${a0.value}" <#if attribute1.value?exists && attribute1.value==a0.value>selected</#if> >${a0.caption}</option>
						</#list>
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
					<#list attribute1.type.candidateItems as a1>
						<input type="radio" id="${a1.value}" value="${a1.value}" name="${attribute1.type.id}" <#if attribute1.value?exists && attribute1.value==a1.value>checked="checked"</#if> /><label for="${a1.value}">${a1.caption}</label>
					</#list>
					<#break>
				<#default>
					<input id="${attribute1.type.id}" name="${attribute1.type.id}" onkeypress="checkEvent();" value="<#if attribute1.value?exists>${attribute1.value}</#if>" style="width:70%" maxlength="50"  />
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
					 <input name="id" type="hidden" id="id" value="${groupChildren.uuid}" />
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
	   <iframe name="underframe" data-app="uum" id="underframe" src="" frameborder="0" width="100%" scrolling=no></iframe>
	   </td></tr></tbody>
    </table>
</form>
<form id="Table23" target="underframe" method="post" >
<input type="hidden" name="uuid" value="${groupChildren.uuid}">
<input type="hidden" name="page" value="1">
<input type="hidden" name="pagesize" value="3">
</form>
</body>
</html>