<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

  function subchick(obj){
  	if(!obj.checked){
  		$("#moveuser_All").removeAttr("checked");
  	}
  }
  
  $(function(){
     $("#moveuser_All").click(function(){
     	if($(this).attr("checked")){
     	
  		 $("[name='moveusers']").attr("checked",'true');
  		 }else{
  		 $("[name='moveusers']").removeAttr("checked");//取消全选
  		 }
  	});
     $("#searchSubmit").click(function(){
             if($("#searchcontent").val().length>0){
               f1.action='<@spring.url "/duty/userSearch.nsf" />';
               $("#f1").submit();
               }else{
                alert("请输入查询内容!");
              }
  	});
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}&pagesize='+pagesizegovalue;
  	});
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});

    $("#movegroupuser").click(function(){
	    if($("[name='moveusers']:checked").length<1){
	    	alert("至少选择一个！");
	    	return;
	    }
	    var url = '<@spring.url "/rest/duty/${duty.uuid}/remove/"/>';
	    $("[name='moveusers']:checked").each(function(){
			$.ajax({
			  url: url+$(this).val(),
			  async: false,
			  type: "POST"
			 })
	    });
	    window.location.href='<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}';
    });
});
//]]>
</script>
<form name="f1" id="f1" method="post" action="">
	 <table  cellspacing="0" width="100%" align="center"  >
					 <tr>
									<td colspan="8" width="100%" align="left" valign="top">
											<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
													<td height="18" width="30%">&nbsp;【${duty.name}】成员列表
													<input type='hidden' name="id" id="id" value="${duty.uuid}"/>
													</td>													</td>
													<td height="21" width="52%" align="right">
														 <input type="text" name="searchcontent" onkeypress="checkEventName();" id="searchcontent" value="" size="10"> 
														 <select id="searchType" name="searchvalue" >
															<option value="username">按姓名搜索</option>
															<option value="userid">按ID搜索</option>
														</select> 
														<input  type="button" class="button"  id="searchSubmit" value="搜索" >
													</td>
													<td height="21" align="right" width="18%">
														<input id="movegroupuser" type="button" class="button"  value="解除" >
													</td>
												</tr>
											</table>
									</td>
					 </tr>
					 <tr>
								<td colspan="8">
									 <table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<td >序号</td>
											<td >姓名</td>
                                    <td >账号</td>
                                     <td ><input type="checkbox" value="0" id="moveuser_All"  ></td>
										</tr>
									 <#if  list ?? >
										<#list list as res>
										    <#if  (res_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
											<td >${javapage.getDataStart()+1+res_index}</td>
											<td >${res.name?html}</a></td>
											<td >${res.id}</td>
		                                    <td width="5%"><input type="checkbox" value="${res.id}" id="moveusers" name="moveusers" onclick="subchick(this)"></td>
										</tr>
										</#list>
										</#if>
									</table>
								</td>
					 </tr>
					 <tr>
									<td align=right colspan="3">
											<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
												<tr>
													<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
													<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
													<input type="button" class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
													<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
													<input type=button class="button"  value="GO"  id="pagego"></td>
													<td width="41%">
													<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
												      <a href="<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}&page=1&pagesize=${javapage.getPageSize()}">首页</a>
													  <#if javapage.isHasPreviousPage()>
													  <a href="<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
													  <#if javapage.isHasNextPage()>
													  <a href="<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
													  <a href="<@spring.url "/duty/userList.nsf"/>?id=${duty.uuid}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
													 </div>
													</td>
												</tr>
											</table>
									</td>
					 </tr>
	  </table>
</form>