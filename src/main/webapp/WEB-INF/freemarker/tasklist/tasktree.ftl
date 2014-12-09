<#import "/spring.ftl" as spring />

<#if taskTreeRoot?? >

<#include "/style/jquery.simpletree.ftl" >

<#include "/script/jquery.simpletree.ftl" >

<script type="text/javascript">
//<![CDATA[

	var simpleTreeCollection;
	$(function(){
		simpleTreeCollection = $('.simpleTree').simpleTree({
			autoclose: true,
			afterClick:function(destination,node){
			 var orgid=destination.attr('id');
			 if(orgid=="0"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/userMain.nsf"/>');
			 }else if(orgid=="1"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/appMain.nsf"/>');
			 }else if(orgid=="2"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/delMain.nsf"/>');
			 }else if(orgid=="3"){
			 	$("#rightform").attr("action",'<@spring.url "/userTemp/newUserMain.nsf"/>');
			 }else if(orgid=="4"){
			 	$("#rightform").attr("action",'<@spring.url "/userTemp/updateUserMain.nsf"/>');
			 }else if(orgid=="5"){
			 	$("#rightform").attr("action",'<@spring.url "/deptTemp/newDepartmentMain.nsf"/>');
			 }else if(orgid=="7"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/deptMain.nsf"/>');
			 }else if(orgid=="8"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/deptDelLog.nsf"/>');
			 }else if(orgid=="9"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/userDelLog.nsf"/>');
			 }else if(orgid=="10"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/groupDelLog.nsf"/>');
			 }else if(orgid=="11"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/adddept.nsf"/>');
			 }else if(orgid=="12"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/adduser.nsf"/>');
			 }else if(orgid=="13"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/batchUpdateDept.nsf"/>');
			 }else if(orgid=="14"){
			 	$("#rightform").attr("action",'<@spring.url "/tasklist/batchUpdateUser.nsf"/>');
			 }else if(orgid=="15"){
			 	$("#rightform").attr("action",'<@spring.url "/sync/synMain.nsf"/>');
			 }else{
			 	$("#rightform").attr("action",'<@spring.url "/deptTemp/updateDepartmentMain.nsf"/>');
			 }
			 $("#rightform").submit();
			},
			afterDblClick:function(node){
			},
			afterMove:function(destination, source, pos){
			},
			afterAjax:function()
			{
			},
			animate:false,
			drag:false,
			stylePath:"<@spring.url "/style/jquery/simpletree/" />"
		});
	});
	 
//]]>

</script>

</#if>

<#if taskTreeRoot?? >
	<div>
	<ul class="simpleTree">
	<li class="root" id="" ><span><@spring.message "task" /></span>
		<ul>
			<#list taskChildren as task>
			<#if userstatus&&task.id=="0">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if userstatus&&task.id=="7">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if appstatus&&task.id=="1">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="2">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="3">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="4">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="5">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="6">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="8">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="9">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="10">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="11">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="12">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="13">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="14">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if superstatus&&task.id=="15">
			<li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<#else>
	<@spring.message "department.notfound" />
</#if>
 <form target="rightframe" id="rightform" style="display:none" action="" >
 <input id="rightformOrgId" type="hidden" name="userId" value="${userid}" >
 </form>
