function matchNum(obj){
	obj.value=obj.value.replace(/[^0-9]/g ,'');
}
function matchEN(obj){
	obj.value=obj.value.replace(/[^a-zA-Z]/g ,'');
}
function matchCH(obj){
	obj.value=obj.value.replace(/[^\u4e00-\u9fa5]/g ,'');
}
function matchEnNum(obj){
	obj.value=obj.value.replace(/[^a-zA-Z0-9]/g ,'');
}
function matchEnNum_(obj){
	obj.value=obj.value.replace(/[^a-zA-Z0-9\_]/g ,'');
}
function matchEnChNum(obj){
	obj.value=obj.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9]/g ,'');
}
function matchEnChNum_(obj){	
	obj.value=obj.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\_\-]/g ,'');
}
function matchEnChNum1_(obj){
	obj.value=obj.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\_]/g ,'');
}