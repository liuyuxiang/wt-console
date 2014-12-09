<#import "/spring.ftl" as spring />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改应用</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
 $(function(){
     $("#searchgroup").click(function(){
     var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#admingroupuuid").val()+'&mygroupuuid=no&flag=true&time='+time;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#admingroupuuid").val(returnVal);
	  	   var url="<@spring.url "/publicgroupdata.nsf"/>"
	       $.post(url,{groupuuids:returnVal},
	       function(data){
			data=checkAjaxData(data);
	        $("#jsgroupAreName").text(data);
	       });
       }
  	 });
		$("#form").submit(function(){
	     	if($("#appname").val()==""){
				alert("请填写[系统名称]");
				return false;
	     	}
		});
     $("#back").click(function(){
  	 });
  });
</script>
</head>
<body style="background:#FFFFFF;">
	<form id="form" action="<@spring.url "/application/updateAppSuc.nsf" />" method="post">
		<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" align="center" border="0" cellspacing="2" cellpadding="0">
			<tr height="24px">
				<td align="right" colspan="3"  bgcolor="#fffff0">系统标识</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				    <input type="hidden" name="uuid" id="uuid" value="${application.uuid}" />
					<span name="appId" id="appId">${application.code}</span>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="name" maxlength="30" id="appname" value="${application.name}"/>
					<font color="red">必填</font>	
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">排序号</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="text" name="order" maxlength="5" id="order" onkeypress="checkEventNUM();" value="${application.order}"/>
					</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">管理组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<span name="jsgroupAreName" id="jsgroupAreName" ><#if  application.adminGroups ?has_content ><#list application.adminGroups as grouplist><#compress>${grouplist.name},</#compress></#list><#else>暂无</#if></span>
				<input name="admingroupuuid" id="admingroupuuid" type="hidden" value="<#if  application.adminGroups ?? ><#list application.adminGroups as grouplist><#compress>${grouplist.uuid},</#compress></#list></#if>" style="width:70%">
				<input type="button" class="button" id="searchgroup" value="选择组"/>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统描述</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="remark" id="remark" value="${application.remark?if_exists}"/></td>
			</tr>
			<#if appProfile??>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">配置文件uuid:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
				<input value="${appProfile.uuid?if_exists}" readonly/>
				用于配置取配置信息
				</td>
			</tr>
			</#if>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">登录地址:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="actionUrlServerSide" id="actionUrlServerSide" value="<#if appProfile??>${appProfile.actionUrlServerSide?if_exists}</#if>"/>
					应用系统的登录地址
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">客户端登录地址:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="actionUrl" id="actionUrl" value="<#if appProfile??>${appProfile.actionUrl?if_exists}</#if>"/>
					单点后的跳转页面
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">验证失败检查关键字:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="errorKeyword" id="errorKeyword" value="<#if appProfile?has_content>${appProfile.errorKeyword?if_exists}</#if>"/>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">用户名输入框:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="inputNameUserid" id="inputNameUserid" value="<#if appProfile??>${appProfile.inputNameUserid?if_exists}</#if>"/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">密码输入框:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="inputNamePassword" id="inputNamePassword" value="<#if appProfile??>${appProfile.inputNamePassword?if_exists}</#if>"/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">提交方法:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="submitMethod" id="submitMethod" value="<#if appProfile??>${appProfile.submitMethod?if_exists}</#if>"/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">页面请求编码:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="charset" id="charset" value="<#if appProfile??>${appProfile.charset?if_exists}</#if>"/></td>
			</tr>
			<#if attributelist??>
		<#list attributelist as attribute0>
		<#if !attribute0.type.hidden>
		<TR class='tb1'>
			<TD align="right" colspan="3" bgcolor="#fffff0" >${attribute0.type.name}:</TD>
			<TD align=left   colspan="3" bgcolor="#fffff0">
				<#switch attribute0.type.pageType >
				<#case "select" >
					<select  name="${attribute0.type.id}" id="${attribute0.type.id}" style="width=50%" >
						<#if attribute0.attValues[0]?exists&&attribute0.attValues[0].getValue()?exists >
							<option value="<#list attribute0.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list>" ><#list attribute0.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list></option>
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
							<#list attribute0.type.candidateItems as a0>
								<option value="${a0.value}" >${a0.value}</option>
							</#list>
						</#if> 
					</select>
					<#break>
				<#case "checkbox" >
					<#--list attribute0.type.candidateItems as a0>
						<#if attribute0.attValues[a0_index]?exists&&attribute0.attValues[a0_index].value==a0.value>
							<input type="checkbox" id="${a0.value}" value="${a0.value}" checked name="${attribute0.type.id+a0_index}" onclick="alert(this.name)"/><label for="${a0.value}">${a0.value}</label>
						<#else>
							<input type="checkbox" id="${a0.value}" value="${a0.value}" name="${attribute0.type.id+a0_index}" onclick="alert(this.name)"/><label for="${a0.value}">${a0.value}</label>
						</#if>
					</#list-->
					<#break>
				<#case "radio" >
					<#if attribute0.attValues[0]?exists&&attribute0.attValues[0].getValue()?exists >
						<input type="radio" id="${attribute0.type.id}" value="${attribute0.attValues[0].getValue()}" checked name="${attribute0.type.id}"/><label for="${attribute0.attValues[0].value}">${attribute0.attValues[0].value}</label>
						<#if attribute0.type.candidateItems ??>
							<#list attribute0.type.candidateItems as a0>
								<#list attribute0.attValues as att>
									<#if att.value!=a0.value>
										<input type="radio" id="${a0.value}" value="${a0.value}" name="${attribute0.type.id}"/><label for="${a0.value}">${a0.value}</label>
									</#if>
								</#list>
							</#list>
						</#if>
					<#else>
						<#if attribute0.type.candidateItems ??>
							<#list attribute0.type.candidateItems as a0>
								<input type="radio" id="${a0.value}" value="${a0.value}" name="${attribute0.type.id}"/><label for="${a0.value}">${a0.value}</label>
							</#list>
						</#if>
					</#if>
					<#break>
				<#case "textarea" >
					<textarea   name="${attribute0.type.id}" id="${attribute0.type.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)"><#list attribute0.attValues as att><#if att.getValue() ??>${att.getValue()}</#if></#list></textarea>&nbsp;&nbsp;长度不能超过100个字符
					<#break>
				<#default>
					<input id="${attribute0.type.id}" name="${attribute0.type.id}" <#if attribute0.type.rule?exists && attribute0.type.rule!="0"> onblur="checkType(this);"</#if> typename="${attribute0.type.name}" attvalue="${attribute0.type.value?if_exists}" onkeypress="checkEvent();" style="width:70%" value="${attribute0.value?if_exists}" maxlength="<#if attribute0.type.length?exists>${attribute0.type.length}<#else>100</#if>" />
				</#switch>
			</TD>
		</TR></#if>
	 </#list></#if>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="submit" class="button" value="提交" id="submit" />
				</td>
			</tr>
		</table>
		
	</form>
</body>
</html>