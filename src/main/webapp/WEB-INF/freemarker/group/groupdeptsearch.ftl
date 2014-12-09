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
     $("#backgroup").click(function(){
  		 window.location.href='<@spring.url "/group/deptcontent.nsf" />?id=${groupChildren.uuid}';
  	});
  });
  $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
              var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               f1.action='<@spring.url "/group/groupdeptsearch.nsf" />?id=${groupChildren.uuid}&searchvalue='+searchvalue;
               $("#f1").submit();
               }else{
                alert("请输入查询内容!");
              }
  	});
  });
  
    $(function(){
     $("#chkListSel_All").click(function(){
  		 chkall("f1",this,"moveusers");
  	});
  });
$(function(){
     $("#deluser_All").click(function(){
  		 chkall("f1",this,"delusers");
  	});
  });
      function subchick1(obj){
  	if(!obj.checked){
  		document.getElementById("chkListSel_All").checked=false;
  	}
  }
      function subchick2(obj){
  	if(!obj.checked){
  		document.getElementById("deluser_All").checked=false;
  	}
  }
  
function chkall(input1,input2,name)
{
    var objForm = document.forms[input1];
    var objLen = objForm.length;
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
 $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
          var searchvalue=$("#searchType").val();
             f1.action="<@spring.url "/group/groupdeptsearch.nsf" />?id=${groupChildren.uuid}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
             $("#f1").submit();
   
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/group/groupdeptsearch.nsf" />?id=${groupChildren.uuid}&page="+pagegovalue+"&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#home").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/group/groupdeptsearch.nsf" />?id=${groupChildren.uuid}&page=1&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
    $(function(){
     $("#last").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/group/groupdeptsearch.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getLastPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#previous").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/group/groupdeptsearch.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getPreviousPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
       $(function(){
     $("#next").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/group/groupdeptsearch.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getNextPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#adddepttoadmingroup").click(function(){
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
			            f1.action="<@spring.url "/adddeptstoadmingroup.nsf" />?id=${groupChildren.uuid}&page=1&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue+"&depts="+valueStr;
			  	        $("#f1").submit();
                 }else{
                  alert("至少选择一个！");
                }
  	});
  });
$(function(){
     $("#deldepttoadmingroup").click(function(){
       	  var   a   =   document.getElementsByName("delusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}} 
                if(valueStr.length>0){
                if(confirm("真的要移除吗？")){
                 var pagegovalue=$("#pagegovalue").val();
                 var pagesizegovalue=$("#pagesizegovalue").val();
                 var searchvalue=$("#searchType").val();
		             var url="<@spring.url "/searchgroupmovedept.nsf" />?id=${groupChildren.uuid}&depts="+valueStr+"&page="+pagegovalue+"&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
				          f1.action=url;
				         $("#f1").submit();
                  }
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
                        <tr style="background-color:#DAE9F9;font-size:12px;"> 
                            <td height="18" width="22%">&nbsp;搜索结果&nbsp; </td><td>
                            <input  class="button" type="button" id="backgroup" value="返回【${groupChildren.name}】列表" ></td>
                            <td height="21" align="right" width="78%">
                            <input type="text"  name="searchcontent" id="searchcontent" size="10" onkeypress="checkEventName();" value="${searchcontent}">  
							  <select id="searchType" name="searchType" >
															<option value="username" <#if searchvalue=="username" >selected</#if> >按部门中文名搜索</option>
															<option value="userid" <#if searchvalue=="userid" >selected</#if>>按部门名称搜索</option>
							  </select> 
							 <input  type="button" class="button"  id="searchSubmit" value="搜  索" >
							  <input  class="button" type="button" id="adddepttoadmingroup"  value="加  入"  >
							  <input  class="button" type="button" id="deldepttoadmingroup"  value="移  除"  >
							 </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                            <table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
                                <tr style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">序号</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">部门名称</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">部门中文名</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">所在管理组</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF"><font color="#000000">部门排序号</font>
                                    </td>
                                    <td align="center" bgcolor="#97C6DF">
                                    <input type="checkbox" value="0" id="chkListSel_All" ></td>
                                    <td align="center" bgcolor="#97C6DF">
                                    <input type="checkbox" value="0" id="deluser_All" ></td>
                                </tr>
                                 
	                               <#if  deptlist ?? >
									<#list deptlist as dept>
									    <#if  (dept_index+1)%2=0>
									 	<tr style="background-color:#DAE9F9;" align="center">
									 	<#else>
									 	<tr style="background-color:#EEEEEE;" align="center">
									 	</#if>
										<td width="5%">${javapage.getDataStart()+1+dept_index}</td>
										<td  >${dept.code}</td>
										<td  >${dept.name?html}</td>
	                                    <td ><#if  uumService.getDepartmentManagedGroups(dept)  ??><#list uumService.getDepartmentManagedGroups(dept) as userdept>${userdept.name}<#if userdept_has_next>,</#if></#list></#if></td>
										<td  >${dept.order}</td>
	                                    <td  width="5%">
	                                   <#if    uumService.existDepartmentManagedUnderGroup(dept,groupChildren)  >
	                                                                                                    已加入
	                                    <#else>
	                                    <input type="checkbox" value="${dept.uuid}" id="moveusers" name="moveusers"  onclick="subchick1(this)">
	                                    </#if>
	                                    </td>
	                                    <td width="5%">
	                                    <#if    uumService.existDepartmentManagedUnderGroup(dept,groupChildren)>
	                                    <input type="checkbox" value="${dept.uuid}" id="delusers" name="delusers" onclick="subchick2(this)">
	                                    </#if></td>
									</tr>
									</#list>
									</#if>
                            

                            </table>
                            </td>
                        <tr>
                            <td align=left colspan="3">
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