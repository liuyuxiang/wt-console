<#import "/spring.ftl" as spring />
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
  		 window.location.href='<@spring.url "/audit/auditList.nsf" />';
  	});
  });
      $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               f1.action='<@spring.url "/audit/auditSearchList.nsf" />?searchvalue='+searchvalue;
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
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
          var searchvalue=$("#searchType").val();
             f1.action="<@spring.url "/audit/auditSearchList.nsf" />?pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
             $("#f1").submit();
   
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/audit/auditSearchList.nsf" />?page="+pagegovalue+"&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#home").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/audit/auditSearchList.nsf" />?page=1&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
    $(function(){
     $("#last").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/audit/auditSearchList.nsf"/>?page=${javapage.getLastPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#previous").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/audit/auditSearchList.nsf"/>?page=${javapage.getPreviousPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
       $(function(){
     $("#next").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/audit/auditSearchList.nsf"/>?page=${javapage.getNextPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
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
                            <td> <input  class="button" type="button" id="backdept" value="返回审计列表" ></td> 
                            <td height="21" align="center" width="70%"> 
                            <input type="text"  name="searchcontent" onkeypress="checkEventName();" id="searchcontent" size="10" value="${searchcontent}">  
							  <select id="searchType" name="searchType" >
															<option value="user" <#if searchvalue=="user" >selected</#if>>用户</option>
															<option value="dept" <#if searchvalue=="dept" >selected</#if>>部门</option>
															<option value="group" <#if searchvalue=="group" >selected</#if>>组</option>
															<option value="consumer" <#if searchvalue=="consumer" >selected</#if>>操作者</option>
							  </select> 
							 <input  type="button" class="button"  id="searchSubmit" value="搜  索" >
							 </td>
                        </tr>
                        
                        <tr>
                            <td colspan="4">
				<table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<td >序号</td>
											<td >操作用户</td>
											<td >操作类型</td>
											<td width='20%'>修改前的值</td>
											<td width='20%'>修改后的值</td>
											<td >资源名称</td>
											<td >操作者IP</td>
		                                    <td >备注</td>
											<td >时间</td>
		                                <td style='display:none'><input type="checkbox" value="0" id="moveuser_All"  ></td>
										</tr>
										<#if  tasklist?has_content>
										<#list tasklist as user>
										    <#if  (user_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
											<td >${javapage.getDataStart()+1+user_index}</td>
											<td >${user.editPerson}</td>
											<td >${user.remark?if_exists?html}</td>
											<td >${user.beforeValue?if_exists?html}</td>
											<td >${user.afterValue?if_exists?html}</td>
											<td >${user.fieldName?if_exists?html}</td>
											<td >${user.operatorIpAdderss?if_exists?html}</td>
		                                    <td >${user.logid?if_exists?html}</td>
											<td >${user.editDate}</td>
		                                    <td style='display:none'><input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" ></td>
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