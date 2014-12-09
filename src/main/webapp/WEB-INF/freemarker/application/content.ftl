<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[
 $(function(){
	$("#authorized").click(function(){
	    window.location.href='<@spring.url "/application/content.nsf"/>?id=${application.uuid}&type=authorized';
	});
	$("#unauthorized").click(function(){
	    window.location.href='<@spring.url "/application/content.nsf"/>?id=${application.uuid}&type=unauthorized';
	});
	$("#updateapp").click(function(){
	    window.location.href='<@spring.url "/application/updateApp.nsf"/>?uuid=${application.uuid}';
	});
	$("#delgroup").click(function(){
	    if(confirm("真的要删除吗？")){
	        var url="<@spring.url "/application/existResourceUnderApp.nsf"/>?id=${application.uuid}"
	        $.post(url,function(data){
	            data=checkAjaxData(data);
	            if(data=="true"&&!confirm("应用系统下有用户映射,确认删除吗?")){
	            	return false;
	            }
	            var url="<@spring.url "/application/delete.nsf"/>?id=${application.uuid}";
	            $.post(url,function(data){
	                window.parent.parent.parent.location.reload();
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
                        <td><img id="authorized" src="<#if type="authorized"><@spring.url "/style/default/images/user.gif"/><#else><@spring.url "/style/default/images/user1.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
                        <td><img id="unauthorized" src="<#if type="unauthorized"><@spring.url "/style/default/images/qx.gif"/><#else><@spring.url "/style/default/images/qx1.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
                        <td><img id="delgroup" src="<@spring.url "/style/default/images/delgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
                        <td><img id="updateapp" src="<@spring.url "/style/default/images/modgroup.gif"/>" style="cursor: hand" ></td><td>&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td valign="top" align="center">
                <#switch type >
                <#case "authorized" >
                <iframe name="rightframe" data-app="uum"  src="<@spring.url "/application/authorizedList.nsf"/>?id=${application.uuid}" frameborder="0" width="100%" scrolling=no ></iframe>
                <#break>
                <#case "unauthorized" >
                <iframe name="rightframe" data-app="uum"  src="<@spring.url "/application/unauthorizedList.nsf"/>?id=${application.uuid}" frameborder="0" width="100%" scrolling=no></iframe>
                <#break>
                <#default>
                </#switch>
            </td>
        </tr>
    </table>
</form>