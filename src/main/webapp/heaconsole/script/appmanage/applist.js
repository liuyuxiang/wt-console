$(function(){
	$("#addapp").click(function(){
		window.location = 'heaconsole/web/appmanage/addapp1.jsp?id=' + $(this).attr("indexId");
	});
	$(".editapp").each(function(i,o){
		$(o).click(function(){
			var appId = $(this).attr("appId");
		});
	});
	$(".deleteapp").click(function(){
		if(confirm("是否确定删除该应用")){
			var appId = $(this).attr("appId");
			$.ajax({
				url:'heaAppManageAction.hea',
				data:{action:'deleteApp',id:appId},
				type:'POST',
				async:false,
				success:function(data){
					if(data){
						alert("删除成功！");
						top.refreshTree($("#addapp").attr("indexId"));
						window.location = 'heaAppManageAction.hea?action=appList&treeNodeId=' + $("#addapp").attr("indexId");
					}
				}
			});
		}
	});
});