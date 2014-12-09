<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
//<![CDATA[
	
   $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/attr/search.nsf" />?id=${id}&type=${type}&searchType=${searchType}&searchcontent=${searchcontent}&pagesize='+pagesizegovalue;
  	});
  });
   $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/attr/search.nsf" />?id=${id}&type=${type}&searchType=${searchType}&searchcontent=${searchcontent}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
 $(function(){
     $("#back").click(function(){
  		 window.location.href='<@spring.url "/attr/attrlist.nsf"/>?id=${id}&type=${type}';
  	});
  });
 function isDel(uuid){
	if(confirm("确定删除?")){
		window.location.href='<@spring.url "/attr/attrdel.nsf"/>?id=${id}&type=${type}&attruuid='+uuid;
	}
 }
 function isUpd(uuid){
	window.location.href='<@spring.url "/attr/attredit.nsf"/>?id=${id}&type=${type}&attruuid='+uuid;
 }
  $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             var pagegovalue=$("#pagegovalue").val();
             var pagesizegovalue=$("#pagesizegovalue").val();
             var strsearch="&page="+pagegovalue+"&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue+"&searchcontent="+searchcontent;
             if(searchcontent.length>0){
               f1.action='<@spring.url "/attr/search.nsf" />?id=${id}&type=${type}';
               $("#f1").submit();
             }else{
               alert("请输入查询内容!");
             }
  	});
  	$("#pagegovalue").attr("style","ime-mode:disabled");
  	$("#pagegovalue").keypress(function(e){
  		return checkKeyPressNum(e);
  	});
  	$("#pagegovalue").bind('paste',function(){
  		return false;
  	});
  	
  	$("#pagesizegovalue").attr("style","ime-mode:disabled");
  	$("#pagesizegovalue").keypress(function(e){
  		return checkKeyPressNum(e);
  	});
  	$("#pagesizegovalue").bind('paste',function(){
  		return false;
  	});
  });

//]]>
</script>

<form name="f1" id="f1" method="post" action="">
	<table  cellspacing="0" width="100%" align="center"  >
		<tr>
			<td width="100%" align="left" valign="top">
				<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
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
						<td width="10%" align="lift"><input type="button" class="button" value="返回属性列表" id="back" ></td>
						<td height="21" width="52%" align="right">
							<input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEvent();" value="<#if searchcontent??>${searchcontent}</#if>" size="10"> 
								<select id="searchType" name="searchType" >
								<#if searchType??>
									<option value="attrname" <#if searchType=="username" >selected</#if>>按属性名称搜索</option>
									<option value="attrid" <#if searchType=="attrid" >selected</#if>>按属性标识搜索</option>
								<#else>
									<option value="attrname" selected>按属性名称搜索</option>
									<option value="attrid">按属性标识搜索</option>
								</#if>
								</select> 
							<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
						</td>
						<td height="21" align="right" width="18%">
							<!--input id="moveuser" type="button" class="button"  value="拒绝访问" -->
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8">
				<table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
					<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
						<td width="40" height="21" align="center" class="">序号</td>
						<td width="90" align="center" class="">属性名称</td>
						<td align="center" class="">属性标识</td>
						<td align="center" class="">是否为同步属性</td>
						<td align="center" class="">操作</td>
					</tr>
					<#if attrlist??>
						<#list attrlist as app>
						    <#if  (app_index+1)%2=0>
							 	<tr height="21" style="background-color:#DAE9F9;" align="center">
						 	<#else>
							 	<tr height="21" style="background-color:#EEEEEE;" align="center">
						 	</#if>
						 		<td >${javapage.getDataStart()+1+app_index}</td>
						 		<td nowrap><a href="#" onclick="isUpd('${app.uuid?js_string}')">${app.name?html}</a></td>
								<td >${app.id}</td>
								<td >
									<#if uumService.existResourceSyncPropName(app.id)>
										同步属性
									<#else>
										非同步属性
									</#if>
								</td>
								<td ><a id="updbutton" href="#" onclick="isUpd('${app.uuid?js_string}')">编辑</a>&nbsp
								<a id="delbutton" href="#" onclick="isDel('${app.uuid?js_string}')">删除</a></td>
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
							<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
							<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" id="pagegovalue"> 
							<input type=button class="button"  value="GO"  id="pagego"></td>
						<td width="41%">
							<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
								<a href="<@spring.url "/attr/search.nsf"/>?id=${id}&type=${type}&page=1&pagesize=${javapage.getPageSize()}&searchType=${searchType}&searchcontent=${searchcontent}">首页</a>
								<#if javapage.isHasPreviousPage()>
								<a href="<@spring.url "/attr/search.nsf"/>?id=${id}&type=${type}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}&searchType=${searchType}&searchcontent=${searchcontent}">上页</a><#else>上页</#if>
								<#if javapage.isHasNextPage()>
								<a href="<@spring.url "/attr/search.nsf"/>?id=${id}&type=${type}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}&searchType=${searchType}&searchcontent=${searchcontent}">下页</a><#else>下页</#if>
								<a href="<@spring.url "/attr/search.nsf"/>?id=${id}&type=${type}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}&searchType=${searchType}&searchcontent=${searchcontent}">尾页</a>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
