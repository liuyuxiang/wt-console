<#import "/spring.ftl" as spring />
	<div class="header_background">
		<div class="header_logo"></div>
		<span class="header_welcome">${userDept.name?if_exists}&nbsp;${loginuser.name}&nbsp;欢迎您&nbsp;<a target="_self" href='<@spring.url "/logout.nsf"/>?logout=true'>注销</a></span>
	</div>
	<div style="clear:both;"></div>