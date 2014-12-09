<#import "/spring.ftl" as spring />
<#list deptChildren as dept>

<li id="${dept.uuid}"><input onchange="checkboxchanged(this)" style="vertical-align: middle;" type="${type}" value="${dept.uuid}" id="treedeptid" name="treedeptid" <#if selecteduuids?seq_contains(dept.uuid)>  checked </#if>/><span style="vertical-align: middle;" >${dept.name?html}</span><span id="${dept.uuid}" style="display:none;">{"uuid":"${dept.uuid?html}","id":"${dept.deptCode?html}","name":"${dept.name?html}","type":"d"}</span>
		<ul class="ajax">
			<li>{url:<@spring.url "/ud/simpletree/publicajax.nsf?id="+dept.uuid+"&type="+type+"&changetype=${changetype}&&selected=${selected}"/>}</li>
		</ul>
</li>

</#list>
			<#list members as user>
			<li id="${user.uuid}" ><input onchange="checkboxchanged(this)" style="vertical-align: middle;" type="${type}" value="${user.uuid}" id="treedeptid" name="treedeptid" <#if (selecteduuids?seq_contains(user.uuid))>  checked </#if>/><span style="vertical-align: middle;" >${user.name?html}</span><span id="${user.uuid}" style="display:none;">{"uuid":"${user.uuid?html}","id":"${user.id?html}","name":"${user.name?html}","type":"u"}</span>
			</li>
			</#list>
