<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script language="javascript"  type="text/javascript" src="<@spring.url "/js/py.js" />"></script>
</head>
<body >
<script type="text/javascript">
//<![CDATA[
  
  
  
//]]>
</script>
<form name="user" id="user" method="post" enctype="multipart/form-data" action="<@spring.url "/tasklist/adduser.nsf" />">

		<table class="tableborder1" id="tt" width="50%" align="center" border="0" cellspacing="2"
		cellpadding="0">
			<TR ><br/><br/><br/><br/><br/>
			<TD align="center"  bgcolor="#fffff0">导入文件</TD>
			<TD align=left colspan="3"  bgcolor="#fffff0"><input name="imageFile" type="file"/></TD>
		</TR>
		<tr>
		<TD align="center"   bgcolor="#fffff0">下载模版</TD>
		<TD  align=left colspan="3"  bgcolor="#fffff0">
		   <a href="<@spring.url "/tasklist/downloadusertemp.nsf" />">新增用户模版</a>
		</TD>
		</tr>
		
	</table>
</div>

<p><p>
<table id="Table2" class="tableborder1" width="100%" border="0"
    cellspacing="1" align="center">
	<table id="table" align="center">
	   <TR >
			<TD align=center colspan=4  bgcolor="#fffff0" >
			   <input type="submit"  value="导入" id="newUserAdd"  class="button" />
			   <br/>
			   <#if flag ??>
			   <#if flag="true">导入成功</#if>
			   <#if flag="false">导入失败,请确认文件是否过大、格式是否正确并重新导入</#if>
			   </#if>
			</TD>
		</TR>
</table>

</table>
 
</form>
</body>
</html>