<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
  function back(){
  	 document.location="<@spring.url "/attr/synclist.nsf"/>?id=${id}&type=${type}";
  }
  function delect(){
  	if(confirm("确定删除?")){
  	  window.location.href='<@spring.url "/attr/syncdel.nsf"/>?id=${id}&type=${type}&syncuuid=${app.uuid}';
  	}
  }
function check(){
	$("#ac").submit();
}
</script>

<div style="overflow-y:scroll;height:458px;width:100%;background:#FFFFFF;" >
	<form name="ac" id="ac" action="<@spring.url "/attr/synceditsuc.nsf" />" method="post">
		<table id="tt"  width="100%" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" align="center" border="0" cellspacing="1" cellpadding="0">
			<input type="hidden" name="syncuuid" id="syncuuid" value="${app.uuid}"/>
			<input type="hidden" name="id" id="id" value="${id}"/>
			<input type="hidden" name="type" id="type" value="${type}"/>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">同步属性类型</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="hidden" name="name" value="${app.propType}">
							<#if app.propType =="b">
								<input value="基本属性" readOnly/>
							<#else>
								<input value="扩展属性" readOnly/>
							</#if>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">同步属性编码</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="content" id="content" readonly value="<#if app.propName??>${app.propName}</#if>"/>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">同步处理方式</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="100" name="functions" id="functions" value="<#if app.transFunc??>${app.transFunc}</#if>"/>
				</td>
			</tr>
			<tr><td align="center" colspan="6" bgcolor="#fffff0">
				<input type="button" class="button" value="提交" onclick="check()"/>&nbsp;&nbsp;
				<input type="reset" class="button" value="重置"/>&nbsp;&nbsp;
				<input type="button" class="button" value="删除" onclick="delect()"/>&nbsp;&nbsp;
				<input type="button" class="button" value="返回" onclick="back()"/>&nbsp;&nbsp;
			</td></tr>
		</table>
	</form>
</div>
