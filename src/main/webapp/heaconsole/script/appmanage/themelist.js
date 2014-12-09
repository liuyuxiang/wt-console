$(function(){
	$("#addThemeInfo").click(function(){
		window.location = 'heaconsole/web/appmanage/addtheme.jsp?appId=' + $("#appId").val() + "&forward=themelist";
	});
	
	$(".deletetheme").each(function(i,o){
		$(o).click(function(){
			var resId = $(this).attr("tid");
			var appId = $("#appId").val();
			$.ajax({
				url:'heaAppResourceAction.hea',
				data:{action:'deleteTheme',id:resId,appId:appId,forward:'themelist'},
				success:function(data){
					alert("资源删除成功！");
					if(data == "themelist"){
						window.location = 'heaAppResourceAction.hea?action=themeList&appId=' + appId ;
					}else{
						window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId=' + appId ;
					}
				}
			});
		});
	})
	
	$(".edittheme").each(function(i,o){
		$(o).click(function(){
			var themeId = $(this).attr("tid");
			var appId = $("#appId").val();
			window.location = 'heaAppResourceAction.hea?action=toUpdateTheme&id=' + themeId + "&forward=themelist";
		})
	})
})