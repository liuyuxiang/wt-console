<#import "/spring.ftl" as spring />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>编辑应用</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
</head>
<script type="text/javascript">
//<![CDATA[
function next(str){
	f1.action=str;
	$("#f1").submit();
}
$(function(){
	$("#pagesizego").click(function(){
		var pagesizegovalue=$("#pagesizegovalue").val();
		window.location.href='<@spring.url "/app/search/appsearchgroupuser.nsf" />?type=${type}&searchcontent=${searchcontent}&groupuuid=${groupuuid}&searchType=${searchType}&pagesize='+pagesizegovalue;
	});
});
$(function(){
	$("#pagego").click(function(){
		var pagegovalue=$("#pagegovalue").val();
		var pagesizegovalue=$("#pagesizegovalue").val();
		window.location.href='<@spring.url "/app/search/appsearchgroupuser.nsf" />?type=${type}&searchcontent=${searchcontent}&groupuuid=${groupuuid}&searchType=${searchType}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
	});
});
$(function(){
	$("#back").click(function(){
		f1.action='<@spring.url "/app/appuserlist.nsf"/>';
		$("#f1").submit();
	});
});
$(function(){
	$("#searchSubmit").click(function(){
		var searchvalue=$("#searchType").val();
		var searchcontent=$("#searchcontent").val();
		if(searchcontent.length>0){
			f1.action='<@spring.url "/app/search/appsearchgroupuser.nsf" />';
			$("#f1").submit();
		}else{
			alert("请输入查询内容!");
		}
	});
});
$(function(){
	$("#moveuser").click(function(){
		var a = document.getElementsByName("moveusers");
		if(a!=null){
			var valueStr="";
			for (i=0;i<a.length;i++) {
				if (a[i].checked) {
					valueStr+=a[i].value+',';
				}
			}
		}
		if(valueStr.length>0){
			if(confirm("确认增加吗？")){
				document.getElementById('userids').value=valueStr;
				f1.action='<@spring.url "/app/appupdategroupuser.nsf"/>';
				document.getElementsByName("userids").value=valueStr;
				$("#f1").submit();
			}
		}else{
			alert("未选择用户！");
		}
	});
});

$(function(){
	$("#moveuser1").click(function(){
		var a = document.getElementsByName("moveusers1");
		if(a!=null){
			var valueStr="";
			for (i=0;i<a.length;i++)  {
				if (a[i].checked) {
					valueStr+=a[i].value+',';
				}
			}
		}
		if(valueStr.length>0){
			if(confirm("真的要移除吗？")){
				document.getElementById('userids').value=valueStr;
				f1.action='<@spring.url "/app/appdelgroupuser.nsf" />';
				$("#f1").submit();
			}
		}else{
			alert("未选择用户！");
		}
	});
});
$(function(){
	$("#moveuser_All1").click(function(){
		 chkall1("f1",this);
	});
});
function subchick1(obj){
	if(!obj.checked){
		document.getElementById("moveuser_All1").checked=false;
	}
}

