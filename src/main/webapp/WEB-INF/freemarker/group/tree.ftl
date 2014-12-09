<#import "/spring.ftl" as spring />

<#if groupTreeRoot?? >

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
			  $("#groupuuid").val(orgid);
		   var url="<@spring.url "/group/isapplicationgroup.nsf"/>"
	       $.post(url,{id:orgid},
	       function(data){
data=checkAjaxData(data);
	       if(data=="false"){
			 $("#rightform").submit();
	        }else{
	          $("#apprightform").submit();
	        }
	        });
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
<#if groupTreeRoot?? >
	<div>
	<ul class="simpleTree">
	<li class="root" id="${groupTreeRoot.uuid}" ><span><@spring.message "group" /></span>
		<ul>
			<#list groupChildren as group>
			<li id="${group.uuid}"><span class="text" id="text"> ${group.name?html}</span>
				<#if group.hasChildren>
					<ul class="ajax">
						<li>{url:<@spring.url "/group/simpletree/ajax.nsf?id="+group.uuid />}</li>
					</ul>
				</#if>
			</li>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<#else>
	<@spring.message "group.notfound" />
</#if>
 <form target="rightframe" id="rightform" style="display:none" action="<@spring.url "/group/content.nsf"/>" >
 <input id="rightformOrgUuid" type="hidden" name="id" value="" >
 </form>
  <form target="rightframe" id="apprightform" style="display:none" action="<@spring.url "/app/appcontent.nsf"/>" >
 <input id="groupuuid" type="hidden" name="groupuuid" value="" >
 </form>
