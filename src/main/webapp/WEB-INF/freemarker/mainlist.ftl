<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
	<table  id="table1" style="border: 1px solid rgb(131, 175, 221);" ><!--内容-->
				<tr>
				<td id="tdleft" valign="top">
							<table id="table1">
									<tr>
										<td style="vertical-align: top;">
												<div id="sidebar" style="overflow-y:scroll;height:100%;width:100%;background:#FFFFFF;" >
													<#switch currentMenu >
													  <#case "USER_PAGE" >
													  	<#include "/dept/tree.ftl" />
													  <#break>
													    <#case "GROUP_PAGE" >
													  	<#include "/group/tree.ftl" />
													  <#break>
													  <#case "APP_PAGE" >
													  	 <#include "/application/tree.ftl" />
													  <#break>
													  <#case "DUTY_PAGE" >
													  	 <#include "/duty/tree.ftl" />
													  <#break>
													  <#case "ATT_PAGE" >
													    <#include "/attr/tree.ftl" />
													  <#break>
													  <#case "TASK_PAGE" >
													    <#include "/tasklist/tree.ftl" />
													  <#break>
													  <#default>
													</#switch>
												</div>
										</td>
									</tr>
							</table>
					    </td>
						<td id="tdright" valign="top" align="center"  >
												<div id="content" >
													  	<iframe name="rightframe" data-app="uum" src="<@spring.url "/defaultbackground.nsf"/>" frameborder="0" width="100%" ></iframe>
			                                    </div>
						</td>

				</tr>
		</table>
