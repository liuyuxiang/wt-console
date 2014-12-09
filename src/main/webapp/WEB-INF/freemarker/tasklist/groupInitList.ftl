<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

    $(function(){
     $("#filterdept").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要回退吗？")){
		             var url="<@spring.url "/tasklist/rollbackgroup.nsf" />?userId=${user.id}&deptids="+valueStr;
				       $.post(url,function(data){
				           window.location.href='<@spring.url "/tasklist/groupInitList.nsf" />?&userId=${user.id}';
				        });
                  }
                   }else{
                   alert("至少选择一个！");
                 }
  	});
  });
  $(function(){
     $("#authordept").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要通过验证吗？")){
                      var url="";
			           window.location.href='<@spring.url "/tasklist/passgroup.nsf" />?userId=${user.id}&deptids='+valueStr;
			            $.post(url,function(data){
				           window.location.href='<@spring.url "/tasklist/groupInitList.nsf" />?userId=${user.id}';
				        });
                }}else{
                  alert("至少选择一个！");
                }
  	});
  });
    $(function(){
     $("#moveuser_All").click(function(){
  		 chkall("f1",this);
  	});
  });
     $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/tasklist/groupInitList.nsf" />?userId=${user.id}&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/tasklist/groupInitList.nsf" />?userId=${user.id}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
  function chkall(input1,input2)
{
    var objForm = document.forms[input1];
    var objLen = objForm.length;
    for (var iCount = 0; iCount < objLen; iCount++)
    {
        if (input2.checked == true)
        {
            if (objForm.elements[iCount].name == "moveusers")
            {
                objForm.elements[iCount].checked = true;
            }
        }
        else
        {
            if (objForm.elements[iCount].name == "moveusers")
            {
                objForm.elements[iCount].checked = false;
            }
        }
    }
} 
//]]>
</script>
<form name="f1" id="f1" method="post" action="">
	<table  cellspacing="0" width="100%" align="center"  >
		<tr>
			<td width="100%" align="left" valign="top">
				<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
													<td height="21" align="right" width="18%">
														<input id="authordept" type="button"  class="button"  value="通过" > 
														<input id="filterdept" type="button" class="button"  value="退回" >
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
											<td >角色标识</td>
											<td >角色名称</td>
		                                    <td >创建者</td>
											<td >排序号</td>
											<td ><input type="checkbox" value="0" id="moveuser_All"  ></td>
										</tr>
										<#if  deptlist ?? >
										<#list deptlist as dept>
										    <#if  (dept_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
											<td >${javapage.getDataStart()+1+dept_index}</td>
											<td >${dept.code}</td>
											<td >${dept.deptCode}</td>
											<td >${dept.name}</td>
		                                    <td ><#if  dept.name ?? >${dept.name}</#if></td>
											<td ><#if  dept.order ?? >${dept.order}</#if></td>
											<td ><input type="checkbox" value="${dept.deptCode}" id="moveusers" name="moveusers" onclick="subchick(this)"></td>
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
									<input size=1 value="${javapage.getPageSize()}"  onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
									<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
									<input size=1 value="${javapage.getCurrentPage()}"  onkeypress="checkEventNUM();" id="pagegovalue"> 
									<input type=button class="button"  value="GO"  id="pagego"></td>
									<td width="41%">
									<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
								      <a href="<@spring.url "/tasklist/deptInitList.nsf"/>?page=1&pagesize=${javapage.getPageSize()}&userId=${user.id}">首页</a>
									  <#if javapage.isHasPreviousPage()>
									  <a href="<@spring.url "/tasklist/deptInitList.nsf"/>?page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}&userId=${user.id}">上页</a><#else>上页</#if>
									  <#if javapage.isHasNextPage()>
									  <a href="<@spring.url "/tasklist/deptInitList.nsf"/>?page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}&userId=${user.id}">下页</a><#else>下页</#if>
									  <a href="<@spring.url "/tasklist/deptInitList.nsf"/>?page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}&userId=${user.id}">尾页</a>
									 </div>
									</td>
								</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
