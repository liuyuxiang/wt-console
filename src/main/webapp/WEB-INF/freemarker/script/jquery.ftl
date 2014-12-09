<#import "/spring.ftl" as spring />
<script type="text/javascript" src="<@spring.url "/js/jquery/jquery-1.6.2.min.js"/>" ></script>
<script type="text/javascript" src="<@spring.url "/js/jquery/sorter/jquery.tablesorter.js"/>"></script>
<script type="text/javascript" src="<@spring.url "/js/jquery/sorter/jquery.metadata.js"/>"></script>
<script type="text/javascript" src="<@spring.url "/js/fitiframe.js"/>" ></script>
<script type="text/javascript" src="<@spring.url "/script/constants.nsf"/>"></script>
<script type="text/javascript" src="<@spring.url "/js/validate.js" />"></script>
<script language="javascript"  type="text/javascript" src="<@spring.url "/js/checkText.js" />"></script>
<script type="text/javascript">
//<![CDATA[
	
   $(function(){
  	$("#pagegovalue").attr("style","ime-mode:disabled");
  	$("#pagegovalue").keypress(function(e){
  		return checkKeyPressNum(e);
  	});
  	$("#pagegovalue").bind('paste',function(){
  		return false;
  	});
  	
  	$("#pagesizegovalue").attr("style","ime-mode:disabled");
  	$("#pagesizegovalue").keypress(function(e){  		
  		return checkKeyPressNum(e);
  	});
  	$("#pagesizegovalue").bind('paste',function(){
  		return false;
  	});

  	fitheight(window);
 function getDocumentBody() {
    	    return (document.compatMode && document.compatMode != "BackCompat") ? document.documentElement : document.body;
    	} 	
    	
    	$("#rightframe1").height(getDocumentBody().clientHeight-5-$(".header_background").height()-$("#menu").height()-$("#footer").height());
  });
//]]>
</script>


