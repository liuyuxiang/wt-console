var logoResType = "05";
$(function(){
	/**
	 * 
	 */
	$("#addLogoInfo").click(function(){
			window.location = "heaconsole/web/appmanage/addlogo.jsp?appId=" + $("#appId").val() + "&resType=" + logoResType + "&forward=05";
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
			var resType = "";
			var appId = $("#appId").val();
			$.ajax({
				url:'heaAppResourceAction.hea',
				data:{action:'deleteResource',id:resId,appId:appId,forward:'05'},
				success:function(data){
					alert("资源删除成功！");
					if(data == "05"){
						window.location = "heaAppResourceAction.hea?action=resourceList&appId=" + appId + "&resType=" + data ;
					}else{
						window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId=' + appId;
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