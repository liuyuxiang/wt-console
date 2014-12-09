<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
	
 $(function(){
     $("#pagesizego").click(function(){
             userlog.action="<@spring.url "/user/userlog.nsf" />";
             $("#userlog").submit();
   
  	});
  });
    $(function(){
     $("#pagego").click(function(){
            userlog.action="<@spring.url "/user/userlog.nsf" />";
  	        $("#userlog").submit();
  	});
  });

//]]>
</script>
<form name="userlog" id="userlog" method="post" action="">
<input type="hidden" name="userid" value="${userid}">
<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<td >序号</td>
											<td >属性标识</td>
											<td >修改前的值</td>
											<td >修改后的值</td>
											<td >用户描述</td>
											<td >修改人</td>
											<td >修改时间</td>
										</tr>
										<#if  loglist ?? >
										<#list loglist as userlog>
										    <#if  (userlog_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
											<td >${logpage.getDataStart()+1+userlog_index}</td>
											<td >${userlog.logid!"&nbsp;"}</td>
											<td >${userlog.beforeValue!"&nbsp;"}</td>
											<td >${userlog.afterValue!"&nbsp;"}</td>
											<td >${userlog.remark!"&nbsp;"}</td>
											<td >${userlog.editPerson!"&nbsp;"}</td>
											<td >${userlog.editDate!"&nbsp;"}</td>
										</tr>
										</#list>
										</#if>
<tr>
<td align=left colspan="7">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
					<tr>
						<td colspan="4" align="left">共${logpage.getQuantityOfData()} 条记录   每页 
							<input size=1 name="pagesize" value="${logpage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
							<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
							<input size=1 name="page" value="${logpage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
							<input type=button class="button" value="GO" id="pagego"></td>
						<td colspan="3">
							<div align="right">页次${logpage.getCurrentPage()}/${logpage.lastPage}
								<a id="home" href="<@spring.url "/user/userlog.nsf"/>?userid=${userid}&page=1&pagesize=${logpage.getPageSize()}">首页</a>
								<#if logpage.isHasPreviousPage()>
								<a id="previous" href="<@spring.url "/user/userlog.nsf"/>?userid=${userid}&page=${logpage.getPreviousPage()}&pagesize=${logpage.getPageSize()}">上页</a><#else>上页</#if>
								<#if logpage.isHasNextPage()>
								<a id="next" href="<@spring.url "/user/userlog.nsf"/>?userid=${userid}&page=${logpage.getNextPage()}&pagesize=${logpage.getPageSize()}">下页</a><#else>下页</#if>
								<a id="last" href="<@spring.url "/user/userlog.nsf"/>?userid=${userid}&page=${logpage.getLastPage()}&pagesize=${logpage.getPageSize()}">尾页</a>
							</div>
						</td>
					</tr>
</table>
</td>
</tr>
</table>
</form>
