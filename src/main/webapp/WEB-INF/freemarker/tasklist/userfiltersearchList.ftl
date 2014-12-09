<#import "/spring.ftl" as spring />
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script type="text/javascript">
//<![CDATA[

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
			           window.location.href='<@spring.url "/authorfilteruser.nsf" />?userId=${user.id}&id=${group.uuid}&userids='+valueStr;
			            $.post(url,function(data){
							history.back();
				           //window.location.href='<@spring.url "/tasklist/userfiltersearchList.nsf" />?id=${group.uuid}&userId=${user.id}';
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
     $("#back").click(function(){
				           window.location.href='<@spring.url "/tasklist/userfilterList.nsf" />?id=${group.uuid}&userId=${user.id}';
  	});
  });
 $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
          var searchvalue=$("#searchType").val();
             f1.action="<@spring.url "/userfiltersearch.nsf" />?id=${group.uuid}&userId=${user.id}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
             $("#f1").submit();
   
  	});
  });
    $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/userfiltersearch.nsf" />?id=${group.uuid}&userId=${user.id}&page="+pagegovalue+"&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
  $(function(){
     $("#home").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/userfiltersearch.nsf" />?id=${group.uuid}&userId=${user.id}&page=1&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
    $(function(){
     $("#last").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/userfiltersearch.nsf"/>?id=${group.uuid}&userId=${user.id}&page=${javapage.getLastPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
      $(function(){
     $("#previous").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/userfiltersearch.nsf"/>?id=${group.uuid}&userId=${user.id}&page=${javapage.getPreviousPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
       $(function(){
     $("#next").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
            var searchvalue=$("#searchType").val();
            f1.action="<@spring.url "/userfiltersearch.nsf"/>?id=${group.uuid}&userId=${user.id}&page=${javapage.getNextPage()}&pagesize="+pagesizegovalue+"&searchvalue="+searchvalue;
  	        $("#f1").submit();
  	});
  });
    $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               f1.action='<@spring.url "/userfiltersearch.nsf" />?id=${group.uuid}&userId=${user.id}&searchvalue='+searchvalue;
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
													<td height="18" width="10%" style="color:red">&nbsp;应用系统【${group.name}】成员管理</td>
													<td height="18" width="3%">
													   <select name="s" id="s" " onChange="javascript:var jmpURL=this.options[selectedIndex].value; if(jmpURL!='') {popupDiv('pop-div');window.location.href=jmpURL;} else {this.selectedIndex=0;}">>
													      <option value="0">请选择应用系统</option>
					                                 <#if groups ??>
				                                     <#list groups as app>
				                                          <option value="<@spring.url '/tasklist/userfilterList.nsf'/>?id=${app.uuid}&userId=${user.id}">${app.name}</option>
				                                     </#list>
				                                     </#if>
				                                     </select>
													</td>
													<td height="21" width="20%" align="right">
														 <input type="text" name="searchcontent" id="searchcontent" onkeypress="checkEventName();" value="${searchcontent}" size="10"> 
														 <select id="searchType" name="searchType" >
															<option value="username" <#if searchvalue=="username" >selected</#if> >按姓名搜索</option>
															<option value="userid" <#if searchvalue=="userid" >selected</#if>>按ID搜索</option>
														</select> 
														<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
													</td>
													<td height="21" align="right" width="18%">
													    <input id="back" type="button" class="button"  value="返回" >
														<input id="authoruser" type="button"  class="button"  value="预授权" >
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
											<td >用户ID</td>
											<td >姓名</td>
		                                    <td >用户所在部门</td>
											<td >排序号</td>
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
											<td >${javapage.getDataStart()+1+userDept_index}</td>
											<td >${user.id}</td>
											<td >${user.name?html}</a></td>
		                                    <td >${dept.path}</td>
											<td >${user.order}</td>
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
									<input size=1 value="${javapage.getPageSize()}" maxlength="3" onkeypress="checkEventNUM();" id="pagesizegovalue"  > 条 
									<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
									<input size=1 value="${javapage.getCurrentPage()}" maxlength="3" onkeypress="checkEventNUM();" id="pagegovalue"> 
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
</form>
