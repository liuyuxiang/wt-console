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
			 	$("#rightform").attr("action",'<@spring.url "/taskCandidate/taskCandidateMain.nsf"/>');
			 }else if(orgid=="1"){
			 	return false;
			 }else{
			    $("#rightform").attr("action",'<@spring.url "'+orgid+'"/>');
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
			<#if superstatus&&task.id=="0">
			    <li id="${task.id}"><span class="text" id="text">${task.name?html}</span></li>
			</#if>
			<#if userstatus&&task.id=="1">
			    <li id="${task.id}"><span class="text" id="text">${task.name?html}</span>
			       <ul class="listTree">
			         <#if tasklist ??>
			         <#list tasklist as tlist>
			            <#if superstatus||taskListService.isAuthorForTaskList(tlist.uuid)>
			                <li id="${tlist.linkUrl}" name="${tlist.uuid}"><span class="text" id="text">${tlist.linkName?html}</span>
			                </li>
			            </#if>
			         </#list>
			         </#if>
			       </ul>
			    </li>
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
