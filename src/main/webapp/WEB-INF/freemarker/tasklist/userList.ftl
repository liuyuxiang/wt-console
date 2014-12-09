<#import "/spring.ftl" as spring />
<#import "/user/userListMacro.ftl" as user1 />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

    $(function(){
     $("#filteruser").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要过滤吗？")){
                popupDiv('pop-div');
		             var url="<@spring.url "/filteruser.nsf" />?userId=${user.id}&id=${group.uuid}&userids="+valueStr;
				       $.post(url,function(data){
				           window.location.href='<@spring.url "/tasklist/userList.nsf" />?id=${group.uuid}';
				        });
                  }
                   }else{
                   alert("至少选择一个！");
                 }
  	});
  });
  $(function(){
     $("#authoruser").click(function(){
       	  var   a   =   document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要预授权吗？")){
                popupDiv('pop-div');
                   var url="";
			           window.location.href='<@spring.url "/authoruser.nsf" />?userId=${user.id}&id=${group.uuid}&userids='+valueStr;
			            $.post(url,function(data){
				           window.location.href='<@spring.url "/tasklist/userList.nsf" />?id=${group.uuid}';
				        });
                }}else{
                  alert("至少选择一个！");
                }
  	});
  });
      $(function(){
     $("#allauthoruser").click(function(){
  		 if(confirm("真的要全预授权吗？")){
  		 popupDiv('pop-div');
  		     var url="";
			           window.location.href='<@spring.url "/allauthoruser.nsf" />?userId=${user.id}&id=${group.uuid}';
			            $.post(url,function(data){
				           window.location.href='<@spring.url "/tasklist/userList.nsf" />?id=${group.uuid}';
				        });
                }
  	});
  });
  $(function(){
     $("#allfilteruser").click(function(){
  		 if(confirm("真的要全过滤吗？")){
  		 popupDiv('pop-div');
  		    var url="";
			           window.location.href='<@spring.url "/allfilteruser.nsf" />?userId=${user.id}&id=${group.uuid}';
			            $.post(url,function(data){
				           window.location.href='<@spring.url "/tasklist/userList.nsf" />?id=${group.uuid}';
				        });
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
  		 window.location.href='<@spring.url "/tasklist/userList.nsf" />?id=${group.uuid}&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/tasklist/userList.nsf" />?id=${group.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
    $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               f1.action='<@spring.url "/usersearch.nsf" />?id=${group.uuid}&userId=${user.id}&searchvalue='+searchvalue;
               $("#f1").submit();
               }else{
                alert("请输入查询内容!");
              }
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
    function hideDiv(div_id) {   
        $("#mask").remove();   
        $("#" + div_id).animate({left: 0, top: 0, opacity: "hide" }, "slow");   
    }  

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

<form name="f1" id="f1" method="post" action="">
	<table  cellspacing="0" width="100%" align="center"  >
		<tr>
			<td width="100%" align="left" valign="top">
				<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
												<tr>
													<td height="18" width="10%" style="color:red">应用系统【${group.name}】成员管理</td>
													<td height="18" width="3%">
													   <select name="s" id="s" " onChange="javascript:var jmpURL=this.options[selectedIndex].value; if(jmpURL!='') {popupDiv('pop-div');window.location.href=jmpURL;} else {this.selectedIndex=0;}">>
													      <option value="0">请选择应用系统</option>
					                                 <#if groups ??>
				                                     <#list groups as app>
				                                          <option value="<@spring.url '/tasklist/userList.nsf'/>?id=${app.uuid}&userId=${user.id}">${app.name}</option>
				                                     </#list>
				                                     </#if>
				                                     </select>
													</td>
													<td height="21" width="20%" align="right">
														 <input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEventName();" value="" size="10"> 
														 <select id="searchType" name="searchType" >
															<option value="username">按姓名搜索</option>
															<option value="userid">按ID搜索</option>
														</select> 
														<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
													</td>
													<td height="21" align="right" width="18%">
													    <input id="allauthoruser" type="button"  class="button"  value="全预授权" > 
														<input id="allfilteruser" type="button" class="button"  value="全过滤" >
														<input id="authoruser" type="button"  class="button"  value="预授权" > 
														<input id="filteruser" type="button" class="button"  value="过滤" >
													</td>
												</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8">
				<table width="100%"  id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" >
										<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
											<#if macro ??>
											 <@user1.headers/>
											<#else>
											<td >序号</td>
											<td >用户ID</td>
											<td >姓名</td>
		                                    <td >用户所在部门</td>
											<td >排序号</td>
											</#if>
		                                <td ><input type="checkbox" value="0" id="moveuser_All"  ></td>
										</tr>
										<#if  userlist ?? >
										<#list userlist as userDept>
											<#assign user=userDept[0] dept=userDept[1]>
										    <#if  (userDept_index+1)%2=0>
										 	<tr style="background-color:#DAE9F9;" align="center">
										 	<#else>
										 	<tr style="background-color:#EEEEEE;" align="center">
										 	</#if>
										 	<#if macro ??>
										 	<@user1.cols user.uuid/>
										    <#else>
											<td >${javapage.getDataStart()+1+userDept_index}</td>
											<td >${user.id}</td>
											<td >${user.name?html}</a></td>
		                                    <td >${dept.path}</td>
											<td >${user.order}</td>
											</#if>
		                                    <td ><input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" ></td>
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
									<input size=1 value="${javapage.getPageSize()}" id="pagesizegovalue" onkeypress="checkEventNUM();" /> 条 
									<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
									<input size=1 value="${javapage.getCurrentPage()}" id="pagegovalue" onkeypress="checkEventNUM();"> 
									<input type=button class="button"  value="GO"  id="pagego"></td>
									<td width="41%">
									<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
								      <a href="<@spring.url "/tasklist/userList.nsf"/>?id=${group.uuid}&page=1&pagesize=${javapage.getPageSize()}">首页</a>
									  <#if javapage.isHasPreviousPage()>
									  <a href="<@spring.url "/tasklist/userList.nsf"/>?id=${group.uuid}&page=${javapage.getPreviousPage()}&pagesize=${javapage.getPageSize()}">上页</a><#else>上页</#if>
									  <#if javapage.isHasNextPage()>
									  <a href="<@spring.url "/tasklist/userList.nsf"/>?id=${group.uuid}&page=${javapage.getNextPage()}&pagesize=${javapage.getPageSize()}">下页</a><#else>下页</#if>
									  <a href="<@spring.url "/tasklist/userList.nsf"/>?id=${group.uuid}&page=${javapage.getLastPage()}&pagesize=${javapage.getPageSize()}">尾页</a>
									 </div>
									</td>
								</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
