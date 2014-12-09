var bannerResType = "07";
$(function(){
	$("#addBannerInfo").click(function(){
		window.location = "heaconsole/web/appmanage/addlogo.jsp?appId=" + $("#appId").val() + "&resType=" + bannerResType + "&forward=" + bannerResType;
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
				data:{action:'deleteResource',id:resId,appId:appId,forward:bannerResType},
				success:function(data){
					alert("资源删除成功！");
					window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId=' + appId;
					if(data == bannerResType){
						window.location = 'heaAppResourceAction.hea?action=resourceList&appId=' + appId + "&resType=" + data ;
					}
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
})