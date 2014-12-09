<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<script type="text/javascript">
//<![CDATA[
 $(function(){
     $("#dept").click(function(){
  		 window.location.href='<@spring.url "/main.nsf?menuId=0"/>';
  	});
  });
  $(function(){
     $("#user").click(function(){
  		 window.location.href='<@spring.url "/w/index"/>';
  	});
  });
   $(function(){
     $("#org").click(function(){
  		 window.location.href='<@spring.url "/main.nsf?menuId=1"/>';
  	});
  });
  
   $(function(){
     $("#app").click(function(){
  		 window.location.href='<@spring.url "/main.nsf?menuId=2"/>';
  	});
  });
     $(function(){
     $("#att").click(function(){
  		 window.location.href='<@spring.url "/main.nsf?menuId=3"/>';
  	});
  });
     $(function(){
     $("#task").click(function(){
  		 window.location.href='<@spring.url "/main.nsf?menuId=4"/>';
  	});
  });
  $(function(){
     $("#application").click(function(){
  		 window.location.href='<@spring.url "/main.nsf?menuId=5"/>';
  	});
  });
  $(function(){
     $("#system").click(function(){
  		 window.location.href='<@spring.url "/w/system"/>';
  	});
  });
  //]]>
</script>
<table cellspacing="0" cellpadding="0" border="0"> 
             <tr>
				<td id="user"><img id="userimg" style="cursor:pointer;" src="<#if currentMenu=="USER_PAGE" ><@spring.url "/style/default/images/seltab_d_yh.jpg" /><#else><@spring.url "/style/default/images/tab4_d_yyxt.jpg" /></#if>" id="tab1" /></td>
				<td id="org"><img  id="orgimg" style="cursor:pointer;" src="<#if currentMenu=="GROUP_PAGE" ><@spring.url "/style/default/images/seltab2.gif" /><#else><@spring.url "/style/default/images/tab2.gif" /></#if>" id="tab2" /></td>
			  	<#if  superstatus >
				<td id="app"><img  id="appimg" style="cursor:pointer;" src="<#if currentMenu=="APP_PAGE" ><@spring.url "/style/default/images/seltaby.gif" /><#else><@spring.url "/style/default/images/taby.gif" /></#if>" id="tab3" /></td>
			    <td id="task"><img  id="taskimg" style="cursor:pointer;" src="<#if currentMenu=="TASK_PAGE" ><@spring.url "/style/default/images/seltab_d.gif" /><#else><@spring.url "/style/default/images/tab4_d.gif" /></#if>" /></td>
				<td id="system"><img  id="sysimg" style="cursor:pointer;" src="<@spring.url "/style/default/images/tab4_sys.gif" />" id="tab" /></td>
			    </#if>
			</tr>
 </table>



