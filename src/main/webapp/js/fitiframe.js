function fitheight(win) {
	var key = "b_" + guid();
	var uuid = guid();

	$(win.document.body).attr(key, uuid);
	try {
		var parentFrames = null;
		try {
			parentFrames = $(win.parent.document).find("iframe");
		} catch(e) {
			//Permission denied
		}
		if(parentFrames != null) {
			for(var c = 0; c < parentFrames.length; c++) {
				var parentFrame = parentFrames[c];
				if($(parentFrame).data("app")!="uum"){
					continue;
				}
				var childBody = $(parentFrame).contents().find("body[" + key + "=" + uuid + "]");

				if(childBody.length > 0) {
					$(parentFrame).height(0);

					var fHeight = $(win.document).find("html").outerHeight(true);
					var f2Height = $(win.document).find("body").outerHeight(true);
					var f3Height = win.document.body.scrollHeight;
					var height = Math.max(fHeight, f2Height);
					height = Math.max(height, f3Height);
					//alert(fHeight + "-" + f2Height + "-" + f3Height);
					$(parentFrame).height(height);

					fitheight(win.parent);
					break;
				}

			}
		}
	} finally {
		$(win.document.body).removeAttr(key);
	}

	function guid() {
		function s4() {
			return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
		}

		return (s4() + s4() + "-" + s4() + "-" + s4() + "-" + s4() + "-" + s4() + s4() + s4());
	}

}