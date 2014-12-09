<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
 $(function(){
     $("#useradmin").click(function(){
        var date=new Date;
		var time=parseInt(date.getSeconds());
     
  		 window.location.href='<@spring.url "/group/content.nsf"/>?id=${groupChildren.uuid}&type=useradmin&time='+time;
  	});
  });
   $(function(){
     $("#authorization").click(function(){
  		  window.location.href='<@spring.url "/group/content.nsf"/>?id=${groupChildren.uuid}&type=authorization';
  	});
  });
      $(function(){
     $("#addgroup").click(function(){
       window.location.href='<@spring.url "/group/addgroup.nsf"/>?id=${groupChildren.uuid}';
  	});
  });
  $(function(){
     $("#updategroup").click(function(){
       window.location.href='<@spring.url "/group/updategroup.nsf"/>?id=${groupChildren.uuid}';
  	});
  });
$(function(){
    $("#delgroup").click(function(){
        if(confirm("真的要删除吗？")){
            var url="<@spring.url "/group/existgroupUnderGroup.nsf"/>?id=${groupChildren.uuid}"
            $.post(url,function(data){
                data=checkAjaxData(data);
                if(data=="false"){
                    var url="<@spring.url "/group/delgroup.nsf"/>?id=${groupChildren.uuid}";
                    $.post(url,function(data){
                        window.parent.location.reload();
                    });
                }else{ 
                    alert("该组内存在用户或其他被管资源，不能被删除!");
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
										<td><img id="useradmin" src="<#if type="useradmin"><@spring.url "/style/default/images/user.gif"/><#else><@spring.url "/style/default/images/user1.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
										<#if isSuper>
										             <td><img id="authorization" src="<#if type="authorization"><@spring.url "/style/default/images/qx.gif"/><#else><@spring.url "/style/default/images/qx1.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
										</#if>
										<#if isAdmin >
										             <td><img id="addgroup" src="<@spring.url "/style/default/images/addgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
													 <td><img id="delgroup" src="<@spring.url "/style/default/images/delgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
									                 <td><img id="updategroup" src="<@spring.url "/style/default/images/modgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
									    </#if>
									</tr>
								</table>
						</td>
					</tr>
					 <tr><td   valign="top" align="center">
					 <#switch type >
						 <#case "useradmin" >
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/group/useradmin.nsf"/>?id=${groupChildren.uuid}" frameborder="0" width="100%" ></iframe>
						 <#break>
						 <#case "authorization" >
							 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/group/authorization.nsf"/>?id=${groupChildren.uuid}" frameborder="0" width="100%" scrolling=no></iframe>
						 <#break>
						 <#default>
						 </#switch>
					 </td></tr>
	  </table>
</form>