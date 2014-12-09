<#import "/spring.ftl" as spring />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>增加应用系统描述</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
 $(function(){
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
	<form id="form" action="<@spring.url "/application/createAppProfile.nsf" />" method="post">
		<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" align="center" border="0" cellspacing="2" cellpadding="0">
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统标识</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="15" name="appId" id="appId" value=""/>
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
				<textarea name="jsgroupAreName" id="jsgroupAreName" rows="5" style="width:40%" value="" disabled></textarea>
				<input name="admingroupuuid" id="admingroupuuid" type="hidden" value="" style="width:70%">
				<input type="button" class="button" id="searchgroup" value="选择组"/>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">系统描述</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="appRemark" id="appRemark" value=""/></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">更多……</TD>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<>
				</td>
			</tr>
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