<#import "/spring.ftl" as spring />
<#list deptChildren as dept>

<li id="${dept.uuid}"><span style="vertical-align: middle;" >${dept.name?html}</span>
		<ul class="ajax">
			<li>{url:<@spring.url "/user/simpletree/publicajax.nsf?id="+dept.uuid+"&type="+type+"&changetype=${changetype}&groupuuids=${groupuuids?if_exists}"/>}</li>
		</ul>
</li>

</#list>
			<#list members as user>
			<li id="${user.uuid}" ><input style="vertical-align: middle;" type="${type}" value="${user.uuid}" id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${user.name?html}</span>
			</li>
			</#list>
