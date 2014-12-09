<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<script type="text/javascript">
//<![CDATA[
$(function(){
	$(".openMessage").click(function(){
		if($(this).val()=="点击查看"){
			$(this).parent().parent().next().toggle(true);
			$(this).val("点击关闭");
		}else{
			$(this).parent().parent().next().toggle(false);
			$(this).val("点击查看");
		}
	});
});
//]]>
</script>
<#if event??>
				<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
					<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
						<td >操作状态</td>
						<td >操作类型</td>
						<td >资源类型</td>
						<td >资源ID</td>
						<td >资源名称</td>
						<td >操作者</td>
						<td >操作者IP地址</td>
						<td >操作时间</td>
					</tr>
					<#assign log=event/>
					 	<tr style="background-color:#DAE9F9;" align="center">
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
						<td >${log.date?if_exists?string("yyyy-MM-dd HH:mm:ss")}</td>
					</tr>
				</table>
<br><br>
<#if eventParamList?has_content>
				<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
					<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
						<td >序号</td>
						<td >修改的属性值</td>
						<td >修改前的值</td>
						<td >修改后的值</td>
					</tr>
					<#list eventParamList as log>
					    <#if  (log_index+1)%2=0>
					 	<tr style="background-color:#DAE9F9;" align="center">
					 	<#else>
					 	<tr style="background-color:#EEEEEE;" align="center">
					 	</#if>
						<td >${1+log_index}</td>
						<td >${log.key}</td>
						<td >${log.originalValue?if_exists?html}</td>
						<td >${log.value?if_exists?html}</td>
					</tr>
					</#list>
				</table>
</#if>
<br><br>

<#if synclogList?has_content>
				<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
					<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
						<td >序号</td>
						<td >同步状态</td>
						<td >应用标识</td>
						<td >操作时间</td>
						<td >查看详细</td>
					</tr>
					<#list synclogList as log>
					    <#if  (log_index+1)%2=0>
					 	<tr style="background-color:#DAE9F9;" align="center">
					 	<#else>
					 	<tr style="background-color:#EEEEEE;" align="center">
					 	</#if>
						<td >${1+log_index}</td>
						<td >
						<#switch log.status>
						<#case 2>
						<img src="<@spring.url "/style/default/images/warning.gif" />"/>
						<#break>
						<#case 0>
						<img src="<@spring.url "/style/default/images/failed.gif" />"/>
						<#break>
						<#default>
						<img src="<@spring.url "/style/default/images/successful.gif" />"/>
						</#switch>

						</td>
						<td >${log.appId}</td>
						<td >${log.handleTime?if_exists?string}</td>
						<td >
						<#if log.syncErrorLog??>
						<#assign errorMessage=log.syncErrorLog/>
						<input type="button" class="openMessage" value="点击查看">
						</td>
						</tr>
						<tr style="display:none;">
						<td COLSPAN=5>
						<span>
						${errorMessage.errorMessage?if_exists?string}
						</span></td>
						<#else>
						处理正常
						</td>
						</#if>
						
						
					</tr>
					</#list>
				</table>
</#if>

</#if>
