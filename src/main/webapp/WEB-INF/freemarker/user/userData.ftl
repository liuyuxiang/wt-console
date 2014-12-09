<#if type??>
	<table class="tableborder1" align="center" width="100%" border="0" cellspacing="2"	cellpadding="0">
	<#if type='1'||type='2'>
		<#list attributelist0 as attribute1>
		<TR class='tb1'>
			<TD align="center"   bgcolor="#fffff0" style="width:30%">${attribute1.type.name}</TD>
		    <TD align="left"  bgcolor="#fffff0"  colspan="3">
				<#switch attribute1.type.pageType >
				<#case "select" >
					<select  name="${attribute1.type.id}" id="${attribute1.type.id}" style="width=50%" >
						<#if attribute1.attValues[0]?exists&&attribute1.attValues[0].getValue()?? >
							<option value="<#list attribute1.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list>" ><#list attribute1.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list></option>
							<#if attribute1.type.candidateItems ??>
								<#list attribute1.type.candidateItems as a1>
									<#list attribute1.attValues as att>
										<#if a1.value != att.getValue() >
											<option value="${a1.value}" >${a1.value}</option>
										</#if> 
									</#list>
								</#list>
							</#if>
						<#else>  
							<#list attribute1.type.candidateItems as a1>
								<option value="${a1.value}" >${a1.value}</option>
							</#list>
						</#if> 
					</select>
					<#break>
				<#case "checkbox" >
						<#--if attribute1.type.candidateItems ??>
							<#list attribute1.type.candidateItems as a1>
								<input type="checkbox" id="${a1.value}" value="${a1.value}" <#if a1.isDefault??&&a1.isDefault>checked</#if> name="${attribute1.type.id+a0_index}"/><label for="${a1.value}">${a1.value}</label>
							</#list>
						</#if-->
					<#break>
				<#case "radio" >
					<#if attribute1.attValues[0]?exists&&attribute1.attValues[0].getValue()?? >
						<input type="radio" id="${attribute1.type.id}" value="${attribute1.attValues[0].getValue()}" checked name="${attribute1.type.id}"/><label for="${attribute1.attValues[0].getValue()}">${attribute1.attValues[0].getValue()}</label>
						<#if attribute1.type.candidateItems ??>
							<#list attribute1.type.candidateItems as a1>
								<#list attribute1.attValues as att>
									<#if att.getValue()!=a1.value>
										<input type="radio" id="${a1.value}" value="${a1.value}" name="${attribute1.type.id}"/><label for="${a1.value}">${a1.value}</label>
									</#if>
								</#list>
							</#list>
						</#if>
					<#else>
						<#if attribute1.type.candidateItems ??>
							<#list attribute1.type.candidateItems as a1>
								<input type="radio" id="${a1.value}" value="${a1.value}" name="${attribute1.type.id}"/><label for="${a1.value}">${a1.value}</label>
							</#list>
						</#if>
					</#if>
					<#break>
				<#case "textarea" >
					<textarea   name="${attribute1.type.id}" id="${attribute1.type.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)"><#list uumService.getStringValuesUnderAttribute(attribute0) as att><#if att.getValue() ??>${att.getValue()}</#if></#list></textarea>&nbsp;&nbsp;长度不能超过100个字符
					<#break>
				<#default>
					<input id="${attribute1.type.id}" name="${attribute1.type.id}" typename="${attribute1.type.name}" <#if attribute1.type.rule?exists && attribute1.type.rule!="0"> onblur="checkType(this);"</#if> attvalue="${attribute1.type.value?if_exists}" onkeypress="checkEvent();" value="<#list attribute1.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list>" style="width:70%" maxlength="<#if attribute1.type.length?exists>${attribute1.type.length}<#else>100</#if>"  />
				</#switch>
			</TD>
		</TR>
	 </#list>
	 <#else>
		  <#list attributelist0 as attribute3>
		  <#if  (attribute3_index)%2=0>
		   <tr>
		     <td align="center"  bgcolor="#fffff0" width="25%">
		 ${attribute3.type.name}
		     </td>
		     <td align="center"  bgcolor="#fffff0" width="25%">
				<input id="${attribute3.type.id}" name="${attribute3.type.id}" readonly
				 <#if attribute3.attValues[0]?exists>
				   <#if attribute3.attValues[0].getValue() ??>
				       <#if attribute3.attValues[0].getValue()!="false">
				          value="${attribute3.attValues[0].getValue()}"
				       </#if>
				   </#if>		 
				 </#if>
				 style="width:70%" maxlength="15"  />
		     </td>
		  <#elseif (attribute3_index+1)%2=0>
		     <td align="center"  bgcolor="#fffff0" width="25%">
		 ${attribute3.type.name}
		     </td>
		     <td align="center"  bgcolor="#fffff0" width="25%">
		     	<input readonly value="<#if attribute3.attValues[0]?exists><#if attribute3.attValues[0].getValue()??><#if attribute3.attValues[0].getValue()=="TRUE"||attribute3.attValues[0].getValue()=="true">是<#else>否</#if><#else>否</#if><#else>否</#if>" >
				<input type="hidden" id="${attribute3.type.id}" name="${attribute3.type.id}" readonly
				 value="<#if attribute3.attValues[0]?exists><#if attribute3.attValues[0].getValue()??><#if attribute3.attValues[0].getValue()=="true">true<#else>false</#if><#else>false</#if><#else>false</#if>"
				 style="width:70%" maxlength="15"  />
		     </td>
		  </tr>
		  </#if>
		  </#list>
	 </#if>
	</table>
</#if>
