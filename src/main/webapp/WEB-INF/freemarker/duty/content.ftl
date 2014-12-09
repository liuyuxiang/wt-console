<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
 $(function(){
	$("#updategroup").click(function(){
	    window.location.href='<@spring.url "/duty/duty.nsf"/>?uuid=${duty.uuid}';
	});
	$("#delgroup").click(function(){
	    if(confirm("真的要删除吗？")){
	        var url="<@spring.url "/rest/duty/${duty.uuid}/hasusers"/>"
	        $.get(url,function(data){
	            if(data&&!confirm("应用系统下有用户映射,确认删除吗?")){
	            	return false;
	            }
	            var url="<@spring.url "/rest/duty/${duty.uuid}"/>";
				$.ajax({
				   type: "DELETE",
				   url: url,
				   success: function(msg){
				     	window.parent.location.reload();
				   },
				   error: function(msg){
				    	alert("删除失败"); 
				   }
	        	});
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
										<td>
										<#if isSuper>
													 <td><img id="delgroup" src="<@spring.url "/style/default/images/delgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
									                 <td><img id="updategroup" src="<@spring.url "/style/default/images/modgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
									    </#if>
									</tr>
								</table>
						</td>
					</tr>
        <tr>
            <td valign="top" align="center">
                <iframe name="rightframe" data-app="uum"  src="<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}" frameborder="0" width="100%" scrolling=no ></iframe>
            </td>
        </tr>
    </table>
</form>