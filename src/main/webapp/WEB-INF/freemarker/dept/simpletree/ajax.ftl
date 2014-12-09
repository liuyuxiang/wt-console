<#import "/spring.ftl" as spring />
<#list deptChildren as dept>
<li id="${dept.uuid}"><span class="text" id="text" > ${dept.name?html}</span>
	<#if dept.hasChildren>
		<ul class="ajax">
			<li>{url:<@spring.url "/dept/simpletree/ajax.nsf?id="+dept.uuid />}</li>
		</ul>
	</#if>
</li>
</#list>