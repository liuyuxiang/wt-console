<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报表前端</title>
<link href="${pageContext.request.contextPath}/css/theme/${empty theme ? 'blue':theme}/index.css" rel="stylesheet" id="themePath" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/rbac/louver/index.css" rel="stylesheet" type="text/css" />

<!-- ajax树状结构所需文件 -->
<link   href="${pageContext.request.contextPath}/common/css/theme/${sessionScope.theme}/component/dhtmlxtree/dhtmlxtree.css" rel="stylesheet" type="text/css" >
<script src="${pageContext.request.contextPath}/common/script/component/dhtmlxtree/dhtmlxcommon.js"></script>
<script src="${pageContext.request.contextPath}/common/script/component/dhtmlxtree/dhtmlxtree.js"></script>
<!-- ajax树状结构所需文件 End:-->



<link id="themePath" href="${pageContext.request.contextPath }/css/theme/jquery/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<%@include file="../../../script/rbac/louver/index_js.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/common/jquery-1.4.2.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery/jquery.ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery/jquery.ui.button.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery/jquery.ui.position.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery/jquery.ui.dialog.js"></script>
<script type="text/javascript">
	$(function(){
		$("<div></div>",{
			'class':'topnav',
			'html':'<a href="#"><bean:message key="ui.changeTheme"/></a>',
			'click':function(){
				var path = $("#themePath").data("path")==null?"blue":$("#themePath").data("path");
				var dialog = $("<div/>",{
								html:'<form>'
									+'<table>'
									+'<tr>'
									+'	<td align="center">'
									+'		<div id="theme1" class="ui-widget-content ui-corner-all div"'
									+'				title="blue"'
									+'				style="width: 100px; height: 50px; background: #28609B;">'
									+'		</div>'
									+'</td>'
									+'<td align="center">'
									+'		<div id="theme2" class="ui-widget-content ui-corner-all div"'
									+'			title="red"'
									+'			style="width: 100px; height: 50px; background: #9F290B;"></div>'
									+'</td>'
									+'<td align="center">'
									+'		<div id="theme3" class="ui-widget-content ui-corner-all div"'
									+'			title="green"'
									+'			style="width: 100px; height: 50px; background: #1A6868;"></div>'
									+'</td>'
									+'</tr>'
									+'</table>'
									+'</form>'
							});
							dialog.find("div")
							.click(function(){
								path = $(this).attr("title");
								dialog.find("div").css("border","");
								$(this).css("border","3px solid #ABABAB");
								$("#themePath").attr("href","css/theme/"+path+"/css.css");
								changeFrameStyle("css/theme/"+path+"/subcss.css");
								$("#tabthemepath").attr("href","css/component/jerichotab/" + path + "/jquery.jerichotab.css");
							});
				dialog.find("div[title='"+path+"']").css("border","3px solid #ABABAB");
				dialog.dialog({
					title:'<bean:message key="ui.changeTheme"/>',
					content:dialog,
					height:300,
					width:400,
					buttons:{
						'<bean:message key="ui.cancel"/>':function(){
							var _path =$("#themePath").data("path")==null?"blue":$("#themePath").data("path");
							$("#themePath").attr("href","css/theme/"+_path+"/css.css");
							changeFrameStyle("css/theme/"+_path+"/subcss.css");
							$("#tabthemepath").attr("href","css/component/jerichotab/" + _path + "/jquery.jerichotab.css");
							$(this).dialog("close");
						},
						'<bean:message key="ui.ok"/>':function(){
							$("#themePath").data("path",path);
							$.ajax({
								url:'/hea/heaUserPageAction.hea',
								data:'action=saveTheme&theme=' + path,
								success:function(){
								},
								error:function(){
								}
							});
							$(this).dialog("close");
						}
					}
				});
			}
		}).appendTo(".banner");
		$("#themePath").data("path",'${theme}');
	});
