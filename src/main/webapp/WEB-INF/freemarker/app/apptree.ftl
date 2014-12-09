<#import "/spring.ftl" as spring />

<#if appTreeRoot?? >

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
			 $("#rightformOrgUuid").val(orgid);
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

<#if appTreeRoot?? >
	<div>
		<ul class="simpleTree">
			<li class="root" id="rootuuid" ><a href="<@spring.url "/app/applist.nsf"/>" target="rightframe">应用系统配置管理</a>
				<ul>
					<#list appTreeRoot as dept>
					<li id="${dept.uuid}" class="folder-close"><span class="text" id="text">${dept.name?html}</span></li>
					</#list>
				</ul>
			</li>
		</ul>
	</div>	
<#else>
	<@spring.message "department.notfound" />
</#if>
 
 <form target="rightframe" id="rightform" style="display:none" action="<@spring.url "/app/appeditmain.nsf"/>" >
 <input id="rightformOrgUuid" type="hidden" name="appuuid" value="" >
 </form>
