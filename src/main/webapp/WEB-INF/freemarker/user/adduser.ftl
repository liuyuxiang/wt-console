<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script language="javascript"  type="text/javascript" src="<@spring.url "/js/py.js" />"></script>
</head>
<body >
<script type="text/javascript">
//<![CDATA[
 $(function(){
     $("#adduserbutton").click(function(){
  		 if($("#name").val().length<=0){
  		  alert("姓名不能为空");
  		  return false;
  		 }else{
  		  if($("#userid").val().length<=0){
  		  alert("用户标识不能为空");
  		  return false;
  		 }else{
  		 if($("#orderNum").val().length<=0){
  		  alert("排序号不能为空");
  		  return false;
  		 }else{
  		 if($("#deptuuid").val().length<=0){
  		  alert("所在部门不能为空");
  		  return false;
  		 }else{
  		 if($("#groupuuid").val().length<=0){
  		  alert("用户权限组不能为空");
  		  return false;
  		 }else{
  		 if($("#employeenumber").val()!=null&&$("#employeenumber").val().length==0&&!confirm("确定身份证为空吗？")){
			$("#employeenumber").focus();
			return false;
  		 }else{
  		 if($("#primaryUserId").val().length>0){
  		   var url1="<@spring.url "/user/confirmationuser.nsf"/>?uuid="
  		    $.post(url1,{userid:$("#primaryUserId").val()},
	       function(data){
data=checkAjaxData(data);
	       if(data=="false"){
	          $("#confirmationRelationUser").html("关联用户ID不存在!");
	          $("#primaryUserId").focus();
		      return false;
	        }else{
	          var url="<@spring.url "/user/confirmationuser.nsf"/>?uuid="
	       $.post(url,{userid:$("#userid").val()},
	       function(data){
data=checkAjaxData(data);
	       if(data=="false"){
	          $("#adduserform").submit();
	        }else{
	        $("#confirmationuser").html("用户ID已经存在!");
	        $("#userid").focus();
		    return false;
	        }
	        });
	        }
	        });
  		 }else{
  		 if($("#jsgroupuuid").val().length<=0&&${admin?string}!='true'){
  		  alert("可管理该用户的组不能为空");
  		  return false;
  		 }else{
  		   var url="<@spring.url "/user/confirmationuser.nsf"/>?uuid="
	       $.post(url,{userid:$("#userid").val()},
	       function(data){
data=checkAjaxData(data);
	       if(data=="false"){
	          $("#adduserform").submit();
	        }else{
	        $("#confirmationuser").html("用户ID已经存在!");
	        $("#userid").focus();
		    return false;
	        }
	        });
  		   }
  		  }}}}}}
  		 }
  	});
  });
