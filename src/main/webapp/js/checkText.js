function checkEventAll() {
	// 所有特殊字符
	switch (true) {
	case event.keyCode > 32 && event.keyCode < 48:
	case event.keyCode > 57 && event.keyCode < 65:
	case event.keyCode > 90 && event.keyCode < 97:
	case event.keyCode > 122 && event.keyCode < 127:
		event.returnValue = false;
		break;
	default:
		event.returnValue = true;
	}
}
function checkEventName() {
	// 名字框
	switch (true) {
	case event.keyCode == 45:// -
	case event.keyCode == 95:// _
		event.returnValue = true;
		break;
	case event.keyCode > 32 && event.keyCode < 48:
	case event.keyCode > 57 && event.keyCode < 65:
	case event.keyCode > 90 && event.keyCode < 97:
	case event.keyCode > 122 && event.keyCode < 127:
		event.returnValue = false;
		break;
	default:
		event.returnValue = true;
	}
}
function checkEventText() {
	switch (true) {
	case event.keyCode == 40:// (
	case event.keyCode == 41:// )
	case event.keyCode == 44:// ,
	case event.keyCode == 45:// -
	case event.keyCode == 58:// :
	case event.keyCode == 59:// ;
	case event.keyCode == 64:// @
	case event.keyCode == 95:// _
		event.returnValue = true;
		break;
	case event.keyCode > 32 && event.keyCode < 48:
	case event.keyCode > 57 && event.keyCode < 65:
	case event.keyCode > 90 && event.keyCode < 97:
	case event.keyCode > 122 && event.keyCode < 127:
		event.returnValue = false;
		break;
	default:
		event.returnValue = true;
	}
}
function checkEvent() {
	switch (true) {
	case event.keyCode == 40:// (
	case event.keyCode == 41:// )
	case event.keyCode == 44:// ,
	case event.keyCode == 45:// -
	case event.keyCode == 46:// .
	case event.keyCode == 58:// :
	case event.keyCode == 59:// ;
	case event.keyCode == 64:// @
	case event.keyCode == 95:// _
		event.returnValue = true;
		break;
	case event.keyCode > 32 && event.keyCode < 48:
	case event.keyCode > 57 && event.keyCode < 65:
	case event.keyCode > 90 && event.keyCode < 97:
	case event.keyCode > 122 && event.keyCode < 127:
		event.returnValue = false;
		break;
	default:
		event.returnValue = true;
	}
}
function checkEventEN() {
	switch (true) {
	case event.keyCode > 64 && event.keyCode < 91:
	case event.keyCode > 96 && event.keyCode < 123:
		event.returnValue = true;
		break;
	default:
		event.returnValue = false;
	}
}
function checkEventNUM() {
	// alert();
	switch (true) {
	case event.keyCode > 47 && event.keyCode < 58:
		event.returnValue = true;
		break;
	default:
		event.returnValue = false;
	}
}
function checkKeyPressNum(e) {
	var pass=true;
	if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))  {   
		pass=false;  
	}
	return pass;
}
function checkEventID() {
	switch (true) {
	case event.keyCode == 45:// -
	case event.keyCode == 95:// _
	case event.keyCode > 64 && event.keyCode < 91:
	case event.keyCode > 96 && event.keyCode < 123:
	case event.keyCode > 47 && event.keyCode < 58:
		event.returnValue = true;
		break;
	default:
		event.returnValue = false;
	}
}
function checkEventID1() {
	switch (true) {
	case event.keyCode == 45:// -
	case event.keyCode == 47:// /
	case event.keyCode == 95:// _
	case event.keyCode > 64 && event.keyCode < 91:
	case event.keyCode > 96 && event.keyCode < 123:
	case event.keyCode > 47 && event.keyCode < 58:
		event.returnValue = true;
		break;
	default:
		event.returnValue = false;
	}
}

function returnRealValueNUM(obj) {
	obj.value = obj.value.replace(/\D/g, '');
}

function checkType(obj) {
	var regx = new RegExp(obj.attvalue);
	if (obj.value.length > 0 && regx.test(obj.value) == false) {
		obj.focus();
		alert(obj.typename + "的格式不正确");
	}
}

function checkAjaxData(data){
	var reg = new RegExp("<SCRIPT.*\/SCRIPT>","gi");
    return data.replace(/\n/g,"").replace(/\r/g,"").replace(reg,"");
}
