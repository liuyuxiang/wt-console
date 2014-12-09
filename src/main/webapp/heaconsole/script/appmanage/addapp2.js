/**
 * @param {Object} appId
 * 添加logo资源
 */
//function addLogoInfo(appId){
//	window.location = '';
//}
/**
 * 
 * @param {Object} appId
 */
//function addBanner(appId){
//	window.location = '';	
//}
/**
 * 
 * @param {Object} appId
 */
//function addTheme(appId){
//	window.location = ""
//}
var logoResType = "05";
var bannerResType = "07";
$(function(){
	
	$("#addLogoInfo").click(function(){
		window.location = "heaconsole/web/appmanage/addlogo.jsp?appId=" + $("#appId").val() + "&resType=" + logoResType;
	});
	
	$("#addBannerInfo").click(function(){
		window.location = "heaconsole/web/appmanage/addlogo.jsp?appId=" + $("#appId").val() + "&resType=" + bannerResType;
	});
	
	$("#addThemeInfo").click(function(){
		window.location = 'heaconsole/web/appmanage/addtheme.jsp?appId=' + $("#appId").val() ;
	});
	
	/**
	 * 
	 * @param {Object} i
	 * @param {Object} o
	 * @memberOf {TypeName} 
	 */
	$(".deletelogo").each(function(i,o){
		$(o).click(function(){
			var resId = $(this).attr("sid");
			var appId = $("#appId").val();
			$.ajax({
				url:'heaAppResourceAction.hea',
				data:{action:'deleteResource',id:resId,appId:appId},
				success:function(data){
					alert("资源删除成功！");
					window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId=' + appId;
				}
			});
		});
	})
	
	$(".editresource").each(function(i,o){
		$(o).click(function(){
			var resId = $(this).attr("sid");
			var appId = $("#appId").val();
			window.location = 'heaAppResourceAction.hea?action=toUpdateResource&id=' + resId ;
		})	
	})
	
	$(".edittheme").each(function(i,o){
		$(o).click(function(){
			var themeId = $(this).attr("tid");
			var appId = $("#appId").val();
			window.location = 'heaAppResourceAction.hea?action=toUpdateTheme&id=' + themeId;
		})
	})
	
	$(".deletetheme").each(function(i,o){
		$(o).click(function(){
			var resId = $(this).attr("tid");
			var appId = $("#appId").val();
			$.ajax({
				url:'heaAppResourceAction.hea',
				data:{action:'deleteTheme',id:resId,appId:appId},
				success:function(data){
					alert("资源删除成功！");
					window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId=' + appId;
				}
			});
		});
	})
});