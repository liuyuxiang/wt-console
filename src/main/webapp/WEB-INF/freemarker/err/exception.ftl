<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[



$(function(){
	//hideExp();
	$("#show").click(function(){
    	if ($("#show").val() == '显示') {
    		showExp();
    	} else {
    		hideExp();
    	}
  	});
});

function showExp(){
	$("#show").val('隐藏');
    $("#exp").show();
}

function hideExp() {
	
	$("#exp").hide();
	$("#show").val('显示');
    
}

//]]>
</script>
</head>
<body >
	<p style="font-size:20px;text-align:left;">
	<img src="<@spring.url "/style/default/images/err.jpg"/>" style='width:50px;height:50px'/>
	系统出现${exception.message?html}，请与统一用户管理员联系进行处理
	 
	<input  type="button" id="show" class="button"  value="显示"/>
	</p>
	</tr>
	<tr>

	<div style="height:350px; overflow:auto;">	
		<div id='exp' style="display:none;text-align:left;" >
			${exception.stackTrace}
		</div>
	</div>
	
</body>
</html>