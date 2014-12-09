<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<#if  loginuser ?exists >
<script type="text/javascript">
//<![CDATA[
 $(function(){
     $("#userList").click(function(){
  		 window.location.href='<@spring.url "/tasklist/appMain.nsf"/>?userId=${userId}&type=userList';
  	});
  });
   $(function(){
     $("#userfilterList").click(function(){
  		  window.location.href='<@spring.url "/tasklist/appMain.nsf"/>?userId=${userId}&type=userfilterList';
  	});
  });
//]]>
</script>
        <div id='pop-div' style="width: 300px;" class="pop-box">  
            <h4>标题位置</h4>  
            <div class="pop-box-body" >  
                <p>  
                    数据加载中，请稍等…………
                </p>  
            </div>  
        </div>
<script type="text/javascript">
popupDiv('pop-div');
$(function(){
	hideDiv('pop-div');
});
</script>

<form name="f1" id="f1" method="post" action="">
	 <table  cellspacing="0" width="100%" align="center" valign="top"  >
					<tr >
						<td align="left" >
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td ><img id="userList" src="<#if type="userList"><@spring.url "/style/default/images/dcl1.gif"/><#else><@spring.url "/style/default/images/dcl.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
										<td ><img id="userfilterList" src="<#if type="userfilterList"><@spring.url "/style/default/images/ygl1.gif"/><#else><@spring.url "/style/default/images/ygl.gif"/></#if>" style="cursor: hand" ></td><td>&nbsp;</td>
									</tr>
								</table>
						</td>
					</tr>
					 <tr><td   valign="top" align="center">
					 <#switch type >
						 <#case "userList" >
						<iframe name="rightframe" data-app="uum"  src="<@spring.url "/tasklist/userList.nsf"/>?userId=${userId}"  width="99%"  height="470" frameborder="0" scrolling=auto></iframe>
						<#break>
						 <#case "userfilterList" >
						 <iframe name="rightframe" data-app="uum"  src="<@spring.url "/tasklist/userfilterList.nsf"/>?userId=${userId}"  width="99%"  height="470" frameborder="0" scrolling=auto></iframe>
						<#break>
						 <#default>
						 
					</#switch>
					</td></tr>
	  </table>
</form>
<#else>
<font color="red">你还没登录！</font>
</#if>
