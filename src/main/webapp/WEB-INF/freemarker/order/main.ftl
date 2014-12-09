<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理 (${ver?if_exists})</title>

<#include "/style/style.ftl" >

<#include "/script/jquery.ftl" >
</head>
<body>
<div id="wrapper1" style="width:100%;margin-left:-20px;"><!--最外层div-->
	<FRAMESET border=0 frameSpacing=0 frameBorder=no cols=220,*>
		<table  id="table1"><!--内容-->
		<tr>
			<td id="tdleft" valign="top">
				<table id="table1">
				<tr>
					<td style="width:300px;height:470px;">
						<div id="sidebar" style="overflow:auto;width:300px;height:470px;background:#FFFFFF;" >
								<#include "/order/tree.ftl" />
						</div>
					</td>
				</tr>
				</table>
			</td>
			<td id="tdright" valign="top">
				<div id="content">
					<iframe style="width:100%;height:470px;" name="rightframe1" id="rightframe1" frameborder="0" scrolling="no" ></iframe>
				</div>
			</td>
		</tr>
		</table>
	</FRAMESET>
</div>
</body>
</html>