<#import "/spring.ftl" as spring />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>应用列表</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
</head>
<script type="text/javascript">
//<![CDATA[
	
   $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/app/applist.nsf" />?pagesize='+pagesizegovalue;
  	});
  });
   $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/app/applist.nsf" />?page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
 $(function(){
     $("#addappbutton").click(function(){
  		 window.location.href='<@spring.url "/app/appadd.nsf"/>';
  	});
  });
 function isDel(uuid){
		 if(confirm("确定删除?")){
  		 window.location.href='<@spring.url "/app/appdel.nsf"/>?appuuid='+uuid;
		}
 }  

//]]>
</script>
<script type="text/javascript">
	// window.parent.parent.frames["lu"].location.reload(); 
	// alert(parent.document.getElementById("lu").src);
</script>
<body style="background:#FFFFFF;">

	<form action="#" method="post">
		<table id="" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="100%">
			<tr>
				<td width="10%"><input type="button" class="button" value="增加应用" id="addappbutton" ></td>
			</tr>
			<tr  style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
				<td width="40" align="center" class="">序号</td>
				<td width="90" align="center" class="">系统名称</td>
				<td align="center" class="">系统标识</td>
				<td align="center" class="">系统简要描述</td>
				<td align="center" class="">操作</td>
			</tr>
			<#if applist??>
				<#list applist as app>
				    <#if  (app_index+1)%2=0>
					 	<tr style="background-color:#DAE9F9;" align="center">
				 	<#else>
					 	<tr style="background-color:#EEEEEE;" align="center">
				 	</#if>
				 		<td >${javapage.getDataStart()+1+app_index}</td>
				 		<td ><a href="<@spring.url "/app/appeditmain.nsf"/>?appuuid=${app.uuid}">${app.name?html}</a></td>
						<td >${app.appId}</td>
						<td >
							<#if app.appRemark??>
								${app.appRemark}
							<#else>
								&nbsp;
							</#if>
						</td>
						<td ><a id="${app.uuid}" href="#" onclick="isDel('${app.uuid?js_string}')">删除</a></td>
						</tr>
				</#list>
			</#if>
		</table>	
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
			<tr>
				<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
						<input size=1 value="${javapage.getPageSize()}" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
						<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
						<input size=1 value="${javapage.getCurrentPage()}" id="pagegovalue"> 
						<input type=button class="button"  value="GO"  id="pagego"></td>
						<td width="41%">
					<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
						<a href="<@spring.url "/app/applist.nsf"/>?page=1&pagesize=${javapage.getPageSize()}">首页</a>
						<#if javapage.isHasPreviousPage()>
						<a href="<@spring.url "/app/applist.nsf"/>?page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
						<#if javapage.isHasNextPage()>
						<a href="<@spring.url "/app/applist.nsf"/>?page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
						<a href="<@spring.url "/app/applist.nsf"/>?page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>