<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
 $(function(){
     $("#user").click(function(){
  		 window.location.href='<@spring.url "/group/authorization.nsf"/>?id=${groupChildren.uuid}&type=user';
  	});
  });
   $(function(){
     $("#dept").click(function(){
  		  window.location.href='<@spring.url "/group/authorization.nsf"/>?id=${groupChildren.uuid}&type=dept';
  	});
  });
  $(function(){
     $("#group").click(function(){
  		  window.location.href='<@spring.url "/group/authorization.nsf"/>?id=${groupChildren.uuid}&type=group';
  	});
  });

//]]>
</script>
<form name="f1" id="f1" method="post" action="">
	 <table  cellspacing="0" width="100%" align="center" valign="top" >
					<tr>
						<td align="left" >
								<table border="0" cellspacing="0" width="100%"  cellpadding="0">
								  <tr >
					              <td colspan="2" style="font-size:12px;">
					                 <input  type="button" id="user" class="button" value="可管理的用户" > 
					                <input  type="button" id="dept" class="button"  value="可管理的部门"> 
					                <input  type="button" id="group" class="button" value=" 可管理的组 "> 
					              </td>
			                     </tr>
								</table>
						</td>
					</tr>
					 <tr><td   valign="top" align="center" >
					 <#switch type >
						 <#case "user" >
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/group/usercontent.nsf"/>?id=${groupChildren.uuid}"  frameborder="0" width="100%" height="392" scrolling=no></iframe>
						 <#break>
						 <#case "dept" >
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/group/deptcontent.nsf"/>?id=${groupChildren.uuid}"  frameborder="0" width="100%" height="392" scrolling=no></iframe>
						 <#break>
						  <#case "group" >
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/group/groupcontent.nsf"/>?id=${groupChildren.uuid}"  frameborder="0" width="100%" height="392" scrolling=no></iframe>
						 <#break>
						 <#default>
						 </#switch>
					 </td></tr>
	  </table>
</form>