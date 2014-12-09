<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
$(function(){
	$("#pagesizego").click(function(){
		var pagesizegovalue=$("#pagesizegovalue").val();
		window.location.href='<@spring.url "/app/appuserlist.nsf" />?type=${type}&groupuuid=${groupuuid}&pagesize='+pagesizegovalue;
	});
});
$(function(){
	$("#pagego").click(function(){
		var pagegovalue=$("#pagegovalue").val();
		var pagesizegovalue=$("#pagesizegovalue").val();
		window.location.href='<@spring.url "/app/appuserlist.nsf" />?type=${type}&groupuuid=${groupuuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
	});
});
$(function(){
	$("#searchSubmit").click(function(){
		var searchvalue=$("#searchType").val();
		var searchcontent=$("#searchcontent").val();
		if(searchcontent.length>0){
			$("#f1").submit();
		}else{
			alert("请输入查询内容!");
		}
	});
});
$(function(){
	$("#addgroup").click(function(){
		window.location.href='<@spring.url "/group/addgroup.nsf"/>?id=${groupuuid}';
	});
});
$(function(){
	$("#updategroup").click(function(){
		window.location.href='<@spring.url "/group/updategroup.nsf"/>?id=${groupuuid}';
	});
});
$(function(){
	$("#delgroup").click(function(){
		if(confirm("真的要删除吗？")){
			var url="<@spring.url "/group/existgroupUnderGroup.nsf"/>?id=${groupuuid}"
			$.post(url,function(data){
				data=checkAjaxData(data);
				if(data=="false"){
					var url="<@spring.url "/group/delgroup.nsf"/>?id=${groupuuid}";
					window.location.href=url;
					window.parent.parent.parent.location.reload();
				}else{
					alert("该组内存在用户，不能被删除!");
					return false;
				}
			});
		}
	});
});

$(function(){
	$("#moveuser").click(function(){
		var a = document.getElementsByName("moveusers1");
		if(a!=null){
			var valueStr="";
			for   (i=0;i<a.length;i++)  {
				if   (a[i].checked) {
				valueStr+=a[i].value+',';
				}
			}
		}
		if(valueStr.length>0){
			if(confirm("真的要移除吗？")){
				f1.action='<@spring.url "/app/appdelgroupuser.nsf" />';
				$("#f1").submit();
			}
		}else{
			alert("未选择用户！");
		}
	});

	$("#adduser").click(function(){
		var a = document.getElementsByName("moveusers");
		if(a!=null){
			var valueStr="";
			for   (i=0;i<a.length;i++)  {
				if   (a[i].checked) {
					valueStr+=a[i].value+',';
				}
			}
		}
		if(valueStr.length>0){
			if(confirm("确认增加吗？")){
				f1.action='<@spring.url "/app/appupdategroupuser.nsf"/>';
				$("#f1").submit();
			}
		}else{
			alert("未选择用户！");
		}
	});
});