function isMaxLen(o){  
 var nMaxLen=o.getAttribute? parseInt(o.getAttribute("maxlength")):"";  
 if(o.getAttribute && o.value.length>nMaxLen){  
 o.value=o.value.substring(0,nMaxLen)  
 }  
}
  $(function(){
     $("#searchdept").click(function(){
  		  var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publictree.nsf"/>?type=checkbox&deptuuids='+$("#deptuuid").val()+'&mydeptuuid=no&flag=true&time='+time;   
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	      $("#deptuuid").val(returnVal);
	  	   var url="<@spring.url "/publicdata.nsf"/>"
	       $.post(url,{deptuuid:returnVal},
	       function(data){
data=checkAjaxData(data);
	         $("#deptName").val(data);
	       });
       }
  	 });
  });
  
  $(function(){
     $("#searchdeptS").click(function(){
  		  var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publictree.nsf"/>?type=radio&deptuuids='+$("#deptuuid").val()+'&mydeptuuid=no&flag=&time='+time;   
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	      $("#deptuuid").val(returnVal);
	  	   var url="<@spring.url "/publicdata.nsf"/>"
	       $.post(url,{deptuuid:returnVal},
	       function(data){
data=checkAjaxData(data);
	         $("#deptName").val(data);
	       });
       }
  	 });
  });
  
  $(function(){
     $("#searchmaindept").click(function(){
        var    url='<@spring.url "/publictree.nsf"/>?type=radio&deptuuids='+$("#maindeptuuid").val()+'&mydeptuuid=no';    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	      $("#maindeptuuid").val(returnVal.substring(0,returnVal.length-1));
  	      $("#deptuuid").val(returnVal);
	  	   var url="<@spring.url "/publicdata.nsf"/>"
	       $.post(url,{deptuuid:returnVal},
	       function(data){
data=checkAjaxData(data);
	         $("#maindeptName").val(data.replace(/,/g, ""));
	       });
       }
  	 });
  });
    $(function(){
     $("#searchgroup").click(function(){
     var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#groupuuid").val()+'&checkedPa=true&mygroupuuid=no&flag=false&isGroup=${admin?string}&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#groupuuid").val(returnVal);
	  	   var url="<@spring.url "/publicgroupdata.nsf"/>"
	       $.post(url,{groupuuids:returnVal},
	       function(data){
data=checkAjaxData(data);
	        $("#groupAreName").val(data);
	       });
       }
  	 });
  });
   $(function(){
   var date=new Date;
		var time=parseInt(date.getSeconds());
     $("#jssearchgroup").click(function(){
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#jsgroupuuid").val()+'&mygroupuuid=no&flag=${admin?string}&time='+time;    
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
function changeimg(id){
		var b = 1;
		for(var a=0;a<3;a++){		
			document.getElementById(b++).src="<@spring.url "/style/default/images/tag4.gif" />";
			var d = b++;
			document.getElementById(d).style.backgroundImage="url(<@spring.url "/style/default/images/tag5.gif" />)";
			document.getElementById('s'+d).style.display="none";
			document.getElementById(b++).src="<@spring.url "/style/default/images/tag6.gif" />";
		}
		b=1;
		document.getElementById(--id).src="<@spring.url "/style/default/images/tag11.gif" />";
		var c = ++id;
		document.getElementById(c).style.backgroundImage="url(<@spring.url "/style/default/images/tag21.gif" />)";
		document.getElementById('s'+c).style.display="";
		document.getElementById(++id).src="<@spring.url "/style/default/images/tag31.gif" />";
		
	}
 
    $(function(){
    	if($("#employeenumber")!=null){
    		$("#employeenumber").next().html("*");
    	}
     $("#userBirthday").click(function(){
  		  WdatePicker();
  	});
  });
  
 
 
    function getpy() {
    var userid = getSpell($("#name").val(), "");
    if(15<userid.length){
	 	$("#userid").val(userid.substr(0,15)); 
	 }else{
	 	$("#userid").val(userid); 
	 }
     $("#mail1").val($("#userid").val()+"@hirisun.com");
	}
//]]>
</script>
<form action="<@spring.url "/user/adduserfrom.nsf" />" method="post" name="adduserform" id="adduserform">
<!-- 标签开始 -->
<table width="70%" align="center" border="0" cellSpacing="0" cellPadding="0">
      <tr>
        <td width="1%"  align="right"><img id="1" src="<@spring.url "/style/default/images/tag11.gif" /> "/></td>
        <td width="18%" id="2" align="center" style="cursor:hand" background="<@spring.url "/style/default/images/tag21.gif"/>" onclick="changeimg('2')"><strong>用户基本信息 </strong></td>
        <td width="1%" align="left"><img  id="3" src="<@spring.url "/style/default/images/tag31.gif"/>"/></td>

        <td width="1%"  align="right"><img id="4" src="<@spring.url "/style/default/images/tag4.gif" />"/></td>
        <td width="18%" id="5" align="center" style="cursor:hand" background="<@spring.url "/style/default/images/tag5.gif" />" onclick="changeimg('5')"><strong>用户通讯信息</strong></td>
        <td width="1%"  align="left"><img id="6" src="<@spring.url "/style/default/images/tag6.gif" />" /></td>

        <td width="1%"  align="right"><img id="7" src="<@spring.url "/style/default/images/tag4.gif" />"/></td>
        <td width="18%" id="8" align="center" style="cursor:hand" background="<@spring.url "/style/default/images/tag5.gif" />" onclick="changeimg('8')"><strong>用户职务信息</strong></td>
        <td width="1%"  align="left"><img id="9" src="<@spring.url "/style/default/images/tag6.gif" />" /></td>
        
       <!-- <td width="1%"  align="right"><img id="10" src="<@spring.url "/style/default/images/tag4.gif" />" /></td>
        <td width="18%" id="11" align="center" background="<@spring.url "/style/default/images/tag5.gif" />" onclick="changeimg('11')" ><strong>应用系统授权管理</strong></td>
        <td width="1%"  align="left"><img id="12" src="<@spring.url "/style/default/images/tag6.gif" />" /></td>-->
       </tr>
</table>
<!-- 标签结束   -->

<!-- 用户基本信息 -->
<div id="s2" style="display:">
	<table class="tableborder1" id="tt" width="100%" align="center" border="0" cellspacing="2"
		cellpadding="0">
		
			
		
		<TR class='tb1'>
			<TD align="right"  bgcolor="#fffff0">姓名</TD>
			<TD align=left   colspan="3" bgcolor="#fffff0">
			<input name="name" maxlength="15" id="name" value="" onKeypress="checkEventName();" onchange="getpy()" onblur="getpy()" style="width:70%" onkeydown="if(event.keyCode==32) return false"  /><font color="red" size="3">*</font>
			</TD>
		</TR>
		<tr>
		<TD align="right" bgcolor="#fffff0">用户标识</TD>
		<TD align=left colspan="3" bgcolor="#fffff0"><input id="userid" style="width:70%" onKeypress="checkEventID();" onkeydown="if(event.keyCode==32) return false" name="userid"   maxlength="15" value=""/><font color="red" id="confirmationuser" size="3">*</font></td>
	 
		</tr>
		<tr style="display:none">
		<TD align="right" bgcolor="#fffff0">关联用户标识</TD>
		<TD align=left colspan="3" bgcolor="#fffff0"><input id="primaryUserId" style="width:70%" name="primaryUserId" maxlength="15" value=""/><font color="red" id="confirmationRelationUser" size="3"></font></td>
	 
		</tr>
		<#if isMultiDept>
			<TR >
			<TD  align=right width="10%" bgcolor="#fffff0" >所在部门</TD>
			<TD  align=left colspan="3" bgcolor="#fffff0">
			<input name="deptName" id="deptName" type="text" value="${deptChildren.name}" disabled style="width:70%"/>
			<input name="deptuuid" id="deptuuid" type="hidden" value="${deptChildren.uuid}">
			<input type="Button" id="searchdept" class="button" value="选择"/><font color="red" size="3">*</font>
			</TD>
		</TR>
		<#else>
		    <TR >
			<TD  align=right width="10%" bgcolor="#fffff0" >所在部门</TD>
			<TD  align=left colspan="3" bgcolor="#fffff0">
			<input name="deptName" id="deptName" type="text" value="${deptChildren.name}" disabled style="width:70%"/>
			<input name="deptuuid" id="deptuuid" type="hidden" value="${deptChildren.uuid}">
			<input type="Button" id="searchdeptS" class="button" value="选择"/><font color="red" size="3">*</font>
			</TD>
		</TR>
		</#if>
	    <TR style="display:none;">
			<TD  align=right width="10%" bgcolor="#fffff0">主属部门</TD>
			<TD  align=left colspan="3" bgcolor="#fffff0">
			<input name="maindeptName" id="maindeptName" type="text" value="${deptChildren.name}" disabled style="width:70%"/>
			<input name="maindeptuuid" id="maindeptuuid" type="hidden" value="${deptChildren.uuid}" >
			<input type="Button" id="searchmaindept" value="选择"  class="button"/>
			</TD>
		</TR>
		<TR>
			<TD align="right" bgcolor="#fffff0">用户权限组</TD>
			<TD align=left  colspan="3" bgcolor="#fffff0">
			<input id="groupAreName"  name="groupAreName" value="" disabled style="width:70%"/>
			<input name="groupuuid" id="groupuuid" type="hidden" value="" style="width:70%">
			<input type="Button" id="searchgroup" value="选择"  class="button" /><font color="red" size="3">*</font></TD>
		</TR>
		 <TR >
			<TD align="right" bgcolor="#fffff0">可管理该用户的组</TD>
			<TD align=left  colspan="3" bgcolor="#fffff0">
			<input id="jsgroupAreName"  name="jsgroupAreName" value="<#if isDefaultAdminUser??><#if isDefaultAdminUser='false'><#else>${group.name}</#if></#if>" disabled style="width:70%"/>
			<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="<#if isDefaultAdminUser??><#if isDefaultAdminUser='false'><#else>${group.uuid}</#if></#if>" style="width:70%">
			<input type="Button" <#if isDefaultAdminUser??><#if isDefaultAdminUser='false'><#else>style="display:none;"</#if></#if> id="jssearchgroup" value="选择"  class="button" /><font color="red" size="3">*</font></TD>
		</TR>
		<TR class='tb1'>
			<TD align="right" bgcolor="#fffff0" >排序号</TD>
			<TD align="left" bgcolor="#fffff0"  colspan="3" >
			<input name="orderNum" id="orderNum" value="0" maxlength="5" id="orderNum" onkeypress="checkEventNUM();" />
			*排序号对于所有的组织机构都有效
			</TD>
		</TR>
		<#if sengMail??>
		 <TR>
			<TD align="right" bgcolor="#fffff0">人员性质</TD>
			<TD align=left  colspan="3" bgcolor="#fffff0">
			<select name="sengMail" id="sengMail">
				<option value="0">新入职人员</option>
				<option value="1">老员工入档</option>
			</select>
			<font color="red" size="3">*</font></TD>
		</TR>
		</#if>
		<#list attributelist0 as attribute0>
		<TR class='tb1' <#if attribute0.id="dataCameFrom">style="display:none"</#if>>
			<TD align="right" bgcolor="#fffff0" >${attribute0.name}</TD>
			<TD align=left colspan="3" bgcolor="#fffff0">
				<#switch attribute0.pageType >
				   <#case "select" >
			          <select  name="${attribute0.id}" id="${attribute0.id}" style="width=50%" > 
			                <#if attribute0.candidateItems ??>
			             	<#list attribute0.candidateItems as a0>
							  <option value="${a0.value}" <#if a0.isDefault??&&a0.isDefault>selected</#if>>${a0.value}</option>
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
							  <input type="radio" id="${a0.value}" value="${a0.value}" name="${attribute0.id}" <#if a0.isDefault??&&a0.isDefault>checked</#if>/><label for="${a0.value}">${a0.value}</label>
						    </#list>
						    </#if>
			       <#break>
			         <#case "textarea" >
			           <textarea name="${attribute0.id}" id="${attribute0.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)" value=""></textarea>&nbsp;&nbsp;长度不能超过100个字符
			       <#break>
					 <#default>
					 <#if attribute0.id!="dataCameFrom">
					  <input id="${attribute0.id}" name="${attribute0.id}" typename="${attribute0.name}" <#if attribute0.rule?exists && attribute0.rule!="0"> onblur="checkType(this);"</#if> onkeypress="checkEvent();" attvalue="${attribute0.value?if_exists}" style="width:70%" value="" maxlength="<#if attribute0.length?exists>${attribute0.length}<#else>100</#if>"  />
					  <font color="red" size="3"></font><!--input id="${attribute0.id}" name="${attribute0.id}" onkeypress="checkEvent();" value="" style="width:70%" maxlength="50"/-->
					<#else>
					  <input id="${attribute0.id}" name="${attribute0.id}" value="1" style="width:70%"/>
					  </#if>
			   </#switch>
			</TD>
		</TR>
	 </#list>
	</table>
</div>
<!-- 用户基本信息结束 -->


<!-- 用户通讯信息 -->
<div id="s5" style="display: none">
	<table class="tableborder1" align="center" width="100%" border="0" cellspacing="2"	cellpadding="0">
		 <#list attributelist1 as attribute1>
	      <TR class='tb1'>
			<TD align="center" bgcolor="#fffff0" style="width:30%">${attribute1.name}</TD>
			<TD align="left"   bgcolor="#fffff0" colspan="3">
				<#switch attribute1.pageType >
				   <#case "select" >
			          <select  name="${attribute1.id}" id="${attribute1.id}" style="width=50%" > 
			                <#if attribute1.candidateItems ??>
			             	<#list attribute1.candidateItems as a1>
							  <option value="${a1.value}" <#if a1.isDefault??&&a1.isDefault>selected</#if>>${a1.value}</option>
						    </#list>
						    </#if>
					</select>
			       <#break>
			        <#case "checkbox" >
			        		<#--if attribute1.candidateItems ??>
			        		<#list attribute1.candidateItems as a1>
							  <input type="checkbox" id="${a1.value}" value="${a1.value}" name="${attribute1.id}" <#if a1.isDefault??&&a1.isDefault>checked</#if>/><label for="${a1.value}">${a1.value}</label>
						    </#list>
						    </#if-->
			       <#break>
			       <#case "radio" >
			        		<#if attribute1.candidateItems ??>
			        		<#list attribute1.candidateItems as a1>
							  <input type="radio" id="${a1.value}" value="${a1.value}" name="${attribute1.id}" <#if a1.isDefault??&&a1.isDefault>checked</#if>/><label for="${a1.value}">${a1.value}</label>
						    </#list>
						    </#if>
			       <#break>
			         <#case "textarea" >
			           <textarea   name="${attribute1.id}" id="${attribute1.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)" value=""></textarea>&nbsp;&nbsp;长度不能超过100个字符
			       <#break>
					 <#default>
					  <input id="${attribute1.id}" name="${attribute1.id}" typename="${attribute1.name}" <#if attribute1.rule?exists && attribute1.rule!="0"> onblur="checkType(this);"</#if> onkeypress="checkEvent();" attvalue="${attribute1.value?if_exists}" style="width:70%" value="" maxlength="<#if attribute1.length?exists>${attribute1.length}<#else>100</#if>"  />
					  <font></font>
					  <!--input id="${attribute1.id}" name="${attribute1.id}" onkeypress="checkEvent();" value="" style="width:70%" maxlength="50"/-->
			   </#switch>
			</TD>
		</TR>	
	 </#list>
	</table>
</div>
<!-- 用户通讯信息结束 -->


<!-- 用户职务信息 -->
<div id="s8" style="display: none">
		<table class="tableborder1" align="center" width="100%" border="0" cellspacing="2"	cellpadding="0">
		<#list attributelist2 as attribute2>
	      <TR class='tb1'>
			<TD align="center" bgcolor="#fffff0" style="width:30%">${attribute2.name}</TD>
			<TD align="left"   bgcolor="#fffff0" colspan="3">
				<#switch attribute2.pageType >
				   <#case "select" >
			          <select  name="${attribute2.id}" id="${attribute2.id}" style="width=50%" > 
			                <#if attribute2.candidateItems ??>
			             	<#list attribute2.candidateItems as a2>
							  <option value="${a2.value}" <#if a2.isDefault??&&a2.isDefault>selected</#if>>${a2.value}</option>
						    </#list>
						    </#if>
					</select>
			       <#break>
			        <#case "checkbox" >
			        		<#--if attribute2.candidateItems ??>
			        		<#list attribute2.candidateItems as a2>
							  <input type="checkbox" id="${a2.value}" value="${a2.value}" name="${attribute2.id}" <#if a2.isDefault??&&a2.isDefault>checked</#if>/><label for="${a2.value}">${a2.value}</label>
						    </#list>
						    </#if-->
			       <#break>
			       <#case "radio" >
			        		<#if attribute2.candidateItems ??>
			        		<#list attribute2.candidateItems as a2>
							  <input type="radio" id="${a2.value}" value="${a2.value}" name="${attribute2.id}" <#if a2.isDefault??&&a2.isDefault>checked</#if>/><label for="${a2.value}">${a2.value}</label>
						    </#list>
						    </#if>
			       <#break>
			         <#case "textarea" >
			           <textarea name="${attribute2.id}" id="${attribute2.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)" value=""></textarea>&nbsp;&nbsp;长度不能超过100个字符
			       <#break>
					 <#default>
					  <input id="${attribute2.id}" name="${attribute2.id}" typename="${attribute2.name}" <#if attribute2.rule?exists && attribute2.rule!="0"> onblur="checkType(this);"</#if> onkeypress="checkEvent();" attvalue="${attribute2.value?if_exists}" style="width:70%" value="" maxlength="<#if attribute2.length?exists>${attribute2.length}<#else>100</#if>"  />
					  <!--input id="${attribute2.id}" name="${attribute2.id}" onkeypress="checkEvent();" value="" style="width:70%" maxlength="50"/-->
			   </#switch>
			</TD>
		</TR>
	 </#list>
	</table>
</div>
<div id="s11" style="display: none">
这里要放啥子？？？
</div>
<p><p>
<!--
<table cellspacing="1" border="0" align="center" width="100%" class="tableborder1" id="Table21">
	<tbody><tr class="tb1">
			<td bgcolor="#fffff0" align="right" width="10%">创建时间</td>
			<td bgcolor="#fffff0" align="left" width="50%"><input type="text" style="border: 0px none ;" disabled="disabled" value="" size="40" name="cdate"/>
			</td>
			<td bgcolor="#fffff0" align="right" width="10%">创建人</td>
			<td bgcolor="#fffff0" align="left" width="50%"><input type="text" style="border: 0px none ;" disabled="disabled" value="" name="cperson"/></td>
		</tr>
		<tr class="tb1">
			<td bgcolor="#fffff0" align="right" width="10%">修改时间</td>
			<td bgcolor="#fffff0" align="left" width="50%"><input type="text" style="border: 0px none ;" disabled="disabled" value="" size="40" name="editDate"/>
			</td>
			<td bgcolor="#fffff0" align="right" width="10%">修改人</td>
			<td bgcolor="#fffff0" align="left" width="50%"><input type="text" style="border: 0px none ;" disabled="disabled" value="" name="editPerson"/></td>
		</tr>
		<tr class="tb1">
			<td bgcolor="#fffff0" align="right" width="10%">审核时间</td>
			<td bgcolor="#fffff0" align="left" width="50%"><input type="text" style="border: 0px none ;" disabled="disabled" value="" size="40" name="autiDate"/>
			</td>
			<td bgcolor="#fffff0" align="right" width="10%">审核人</td>
			<td bgcolor="#fffff0" align="left" width="50%"><input type="text" style="border: 0px none ;" disabled="disabled" value="" name="autiPerson"/></td>
		</tr>
</tbody></table>-->
<table id="Table2" class="tableborder1" width="100%" border="0"
    cellspacing="1" align="center">
	<TR class='tb1'>
			<TD align=center colspan=4  bgcolor="#fffff0">
			     <input type="button" class="button" value="提交" id="adduserbutton" >
				 <input type="button" name="button" class="button" value="取消"  onclick="history.back()" />
			</TD>
		</TR>
</table>
 
</form>
</body>
</html>