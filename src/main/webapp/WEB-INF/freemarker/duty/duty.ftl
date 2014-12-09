<#import "/spring.ftl" as spring />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>增加应用</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
//<![CDATA[
$(function(){
	$("#submit").click(function(){
	 	if($("#name").val()==""){
			alert("请填写[职务名称]");
			return false;
	 	}
	 	if($("#id").val()==""){
	 		getDutyId();
	 	}
	 	if($("#level").val()==""){
			alert("请填写[职务级别]");
			return false;
	 	}
	 	var u = "<@spring.url "/rest/duty"/>";
	 	var d = {
	 		uuid  : $("#uuid").val(),
	 		id    : $("#id").val(),
	 		name  : $("#name").val(),
	 		level : $("#level").val()
	 	};
	 	$.post(u, d,function(data) {
			$("#uuid").val(data.uuid);
	 		if(confirm("操作成功,刷新页面?")){
	 			window.parent.location.reload();
	 			//window.location.href='<@spring.url "/duty/userList.nsf"/>?id='+data.uuid;
	 		}
		});
	});
	$("#name").blur(function(){
		getDutyId();
	});
});
//]]>
function getDutyId(){
	<#if duty??><#else>
	$.ajax({
		type: "GET",
		url: "<@spring.url "/rest/duty/getid"/>?name="+$("#name").val(),
		dataType: "text",
		async:false,
		success: function(msg){
			$("#id").val(msg);
		}
	});
	</#if>
}
</script>
</head>
<body style="background:#FFFFFF;">
	<input type="hidden" name="uuid" id="uuid" value="<#if duty??>${duty.uuid}</#if>"/>
		<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="70%" align="center" border="0" cellspacing="2" cellpadding="0">
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">职务名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="name" maxlength="30" id="name" value="<#if duty??>${duty.name}</#if>"/>
					<font color="red">必填</font>	
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">职务编码</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" readonly maxlength="15" name="id" id="id" value="<#if duty??>${duty.id}</#if>"/>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">职务级别</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="level" maxlength="2" id="level" onkeypress="checkEventNUM();" value="<#if duty??>${duty.level}</#if>"/>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="submit" class="button" value="提交" id="submit" />
				</td>
			</tr>
		</table>
</body>
</html>