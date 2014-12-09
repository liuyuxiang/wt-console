<#import "/spring.ftl" as spring />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>增加应用</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
	window.parent.frames["lu"].location.reload(); 
</script>
<script type="text/javascript">
	function back(){
  	 window.location.href='<@spring.url "/app/applist.nsf"/>';
  	}
  
	function check(){
		var appname = document.getElementById("appname").value;
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
		//var appstatus = document.getElementById("appstatus").value;
		var apporder = document.getElementById("apporder").value;
		var jsgroupuuid = document.getElementById("jsgroupuuid").value;
		if(appname==""){
			alert("请填写[系统名称]");
			return false;
		}
		if(appId==""){
			alert("请填写[系统ID]");
			return false;
		}
		if(jsgroupuuid==""){
			alert("请填指定[管理组]");
			return false;
		}
/*		
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
*/
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
</head>
<body style="background:#FFFFFF;">
		${suc}
	<form id="ac" action="<@spring.url "/app/appaddsuc.nsf" />" method="post">
		<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" align="center" border="0" cellspacing="2" cellpadding="0">
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="appname" maxlength="30" id="appname" value=""/>
					<font color="red">必填</font>	
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统ID</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="15" name="appId" id="appId" value=""/>
					<font color="red">必填</font>	
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统简要概述</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="appRemark" id="appRemark" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统访问地址</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="200" name="appUrl" id="appUrl" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"><br></br></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0"></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统单点登陆页面</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="200" name="appSingleLoginUrl" id="appSingleLoginUrl" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">登陆测试用户名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appUserName" id="appUserName" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">登陆测试密码</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appPassword" id="appPassword" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"><br></br></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0"></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统数据库地址</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="200" name="appDBUrl" id="appDBUrl" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库访问端口</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="10" name="appDBPort" id="appDBPort" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appDBName" id="appDBName" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库连接用户名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appDBUserName" id="appDBUserName" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">数据库连接密码</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="appDBPassword" id="appDBPassword" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">中间表的表名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="50" name="appDBMidTableName" id="appDBMidTableName" value=""/></td>
			</tr>
		<#--
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">排序号</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					</td>
			</tr>
			-->
				<input type="hidden" name="apporder" maxlength="5" id="apporder" onkeypress="checkEventNUM();" value=""/>
			
			<#--
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统状态</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="appstatus" id="appstatus" maxlength="1" onkeypress="checkEventNUM();" value="1"/></td>
			</tr>
			-->
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">管理组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<textarea name="jsgroupAreName" id="jsgroupAreName" rows="5" style="width:40%" value="" disabled></textarea>
				<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="" style="width:70%">
				<input type="button" class="button" id="jssearchgroup" value="选择组"/>
				
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="button" class="button" value="提交" onclick="check()" />&nbsp;&nbsp;
					<input type="button" class="button" value="返回" onclick="back()" />
				</td>
			</tr>
		</table>	
		
	</form>
</body>
</html>