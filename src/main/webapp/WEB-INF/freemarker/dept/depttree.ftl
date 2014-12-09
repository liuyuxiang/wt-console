<#import "/spring.ftl" as spring />

<#if deptTreeRoot?? >

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
				 //alert("text2-"+$('span:first',node).text());
			},
			afterMove:function(destination, source, pos){
				 //alert("destination-"+destination.attr('id')+" source-"+source.attr('id')+" pos-"+pos);
			},
			afterAjax:function()
			{
				// alert('Loaded');
			},
			animate:false,
			drag:false,
			stylePath:"<@spring.url "/style/jquery/simpletree/" />"
		});
	});
	 
//]]>
</script>

</#if>

<#if deptTreeRoot?? >
	<div>
	<ul class="simpleTree">
	<li class="root" id="${deptTreeRoot.uuid}" ><span><@spring.message "department" /></span>
		<ul>
			<#list deptChildren as dept>
			<li id="${dept.uuid}"><span class="text" id="text"> ${dept.name?html}</span>
				<#if dept.hasChildren>
					<ul class="ajax">
						<li>{url:<@spring.url "/dept/simpletree/ajax.nsf?id="+dept.uuid />}</li>
					</ul>
				</#if>
			</li>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<#else>
	<@spring.message "department.notfound" />
</#if>
 <form target="rightframe" id="rightform" style="display:none" action="<@spring.url "/getSubDept.nsf"/>" >
 <input id="rightformOrgUuid" type="hidden" name="id" value="" >
 </form>