$(function(){
	$("#a").click(function(){
		f1.action='<@spring.url "/app/appedit.nsf" />';
		$("#f1").submit();
	});
});
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
	var name = "moveusers1";
	<#if type!="appuserlist">name="moveusers";</#if>
	for (var iCount = 0; iCount < objLen; iCount++)
	{
		if (input2.checked == true)
		{
			if (objForm.elements[iCount].name == name)
			{
				objForm.elements[iCount].checked = true;
			}
		}
		else
		{
			if (objForm.elements[iCount].name == name)
			{
				objForm.elements[iCount].checked = false;
			}
		}
	}
}
</script>
<form name="f1" id="f1" method="post" action="<@spring.url "/app/search/appsearchgroupuser.nsf" />" method="post">
<input type="hidden" name="type" id="type" value="${type}"/>
<input type="hidden" name="groupuuid" id="groupuuid" value="${groupuuid}"/>
<input type="hidden" name="userids" id="userids" value=""/>
<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
	<tr>
		<td height="18" width="30%">
			<table border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
				<tr >
				<td height="18" width="30%" nowrap>&nbsp;【${group.name}】<#if type="appuserlist">已<#else>待</#if>授权成员列表</td>
				<td style="display:none"><img id="addgroup" src="<@spring.url "/style/default/images/addgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
				<td style="display:none"><img id="delgroup" src="<@spring.url "/style/default/images/delgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
				<td style="display:none"><img id="updategroup" src="<@spring.url "/style/default/images/modgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
			</tr></table>
		<td height="21" width="52%" align="right">
			<input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEvent();" value="" size="10"> 
				<select id="searchType" name="searchType" >
					<option value="username">按姓名搜索</option>
					<option value="userid">按ID搜索</option>
				</select> 
			<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
		</td>
		<td height="21" align="right" width="18%">
		<#if type="appuserlist">
			<input id="moveuser" type="button" class="button"  value="拒绝访问" >
		<#else>
			<input id="adduser" type="button" class="button"  value="允许访问" >
		</#if>
		</td>
	</tr>
</table>
<table width="100%" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" style="font-size:12px;" >
	<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
		<td >序号</td>
		<td >用户ID</td>
		<td >姓名</td>
		<td >用户所在单位</td>
		<td >用户所在部门</td>
		<#if showAppAccount??>
		<#if showAppAccount='true'>
		<td >应用系统账号</td>
		</#if>
		</#if>
        <td width="70" valign="middle"><input type="checkbox" value="0" id="moveuser_All"></td>
	</tr>
	<#if  userlist ??>
		<#list userlist as user>
			<#if  (user_index+1)%2=0>
			   <tr style="background-color:#DAE9F9;" align="center">
			<#else>
			   <tr style="background-color:#EEEEEE;" align="center">
			</#if>
					<td >${javapage.getDataStart()+1+user_index}</td>
					<td >${user.id}</td>
					<td >
						<#if type="appuserlist">
						<a href="<@spring.url "/app/appedituser.nsf?useruuid="/>${user.uuid}&groupuuid=${groupuuid}">${user.name?html}</a>
						<#else>
						${user.name?html}
						</#if>
					</td>
					<td >
						<#list uumService.getUserOrgDepartment(user) as dept><#compress>${dept.name},</#compress></#list>
					</td>
					<td >
						<#list user.getDepartments() as dept><#compress>${dept.name},</#compress></#list>
					</td>
					<#if showAppAccount??>
					<#if showAppAccount='true'>
						<td >
							<#list uumService.getUserAccountInApplication(user.id,groupuuid) as a><#if a??>${a}</#if></#list>
						</td>
					</#if>
					</#if>
	                <td ><input type="checkbox" value="${user.uuid}" id="moveusers1" onclick="subchick(this)" <#if type="appuserlist">name="moveusers1"<#else>name="moveusers"</#if> ></td>
			   </tr>
		</#list>
	</#if>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
	<tr>
		<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
				<input size=1 maxlength="3" value="${javapage.getPageSize()}" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
				<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
				<input size=1 maxlength="3" value="${javapage.getCurrentPage()}" id="pagegovalue"> 
				<input type=button class="button"  value="GO"  id="pagego"></td>
				<td width="41%">
			<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
				<a href="<@spring.url "/app/appuserlist.nsf"/>?type=${type}&groupuuid=${groupuuid}&page=1&pagesize=${javapage.getPageSize()}">首页</a>
				<#if javapage.isHasPreviousPage()>
				<a href="<@spring.url "/app/appuserlist.nsf"/>?type=${type}&groupuuid=${groupuuid}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
				<#if javapage.isHasNextPage()>
				<a href="<@spring.url "/app/appuserlist.nsf"/>?type=${type}&groupuuid=${groupuuid}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
				<a href="<@spring.url "/app/appuserlist.nsf"/>?type=${type}&groupuuid=${groupuuid}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
			</div>
		</td>
	</tr>
</table>
</form>
