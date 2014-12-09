<#import "/spring.ftl" as spring />
<#import "/user/userListMacro.ftl" as user1 />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

    $(function(){
     $("#filteruser").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                    if(confirm("真的要回退吗？")){
                    	window.location.href='<@spring.url "/tasklist/rollbackdeluser.nsf" />?userids='+valueStr;
                    }
                }else{
                    alert("至少选择一个！");
                }
  	});
  });
$(function(){
	$("#deleteuser").click(function(){
		var a = document.getElementsByName("moveusers");
		if(a!=null){ var valueStr="";
			for (i=0;i<a.length;i++) {
				if (a[i].checked) {
					valueStr+=a[i].value+',';
				}
			}
		}
		if(valueStr.length>0){
			if(confirm("真的要永久删除用户吗？")){
				window.location.href='<@spring.url "/tasklist/deleteUser.nsf" />?userId=${user.id}&userids='+valueStr;
			}
		}else{
			alert("至少选择一个！");
		}
	});
});
    $(function(){
     $("#moveuser_All").click(function(){
  		 chkall("f1",this);
  	});
  });
     $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/tasklist/deluserList.nsf" />?userId=${user.id}&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/tasklist/deluserList.nsf" />?userId=${user.id}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
      $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               f1.action='<@spring.url "/tasklist/deleteusersearch.nsf" />?id=${user.id}&searchvalue='+searchvalue;
               $("#f1").submit();
               }else{
                alert("请输入查询内容!");
              }
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
<form name="f1" id="f1" method="post" action="">
	<table  cellspacing="0" width="100%" align="center"  >
		<tr>
			<td width="100%" align="left" valign="top">
				<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
													<td align="right">
														 <input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEventName();" value="" size="10"> 
														 <select id="searchType" name="searchType" >
															<option value="username">按姓名搜索</option>
															<option value="userid">按ID搜索</option>
														</select> 
														<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
													</td>
													<td height="21" align="right" width="18%">
														<input id="deleteuser" type="button"  class="button"  value="永久删除" > 
														<input id="filteruser" type="button" class="button"  value="退回" >
													</td>
												</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8">
				<table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<#if macro ??>
											 <@user1.headers/>
											<#else>
											<td >序号</td>
											<td >用户ID</td>
											<td >姓名</td>
		                                    <td >用户所在部门</td>
											<td >排序号</td>
											</#if>
											<td >用户状态</td>
											<td >更新时间</td>
		                                <td ><input type="checkbox" value="0" id="moveuser_All"  ></td>
										</tr>
										<#if  userlist ?? >
										<#list userlist as user>
										    <#if  (user_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
										 	<#if macro ??>
										 	<@user1.cols user.uuid/>
										    <#else>
											<td >${javapage.getDataStart()+1+user_index}</td>
											<td >${user.id}</td>
											<td >${user.name?html}</a></td>
		                                    <td ><#list uumService.getUserDepartments(user) as dept>${dept.path},</#list></td>
											<td >${user.order}</td>
											</#if>
											<td >
											<#switch user.status >
											   <#case -1 >
											                删除用户
											      <#break>
											   <#case -8 >
											                退回用户
											      <#break>
											   <#case -11 >
											                离职用户
											      <#break>
											   <#default>
											   	   &nbsp;
											   </#switch>
											</td>
											<td>${user.lastUpdateTime!"无日期"}</td>
		                                    <td ><input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" ></td>
										</tr>
										
										  
										</#list>
										</#if>
				</table>
			</td>
		</tr>
		<tr>
			<td align=right colspan="3">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
								<tr>
									<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
									<input size=1 value="${javapage.getPageSize()}"  onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
									<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
									<input size=1 value="${javapage.getCurrentPage()}"  onkeypress="checkEventNUM();" id="pagegovalue"> 
									<input type=button class="button"  value="GO"  id="pagego"></td>
									<td width="41%">
									<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
								      <a href="<@spring.url "/tasklist/deluserList.nsf"/>?page=1&pagesize=${javapage.getPageSize()}&userId=${user.id}">首页</a>
									  <#if javapage.isHasPreviousPage()>
									  <a href="<@spring.url "/tasklist/deluserList.nsf"/>?page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}&userId=${user.id}">上页</a><#else>上页</#if>
									  <#if javapage.isHasNextPage()>
									  <a href="<@spring.url "/tasklist/deluserList.nsf"/>?page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}&userId=${user.id}">下页</a><#else>下页</#if>
									  <a href="<@spring.url "/tasklist/deluserList.nsf"/>?page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}&userId=${user.id}">尾页</a>
									 </div>
									</td>
								</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
