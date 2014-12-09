<#import "/spring.ftl" as spring />
<#import "/user/userListMacro.ftl" as user1 />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>
</head>
<script type="text/javascript">
//<![CDATA[
	$(function() {
		$("table").tablesorter();
		
	});
 $(function(){
     $("#adddept").click(function(){
  		 window.location.href='<@spring.url "/dept/adddept.nsf" />?id=${deptChildren.uuid}';
  	});
  });
  
   $(function(){
     $("#adduser").click(function(){
  		// window.location.href='<@spring.url "/user/adduser.nsf" />?id=${deptChildren.uuid}';
  		window.location.href='<@spring.url "/w/createuser" />?id=${deptChildren.uuid}';
  	});
  });
  
    $(function(){
     $("#moveuser").click(function(){
       var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要移除吗？")){
		               var url="<@spring.url "/moveuser.nsf" />?id=${deptChildren.uuid}&userids="+valueStr+'&page='+pagegovalue+'&pagesize='+pagesizegovalue;
				       $.post(url,function(data){
				       		window.location.reload();
				           //window.location.href='<@spring.url "/dept/content.nsf" />?id=${deptChildren.uuid}';
				        });
                  }
                   }else{
                   alert("至少选择一个！");
                 }
  	});
  });
    $(function(){
     $("#rollbackuser").click(function(){
       var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要恢复人员原部门吗？")){
		               var url="<@spring.url "/rollbackuser.nsf" />?id=${deptChildren.uuid}&userids="+valueStr+'&page='+pagegovalue+'&pagesize='+pagesizegovalue;
				       $.post(url,function(data){
data=checkAjaxData(data);
				            if(data.indexOf("false")>0){
				            	var al = "以下人员恢复失败，请确认：\n";
				            	al+=data.replace("false,","");
				            	alert(al);
				            }
				       		window.location.reload();
				        });
                  }
                   }else{
                   alert("至少选择一个！");
                 }
  	});
  });
    $(function(){
     $("#leftuser").click(function(){
       var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("所选用户是离职状态吗？")){
		               var url="<@spring.url "/leftuser.nsf" />?id=${deptChildren.uuid}&userids="+valueStr+'&page='+pagegovalue+'&pagesize='+pagesizegovalue;
				       $.post(url,function(data){
				       	window.location.reload();
				           //window.location.href='<@spring.url "/dept/content.nsf" />?id=${deptChildren.uuid}';
				        });
                  }
                   }else{
                   alert("至少选择一个！");
                 }
  	});
  });
  $(function(){
     $("#deletuser").click(function(){
       var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要删除吗？")){
                		var url='<@spring.url "/deluser.nsf" />?id=${deptChildren.uuid}&userids='+valueStr+'&page='+pagegovalue+'&pagesize='+pagesizegovalue;
			            $.post(url,function(data){
			            	window.location.reload();
			            });
			            //window.location.href=
                }}else{
                  alert("至少选择一个！");
                }
  	});
  });
  
    $(function(){
     $("#deletuser_viewdept").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要删除吗？")){
			           window.location.href='<@spring.url "/deluser.nsf" />?id=${deptChildren.uuid}&viewstatus=viewsub&userids='+valueStr+'&page='+pagegovalue+'&pagesize='+pagesizegovalue;
                }}else{
                  alert("至少选择一个！");
                }
  	});
  });
  
