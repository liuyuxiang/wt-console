<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录信息</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script LANGUAGE="JavaScript">
 $(function(){
     $("#changeData").click(function(){
     	var url = '<@spring.url "/user/userHelper.nsf"/>';
     	var title = "员工自助";
     	var arg = "width=400,height=300,left=0,top=0,scrollbars=yes,status=yes,toolbar=yes,menubar=yes,location=yes,resizable=yes";
     	window.open(url,title,arg);
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
</head>
<body>
<#if msg?exists>
<br/><br/><br/><br/>
  ${msg?string}
<#else>
        <table width="85%" cellspacing="0" cellpadding="4" border="0" align="center">
          <tr>
            <td><span style="font-size: 12px;color: #00594E;text-decoration: none;line-height: 20px;">欢迎${user.primaryDepartment.name}<br>${user.name}登录</span></td>
            </tr>
          <tr>
            <td><span style="font-size: 12px;color: #00594E;text-decoration: none;line-height: 20px;"><#if pwdDueTime?exists>密码到期时间：${pwdDueTime?date}<br></#if><!--为确保账户安全，请定时进行密码修改!--></span></td>
            </tr>
          <tr>
            <td>
            	<input name="changeData" id="changeData" type="button" value="员工自助" class="button"/>
				<input name="changePwd" id="changePwd" type="button" value="修改密码" class="button"/>
            </td>
          </tr>
  </table>
</#if>
</body>
</html>