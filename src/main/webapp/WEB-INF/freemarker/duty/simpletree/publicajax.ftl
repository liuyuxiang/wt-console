<#import "/spring.ftl" as spring />
<#if users??>
			<#list users as user>
			<li id="${user.uuid}" ><input onchange="checkboxchanged(this)" style="vertical-align: middle;" type="checkbox" value="${user.uuid}" id="treedutyid" name="treedutyid" /><span style="vertical-align: middle;" >${user.name?html}</span><span id="${user.uuid}" style="display:none;">{"uuid":"${user.uuid?html}","id":"${user.id?html}","name":"${user.name?html}","type":"u"}</span>
			</li>
			</#list>
</#if>