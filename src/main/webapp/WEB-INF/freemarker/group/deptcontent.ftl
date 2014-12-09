<#import "/spring.ftl" as spring />
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
               f1.action='<@spring.url "/group/groupdeptsearch.nsf" />?id=${groupChildren.uuid}&searchvalue='+searchvalue;
               $("#f1").submit();
               }else{
                alert("请输入查询内容!");
              }
  	});
  });
     $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/group/deptcontent.nsf" />?id=${groupChildren.uuid}&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/group/deptcontent.nsf" />?id=${groupChildren.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
 
$(function(){
     $("#deletdept").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}} 
                if(valueStr.length>0){
                if(confirm("真的要移除吗？")){
                 var pagegovalue=$("#pagegovalue").val();
                 var pagesizegovalue=$("#pagesizegovalue").val();
		             var url="<@spring.url "/groupmovedept.nsf" />?id=${groupChildren.uuid}&depts="+valueStr+"&page="+pagegovalue+"&pagesize="+pagesizegovalue;
				       $.post(url,function(data){
				           window.location.href='<@spring.url "/group/deptcontent.nsf" />?id=${groupChildren.uuid}';
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
	 <table  cellspacing="0" width="100%" align="left" valign="top" >
					 <tr>
					         <td colspan="8" width="100%" align="left" valign="top">
											<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
													<td height="18" width="30%">&nbsp;【${groupChildren.name}】可管理部门列表</td>
													<td height="21" width="52%" align="right">
														 <input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEventName();" value="" size="10"> 
														 <select id="searchType" name="searchType" >
															<option value="username">按部门中文名搜索</option>
															<option value="userid">按部门名称搜索</option>
														</select> 
														<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
													</td>
													<td height="21" align="right" width="18%">
														<input id="deletdept" type="button"  class="button"  value="移 除" > 
													</td>
												</tr>
											</table>
				          </td>
					 </tr>
					 
					 <tr>
								<td colspan="4">
									 <table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<td >序号</td>
											<td >部门名称</td>
											<td >部门中文名</td>
		                                    <td >上级部门</td>
											<td >部门排序号</td>
		                                <td ><input type="checkbox" value="0" id="moveuser_All"  ></td>
										</tr>
									 <#if  deptlist ?? >
										<#list deptlist as dept>
										    <#if  (dept_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
											<td >${javapage.getDataStart()+1+dept_index}</td>
											<td ><#if  dept.code ?? >${dept.code?html}</#if></td>
											<td >${dept.name?html}</td>
		                                    <td ><#if  dept.getParent() ?? >${dept.getParent().getName()}</#if></td>
											<td >${dept.order}</td>
		                                    <td ><input type="checkbox" value="${dept.uuid}" id="moveusers" name="moveusers" onclick="subchick(this)"></td>
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
													<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
													<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
													<input type=button class="button"  value="GO"  id="pagego"></td>
													<td width="41%">
													<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
												      <a href="<@spring.url "/group/deptcontent.nsf"/>?id=${groupChildren.uuid}&page=1&pagesize=${javapage.getPageSize()}">首页</a>
													  <#if javapage.isHasPreviousPage()>
													  <a href="<@spring.url "/group/deptcontent.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
													  <#if javapage.isHasNextPage()>
													  <a href="<@spring.url "/group/deptcontent.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
													  <a href="<@spring.url "/group/deptcontent.nsf"/>?id=${groupChildren.uuid}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
													 </div>
													</td>
												</tr>
											</table>
									</td>
					 </tr>
	  </table>
</form>