function chkall1(input1,input2)
{
	var objForm = document.forms[input1];
	var objLen = objForm.length;
	for (var iCount = 0; iCount <  objForm.length; iCount++)
	{
		if (input2.checked == true)
		{
			if (objForm.elements[iCount].name == "moveusers1")
			{
				objForm.elements[iCount].checked = true;
			}
		}
		else
		{
			if (objForm.elements[iCount].name == "moveusers1")
			{
				objForm.elements[iCount].checked = false;
			}
		}
	}
} 
$(function(){
	$("#moveuser_All").click(function(){
		chkall("f1",this);
	});
});
function subchick(obj){
	if(!obj.checked){
		document.getElementById("moveuser_All").checked=false;
	}
}

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
//]]>
</script>
<body style="background:#FFFFFF;">
<form name="f1" id="f1" action="<@spring.url "/app/search/appsearchgroupuser.nsf" />" method="post">
<input type="hidden" name="groupuuid" id="groupuuid" value="${groupuuid}"/>
<input type="hidden" name="userids" id="userids" value=""/>
<input type="hidden" name="type" id="userids" value="${type}"/>
<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
		<tr>
			<td height="21" align="left">搜索结果</td>
			<td height="21" align="left"><input  type="button" class="button"  id="back" value="返回列表" ></td>
			<td height="21" align="right">
				<input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEvent();" value="${searchcontent}" size="10"> 
					<select id="searchType" name="searchType" >
						<#if  searchType=='username'>
							<option value="username" selected="selected">按姓名搜索</option>
							<option value="userid">按ID搜索</option>
						<#else>
							<option value="username">按姓名搜索</option>
							<option value="userid"  selected="selected">按ID搜索</option>
						</#if>
					</select> 
				<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
				
			</td>
			<td align="right" width="18%">
				<input id="moveuser" align="center" type="button" class="button"  value="允许访问">
				<#if type=="appuserlist">
				<input id="moveuser1" align="center" type="button" class="button"  value="拒绝访问">
				</#if>
			</td>
		</tr>
	</table>
	<table width="100%" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" style="font-size:12px;" >
		<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
			<td >序号</td>
			<td >用户ID</td>
			<td >姓名</td>
		<td >用户所在部门</td>
		<#if showAppAccount??>
		<#if showAppAccount='true'>
		<td >应用系统账号</td>
		</#if>
		</#if>
	        <td width="70" valign="middle"><input type="checkbox" value="0" id="moveuser_All"  name="moveuser_All" ></td>
	        <#if type=="appuserlist">
	        <td width="70" valign="middle"><input type="checkbox" value="1" id="moveuser_All1" name=="moveuser_All1"></td>
			</#if>
		</tr>
		
		
		<#if  userlist ??>
			<#list userlist as userlist>	
			<#assign user=userlist[0] orgDeptName=userlist[1] deptName=userlist[2] isApp=userlist[3]>
				<#if  (userlist_index+1)%2=0>
				   <tr style="background-color:#DAE9F9;" align="center">
				<#else>
				   <tr style="background-color:#EEEEEE;" align="center">
				</#if>
					<td >${javapage.getDataStart()+userlist_index+1}</td>
					<td >${user.id?html}</td>
					<td ><#if isApp><a href="<@spring.url "/app/appedituser.nsf?useruuid="/>${user.uuid}&groupuuid=${groupuuid}" >${user.name?html}</a><#else>${user.name?html}</#if></td>
					<td >${orgDeptName}->${deptName}</td>
					<#if showAppAccount??>
					<#if showAppAccount='true'>
						<td >
							<#list uumService.getUserAccountInApplication(user.id,groupuuid) as a><#if a??>${a}</#if></#list>
						</td>
					</#if>
					</#if>
					<#if isApp>
		                <td width="70" valign="middle">已加入</td>
		                <#if type=="appuserlist">
		                <td width="70" valign="middle"><input type="checkbox" value="${user.uuid}" id="moveusers1" onclick="subchick1(this)" name="moveusers1" ></td>
					    </#if>
				   <#else>
				   		<td width="70" valign="middle"><input type="checkbox" value="${user.uuid}" id="moveusers" onclick="subchick(this)" name="moveusers" ></td>
	                	<#if type=="appuserlist">
	                	<td width="70" valign="middle">未加入</td>
	                	</#if>
				   </#if>
				   </tr>
			</#list>
		</#if>
		
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
		<tr>
			 <td width="59%">共${javapage.getQuantityOfData()} 条记录    每页 
					<input size=1 value="${javapage.getPageSize()}" id="pagesizegovalue"  > 条 
					<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
					<input size=1 value="${javapage.getCurrentPage()}" id="pagegovalue"> 
					<input type=button class="button"  value="GO"  id="pagego"></td>
					<td width="41%">
				<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
					<a href="#" onclick="next('<@spring.url "/app/search/appsearchgroupuser.nsf"/>?type=${type}page=1&pagesize=${javapage.getPageSize()}')">首页</a>
					<#if javapage.isHasPreviousPage()>
						<a href="#" onclick="next('<@spring.url "/app/search/appsearchgroupuser.nsf"/>?type=${type}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}')">上页</a><#else>上页</#if>
					<#if javapage.isHasNextPage()>
						<a href="#" onclick="next('<@spring.url "/app/search/appsearchgroupuser.nsf"/>?type=${type}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}')">下页</a><#else>下页</#if>
						<a href="#" onclick="next('<@spring.url "/app/search/appsearchgroupuser.nsf"/>?type=${type}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}')">尾页</a>
				</div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>