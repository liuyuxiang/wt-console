<#import "/spring.ftl" as spring />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>增加应用</title>
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
	     	if($("#appId").val()==""){
				alert("请填写[系统标识]");
				return false;
	     	}
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
	<form id="form" action="<@spring.url "/application/createApp.nsf" />" method="post">
		<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" align="center" border="0" cellspacing="2" cellpadding="0">
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统标识</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="15" name="code" id="appId" value=""/>
					<font color="red">必填</font>	
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="name" maxlength="30" id="appname" value=""/>
					<font color="red">必填</font>	
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">排序号</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="text" name="order" maxlength="5" id="order" onkeypress="checkEventNUM();" value=""/>
					</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">管理组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<span name="jsgroupAreName" id="jsgroupAreName">暂无</textarea>
				<input name="admingroupuuid" id="admingroupuuid" type="hidden" value="" style="width:70%">
				<input type="button" class="button" id="searchgroup" value="选择组"/>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统描述</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="remark" id="remark" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">登录地址:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="actionUrlServerSide" id="actionUrlServerSide" value=""/>
					应用系统的登录地址</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">客户端登录地址:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="actionUrl" id="actionUrl" value=""/>
					单点后的跳转页面</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">验证失败检查关键字:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="errorKeyword" id="errorKeyword" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">用户名输入框:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="inputNameUserid" id="inputNameUserid" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">密码输入框:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="inputNamePassword" id="inputNamePassword" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">提交方法:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="submitMethod" id="submitMethod" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">页面请求编码:</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="charset" id="charset" value=""/></td>
			</tr>
			<#if attributelist??>
		<#list attributelist as attribute0>
		<TR class='tb1'>
			<TD align="right" colspan="3" bgcolor="#fffff0" >${attribute0.name}：</TD>
			<TD align=left colspan="3" bgcolor="#fffff0">
				<#switch attribute0.pageType >
				   <#case "select" >
			          <select  name="${attribute0.id}" id="${attribute0.id}" style="width=50%" > 
			                <#if attribute0.candidateItems ??>
			             	<#list attribute0.candidateItems as a0>
							  <option value="${a0.value}" <#if a0.isDefault??&&a0.isDefault>selected</#if>>${a0.value}</option>
						    </#list>
						    </#if>
					</select>
			       <#break>
			        <#case "checkbox" >
			        		<#--if attribute0.candidateItems ??>
			        		<#list attribute0.candidateItems as a0>
							  <input type="checkbox" id="${a0.value}" value="${a0.value}" name="${attribute0.id}" <#if a0.isDefault??&&a0.isDefault>checked</#if>/><label for="${a0.value}">${a0.value}</label>
						    </#list>
						    </#if-->
			       <#break>
			       <#case "radio" >
			        		<#if attribute0.candidateItems ??>
			        		<#list attribute0.candidateItems as a0>
							  <input type="radio" id="${a0.value}" value="${a0.value}" name="${attribute0.id}" <#if a0.isDefault??&&a0.isDefault>checked</#if>/><label for="${a0.value}">${a0.value}</label>
						    </#list>
						    </#if>
			       <#break>
			         <#case "textarea" >
			           <textarea name="${attribute0.id}" id="${attribute0.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)" value=""></textarea>&nbsp;&nbsp;长度不能超过100个字符
			       <#break>
					 <#default>
					 <#if attribute0.id!="dataCameFrom">
					  <input id="${attribute0.id}" name="${attribute0.id}" typename="${attribute0.name}" <#if attribute0.rule?exists && attribute0.rule!="0"> onblur="checkType(this);"</#if> onkeypress="checkEvent();" attvalue="${attribute0.value?if_exists}" style="width:70%" value="" maxlength="<#if attribute0.length?exists>${attribute0.length}<#else>100</#if>"  />
					  <font color="red" size="3"></font><!--input id="${attribute0.id}" name="${attribute0.id}" onkeypress="checkEvent();" value="" style="width:70%" maxlength="50"/-->
					<#else>
					  <input id="${attribute0.id}" name="${attribute0.id}" value="1" style="width:70%"/>
					  </#if>
			   </#switch>
			</TD>
		</TR>
	 </#list></#if>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="submit" class="button" value="提交" id="submit" />&nbsp;&nbsp;
					<input type="button" class="button" value="返回" id="back" />
				</td>
			</tr>
		</table>
		
	</form>
</body>
</html>