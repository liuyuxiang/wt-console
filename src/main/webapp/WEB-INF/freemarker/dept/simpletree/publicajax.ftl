<#import "/spring.ftl" as spring />
<#list deptChildren as dept>
<#if mydeptuuid!=dept.uuid>
<li id="${dept.uuid}"><input style="vertical-align: middle;" type="${type}" value="${dept.uuid}" <#if deptuuids?contains(dept.uuid)> checked </#if> id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${dept.name?html}</span>
	<#if dept.hasChildren>
		<ul class="ajax">
			<li>{url:<@spring.url "/dept/simpletree/publicajax.nsf?id="+dept.uuid+"&type="+type+"&deptuuids=${deptuuids}&mydeptuuid=${mydeptuuid}&flag=${flag}" />}</li>
		</ul>
	</#if>
</li>
</#if>
</#list>