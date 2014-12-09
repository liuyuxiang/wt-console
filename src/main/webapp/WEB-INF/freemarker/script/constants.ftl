$(function(){
	//$("#pagesizegovalue").attr("maxlength","${maxRecordPerPage}".length);
	$("#pagesizegovalue").blur(function(){
		var pageSizeValue=$("#pagesizegovalue").val();
		var maxRecordPerPage=${maxRecordPerPage};		
		if(parseInt(pageSizeValue)>parseInt(maxRecordPerPage)){
			alert("每页显示条数最大是${maxRecordPerPage}条,请重新输入!");
			this.value="${maxRecordPerPage}";
			this.select();
		}
	});
});
function goCursorEnd(obj){
	if(obj==null){
		obj=event.srcElement;
	}
	var text =obj.createTextRange();
	text.collapse(false);
	text.select();
}