<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改用户密码</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
</head>
<#if user ??>
<script LANGUAGE="JavaScript">
function doSubmit()
{
	
	if((doForm.password.value=="")||(doForm.password.value==null))
	{
		alert('请输入新密码');
		return false;
	}
	if((doForm.repassword.value=="")||(doForm.repassword.value==null)){
		alert('请输入确认密码');
		return false;
	}
	if(doForm.password.value!=doForm.repassword.value)
	{
		alert('两次输入的密码不一致');
		return false;
	}
	if (doForm.password.value.length<6 || doForm.password.value.length>16) {
		alert('密码长度小于6位或者大于16位');
		return false;
	}
	if((doForm.oldpassword.value=="")||(doForm.oldpassword.value==null))
	{
		alert('请输旧入密码');
		return false;
	}else{
		 var url="<@spring.url "/user/userispwd.nsf"/>?userid=${user.id}"
	   $.post(url,{oldpassword:$("#oldpassword").val()},
	       function(data){
data=checkAjaxData(data);
	       if(data=="false"){
	          $("#confirmationuser").html("密码输入错误!");
	          $("#oldpassword").focus();
		       return false;
	         } else{
	           return true;
	         }
	  });
	}

}
</script>
<body>
<script LANGUAGE="JavaScript">
<#if  status ?? >
<#if status="false">
alert("修改密码失败");
<#elseif status="true">
if (confirm("修改密码成功,关闭窗口吗？")){
	window.close();
}
<#else>
alert("同步到目录失败");
</#if>
</#if>
</script>
<form action="<@spring.url "/updateuserpwdform.nsf" />?userid=${user.id}"   method="post" id="doForm" name="doForm" onsubmit="return doSubmit()" >
<table width="95%" cellspacing="0" cellpadding="0" border="0" align="center">
 
      <tr>
        <td bgcolor="#f1faf9" style="border:1px solid #CAEAFF;">
        <table width="85%" cellspacing="0" cellpadding="4" border="0" align="center">
          <tr>
            <td width="37%" style="font-size: 12px;color: #00594E;text-decoration: none;line-height: 20px;">用户ID</td>
            <td width="63%" style="font-size: 12px;color: #FF0000;">${user.id}</td>
            </tr>
          <tr>
            <td><span style="font-size: 12px;color: #00594E;text-decoration: none;line-height: 20px;">旧密码</span></td>
            <td><input type="password" size="16" id="oldpassword" style="border: 1px dotted #99DCE3;background-color: #FFFFFF;width: 140px;height: 18px;"  name="oldpassword"/>
            </td>
            </tr>
          <tr>
            <td><span style="font-size: 12px;color: #00594E;text-decoration: none;line-height: 20px;">新密码</span></td>
            <td><input type="password" size="16" id="password" style="border: 1px dotted #99DCE3;background-color: #FFFFFF;width: 140px;height: 18px;" onkeyup='this.value=this.value.replace(/\s+/g,"");'  name="password"/></td>
            </tr>
          <tr>
            <td><span style="font-size: 12px;color: #00594E;text-decoration: none;line-height: 20px;">确认密码</span></td>
            <td><input type="password" size="16" id="repassword" style="border: 1px dotted #99DCE3;background-color: #FFFFFF;width: 140px;height: 18px;" onkeyup='this.value=this.value.replace(/\s+/g,"");' name="repassword"/></td>
            </tr>
          <tr>
            <td height="65" align="center" colspan="2"><span style="font-size: 12px;color: #FF0000;">密码只能为6--16位数字及字母的任意组合，请不要使用原始密码!</span></td>
            </tr>
          <tr>
            <td height="30" align="center" colspan="2"><img src="<@spring.url "/style/default/images/qd.gif" />"  onclick="doSubmit();"/><span style="font-size: 12px;color: #FF0000;" id="confirmationuser"></span></td>
            </tr>
         </table></td>
      </tr>
  </table>
</form>
<#else>
<br/><br/><br/><br/>
  您好！你的账号不存在，请联系管理员。
</#if>
</body>
</html>