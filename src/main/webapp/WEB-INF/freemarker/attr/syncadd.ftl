<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
	function back(){
		window.location.href='<@spring.url "/attr/synclist.nsf"/>?id=${id}&type=${type}';
	}

	function check(){
		var content = document.getElementById("content").value;
		if(content==""){
			alert("请填写[同步属性编码]");
			return false;
		}
		var url="<@spring.url "/attr/confirmationsync.nsf"/>";
		$.post(url,{code:$("#content").val()},function(data){
data=checkAjaxData(data);
			if(data!="false"){
				$("#confirmationsync").html("同步属性已存在");
				$("#content").focus();
				return false;
			}else{
				$("#ac").submit();
			}
		});
	}

</script>
	<div style="overflow-y:scroll;height:458px;width:100%;background:#FFFFFF;" >
	<form id="ac" action="<@spring.url "/attr/syncaddsuc.nsf" />" method="post">
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" name="type" value="${type}"/>
		<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="100%" align="center" border="0" cellspacing="1" cellpadding="0">
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">同步属性类型</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<select name="name" id="name">
						<option value="c">扩展属性</option>
						<option value="b">基本属性</option>
					</select>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">同步属性编码</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input name="content" id="content" value=""/>
				<font id="confirmationsync" color="red">必填</font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">处理方式</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input name="functions" maxlength="30" id="functions" value=""/>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"></td>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="button" class="button" value="提交" onclick="check()" />&nbsp;&nbsp;
					<input type="reset" class="button" value="重置"/>&nbsp;&nbsp;
					<input type="button" class="button" value="返回" onclick="back()" />
				</td>
			</tr>
		</table>
	</form>
</div>