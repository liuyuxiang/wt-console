<#macro headers>
	<#if userListHeaders??>
		<#list userListHeaders as userListHeader>
			<td>${userListHeader}</td>
		</#list>
	<#else>
		need [userListHeaders]!
	</#if>
</#macro>

<#macro cols ulUserUUID>
	<#if userListDatas??>
		<#assign userRowDatas=userListDatas[ulUserUUID]>
		<#list userListCols as col>
			<#switch col >
				<#case "num" >
					<td >${userRowDatas["num"]}</td >
				<#break>
				<#case "userID" >
					<td >${userRowDatas["userID"]}</td >
				<#break>
				<#case "userName" >
					<td >
						<#if deptChildren?? && userRowDatas["userObject"].status!=-1 >
							<a href="<@spring.url "/w/createuser"/>?id=${deptChildren.uuid}&userid=${ulUserUUID}">${userRowDatas["userName"]?html}</a>
						<#else>
							${userRowDatas["userName"]?html}
						</#if>
					</td >
				<#break>
				<#case "userDept" >
					<td >${userRowDatas["userDept"]?html}</td >
				<#break>
				<#case "erpCode" >
					<td >${userRowDatas["erpCode"]}</td >
				<#break>
				<#case "relatedUser" >
					<td >${userRowDatas["relatedUser"]}</td >
				<#break>
				<#case "userDescription" >
					<td >&nbsp;</td >
				<#break>
				<#case "orderNum" >
					<td >${userRowDatas["orderNum"]!"0"}</td>
				<#break>
				<#default>
					<td>&nbsp;</td>
			</#switch>
		</#list>
	<#else>
		need [userListDatas]!
	</#if>
</#macro>