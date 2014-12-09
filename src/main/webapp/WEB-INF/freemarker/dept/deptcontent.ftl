<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

$(function(){ 
     $("#deldept").click(function(){
		      if('${deptChildren.name}'=="ROOT"){
		        alert("根节点不能删除!");
		        return false;
		      }
		      else{
		      if(confirm("真的要删除吗？")){
		  		     var url="<@spring.url "/dept/existUserUnderDepartment.nsf"/>?id=${deptChildren.uuid}"
			         $.post(url,function(data){
data=checkAjaxData(data);
										       if(data=="false"){
										           window.location.href='<@spring.url "/deldeptfrom.nsf" />?id=${deptChildren.uuid}';
									  		       window.parent.location.reload();
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
     $("#adddept").click(function(){
  		 window.location.href='<@spring.url "/dept/adddept.nsf" />?id=${deptChildren.uuid}';
  	});
  });
  
  $(function(){
     $("#updatedept").click(function(){
  		 window.location.href='<@spring.url "/dept/updatedept.nsf" />?id=${deptChildren.uuid}';
  	});
  });

     $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/getSubDept.nsf" />?userId=${user.id}&id=${deptChildren.uuid}&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/getSubDept.nsf" />?userId=${user.id}&id=${deptChildren.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
  
//]]>
</script>
<form name="f1"  valign="top" id="f1" method="post" action="">
	<table  cellspacing="0" width="100%" valign="top" >
		<tr>
			<td width="100%" align="left" valign="top">
				<table width="100%"  valign="top" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
					        <td colspan="2" style="font-size:12px;">
					         <#if uumService.isDepartmentManager(uumService.getLoginUser(), deptChildren) >
					                <input  type="button" id="deldept" class="button" value="删除当前机构"/> 
					                <input  type="button" id="adddept" class="button"  value="增加下级机构"/>
					                <input  type="button" id="updatedept" class="button"  value="修改属性"/>
					        </#if>   
					        </td>
			   </tr>
				</table>
			</td>
		</tr>
		<tr height="100%">
			<td colspan="8">
				<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<td >序号</td>
		                                    <td >部门ID</td>
											<td >子部门名称</td>
		                                    <td >部门ERP编码</td>
		                                    <td >排序号</td>
										</tr>
										<#if  deptlist ?? >
										<#list deptlist as dept>
										    <#if  (dept_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
											<td >${javapage.getDataStart()+1+dept_index}</td>
		                                    <td ><#if  dept.deptCode ?? >${dept.deptCode}</#if></td>
											<td >${dept.name}</td>
		                                    <td ><#if  dept.code ?? >${dept.code}</#if></td>
		                                    <td ><#if  dept.order ?? >${dept.order}</#if></td>		                                   
										</tr>
										
										  
										</#list>
										</#if>
				</table>
			</td>
		</tr>
		<tr height="20px">
			<td align=right colspan="3">
				<table width="100%" height="20px" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
								<tr>
									<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
									<input size=1 value="${javapage.getPageSize()}" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
									<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
									<input size=1 value="${javapage.getCurrentPage()}" id="pagegovalue"> 
									<input type=button class="button"  value="GO"  id="pagego"></td>
									<td width="41%">
									<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
								      <a href="<@spring.url "/getSubDept.nsf"/>?page=1&id=${deptChildren.uuid}&pagesize=${javapage.getPageSize()}&userId=${user.id}">首页</a>
									  <#if javapage.isHasPreviousPage()>
									  <a href="<@spring.url "/getSubDept.nsf"/>?page=${javapage.getPreviousPage()}&id=${deptChildren.uuid}&pagesize=${javapage.getPageSize()}&userId=${user.id}">上页</a><#else>上页</#if>
									  <#if javapage.isHasNextPage()>
									  <a href="<@spring.url "/getSubDept.nsf"/>?page=${javapage.getNextPage()}&id=${deptChildren.uuid}&pagesize=${javapage.getPageSize()}&userId=${user.id}">下页</a><#else>下页</#if>
									  <a href="<@spring.url "/getSubDept.nsf"/>?page=${javapage.getLastPage()}&id=${deptChildren.uuid}&pagesize=${javapage.getPageSize()}&userId=${user.id}">尾页</a>
									 </div>
									</td>
								</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
