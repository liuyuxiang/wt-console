<#import "/spring.ftl" as spring />
<#import "/user/userListMacro.ftl" as user1 />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>

<#include "/style/style.ftl" >

<#include "/script/jquery.ftl" >

<script type="text/javascript">
//<![CDATA[

    $(function(){
     $("#backdept").click(function(){
  		 window.location.href='<@spring.url "/dept/content.nsf" />?id=${deptChildren.uuid}';
  	});
  });
    $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
                var searchcontent=$("#searchcontent").val();
             if(searchvalue.indexOf("ERP") > 0||searchvalue.indexOf("NEW")>0||searchcontent.length>0){
               f1.action="<@spring.url "/dept/deptsearch.nsf" />?id=${deptChildren.uuid}&searchvalue="+searchvalue;
               $("#f1").submit();
             }else{
              alert("请输入查询内容!");
             }
  	});
  });
    $(function(){
     $("#chkListSel_All").click(function(){
  		 chkall("f1",this);
  	});
  });
    $(function(){
     $("#delChkListSel_All").click(function(){
  		 chkall1("f1",this);
  	});
  });
  
    function subchick(obj){
  	if(!obj.checked){
  		document.getElementById("chkListSel_All").checked=false;
  	}
  }
    function subchick1(obj){
  	if(!obj.checked){
  		document.getElementById("delChkListSel_All").checked=false;
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
function chkall1(input1,input2)
{
    var objForm = document.forms[input1];
    var objLen = objForm.length;
    for (var iCount = 0; iCount < objLen; iCount++)
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
     $("#deletuser").click(function(){
       var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
       	  var   a   =   document.getElementsByName("moveusers1");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要删除吗？")){
                		var url='<@spring.url "/deluser.nsf" />?id=${deptChildren.uuid}&userids='+valueStr+'&page='+pagegovalue+'&pagesize='+pagesizegovalue;
			            $.post(url,function(data){
             f1.action="<@spring.url "/dept/deptsearch.nsf" />?id=${deptChildren.uuid}&pagesize="+pagesizegovalue+"&searchvalue="+$("#searchType").val();
             $("#f1").submit();
			            });
                }}else{
                  alert("至少选择一个！");
                }
  	});
  });

 $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
          var searchvalue=$("#searchType").val();
             f1.action="<@spring.url "/dept/deptsearch.nsf" />?id=${deptChildren.uuid}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
             $("#f1").submit();
   
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/dept/deptsearch.nsf" />?id=${deptChildren.uuid}&page="+pagegovalue+"&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#home").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/dept/deptsearch.nsf" />?id=${deptChildren.uuid}&page=1&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
    $(function(){
     $("#last").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/dept/deptsearch.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getLastPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#previous").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/dept/deptsearch.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getPreviousPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
       $(function(){
     $("#next").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/dept/deptsearch.nsf"/>?id=${deptChildren.uuid}&page=${javapage.getNextPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#addusertodept").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                        var pagegovalue=$("#pagegovalue").val();
			            var pagesizegovalue=$("#pagesizegovalue").val();
			            var searchvalue=$("#searchType").val();
			            f1.action="<@spring.url "/adduserstodept.nsf" />?id=${deptChildren.uuid}&page=1&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue+"&users="+valueStr;
			  	        $("#f1").submit();
                 }else{
                  alert("至少选择一个！");
                }
  	});
  });
