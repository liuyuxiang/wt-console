<#import "/spring.ftl" as spring />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>编辑应用</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
  $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               ac.action='<@spring.url "/app/search/appsearchalluser.nsf" />';
               $("#ac").submit();
               }else{
                alert("请输入查询内容!");
              }
  	});
  });
  $(function(){
     $("#moveuser").click(function(){
       	  var a = document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要移除吗？")){
                	 document.getElementById('userids').value=valueStr;
		             ac.action='<@spring.url "/app/appdeluseradmin.nsf" />';
               		 $("#ac").submit();
                  }
                   }else{
                   alert("未选择用户！");
                 }
  	});
  });
  $(function(){
     $("#moveuser_All").click(function(){
  		 chkall("ac",this);
  	});
  });
  function chkall(input1,input2)
{
    var objForm = document.forms[input1];
    var objLen = objForm.length;
    for (var iCount = 0; iCount < objLen; iCount++)
    {
        if (input2.checked == true)
        {
            if (objForm.elements[iCount].name == "moveusers")
            {
                objForm.elements[iCount].checked = true;
            }
        }
        else
        {
            if (objForm.elements[iCount].name == "moveusers")
            {
                objForm.elements[iCount].checked = false;
            }
        }
    }
} 
 $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/app/appedit.nsf" />?appuuid=${app.uuid}&pagesize='+pagesizegovalue;
  	});
  });
   $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/app/appedit.nsf" />?appuuid=${app.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
  function back(){
  	 window.parent.location="<@spring.url "/app/applist.nsf"/>";   
  }
  
  function check(){
		var appname = document.getElementById("name").value;
		var appId = document.getElementById("appId").value;
		var appRemark = document.getElementById("appRemark").value;
		var appUrl = document.getElementById("appUrl").value;
		var appSingleLoginUrl = document.getElementById("appSingleLoginUrl").value;
		var appUserName = document.getElementById("appUserName").value;
		var appPassword = document.getElementById("appPassword").value;
		var appDBUrl = document.getElementById("appDBUrl").value;
		var appDBPort = document.getElementById("appDBPort").value;
		var appDBName = document.getElementById("appDBName").value;
		var appDBUserName = document.getElementById("appDBUserName").value;
		var appDBPassword = document.getElementById("appDBPassword").value;
		var appDBMidTableName = document.getElementById("appDBMidTableName").value;
		var appstatus = document.getElementById("appstatus").value;
		var apporder = document.getElementById("apporder").value;
		
		if(appname==""){
			alert("请填写[系统名称]");
			return false;
		}
		if(appId==""){
			alert("请填写[系统ID]");
			return false;
		}
		
		if(appRemark==""){
			alert("请填写[系统简要概述]");
			return false;
		}
		if(appUrl==""){
			alert("请填写[系统访问地址]");
			return false;
		}		
		if(appSingleLoginUrl==""){
			alert("请填写[系统单点登陆页面]");
			return false;
		}
		if(appUserName==""){
			alert("请填写[登陆测试用户名]");
			return false;
		}
		if(appPassword==""){
			alert("请填写[登陆测试密码]");
			return false;
		}
		if(appDBUrl==""){
			alert("请填写[系统数据库地址]");
			return false;
		}
		if(appDBPort==""){
			alert("请填写[数据库访问端口]");
			return false;
		}
		if(appDBName==""){
			alert("请填写[数据库名称]");
			return false;
		}
		if(appDBUserName==""){
			alert("请填写[数据库连接用户名]");
			return false;
		}
		if(appDBPassword==""){
			alert("请填写[数据库连接密码]");
			return false;
		}
		if(appDBMidTableName==""){
			alert("请填写[中间表的表名]");
			return false;
		}
		if(appstatus==""){
			alert("请填写[系统状态]");
			return false;
		}
		if(apporder==""){
			alert("请填写[排序号]");
			return false;
		}
		document.getElementById("ac").submit();
	}
	$(function(){
     $("#jssearchgroup").click(function(){
  		  var    width=300;    
        var    height=300;    
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#jsgroupuuid").val()+'&mygroupuuid=no';    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth='+width+'px;dialogHeight='+ height+'px;resizable=no;help=no;center=yes;status=no;scroll=yes;edge=sunken');    
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
</script>
<script type="text/javascript">
	window.parent.frames.item(1).location.reload();
</script>
</head>
<body style="background:#FFFFFF;">
	<form name="ac" id="ac" action="<@spring.url "/app/appeditsuc.nsf" />" method="post">
		<table id="tt"  width="100%" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" align="center" border="0" cellspacing="2" cellpadding="0">
			<input type="hidden" name="appuuid" id="appuuid" value="${app.uuid}"/>
			<input type="hidden" name="userids" id="userids" value=""/>
			<input type="hidden" name="cuappname" id="cuappname" value="${cuappname}"/>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" disabled="disabled" maxlength="30" name="name" id="name" value="${app.name}"/></td>
					
					
				<td align="right" colspan="3"  bgcolor="#fffff0">系统单点登陆页面</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="text" maxlength="200" name="appSingleLoginUrl" id="appSingleLoginUrl" value="<#if app.appSingleLoginUrl??>${app.appSingleLoginUrl}<#else>&nbsp;</#if>"/></td>
			
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统ID</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text"  disabled="disabled" maxlength="15" name="appId" id="appId" value="${app.appId}"/></td>
			
				<td align="right" colspan="3"  bgcolor="#fffff0">登陆测试用户名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="text" maxlength="20" name="appUserName" id="appUserName" value="<#if app.appUserName??>${app.appUserName}<#else>&nbsp;</#if>"/></td>
			
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统简要概述</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="appRemark" id="appRemark" value="<#if app.appRemark??>${app.appRemark}<#else>&nbsp;</#if>"/></td>
			
				<td align="right" colspan="3"  bgcolor="#fffff0">登陆测试密码</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appPassword" id="appPassword" value="<#if app.appPassword??>${app.appPassword}<#else>&nbsp;</#if>"/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统访问地址</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="200" name="appUrl" id="appUrl"  value="<#if app.appUrl??>${app.appUrl}<#else>&nbsp;</#if>"/></td>
			
				<td align="right" colspan="3"  bgcolor="#fffff0">系统数据库地址</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="200" name="appDBUrl" id="appDBUrl" value="<#if app.appDBUrl??>${app.appDBUrl}</#if>"/></td>
			</tr>
			
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库访问端口</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="10" name="appDBPort" id="appDBPort" value="<#if app.appDBPort??>${app.appDBPort}</#if>"/></td>
			
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appDBName" id="appDBName" value="<#if app.appDBName??>${app.appDBName}</#if>"/></td>
				
			</tr>
		
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库连接用户名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appDBUserName" id="appDBUserName" value="<#if app.appDBUserName??>${app.appDBUserName}</#if>"/></td>
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库连接密码</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appDBPassword" id="appDBPassword" value="<#if app.appDBPassword??>${app.appDBPassword}</#if>"/></td>
			</tr>

			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">中间表的表名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appDBMidTableName"  id="appDBMidTableName" value="<#if app.appDBMidTableName??>${app.appDBMidTableName}</#if>"/></td>
			
				
				<td align="right" colspan="3"  bgcolor="#fffff0">管理组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="text" name="jsgroupAreName" id="jsgroupAreName" readonly="readonly" value="${jsgroupAreName}"></textarea>
				<input type="hidden" name="jsgroupuuid" id="jsgroupuuid" value="${jsgroupuuid}" style="width:100%" >
				<input type="button" class="button" id="jssearchgroup" value="选择组"/>
			</tr>
			
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">access group</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="accessGroup" readonly="readonly" id="accessGroup" value="<#if app.accessGroup??>${app.accessGroup.name?html}</#if>"/></td>
				
				<td align="right" colspan="3"  bgcolor="#fffff0"><br></br></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0"></td>
			</tr>
		</table>
		<input type="submit" class="button" value="提交"/>&nbsp;&nbsp;
		<input type="button" class="button" value="返回" onclick="back()"/>&nbsp;&nbsp;
	</form>
</body>
</html>