</script>
<script type="text/javascript">
	
	function changeMainNavState()
	{
		
		var mainNavState = document.getElementById("mainNav_draw").className;
		if(mainNavState == "mainnav_menu_drawback")
			{
				document.getElementById("mainNav_draw").className = 'mainnav_menu_drawgo';
				
			}else
				{
					document.getElementById("mainNav_draw").className = 'mainnav_menu_drawback';
				}
		$("#header").slideToggle("fast", function autoChangeHeight(){
					var mainNavState = document.getElementById("mainNav_draw").className;
					var headerHeight =0;
					if(mainNavState == "mainnav_menu_drawback")
						{
							headerHeight =document.getElementById('header').offsetHeight;
						}
			     	var mainnavHeight = document.getElementById('mainnav').offsetHeight;
			     	var footerHeight = document.getElementById('footer').offsetHeight;
			     	var loginHeight = document.getElementById('login').offsetHeight;
			     	var clientHeight = document.body.clientHeight;
			     	var maincontentHeight = clientHeight - headerHeight - mainnavHeight - footerHeight;
			     	document.getElementById('maincontent').style.height = maincontentHeight;
			     	document.getElementById('siderbar_menu').style.height = maincontentHeight - loginHeight;
			     });
		
	}
	function changeFrameStyle(stylePath)
	{
		var myframes = window.frames;
		for (var i = 0; i < myframes.length; i++) {
			var linkObj = myframes[i].document.getElementById("themePath");
			if(linkObj)
				{
					linkObj.href ="${pageContext.request.contextPath}/"+ stylePath;
				}
				
		}
		
	}
	
</script>

</head>

<body onload="changeIframeBG('mainContent_0');autoWindowHeight();writeCurrentTime();">
<div class="container">
	<div class="header_1" id="header">
	  <div class="logo_1"></div>
	  <div class="banner">
	  		
	  </div>  
	</div>

	<!-- mainnav start-->
	<div class="mainnav" id="mainnav">
	  <div class="welcome">
		<div class="btn_1" onclick="loginOut();">注销</div>
		</div>  
	  <!--<div class="mainnav_menucurrent">栏目名称</div>
	  <div class="mainnav_menu">待办事宜</div>
	  -->
	  <c:forEach items="${indexList}" var="item" varStatus="vst">
	  	<c:if test="${vst.first}">
	  		<script type="text/javascript">
	  			selectId = '${item.indexuuid}';
	  		</script>
	  		<div class="mainnav_menucurrent" id="divMenu_${vst.index}" onclick="clickTitleBtn('${item.indexuuid}',${vst.index});">${item.indexname}</div>
	  	</c:if>
	  	<c:if test="${vst.first != true}">
		  	<div class="mainnav_menu" id="divMenu_${vst.index}" onclick="clickTitleBtn('${item.indexuuid}',${vst.index});">${item.indexname }</div>
	  	</c:if>
	  </c:forEach>
	  <!--  <div class="time" id="divTime">Time:3月31日 星期四 16：42</div> -->
	  <div id="mainNav_draw" class="mainnav_menu_drawback" onclick="changeMainNavState()"></div>
	  <div class="mainnav_menu_1">
	    <div class="search">
	    	<input name="textfield" type="text" class="ipt_3" id="textfield" size="20" />
	    </div>
	    <div  class="btn_1">搜索</div>
     </div>	
	</div>
	<!-- mainnav start-->

	<div class="maincontent" id="maincontent"><!--主内容区间-->
		<div class="siderbar"><!--侧边栏-->
		  <div class="login" id="login"><!--此处显示login 的内容-->
			<div class="login_content">欢迎登录办公平台子系统</div>
		  </div>
		  <div class="siderbar_menu" id="siderbar_menu"><!--此处显示  class "siderbar_menu" 的内容-->
		  	 <c:forEach items="${indexList}" var="item" varStatus="vst">
		  	<c:if test="${vst.first}">
		  		<div id="divSiderbar_${vst.index}"  class="divSiderbar_1"></div>
		  		<script type="text/javascript">
		  			 buildTree('${item.indexuuid}',${vst.index});
		  		</script>
		  	</c:if>
		  	<c:if test="${vst.first != true}">
			  	<div id="divSiderbar_${vst.index}"  class="divSiderbar_2"></div>
		  	</c:if>
	  		</c:forEach>
		  </div>
		</div><!--侧边栏结束-->
		<div class="drawback"><div class="drawback_icon" onclick="showHideMenu()"></div></div><!--收缩按钮-->
		<div class="content_1" id="content_1">
			<c:forEach items="${indexList}" var="item" varStatus="vst">
				<c:if test="${vst.first}">
					<iframe id="mainContent_${vst.index}" src="${pageContext.request.contextPath}/modules/rbac/louver/welcome.jsp" allowTransparency="true" frameborder="0" class="iframeContent_1"></iframe>
		  		</c:if>
			  	<c:if test="${vst.first != true}">
			  		<iframe id="mainContent_${vst.index}" src="${pageContext.request.contextPath}/modules/rbac/louver/welcome.jsp" allowTransparency="true" frameborder="0" class="iframeContent_2"></iframe>
			  	</c:if>
			</c:forEach>
		</div>
	</div>
	<div class="footer" id="footer"></div>
</div>
</body>
</html>
