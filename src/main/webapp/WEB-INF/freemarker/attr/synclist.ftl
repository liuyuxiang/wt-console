<#import "/spring.ftl" as spring />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>同步列表</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
</head>
<script type="text/javascript">
//<![CDATA[
	
   $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/attr/synclist.nsf" />?id=${id}&type=${type}&pagesize='+pagesizegovalue;
  	});
  });
   $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/attr/synclist.nsf" />?id=${id}&type=${type}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
 $(function(){
     $("#addattrbutton").click(function(){
  		 window.location.href='<@spring.url "/attr/syncadd.nsf"/>?id=${id}&type=${type}';
  	});
  });
 function isDel(uuid){
	if(confirm("确定删除?")){
		window.location.href='<@spring.url "/attr/syncdel.nsf"/>?id=${id}&type=${type}&syncuuid='+uuid;
	}
 }
 function isUpd(uuid){
	window.location.href='<@spring.url "/attr/syncedit.nsf"/>?id=${id}&type=${type}&syncuuid='+uuid;
 }
//]]>
</script>
<body  >

	<form id="f1" name="f1" action="#" method="post">
		<table id="" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="100%">
			<tr>
				<td width="10%"><input type="button" class="button" value="增加同步属性" id="addattrbutton" ></td>
				<td colspan="8" width="94%" align="left" valign="top">
					<table width="100%" style="background-color:#B7D6F5;font-size:12px;">
						<tr>
							<td height="18" width="30%" align="left">当前位置&nbsp;
							<#switch id>
							  <#case "0">
							    用户
							    <#break>
							  <#case "1">
							    角色
							    <#break>
							  <#default>
							    部门
							</#switch>
							</td>
							<td height="21" width="52%" align="right">
							</td>
							<td height="21" align="right" width="18%">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr  style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
				<td width="40" align="center" class="">序号</td>
				<td width="90" align="center" class="">同步属性类型</td>
				<td align="center" class="">同步属性编码</td>
				<td align="center" class="">同步处理方式</td>
				<td align="center" class="">操作</td>
			</tr>
			<#if attrlist??>
				<#list attrlist as app>
				    <#if  (app_index+1)%2=0>
					 	<tr style="background-color:#DAE9F9;" align="center">
				 	<#else>
					 	<tr style="background-color:#EEEEEE;" align="center">
				 	</#if>
				 		<td >${javapage.getDataStart()+1+app_index}</td>
				 		<td >
				 			<#if app.propType =="b">
								基本属性
							<#else>
								扩展属性
							</#if>
						</td>
						<td >
							<#if app.propName??>
								<a href="#" onclick="isUpd('${app.uuid?js_string}')">${app.propName?html}</a>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td >
							<#if app.transFunc??>
								${app.transFunc}
							<#else>
								&nbsp;
							</#if>
						</td>
						<td ><a id="updbutton" href="#" onclick="isUpd('${app.uuid?js_string}')">编辑</a>&nbsp
						<a id="delbutton" href="#" onclick="isDel('${app.uuid?js_string}')">删除</a></td>
						</tr>
				</#list>
			</#if>
		</table>	
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
			<tr>
				<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
						<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
						<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
						<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
						<input type=button class="button"  value="GO"  id="pagego"></td>
						<td width="41%">
					<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
						<a href="<@spring.url "/attr/synclist.nsf"/>?id=${id}&type=${type}&page=1&pagesize=${javapage.getPageSize()}">首页</a>
						<#if javapage.isHasPreviousPage()>
						<a href="<@spring.url "/attr/synclist.nsf"/>?id=${id}&type=${type}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
						<#if javapage.isHasNextPage()>
						<a href="<@spring.url "/attr/synclist.nsf"/>?id=${id}&type=${type}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
						<a href="<@spring.url "/attr/synclist.nsf"/>?id=${id}&type=${type}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>