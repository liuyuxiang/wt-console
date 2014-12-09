<#import "/spring.ftl" as spring />

<#if attrTreeRoot?? >

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
			 var orgtype=destination.attr('type');
			 $("#rightformOrgId").val(orgid);
			 $("#rightformOrgType").val(orgtype);
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

<#if attrTreeRoot?? >
	<div>
	<ul class="simpleTree">
	<li class="root" id="" ><span><@spring.message "attribute" /></span>
		<ul>
			<#list attrChildren as attr>
			<li id="${attr.id}"><span class="text" id="text">${attr.name?html}</span>
			</li>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<#else>
	<@spring.message "department.notfound" />
</#if>
 <form target="rightframe" id="rightform" style="display:none" action="<@spring.url "/attr/list.nsf"/>" >
 <input id="rightformOrgId" type="hidden" name="id" value="" >
 <input id="rightformOrgType" type="hidden" name="type" value="" >
 </form>
