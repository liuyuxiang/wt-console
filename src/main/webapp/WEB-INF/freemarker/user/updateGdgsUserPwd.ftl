<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改用户密码</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
</head>
<script LANGUAGE="JavaScript">
function doSubmit()
{
	if((doForm.userid.value=="")||(doForm.userid.value==null))
	{
		alert('用户ID');
		return false;
	}
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
	if (doForm.password.value.length<8 || doForm.password.value.length>16) {
		alert('密码长度小于8位或者大于16位');
		return false;
	}
	if((doForm.oldpassword.value=="")||(doForm.oldpassword.value==null))
	{
		alert('请输旧入密码');
		return false;
	}else{
		 var url="<@spring.url "/user/userispwdAndGdGs.nsf"/>?userid="+doForm.userid.value;
	   $.post(url,{oldpassword:$("#oldpassword").val()},
	       function(data){
	       if(data=="false"){
	          $("#confirmationuser").html("密码输入错误或者不是供电公司账户!");
	          $("#userid").focus();
		       return false;
	         } else{
	          $("#doForm").submit();
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
<form action="<@spring.url "/updateuserpwdform.nsf" />?flag=gdgs"   method="post" id="doForm" name="doForm" onsubmit="return doSubmit()" >
<table width="95%" cellspacing="0" cellpadding="0" border="0" align="center">
 
      <tr>
        <td bgcolor="#f1faf9" style="border:1px solid #CAEAFF;">
        <table width="85%" cellspacing="0" cellpadding="4" border="0" align="center">
          <tr>
            <td><span style="font-size: 12px;color: #00594E;text-decoration: none;line-height: 20px;">用户ID</span></td>
            <td><input type="text" size="16" id="userid" style="border: 1px dotted #99DCE3;background-color: #FFFFFF;width: 140px;height: 18px;"  name="userid"/>
            </td>
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
            <td height="65" align="center" colspan="2"><span style="font-size: 12px;color: #FF0000;">密码只能为8--16位数字及字母的任意组合，请不要使用原始密码!</span></td>
            </tr>
          <tr>
            <td height="30" align="center" colspan="2"><img src="<@spring.url "/style/default/images/qd.gif" />"  onclick="doSubmit();"/><span style="font-size: 12px;color: #FF0000;" id="confirmationuser"></span></td>
            </tr>
         </table></td>
      </tr>
  </table>
</form>
</body>
</html>