$(function(){
    $("#updatedept").click(function(){
        if('${deptChildren.name?html?js_string}'=="ROOT"){
            alert("根节点不能更新!");
            return false;
        }else{
            window.location.href='<@spring.url "/dept/updatedept.nsf" />?id=${deptChildren.uuid}';
        }
    });
    $("#deldept").click(function(){
        if('${deptChildren.name}'=="ROOT"){
            alert("根节点不能删除!");
            return false;
        }else{
            if(confirm("真的要删除吗？")){
                var url="<@spring.url "/dept/existUserUnderDepartment.nsf"/>?id=${deptChildren.uuid}"
                $.post(url,function(data){
                    data=checkAjaxData(data);
                    if(data=="false"){
                        url = '<@spring.url "/deldeptfrom.nsf" />?id=${deptChildren.uuid}';
                        $.post(url,function(data){
                            window.parent.location.reload();
                        });
                    }else{ 
                        alert("该部门内存在用户，不能被删除!");
                        return false; 
                    }
                });
            }
        }
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
         var pagesizegovalue=$("#pagesizegovalue").val().replace(/[,]/g,'');
  		 window.location.href='<@spring.url "/dept/content.nsf" />?id=${deptChildren.uuid}&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego").click(function(){
		var pagegovalue=$("#pagegovalue").val().replace(/[,]/g,'');
		var pagesizegovalue=$("#pagesizegovalue").val().replace(/[,]/g,'');
		window.location.href='<@spring.url "/dept/content.nsf" />?id=${deptChildren.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
     $(function(){
     $("#pagesizego_v").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val().replace(/[,]/g,'');
  		 window.location.href='<@spring.url "/viewsubdept.nsf" />?id=${deptChildren.uuid}&viewstatus=viewsub&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego_v").click(function(){
           var pagegovalue=$("#pagegovalue").val().replace(/[,]/g,'');
           if(parseInt(pagegovalue)>parseInt("${javapage.lastPage}")){           
           	pagegovalue=${javapage.lastPage};
           }
           var pagesizegovalue=$("#pagesizegovalue").val().replace(/[,]/g,'');
  		 window.location.href='<@spring.url "/viewsubdept.nsf" />?id=${deptChildren.uuid}&page='+pagegovalue+'&viewstatus=viewsub&pagesize='+pagesizegovalue;
  	});
  });
  
    $(function(){
     $("#viewsubdept").click(function(){
           var pagegovalue=$("#pagegovalue").val().replace(/[,]/g,'');
           var pagesizegovalue=$("#pagesizegovalue").val().replace(/[,]/g,'');
           window.location.href='<@spring.url "/viewsubdept.nsf" />?id=${deptChildren.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue+'&viewstatus=viewsub';
  	});
  });
  $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$.trim($("#searchcontent").val());
             if(searchcontent.length>0){
               $("#searchcontent").val(searchcontent);
               f1.action='<@spring.url "/dept/deptsearch.nsf" />?id=${deptChildren.uuid}&searchvalue='+searchvalue;
               $("#f1").submit();
               }else{
                alert("请输入查询内容!");
                $("#searchcontent").val("");
                $("#searchcontent").focus();
               }
  	});
  });

   $(function(){
     $("#viewdept").click(function(){
           var pagegovalue=$("#pagegovalue").val().replace(/[,]/g,'');
           var pagesizegovalue=$("#pagesizegovalue").val().replace(/[,]/g,'');
          window.location.href='<@spring.url "/dept/content.nsf" />?id=${deptChildren.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue+'&viewstatus=viewme';
  	});
  });
    $(function(){
     $("#hydeptuser").click(function(){
  	   window.location.href='<@spring.url "/dept/restorationuser.nsf" />?id=${deptChildren.uuid?url}';
  	});
  });
