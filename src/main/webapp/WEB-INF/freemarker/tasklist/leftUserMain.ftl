<#import "/spring.ftl" as spring />

<#include "/style/style.ftl" >

<#include "/script/jquery.ftl" >
<#if  loginuser ?? >
<iframe name="rightframe" data-app="uum"  src="<@spring.url "/tasklist/leftUserList.nsf"/>?userId=${userId}" frameborder="0" width="99%"  height="470" scrolling=auto></iframe>
<#else>
<font color="red">你还没登录！</font>
</#if>
</body>
