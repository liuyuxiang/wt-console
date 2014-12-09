<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

$(function(){
    $("#pagesizego").click(function(){
             $("#f1").submit();
   
  	});
     $("#pagego").click(function(){
  	        $("#f1").submit();
  	});
	$("#table1 button").addClass("button");
	
	$("#table1 button").each(function(){
		$(this).click(function(){
	        var date=new Date;
	        var time=parseInt(date.getSeconds());
			var uuid = $(this).attr("id");
			var url='<@spring.url "/audit/syncLog.nsf"/>?uuid='+uuid+"&time="+time;
			var returnVal=window.showModalDialog(url ,'','dialogWidth=800px;dialogHeight=400px;status=0;help=0');
		});
	});


    $("#searchSubmit").click(function(){
        alert("不好意思没开发完!");
        return false;
        var searchvalue=$("#searchType").val();
        var searchcontent=$("#searchcontent").val();
        if(searchcontent.length>0){
            f1.action='<@spring.url "/audit/syncLogSearchList.nsf" />?searchvalue='+searchvalue;
            $("#f1").submit();
        }else{
            alert("请输入查询内容!");
        }
    });

});
  
//]]>
</script>
<form name="f1" id="f1" method="post" action="<@spring.url "/audit/syncLogList.nsf" />">
	<table  cellspacing="0" width="100%" align="center"  >
		<tr>
			<td width="100%" align="left" valign="top">
				<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
				<tr>
					<td align="right" style="display:none;">
						 <input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEventName();" value="" size="10"> 
						 <select id="searchType" name="searchType" >
							<option value="user">用户</option>
							<option value="dept">部门</option>
							<option value="group">组</option>
							<option value="consumer">操作者</option>
						</select> 
						<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
					</td>
					<td height="21" align="right" width="18%">
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8">
				<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
					<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
						<td >序号</td>
						<td >操作状态</td>
						<td >操作类型</td>
						<td >资源类型</td>
						<td >资源ID</td>
						<td >资源名称</td>
						<td >操作者</td>
						<td >操作者IP地址</td>
						<td >操作时间</td>
						<td >查看详细</td>
					</tr>
					<#if  tasklist?has_content>
					<#list tasklist as log>
					    <#if  (log_index+1)%2=0>
					 	<tr style="background-color:#DAE9F9;" align="center">
					 	<#else>
					 	<tr style="background-color:#EEEEEE;" align="center">
					 	</#if>
						<td >${javapage.getDataStart()+1+log_index}</td>
						<td >
						<#switch log.status>
						<#case 2>
						<img src="<@spring.url "/style/default/images/successful.gif" />"/>
						<#break>
						<#case 1>
						<img src="<@spring.url "/style/default/images/failed.gif" />"/>
						<#break>
						<#case 4>
						<img src="<@spring.url "/style/default/images/warning.gif" />"/>
						<#break>
						<#default>
						<img src="<@spring.url "/style/default/images/question.gif" />"/>
						</#switch>
						</td>
						<td >${log.type}</td>
						<td >${log.resourceType?if_exists?html}</td>
						<td >${log.resourceId?if_exists?html}</td>
						<td >${log.resourceName?if_exists?html}</td>
						<td >${log.operatorName?if_exists?html}</td>
						<td >${log.operatorIpAdderss?if_exists?html}</td>
						<td >${log.date?if_exists?string}</td>
						<td ><button id="${log.uuid}">查看详细</button></td>
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
						<input size=1 value="${javapage.getPageSize()}" name="pagesize" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
						<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
						<input size=1 value="${javapage.getCurrentPage()}" name="page" id="pagegovalue"> 
						<input type=button class="button"  value="GO"  id="pagego"></td>
						<td width="41%">
						<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
					      <a href="<@spring.url "/audit/syncLogList.nsf"/>?page=1&pagesize=${javapage.getPageSize()}">首页</a>
						  <#if javapage.isHasPreviousPage()>
						  <a href="<@spring.url "/audit/syncLogList.nsf"/>?page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
						  <#if javapage.isHasNextPage()>
						  <a href="<@spring.url "/audit/syncLogList.nsf"/>?page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
						  <a href="<@spring.url "/audit/syncLogList.nsf"/>?page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
						 </div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
