<#import "/spring.ftl" as spring />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>编辑应用</title>
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
	function next(str){
		  ac.action=str;
          $("#ac").submit();
	}
  $(function(){
     $("#searchSubmit").click(function(){
             var searchvalue=$("#searchType").val();
             var searchcontent=$("#searchcontent").val();
             if(searchcontent.length>0){
               ac.action='<@spring.url "/app/search/appsearchalluser.nsf" />';
               $("#ac").submit();
               }else{
                alert("请输入查询内容!");
              }
  	});
  });
  $(function(){
     $("#moveuser").click(function(){
       	  var a = document.getElementsByName("moveusers");  
			 if(a!=null){ var valueStr="";
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                     valueStr+=a[i].value+',';   
                }}}
                if(valueStr.length>0){
                if(confirm("真的要移除吗？")){
                	 document.getElementById('userids').value=valueStr;
		             ac.action='<@spring.url "/app/appdeluseradmin.nsf" />';
               		 $("#ac").submit();
                  }
                   }else{
                   alert("未选择用户！");
                 }
  	});
  });
  $(function(){
     $("#moveuser_All").click(function(){
  		 chkall("ac",this);
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
 $(function(){
     $("#pagesizego").click(function(){
         var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/app/appshowadminuser.nsf" />?appuuid=${app.uuid}&pagesize='+pagesizegovalue;
  	});
  });
   $(function(){
     $("#pagego").click(function(){
           var pagegovalue=$("#pagegovalue").val();
           var pagesizegovalue=$("#pagesizegovalue").val();
  		 window.location.href='<@spring.url "/app/appshowadminuser.nsf" />?appuuid=${app.uuid}&page='+pagegovalue+'&pagesize='+pagesizegovalue;
  	});
  });
  function back(){
  	 window.location.href='<@spring.url "/app/applist.nsf"/>';
  }
</script>
</head>
<body style="background:#FFFFFF;">

	<form name="ac" id="ac" action="" method="post">
	<!-- **系统管理员 列表   -->
	<table width="100%" style="background-color:#DAE9F9;font-size:12px;">
	<input type="hidden" name="appuuid" id="appuuid" value="${app.uuid}"/>
	<input type="hidden" name="userids" id="userids" value=""/>
	<input type="hidden" name="cuappname" id="cuappname" value="${cuappname}"/>
		<tr>
			<td height="18" width="30%">&nbsp;【${cuappname}】应用系统管理员列表
			<td height="21" width="52%" align="right">
				<input type="text" name="searchcontent" id="searchcontent" value="" size="10"> 
					<select id="searchType" name="searchType" >
						<option value="username">按中文名模糊搜索</option>
						<option value="userid">按ID模糊搜索</option>
					</select> 
				<input  type="button" class="button"  id="searchSubmit" value="搜  索" >
			</td>
			<td height="21" align="right" width="18%">
				<input id="moveuser" type="button" class="button"  value="移 除" >
			</td>
		</tr>
	</table>
	<table width="100%" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" >
		<tr align="center" style="background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);">
			<td >序号</td>
			<td >用户ID</td>
			<td >姓名</td>
	        <td width="70" valign="middle"><input type="checkbox" value="0" id="moveuser_All"  ></td>
		</tr>
		<#if  userlist ??>
			<#list userlist as user>
				<#if  (user_index+1)%2=0>
				   <tr style="background-color:#DAE9F9;" align="center">
				<#else>
				   <tr style="background-color:#EEEEEE;" align="center">
				</#if>
						<td >${javapage.getDataStart()+1+user_index}</td>
						<td >${user.id}</td>
						<td >${user.name}</td>
		                <td ><input type="checkbox" value="${user.uuid}" id="moveusers" name="moveusers" ></td>
				   </tr>
			</#list>
		</#if>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
			<tr>
				<td width="59%">共${javapage.getQuantityOfData()} 条记录   每页 
						<input size=1 value="${javapage.getPageSize()}" id="pagesizegovalue"  > 条 
						<input type=button class="button"  value="确定" id="pagesizego"> &nbsp;&nbsp;跳页： 
						<input size=1 value="${javapage.getCurrentPage()}" id="pagegovalue"> 
						<input type=button class="button"  value="GO"  id="pagego"></td>
						<td width="41%">
					<div align="right">页次${javapage.getCurrentPage()}/${javapage.lastPage}
						<a href="#" onclick="next('<@spring.url "/app/appshowadminuser.nsf"/>?&page=1&pagesize=3')">首页</a>
						<#if javapage.isHasPreviousPage()>
						<a href="#" onclick="next('<@spring.url "/app/appshowadminuser.nsf"/>?&page=${javapage.getPreviousPage()}&pagesize=3')">上页</a><#else>上页</#if>
						<#if javapage.isHasNextPage()>
						<a href="#" onclick="next('<@spring.url "/app/appshowadminuser.nsf"/>?&page=${javapage.getNextPage()}&pagesize=3')">下页</a><#else>下页</#if>
						<a href="#" onclick="next('<@spring.url "/app/appshowadminuser.nsf"/>?&page=${javapage.getLastPage()}&pagesize=3')">尾页</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>