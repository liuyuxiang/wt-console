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
  		 window.location.href='<@spring.url "/app/search/appsearchalluser.nsf" />?searchcontent=${searchcontent}&appuuid=${appuuid}&searchType=${searchType}&pagesize='+pagesizegovalue;
  	});
  });
   $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/app/search/appsearchalluser.nsf" />?searchcontent=${searchcontent}&appuuid=${appuuid}&searchType=${searchType}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
  $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               f1.action='<@spring.url "/app/search/appsearchalluser.nsf" />';
               $("#f1").submit();
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
                if(confirm("真的要增加吗？")){
                	  document.getElementById('userids').value=valueStr;
		             f1.action='<@spring.url "/app/appupdateuseradmin.nsf" />';
               		 $("#f1").submit();
                  }
                   }else{
                   alert("未选择用户！");
                 }
  	});
  });
	 $(function(){
     $("#a").click(function(){
  		 f1.action='<@spring.url "/app/appshowadminuser.nsf" />';
         $("#f1").submit();
  	});
  });
   $(function(){
     $("#moveuser_All").click(function(){
  		 chkall("f1",this);
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
//]]>
</script>
<body style="background:#FFFFFF;">
<form name="f1" id="f1" action="<@spring.url "/app/search/appsearchalluser.nsf" />" method="post">
<input type="hidden" name="appuuid" id="appuuid" value="${appuuid}"/>
<input type="hidden" name="userids" id="userids" value=""/>
<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
		<tr>
			<td height="21" width="52%" align="right">
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
				<input  type="button" class="button"  id="a" value="返回编辑页面" >
			</td>
			<td height="21" align="right" width="18%">
				<input id="moveuser" type="button" class="button"  value="增 加">
			</td>
		</tr>
	</table>
	<table width="100%" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" >
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
	        <td width="70" valign="middle"><input type="checkbox" value="0" id="moveuser_All"  ></td>
		</tr>
		<#if  pathlist ??>
			<#list pathlist as user>
				<#if  (user_index+1)%2=0>
				   <tr style="background-color:#DAE9F9;" align="center">
				<#else>
				   <tr style="background-color:#EEEEEE;" align="center">
				</#if>
					<td >${javapage.getDataStart()+1+user_index}</td>
					<td >${user.id}</td>
					<td >${user.name}</td>
	                <td >已加入</td>
				   </tr>
			</#list>
		</#if>
		<#if  userlist ??>
			<#list userlist as user>
				<#if  (user_index+1)%2=0>
				   <tr style="background-color:#DAE9F9;" align="center">
				<#else>
				   <tr style="background-color:#EEEEEE;" align="center">
				</#if>
					<td >${javapage.getDataStart()+1+user_index}</td>
					<td >${user.id}</td>
					<td >${user.name}</td>
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
	                <td ><input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" ></td>
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
					<a href="#" onclick="next('<@spring.url "/app/search/appsearchalluser.nsf"/>?&page=1&pagesize=${javapage.getPageSize()}')">首页</a>
					<#if javapage.isHasPreviousPage()>
						<a href="#" onclick="next('<@spring.url "/app/search/appsearchalluser.nsf"/>?&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}')">上页</a><#else>上页</#if>
					<#if javapage.isHasNextPage()>
						<a href="#" onclick="next('<@spring.url "/app/search/appsearchalluser.nsf"/>?&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}')">下页</a><#else>下页</#if>
						<a href="#" onclick="next('<@spring.url "/app/search/appsearchalluser.nsf"/>?&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}')">尾页</a>
				</div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>