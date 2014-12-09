<#import "/spring.ftl" as spring />
<script type="text/javascript">
//<![CDATA[
	
  function check(obj1){
    if(obj1.checked){
	      var url='<@spring.url "/getSubGroup.nsf" />?pauuid='+obj1.value;
				       $.post(url,function(data){
	      					document.getElementById("pa"+data).checked=true;				            
				        });
	   }
  }
//]]>
</script>
<#list groupChildren as group>
	 <#if mygroupuuid!=group.uuid>
	 <#if isCqGroup??>
	    <li id="${group.uuid}" ><input style="vertical-align: middle;" type="${type}" <#if autoChecked??><#if autoChecked='true'> onclick="check(this);"</#if></#if> value="${group.uuid}" <#if groupuuids?contains(group.uuid)>  checked </#if> id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${group.name?html}</span>
		<#if group.hasChildren>
			<ul class="ajax">
				<li>{url:<@spring.url "/group/simpletree/publicajax.nsf?id="+group.uuid+"&type="+type+"&groupuuids=${groupuuids}&mygroupuuid=${mygroupuuid}&flag=${flag}&adminGroups=${adminGroups?if_exists}" />}</li>
			</ul>
		</#if>
	<#elseif (isSuper??&&isSuper)>
		<li id="${group.uuid}" ><input style="vertical-align: middle;" type="${type}" value="${group.uuid}" <#if groupuuids?contains(group.uuid)>  checked </#if> id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${group.name?html}</span>
	   <#if group.hasChildren>
		<ul class="ajax">
			<li>{url:<@spring.url "/group/simpletree/publicajax.nsf?id="+group.uuid+"&type="+type+"&groupuuids=${groupuuids}&mygroupuuid=${mygroupuuid}&flag=${flag}&adminGroups=${adminGroups?if_exists}" />}</li>
		</ul>
	   </#if>
	<#else>
		<li id="${group.uuid}" ><input style="vertical-align: middle;" <#if adminGroups??&&!adminGroups?contains(group.uuid)>disabled</#if> type="${type}" value="${group.uuid}" <#if groupuuids?contains(group.uuid)>  checked </#if> id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${group.name?html}</span>
	   <#if group.hasChildren>
		<ul class="ajax">
			<li>{url:<@spring.url "/group/simpletree/publicajax.nsf?id="+group.uuid+"&type="+type+"&groupuuids=${groupuuids}&mygroupuuid=${mygroupuuid}&flag=${flag}&adminGroups=${adminGroups?if_exists}" />}</li>
		</ul>
	   </#if>
	</#if>
</li>
</#if>
</#list>