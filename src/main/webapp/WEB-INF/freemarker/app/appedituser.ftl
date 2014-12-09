<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script language="javascript" type="text/javascript">
	function back(){
	 var groupuuid=$("#groupuuid").val();
  	 window.location.href='<@spring.url "/app/appuserlist.nsf"/>?groupuuid='+groupuuid;
  	}
	function c(uuid,typeuuid,str){
		document.getElementById(uuid).value = typeuuid+str;
	}
	function addInput(){
		var newElement = document.Document.createElement('label'); 
		newElement.Element.setAttribute('value', 'Username:');
		var usernameText = document.Document.getElementById('username'); 
		usernameText.appendChild(newElement); 
	}
	
	$(function(){
		$("#clogin").click(function(){
		var otherSystem = document.getElementById("otherSystem");//.parentElement;
			if($("#clogin").attr("checked")){
				$("#loginuser").attr("readOnly","");
				if($("#hidloginuser").val()==""){
					$("#hidloginuser").val("${userid}");
					$("#loginuser").val($("#hidloginuser").val());
				}else{
					$("#loginuser").val($("#hidloginuser").val());
				}
				//for(var i=0;i<otherSystem.length;i++){
				    //otherSystem.style.display="block";
					otherSystem.style.visibility="visible";
				//}
			}else{
				$("#loginuser").attr("readOnly","true");
				//for(var i=0;i<otherSystem.length;i++){
				    //otherSystem.style.display="none";
					otherSystem.style.visibility="hidden";
				//}
			}
		});
	});
	
</script>
	<form action="<@spring.url "/app/appeditusersuc.nsf"/>" method="post">
	<input type="hidden" name="groupuuid" id="groupuuid" value="${groupuuid}"/>
	<input type="hidden" name="useruuid" id="useruuid" value="${useruuid}"/>
	<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" border="0" cellspacing="2" cellpadding="0">
			<tr >
				<td height="10px" align="right" colspan="3"   bgcolor="#fffff0">用户ID</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="userid" disabled="disabled" id="userid" value="${userid}"/></td>
			</tr>
			<tr >
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">用户姓名</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="username" disabled="disabled" id="username" value="${username}"/></td>
			</tr>
			<tr >
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">允许登录</td>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="hidden" name="attuuids" id="attuuids" value="${attrflaguuid}"/>
					<input type="checkbox" name="clogin" id="clogin" <#if flag=="TRUE"||flag=="true">checked</#if>/></td>
			</tr>
			<#if showflag=true>
			<tr>
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">登陆系统用户名</td>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="hidden" name="attvalues" id="attvalues" value="${attrnameuuid}"/>
					<input type="hidden" name="hidloginuser" id="hidloginuser" value="${loginname}"/>
					<input type="text" name="loginuser" id="loginuser" <#if flag!="TRUE"&&flag!="true">readonly</#if> value="${loginname}"/></td>
			</tr>
			
			<tr >
				<td height="10px" align="right" colspan="3"  bgcolor="#fffff0">应用系统登录密码</td>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				     <input type="hidden" name="hidloginuserpwd" id="hidloginuserpwd" value="${attrpwduuid}"/>
					<input type="password" name="loginpwd" id="loginpwd" value="${pwd}" /></td>
			</tr>
			</#if>
			<#if listAtt?has_content>
			<tr ><td colspan="6" align="left">
			<div name="otherSystem" id="otherSystem" <#if flag!="TRUE"&&flag!="true">style="visibility:hidden"</#if>>
				<table style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
				<td colspan="2" align="center" height="22px" bgcolor="#fffff0"><font color='red'>以下选项选择TRUE为允许访问，FALSE为不允许访问，默认为FALSE</font></td>
				</tr>
				<#list listAtt as attribute0>
				<tr>
				<td align="right" width="30%" bgcolor="#fffff0">${attribute0.type.name}系统</td>
				<td align="left" width="40%" bgcolor="#fffff0">
					<select  name="${attribute0.type.id}" id="${attribute0.type.id}" style="width=50%" >
						<#if attribute0.attValues[0]?exists&&attribute0.attValues[0].getValue()?exists >
							<option value="<#list attribute0.attValues as att><#if att.getValue()?exists>${att.getValue()}</#if></#list>" ><#list attribute0.attValues as att><#if att.getValue() ?exists>${att.getValue()}</#if></#list></option>
							<#if attribute0.type.candidateItems ??>
								<#list attribute0.type.candidateItems as a0>
									<#list attribute0.attValues as att>
									   <#if att.getValue() ??>
										<#if a0.value != att.getValue() >
											<option value="${a0.value}" >${a0.value}</option>
										</#if> 
										<#else>
										    <option value="${a0.value}" >${a0.value}</option>
										</#if> 
									</#list>
								</#list>
							</#if>
						<#else>
								<option value="false" >请选择是否登录</option>
							<#list attribute0.type.candidateItems as a0>
								<option value="${a0.value}" >${a0.value}</option>
							</#list>
						</#if>
					</select>
				</td>
			</tr>
				</#list></table>
			</div></td>
			</tr>
			</#if>
			<tr>
			<td align="center" colspan="6"  bgcolor="#fffff0">
				<input type="submit" class="button" value="提交"/>
	<input type="reset" class="button" value="重置"/>
	<input type="button" class="button" value="返回" onclick="back()"/>
			</td>
			</tr>
			</table>
	</form>
