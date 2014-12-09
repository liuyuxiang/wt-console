<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
 $(function(){
     $("#useradmin").click(function(){
  		 window.location.href='<@spring.url "/app/appcontent.nsf"/>?groupuuid=${groupuuid}&type=appuserlist';
  	});
  });
   $(function(){
     $("#authorization").click(function(){
  		  window.location.href='<@spring.url "/app/appcontent.nsf"/>?groupuuid=${groupuuid}&type=authorization';
  	});
  });
      $(function(){
     $("#addgroup").click(function(){
       window.location.href='<@spring.url "/group/addgroup.nsf"/>?id=${groupuuid}';
  	});
  });
  $(function(){
     $("#updategroup").click(function(){
       window.location.href='<@spring.url "/group/updategroup.nsf"/>?id=${groupuuid}';
  	});
  });
   $(function(){
     $("#delgroup").click(function(){
      if(confirm("真的要删除吗？")){
		  		     var url="<@spring.url "/group/existgroupUnderGroup.nsf"/>?id=${groupuuid}"
			         $.post(url,function(data){
						data=checkAjaxData(data);
										       if(data=="false"){
										        var url="<@spring.url "/group/delgroup.nsf"/>?id=${groupuuid}";
										         window.location.href=url;
										           window.parent.parent.parent.location.reload();
										        }else{ 
										          alert("该组内存在用户，不能被删除!");
							                      return false; 
							                      }
							   });
		  		      }
       
  	});
  });
//]]>
</script>
<form name="f1" id="f1" method="post" action="">
	 <table  cellspacing="0" width="100%" align="center" valign="top"  >
					<tr>
						<td align="left" >
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><img id="useradmin" src="<#if type="appuserlist"><@spring.url "/style/default/images/ysq1.gif"/><#else><@spring.url "/style/default/images/ysq.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
										<td><img id="authorization" src="<#if type="authorization"><@spring.url "/style/default/images/dsq1.gif"/><#else><@spring.url "/style/default/images/dsq.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
										<#if    uumService.isGroupManager(uumService.getLoginUser(), groupChildren) >
										             <td><img id="addgroup" src="<@spring.url "/style/default/images/addgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
													 <td><img id="delgroup" src="<@spring.url "/style/default/images/delgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
									                 <td><img id="updategroup" src="<@spring.url "/style/default/images/modgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
										</#if>
									</tr>
								</table>
						</td>
					</tr>
					 <tr><td   valign="top" align="center">
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/app/appuserlist.nsf"/>?groupuuid=${groupuuid}&type=${type}" frameborder="0" width="100%" scrolling=no ></iframe>
					 </td></tr>
	  </table>
</form>