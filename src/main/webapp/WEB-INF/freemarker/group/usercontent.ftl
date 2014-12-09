<#import "/spring.ftl" as spring />
<#import "/user/userListMacro.ftl" as user1 />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

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
  $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
              var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               f1.action='<@spring.url "/group/groupusersearch.nsf" />?id=${groupChildren.uuid}&searchvalue='+searchvalue;
               $("#f1").submit();
               }else{
                alert("请输入查询内容!");
              }
  	});
  });
  $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/group/usercontent.nsf" />?id=${groupChildren.uuid}&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/group/usercontent.nsf" />?id=${groupChildren.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
  $(function(){
     $("#moveadmingroupuser").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}} 
                if(valueStr.length>0){
                if(confirm("真的要移除吗？")){
		             var url="<@spring.url "/moveadmingroupuser.nsf" />?id=${groupChildren.uuid}&userids="+valueStr;
				       $.post(url,function(data){
				           window.location.href='<@spring.url "/group/usercontent.nsf" />?id=${groupChildren.uuid}';
				        });
                  }
                   }else{
                   alert("至少选择一个！");
                 }
  	});
  });

//]]>
</script>
<form name="f1" id="f1" method="post" action="">
	 <table  cellspacing="0" width="100%" align="center" valign="top" >
	 	       <!-- <tr style="background-color:#DAE9F9;font-size:12px;">
					        <td colspan="2" style="font-size:12px;">
					                <input  type="button" id="viewgroupuser" class="button" value="查看当前组成员" > 
					                <input  type="button" id="addgroupuser" class="button"  value="增加组成员" >
					                <input  type="button" id="hygroupuser" class="button"  value="恢复当前组成员"> 
					        </td>
			    </tr>-->
					 <tr>
									<td colspan="8" width="100%" align="left" valign="top">
											<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
													<td height="18" width="30%">&nbsp;【${groupChildren.name}】可管理用户列表</td>
													<td height="21" width="52%" align="right">
														 <input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEventName();" value="" size="10"> 
														 <select id="searchType" name="searchType" >
															<option value="username">按姓名搜索</option>
															<option value="userid">按ID搜索</option>
														</select> 
														<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
													</td>
													<td height="21" align="right" width="18%">
														<input id="moveadmingroupuser" type="button" class="button"  value="移 除" >
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
											<td >排序号</td>
											</#if>
		                                    <td >用户所在管理组</td>
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
											<td >${user.name?html}</td>
		                                    <td >${user.order}</td>
		                                    </#if>
		                                    <td ><#if  uumService.getUserManagedGroups(user)  ??><#list uumService.getUserManagedGroups(user) as usergroup>${usergroup.name}<#if usergroup_has_next>,</#if></#list></#if></td>
											<td ><input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" onclick="subchick(this)"></td>
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
													<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
													<input type="button" class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
													<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
													<input type=button class="button"  value="GO"  id="pagego"></td>
													<td width="41%">
													<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
												      <a href="<@spring.url "/group/usercontent.nsf"/>?id=${groupChildren.uuid}&page=1&pagesize=${javapage.getPageSize()}">首页</a>
													  <#if javapage.isHasPreviousPage()>
													  <a href="<@spring.url "/group/usercontent.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
													  <#if javapage.isHasNextPage()>
													  <a href="<@spring.url "/group/usercontent.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
													  <a href="<@spring.url "/group/usercontent.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
													 </div>
													</td>
												</tr>
											</table>
									</td>
					 </tr>
	  </table>
</form>