//]]>
</script>
<body >
<form name="f1" id="f1" method="post" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="user" >
<tr><td colspan="2" style="font-size:12px;">当前部门:&nbsp;${deptChildren.path}</td></tr>
				<tr>
					        <td colspan="2" style="font-size:12px;">
					         <#if isDeptAdmin >
					                <input  type="button" id="deldept" class="button" value="删除当前机构"/> 
					                <input  type="button" id="adddept" class="button"  value="增加下级机构"/>
					                <input  type="button" id="updatedept" class="button"  value="修改属性"/>
					        </#if>   
					        </td>
			   </tr>
               <tr>
				              <td colspan="8" width="94%" align="left" valign="top">
											<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
													<td height="18" width="30%">
													&nbsp;
													 <#if viewstatus="viewme"><input type="checkbox"  id="viewsubdept"><#else><input type="checkbox" id="viewdept" checked ></#if>
					                                                                                       是否包含下级部门人员
					                                 </td>
					                                 <td> <#if    isDeptAdmin ><input  type="button" id="hydeptuser" class="button"  value="恢复部门下所有已删成员"><#else>&nbsp;</#if></td>
													<td nowrap height="21" width="52%" align="right">
														 <#if    isDeptAdmin ><input type="button" class="button"  id="adduser"  value="新增用户"><#else>&nbsp;</#if>
														 <input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEventName();" value="" size="10"> 
														 <select id="searchType" name="searchType" >
															<option value="username">按姓名搜索</option>
															<option value="userid">按ID搜索</option>
															<option value='deptid'>按部门ID搜索</option>
															<option value='deptname'>按部门名称搜索</option>
														</select> 
														<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
													</td>
													 <#if viewstatus="viewme">
													<td height="21" align="right" width="18%" nowrap>
													 <#if    isDeptAdmin >
														<input id="leftuser" type="button" style="display:none;" class="button"  value="离 职" > 
														<input id="deletuser" type="button"  class="button"  value="删 除" >
														<#if deptChildren.code!="noDepartment">
														<input id="moveuser" type="button" class="button"  value="移 除" >
														<#else>
														<input id="rollbackuser" type="button" class="button"  value="恢复" >
														</#if>
														<#else>&nbsp;</#if>
													</td>
													 <#else>
													 <td height="21" align="right" width="18%">
													 <#if    isDeptAdmin >
														<input id="deletuser_viewdept" type="button"  class="button"  value="删 除" > 
													</#if>
													</td>
													 </#if>
												</tr>
											</table>
									</td>
			 </tr>
			 <tr>
								<td colspan="3">
									<table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<#if macro ??>
											 <@user1.headers/>
											<#else><td >序号</td>
											<td >用户ID</td>
											<td >姓名</td>
											<td style='display:none;'>关联用户</td>
		                                    <td style='display:none;'>用户描述</td>
											<td >排序号</td>
											</#if>
											<#if isDeptAdmin >
		                                <td ><input type="checkbox" value="0" id="moveuser_All"  ></td>
		                                </#if>
										</tr>
										<#if  userlist ?exists >
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
										    <td ><a href="<@spring.url "/w/createuser"/>?id=${deptChildren.uuid}&userid=${user.uuid}">${user.name?if_exists?html}</a></td>
		                                    <td style='display:none;'>
		                                    <#assign uulist=uumService.getRelationUsers(user)/>
		                                    <#if uulist?exists>
		                                    <#list uulist as uu>
		                                    <#compress>${uu.id},</#compress>
		                                    </#list></#if>
		                                    </td>
		                                    <td style='display:none;'>&nbsp;</td>
											<td >${user.order!"0"}</td>
											</#if>
											<#if isDeptAdmin >
		                                    <td ><input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" onclick="subchick(this)"></td>
											</#if>
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
								
									 <#if viewstatus="viewme">
									 	<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
										<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
										<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
										<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
										<input type=button class="button"  value="GO"  id="pagego"></td>
										<td width="41%">
										<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
									      <a href="<@spring.url "/dept/content.nsf"/>?id=${deptChildren.uuid}&page=1&pagesize=${javapage.getPageSize()}">首页</a>
										  <#if javapage.isHasPreviousPage()>
										  <a href="<@spring.url "/dept/content.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
										  <#if javapage.isHasNextPage()>
										  <a href="<@spring.url "/dept/content.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
										  <a href="<@spring.url "/dept/content.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
										 </div>
									  <#else>
									  	<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
										<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
										<input type=button class="button"  value="确定" id="pagesizego_v"> &nbsp;&nbsp;跳页： 
										<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
										<input type=button class="button"  value="GO"  id="pagego_v"></td>
										<td width="41%">
									  		<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
										      <a href="<@spring.url "/viewsubdept.nsf"/>?id=${deptChildren.uuid}&page=1&pagesize=${javapage.getPageSize()}&viewstatus=viewsub">首页</a>
											  <#if javapage.isHasPreviousPage()>
											  <a href="<@spring.url "/viewsubdept.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}&viewstatus=viewsub">上页</a><#else>上页</#if>
											  <#if javapage.isHasNextPage()>
											  <a href="<@spring.url "/viewsubdept.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}&viewstatus=viewsub">下页</a><#else>下页</#if>
											  <a href="<@spring.url "/viewsubdept.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}&viewstatus=viewsub">尾页</a>
										 </div>
									 </#if>
									</td>
								</tr>
							</table>
							</td>
		   </tr>
  </table>
</form>
</body>
</html>