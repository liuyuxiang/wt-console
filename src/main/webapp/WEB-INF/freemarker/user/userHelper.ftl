<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户自助修改</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
</head>
<#if user ?exists>
<script LANGUAGE="JavaScript">
 $(function(){
     $("#changeData").click(function(){
  	     if(confirm("确定提交更新吗？")){
  $.ajax({
			type: "POST",
			url: '<@spring.url "/user/userHelperUpd.nsf"/>',
			cache: false,
			data: $("form").serialize(),
			dataType: "text",
			success: function(text){
				if(text.indexOf("true")!=-1){
					alert("修改成功");
				}
		}
	});
       }
  	 });
  });
  
 $(function(){
     $("#changePwd").click(function(){
     	var url = '<@spring.url "/user/updateuserpwd.nsf"/>';
     	var title = "修改密码";
     	var arg = "width=400,height=300,left=0,top=0,scrollbars=yes,status=yes,toolbar=yes,menubar=yes,location=yes,resizable=yes";
     	window.open(url,title,arg);
  	 });
  });
  
</script>
<body>
<form id="userForm" name="userForm">
<table>
		<TR class='tb1'>
			<TD align="right" height="20" bgcolor="#fffff0" >用户姓名</TD>
			<TD align=left colspan="2" bgcolor="#fffff0">
				${user.name}
			</TD>
		</TR>
		<TR class='tb1'>
			<TD align="right" height="20" bgcolor="#fffff0" >用户登录ID</TD>
			<TD align=left colspan="2" bgcolor="#fffff0">
				${user.id}
			</TD>
		</TR>
		<#list attlist as attribute0>
		<TR class='tb1'>
			<TD align="right" bgcolor="#fffff0" >${attribute0.type.name}</TD>
			<TD align=left colspan="2" bgcolor="#fffff0">
				<!--<input id="${attribute0.type.id}" name="${attribute0.type.id}" <#if attribute0.type.rule?exists && attribute0.type.rule!="0"> onblur="checkType(this);"</#if> attvalue="${attribute0.type.value?if_exists}" typename="${attribute0.type.name?if_exists}" onkeypress="checkEvent();" style="width:70%" value="<#list attribute0.attValues as att><#if att.getValue() ?exists>${att.getValue()}</#if></#list>" maxlength="<#if attribute0.type.length?exists>${attribute0.type.length}<#else>100</#if>"/>
				-->
				<input id="${attribute0.type.id}" name="${attribute0.type.id}" <#if attribute0.type.rule?exists && attribute0.type.rule!="0"> onblur="checkType(this);"</#if> attvalue="${attribute0.type.value?if_exists}" typename="${attribute0.type.name?if_exists}" onkeypress="checkEvent();" style="width:70%" value="${attribute0.value?if_exists}" maxlength="<#if attribute0.type.length?exists>${attribute0.type.length}<#else>100</#if>"/>
			</TD>
		</TR>
	 	</#list>
		<TR class='tb1'>
			<TD align="center" colspan="3" bgcolor="#fffff0">
				<input name="changeData" id="changeData" type="button" value="提交修改" class="button"/>
				<input name="changePwd" id="changePwd" type="hidden" value="修改密码" class="button"/>
				<input type="reset" value="重置" class="button"/>
				<input type="button" value="关闭页面" class="button" onclick="window.close();"/>
			</TD>
		</TR>
	</table>
</form>
</body>
<#else>
<br/><br/><br/><br/>
  您好！你的账号不存在，请联系管理员。
</#if>
</html>