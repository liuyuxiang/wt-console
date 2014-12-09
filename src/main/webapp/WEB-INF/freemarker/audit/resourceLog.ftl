<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
	
 $(function(){
     $("#pagesizego").click(function(){
             $("#userlog").submit();
   
  	});
     $("#pagego").click(function(){
  	        $("#userlog").submit();
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

  });
  
  function pagegogogo(page){
    $("#pagegovalue").val(page);
  	$("#userlog").submit();
  }

//]]>
</script>
<form name="userlog" id="userlog" method="post" action="<@spring.url "/audit/resourceLog.nsf" />">
<input type="hidden" name="uuid" value="${uuid}">
	<table  cellspacing="0" width="100%" align="center"  >
		<tr>
			<td colspan="8">
				<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
					<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
						<td >序号</td>
						<td >操作状态</td>
						<td >操作类型</td>
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
						<input size=1 name="pagesize" value="${javapage.getPageSize()}" onkeypress="checkEventNUM();" id="pagesizegovalue" maxlength="3" > 条 
						<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
						<input size=1 name="page" value="${javapage.getCurrentPage()}" maxlength="3" id="pagegovalue"> 
						<input type=button class="button"  value="GO"  id="pagego"></td>
						<td width="41%">
						<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
					      <a href="javascript:pagegogogo(1);">首页</a>
						  <#if javapage.isHasPreviousPage()>
						  <a href="javascript:pagegogogo(${javapage.getPreviousPage()});">上页</a><#else>上页</#if>
						  <#if javapage.isHasNextPage()>
						  <a href="javascript:pagegogogo(${javapage.getNextPage()})">下页</a><#else>下页</#if>
						  <a href="javascript:pagegogogo(${javapage.getLastPage()})">尾页</a>
						 </div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
