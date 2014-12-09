<#import "/spring.ftl" as spring />
<#list groupList as group>

<li id="${group.uuid}"><input onchange="checkboxchanged(this)" style="vertical-align: middle;" type="checkbox" value="${group.uuid}" id="treegroupid" name="treegroupid" /><span style="vertical-align: middle;" >${group.name?html}</span><span id="${group.uuid}" style="display:none;">{"uuid":"${group.uuid?html}","id":"${group.code?html}","name":"${group.name?html}","type":"g"}</span>
		<ul class="ajax">
			<li>{url:<@spring.url "/group/simpletree/publicgajax.nsf?uuid="+group.uuid/>}</li>
		</ul>
</li>

</#list>
