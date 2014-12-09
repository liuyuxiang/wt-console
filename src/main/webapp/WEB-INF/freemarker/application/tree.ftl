<#import "/spring.ftl" as spring />

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
			 $("#rightformOrgId").val(orgid);
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

	<div>
	<ul class="simpleTree">
	<li class="root" id="" ><span><@spring.message "app" /></span>
		<ul>
<#if appList?has_content>
			<#list appList as app>
			<li id="${app.uuid}"><span class="text" id="text">${app.name?html}</span>
			</li>
			</#list>
</#if>
<#if  superstatus >
			<li id="create"><span class="text" id="text">新增应用</span>
			</li>
</#if>
		</ul>
	</li>
	</ul>
	</div>	
 <form target="rightframe" id="rightform" style="display:none" action="<@spring.url "/application/content.nsf"/>" >
 <input id="rightformOrgId" type="hidden" name="id" value="" >
 </form>
