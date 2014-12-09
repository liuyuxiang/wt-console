function heigth (){
         var pobj = parent.document.getElementsByName("rightframe")[0];
         if (pobj != undefined) {
        	 pobj.height=document.body.scrollHeight;
        	 var ppobj = parent.parent.document.getElementsByName("rightframe")[0];
        	 if (ppobj != undefined) {
        		 ppobj.height=pobj.document.body.scrollHeight;
        		 var pppobj = parent.parent.parent.document.getElementsByName("rightframe")[0];
        		 if (pppobj != undefined) {
        			 pppobj.height=ppobj.document.body.scrollHeight;
        		 }
        	 }
         }
}