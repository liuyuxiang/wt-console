<#import "/spring.ftl" as spring />
<#list groupChildren as group>
<li id="${group.uuid}"><span class="text" id="text" > ${group.name?html}</span>
	<#if group.hasChildren>
		<ul class="ajax">
			<li>{url:<@spring.url "/group/simpletree/ajax.nsf?id="+group.uuid />}</li>
		</ul>
	</#if>
</li>
</#list>