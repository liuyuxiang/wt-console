<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
 $(function(){
     $("#attribute").click(function(){
  		 window.location.href='<@spring.url "/attr/list.nsf"/>?id=${id}&type=attribute';
  	});
  });
   $(function(){
     $("#synchronous").click(function(){
  		  window.location.href='<@spring.url "/attr/list.nsf"/>?id=${id}&type=synchronous';
  	});
  });
//]]>
</script>
<form name="f1" id="f1" method="post" action="">
	 <table  cellspacing="0" width="100%" align="center" valign="top"  >
					<tr style="display:none">
						<td align="left" >
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td ><img id="attribute" src="<#if type="attribute"><@spring.url "/style/default/images/user.gif"/><#else><@spring.url "/style/default/images/user1.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
										<td ><img id="synchronous" src="<#if type="synchronous"><@spring.url "/style/default/images/qx.gif"/><#else><@spring.url "/style/default/images/qx1.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
									</tr>
								</table>
						</td>
					</tr>
					 <tr><td   valign="top" align="center">
					 <#switch type >
						 <#case "attribute" >
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/attr/attrlist.nsf"/>?id=${id}&type=${type}" frameborder="0" width="100%"  height="420" scrolling=no></iframe>
						 <#break>
						 <#case "synchronous" >
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/attr/synclist.nsf"/>?id=${id}&type=${type}" frameborder="0" width="100%"  height="420" scrolling=no></iframe>
						 <#break>
						 <#default>
					</#switch>
					</td></tr>
	  </table>
</form>