function indexUpdate() {
	var orderValue = document.forms[0]['index.indexorder'].value;
	if (orderValue != null && orderValue.replace(/(^\s*)|(\s*$)/g, "") != "") {
		var patt = /^[0-9]+$/;
		if (!patt.test(orderValue)) {
			alert('排序号必须为数字');
			return false;
		}
	}
	return true;
}
function indexUpdate() {
	var orderValue = document.forms[0]['index.indexorder'].value;
	if (orderValue != null && orderValue.replace(/(^\s*)|(\s*$)/g, "") != "") {
		var patt = /^[0-9]+$/;
		if (!patt.test(orderValue)) {
			alert('排序号必须为数字');
			return false;
		}
	}
	if ($("#indexName").val().trim() == "") {
		alert("名称不能为空");
		return false;
	}
	if ($("#indexurl").val().trim() == "") {
		$("#indexurl").val("");
	}
	var sp = /^[^ ^$^\"^\'^,^\^=^<^>]+$/;
	if (!sp.test($("#indexName").val())) {
		alert("不能有特殊字符");
		return false;
	}

	if (!$('form').data('changed')) {
		alert('没有一项值修改，无需保存！');
		return false;
	}

	return true;
}
String.prototype.trim = function() {
	// 用正则表达式将前后空格
	// 用空字符串替代。
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
$(document).ready(function() {
	$("form :input").change(function() {
		$('form').data('changed', true);
	});
});

$(function() {
//	var selecrDiv = '#selectDiv';
//	var inputID = '#input';
//	var selectID = '#select';
//	var width = $(selectID).width();
//	$(selecrDiv).css({
//		'position' : 'relative',
//		'width' : width
//	});
//	$(inputID).css({
//		'position' : 'absolute',
//		'left' : '2px',
//		'top' : '2px',
//		'z-index' : '2',
//		'width' : (width - 18),
//		'vertical-align' : 'middle',
//		'border' : 'none'
//	});
//	$(selectID).css({
//		'position' : 'relative',
//		'z-index' : '1',
//		'right' : '0',
//		'vertical-align' : 'middle'
//	});
//	$(selectID).change(function() {
//		var newValue = $(this).find('option:selected');
//		$(inputID).val(newValue.text());
//	});
//	$(inputID).change(function() {
//		$(selectID).val(""); 
//	});
});
