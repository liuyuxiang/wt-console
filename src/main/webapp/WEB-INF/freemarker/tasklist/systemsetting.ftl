<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<#if  loginuser ?exists >
<script type="text/javascript">
//<![CDATA[

//]]>
</script>

<form name="f1" id="f1" method="post" action="">
	 <table  cellspacing="0" width="100%" align="center" valign="top"  >
					<tr >
						<td align="left" >
IDS同步配置
						</td>
					</tr>
					<tr >
						<td align="left" >
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td ></td>
										<td ></td>
									</tr>
									<tr>
										<td >同步时间</td>
										<td ><select name=""></td>
									</tr>
								</table>
						</td>
					</tr>
	  </table>
</form>
<#else>
<font color="red">你还没登录！</font>
</#if>