//]]>
</script>
</head>
<body style="background:#FFFFFF;">
<form name="f1" id="f1" method="post" action="">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
            <table width="100%" border="0" align="center" cellpadding="0"
                cellspacing="0">
                <tr>
                    <td width="94%" align="left" valign="top">
                    <table id="table1" border="0" cellspacing="0" align="center"
                        width="95%">
                        <tr> 
                            <td height="18" width="10%" >&nbsp;搜索结果&nbsp;</td>
                            <td> <input  class="button" type="button" id="backdept" value="返回【${deptChildren.name}】列表" ></td> 
                            <td height="21" align="center" width="70%"> 
                            <input type="text"  name="searchcontent" onkeypress="checkEventName();" id="searchcontent" size="10" value="${searchcontent}">  
							  <select id="searchType" name="searchType" >
															<option value="username" <#if searchvalue=="username" >selected</#if> >按姓名搜索</option>
															<option value="userid" <#if searchvalue=="userid" >selected</#if>>按ID搜索</option>
															<option value='deptid' <#if searchvalue=="deptid" >selected</#if>>按部门ID搜索</option>
															<option value='deptname' <#if searchvalue=="deptname" >selected</#if>>按部门名称搜索</option>
															<#if macro ??><option value="useridERP"<#if searchvalue=="useridERP" >selected</#if>>按ERP用户编码搜索</option></#if>
							  </select> 
							 <input  type="button" class="button"  id="searchSubmit" value="搜  索" >
							 </td><td align="center" nowrap>
							  <#if isAdmin ><input  class="button" type="button" id="addusertodept"  value="加入" /></#if>
							  <#if isSuperAdmin ><input  class="button" type="button" id="deletuser"  value="删除" /></#if></td>
                        </tr>
                        
                        <tr>
                            <td colspan="4">
                            <table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
                                <tr style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
                                    <#if macro ??>
											 <@user1.headers/>
									<#else>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">序号</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">用户ID</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">姓名</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">所属部门</font>
                                    </td>
                                    </#if>
                                    <td style="display:none;" align="center" bgcolor="#97C6DF"><font color="#000000">关联用户</font>
                                    </td>
                                    <td style='display:none;' align="center" bgcolor="#97C6DF"><font color="#000000">用户描述</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF">
                                    <input type="checkbox" value="0" id="chkListSel_All" ></td>
                                    <#if isSuperAdmin >
                                    <td align="center" bgcolor="#97C6DF">
                                    <input type="checkbox" value="0" id="delChkListSel_All" ></td>
                                    </#if>
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
										<td ><a href="<@spring.url "/w/createuser"/>?id=${deptChildren.uuid}&userid=${user.uuid}">${user.name?html}</a></td>
	                                    <td ><#if  uumService.getUserDepartments(user) ?? ><#list uumService.getUserDepartments(user) as dept>${dept.path},</#list></#if></td>
	                                    </#if>
	                                    <td style="display:none;"><#if uumService.getRelationUsers(user) ??>
		                                    <#list uumService.getRelationUsers(user) as uu>
		                                    <#compress>${uu.id},</#compress>
		                                    </#list></#if>
		                                    </td>
	                                    <td style='display:none;'>&nbsp;</td>
	                                    <td >
	                                   <#if   uumService.existUserUnderDepartment(user,deptChildren)  >
	                                                                                                    已加入
	                                    <#else>
	                                    <input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" onclick="subchick(this)">
	                                    </#if>
	                                    </td>
	                                    <#if isSuperAdmin>
	                                    <td >	                                    
	                                   <#if uumService.isDepartmentManager(loginUser, user.primaryDepartment)  >
	                                    <input type="checkbox" value="${user.uuid}" id="moveusers1" name="moveusers1" onclick="subchick1(this)">
	                                    </#if>
	                                    </td></#if>
									</tr>
									</#list>
									</#if>
                            

                            </table>
                            </td>
                        <tr>
                            <td align=left colspan="4">
                             <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
								<tr>
									<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
									<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
									<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
									<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
									<input type=button class="button"  value="GO"  id="pagego"></td>
									<td width="41%">
									<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
								      <a href="#" id="home">首页</a>
									  <#if javapage.isHasPreviousPage()>
									  <a href="#" id="previous">上页</a><#else>上页</#if>
									  <#if javapage.isHasNextPage()>
									  <a href="#" id="next" >下页</a><#else>下页</#if>
									  <a href="#" id="last">尾页</a>
									 </div>
									</td>
								</tr>
							</table>
                            </td>
                        </tr>
                      
                    </table>
                    </td>
                </tr>
            </table>
            </td>
        </tr>
    </table>
</form>
    </body>
</html>