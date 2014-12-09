<#if groupChildrens??>
<#list groupChildrens as group>
<#compress><#if group?exists>${group.name},</#if></#compress>
</#list> 
</#if>

