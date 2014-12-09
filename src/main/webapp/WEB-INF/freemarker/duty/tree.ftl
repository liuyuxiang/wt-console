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
	<li class="root" id="" ><span><@spring.message "duty" /></span>
		<ul>
<#if dutyList?has_content>
			<#list dutyList as duty>
			<li id="${duty.uuid}"><span class="text" id="text">${duty.name?html}</span>
			</li>
			</#list>
</#if>
<#if  superstatus >
			<li id="create"><span class="text" id="text">新增职务</span>
			</li>
</#if>
		</ul>
	</li>
	</ul>
	</div>	
 <form target="rightframe" id="rightform" style="display:none" action="<@spring.url "/duty/content.nsf"/>" >
 <input id="rightformOrgId" type="hidden" name="id" value="" >
 </form>
