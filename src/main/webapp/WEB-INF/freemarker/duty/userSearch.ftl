<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

function goSubmit(page){
	$("#pagegovalue").val(page);
	f1.action="<@spring.url "/duty/userSearch.nsf" />";
	$("#f1").submit();
}
function addchick(obj){
	if(!obj.checked){
		$("#adduser_All").removeAttr("checked");
	}
}
function delchick(obj){
	if(!obj.checked){
		$("#deluser_All").removeAttr("checked");
	}
}
$(function(){
	$("#backgroup").click(function(){
		window.location.href='<@spring.url "/duty/userList.nsf" />?id=${duty.uuid}';
	});
	$("#searchSubmit").click(function(){
		if($("#searchcontent").val().length>0){
			goSubmit($("#pagegovalue").val());
		}else{
			alert("请输入查询内容!");
		}
	});
	$("#adduser_All").click(function(){
		if($(this).attr("checked")){
			$("[name='addusers']:enabled").attr("checked",'true');
		}else{
			$("[name='addusers']:enabled").removeAttr("checked");//取消全选
		}
	});
	$("#deluser_All").click(function(){
		if($(this).attr("checked")){
			$("[name='delusers']:enabled").attr("checked",'true');
		}else{
			$("[name='delusers']:enabled").removeAttr("checked");//取消全选
		}
	});
	$("#pagesizego").click(function(){
		goSubmit($("#pagegovalue").val());
	});
	$("#pagego").click(function(){
		goSubmit($("#pagegovalue").val());
	});
	$("#home").click(function(){
		goSubmit(1);
	});
	$("#last").click(function(){
		goSubmit("${javapage.getLastPage()}");
	});
	$("#previous").click(function(){
		goSubmit("${javapage.getPreviousPage()}");
	});
	$("#next").click(function(){
		goSubmit("${javapage.getNextPage()}");
	});
	$("#addusertogroup").click(function(){
		if($("[name='addusers']:checked").length<1){
			alert("至少选择一个！");
			return;
		}
		var url = '<@spring.url "/rest/duty/${duty.uuid}/add/"/>';
		$("[name='addusers']:checked").each(function(){
			$.ajax({
				url: url+$(this).val(),
				async: false,
				type: "POST"
			});
		});
		goSubmit($("#pagegovalue").val());
	});
	$("#deluserfromgroup").click(function(){
		if($("[name='delusers']:checked").length<1){
			alert("至少选择一个！");
			return;
		}
		var url = '<@spring.url "/rest/duty/${duty.uuid}/remove/"/>';
		$("[name='delusers']:checked").each(function(){
			$.ajax({
				url: url+$(this).val(),
				async: false,
				type: "POST"
			});
		});
		goSubmit($("#pagegovalue").val());
	});
});

//]]>
</script>
</head>
<body style="background:#FFFFFF;">
<form name="f1" id="f1" method="post" action="">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
            <table width="100%" border="0" align="center" cellpadding="0"
                cellspacing="0">
                <tr>
                    <td width="94%" align="left" valign="top">
                    <table id="table1" border="0" cellspacing="0" align="center"
                        width="95%">
                        <tr style="background-color:#DAE9F9;font-size:12px;"> 
                            <td height="18" width="22%">&nbsp;搜索结果&nbsp; </td><td>
                            <input  class="button" type="button" id="backgroup" value="返回【${duty.name}】列表" >
                           <input  class="button" type="hidden" value="${duty.uuid}" name="id" id="id" ></td>
                            <td height="21" align="right" width="78%">
                            <input type="text"  name="searchcontent" id="searchcontent" size="10" onkeypress="checkEventName();" value="${searchcontent?if_exists}">  
							  <select id="searchType" name="searchvalue" >
								<option value="username" <#if searchvalue??&&searchvalue=="username" >selected</#if> >按姓名搜索</option>
								<option value="userid" <#if searchvalue??&&searchvalue=="userid" >selected</#if>>按ID搜索</option>
							  </select> 
							 <input  type="button" class="button"  id="searchSubmit" value="搜索" >
							  <input  class="button" type="button" id="addusertogroup"  value="添加"  >
							  <input  class="button" type="button" id="deluserfromgroup"  value="解绑"  >
							 </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                            <table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
								<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
									<td >序号</td>
									<td >姓名</td>
                                    <td >账号</td>
                                    <td >添加<input type="checkbox" value="0" id="adduser_All" ></td>
                                    <td >解除<input type="checkbox" value="0" id="deluser_All" ></td>
                                </tr>
                                 
	                               <#if  userlist ?? >
									<#list userlist as res>
									    <#if  (res_index+1)%2=0>
									 	<tr style="background-color:#DAE9F9;" align="center">
									 	<#else>
									 	<tr style="background-color:#EEEEEE;" align="center">
									 	</#if>
										<td width="5%">${javapage.getDataStart()+1+res_index}</td>
											<td >${res.user.name?html}</td>
											<td >${res.user.id}</td>
		                                    <td width="5%">
	                                    <input type="checkbox" value="${res.user.id}" <#if res.duty?exists>disabled</#if> id="addusers" name="addusers" onclick="addchick(this)">
	                                    </td>
		                                    <td width="5%">
	                                    <input type="checkbox" value="${res.user.id}" <#if res.duty?exists><#else>disabled</#if> id="delusers" name="delusers" onclick="delchick(this)">
	                                    </td>
									</tr>
									</#list>
									</#if>
                            </table>
                            </td>
                        <tr>
                            <td align=left colspan="3">
                             <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
								<tr>
									<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
									<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" name="pagesize" id="pagesizegovalue"  > 条 
									<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
									<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" name="page" id="pagegovalue"> 
									<input type=button class="button"  value="GO"  id="pagego"></td>
									<td width="41%">
									<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
								      <a href="#" id="home">首页</a>
									  <#if javapage.isHasPreviousPage()>
									  <a href="#" id="previous">上页</a><#else>上页</#if>
									  <#if javapage.isHasNextPage()>
									  <a href="#" id="next" >下页</a><#else>下页</#if>
									  <a href="#" id="last">尾页</a>
									 </div>
									</td>
								</tr>
							</table>
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
            </table>
            </td>
        </tr>
    </table>
</form>
    </body>
</html>