<#import "/spring.ftl" as spring />
<#assign stylePath = springMacroRequestContext.getContextPath()+"/style/hirisun" />
<link rel="stylesheet" href="${stylePath}/style.css"/>
<link rel="stylesheet" href="${stylePath}/wstyle.css"/>
    <style type="text/css">
        .pop-box {   
            z-index: 9999; /*这个数值要足够大，才能够显示在最上层*/  
            margin-bottom: 3px;   
            display: none;   
            position: absolute;   
            background: #FFF;   
            border:solid 1px #6e8bde;   
        }   
          
        .pop-box h4 {   
            color: #FFF;   
            cursor:default;   
            height: 18px;   
            font-size: 14px;   
            font-weight:bold;   
            text-align: left;   
            padding-left: 8px;   
            padding-top: 4px;   
            padding-bottom: 2px;   
            background: url("../images/header_bg.gif") repeat-x 0 0;   
        }   
          
        .pop-box-body {   
            clear: both;   
            margin: 4px;   
            padding: 2px;   
        } 
        
        
        .mask {   
            color:#C7EDCC;
            background-color:#C7EDCC;
            position:absolute;
            top:0px;
            left:0px;
            filter: Alpha(Opacity=60);
        } 
    </style>
<script type="text/javascript">
      function popupDiv(div_id) {   
        var div_obj = $("#"+div_id);  
        var windowWidth = document.body.clientWidth;       
        var windowHeight = document.body.clientHeight;  
        var popupHeight = div_obj.height();       
        var popupWidth = div_obj.width();    
        var offsetHeight = document.body.offsetHeight;
        if(offsetHeight<$("#s2").height()){
        	offsetHeight=$("#s2").height();
        }
        //添加并显示遮罩层   
        $("<div id='mask'></div>").addClass("mask")   
                                  .width(document.body.scrollWidth)   
                                  .height(offsetHeight + document.body.scrollHeight)   
                                  .appendTo("body")   
                                  .fadeIn(200);
        div_obj.css({"position": "absolute"})   
               .animate({left: windowWidth/2-popupWidth/2,    
                         top: popupHeight/2, opacity: "show" }, "slow");   
                        
    }   
      
    function hideDiv(div_id) {   
        $("#mask").remove();   
        $("#" + div_id).animate({left: 0, top: 0, opacity: "hide" }, "slow");   
    }  
function check(event) {   
    var code;   
    if (!event) {
    	event = window.event;
    }   
    if (event.keyCode) {
    	code = event.keyCode;   
    }else if (event.which){
    	code = event.which; 
    }  
    if (((event.keyCode == 8) &&                                                    //BackSpace    
         ((event.srcElement.type != "text" &&    
         event.srcElement.type != "textarea" &&    
         event.srcElement.type != "password") ||    
         event.srcElement.readOnly == true)) ||    
        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    //CtrlN,CtrlR    
        (event.keyCode == 116) ) {                                                   //F5    
        event.keyCode = 0;    
        event.returnValue = false;    
    }   
    return true;   
} 
if (typeof window.event != 'undefined') {
    document.onkeydown = function() {
        var type = event.srcElement.type;
        var code = event.keyCode;
    if (((event.keyCode == 8) &&                                                    //BackSpace    
         ((event.srcElement.type != "text" &&    
         event.srcElement.type != "textarea" &&    
         event.srcElement.type != "password") ||    
         event.srcElement.readOnly == true)) ||    
        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    //CtrlN,CtrlR    
        (event.keyCode == 116) ) {                                                   //F5    
        event.keyCode = 0;    
        event.returnValue = false;    
    }   
    return true;   
    }
} else { // FireFox/Others
    document.onkeypress = function(e) {
        var type = e.target.localName.toLowerCase();
        var code = e.keyCode;
        if ((code != 8 && code != 13) ||
            (type == 'input' && code != 13 ) ||
            (type == 'textarea') ||
            (type == 'submit' && code == 13)) {
            return true;
        } else {
            return false ;
        }
    }
}
</script>