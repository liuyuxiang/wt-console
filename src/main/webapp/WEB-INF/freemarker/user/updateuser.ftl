<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
<script language="javascript"  type="text/javascript" src="<@spring.url "/js/py.js" />"></script>

</head>
<body>
<script type="text/javascript">
//<![CDATA[
$(function(){
	$("#updateuserbutton").click(function(){
		if($("#name").val().length<=0){
			alert("姓名不能为空");
			return false;
		}
		if($("#orderNum").val().length<=0){
			alert("排序号不能为空");
			return false;
		}
		if($("#deptuuid").val().length<=0){
			alert("部门不能为空");
			return false;
		}
		if($("#employeenumber").val()!=null&&$("#employeenumber").val().length==0){
			if(!confirm("确定身份证为空吗？")){
				$("#employeenumber").focus();
				return false;
			}
		}
		if($("#deptuuid").val().length<=0){
			$("#deptuuid").val($("#maindeptuuid").val()+",");
		}
		
		if($("#primaryUserId").val().length>0){
			var url1="<@spring.url "/user/confirmationuser.nsf"/>?uuid="
			$.post(url1,{userid:$("#primaryUserId").val()},function(data){
data=checkAjaxData(data);
				if(data=="false"){
					$("#confirmationRelationUser").html("关联用户ID不存在!");
					$("#primaryUserId").focus();
					return false;
				}else{
					var url="<@spring.url "/user/confirmationuser.nsf"/>?uuid=${user.uuid}"
					$.post(url,{userid:$("#userid").val()},function(data){
data=checkAjaxData(data);
						if(data=="false"){
							$("#updateuserform").submit();
						}else{
							$("#confirmationuser").html("用户ID已经存在!");
							$("#userid").focus();
							return false;
						}
					});
				}
			});
		}else{
			var url="<@spring.url "/user/confirmationuser.nsf"/>?uuid=${user.uuid}"
			$.post(url,{userid:$("#userid").val()},function(data){
data=checkAjaxData(data);
				if(data=="false"){
					$("#updateuserform").submit();
				}else{
					$("#confirmationuser").html("用户ID已经存在!");
					$("#userid").focus();
					return false;
				}
			});
		}
	});
});
   $(function(){
     $("#pid").click(function(){
		var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publictree.nsf"/>?type=checkbox&deptuuids='+$("#deptuuid").val()+'&mydeptuuid=no&flag=true&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#deptuuid").val(returnVal);
	  	   var url="<@spring.url "/publicdata.nsf"/>"
	       $.post(url,{deptuuid:returnVal},
	       function(data){
data=checkAjaxData(data);
	         $("#deptName").val(data);
	       });
       }
  	 });
  });
     $(function(){
     $("#uid").click(function(){
		var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicusertree.nsf"/>?type=checkbox&deptuuids=no&useruuids='+$("#useruuids").val()+'&mydeptuuid=no&flag=true&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
       
  	     if(returnVal!=null){
  	       $("#useruuids").val(returnVal);
	  	   var url="<@spring.url "/publicuserdata.nsf"/>"
	       $.post(url,{useruuid:returnVal},
	       function(data){
data=checkAjaxData(data);
	         $("#userNames").val(data);
	       });
       }
  	 });
  });
  $(function(){
     $("#pidS").click(function(){
		var date=new Date;
		var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publictree.nsf"/>?type=radio&deptuuids='+$("#deptuuid").val()+'&mydeptuuid=no&flag=&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#deptuuid").val(returnVal);
	  	   var url="<@spring.url "/publicdata.nsf"/>?updateuseruuid=${user.id}"
	       $.post(url,{deptuuid:returnVal},
	       function(data){
data=checkAjaxData(data);
	         $("#deptName").text(data);
	       });
       }
  	 });
  });
  
  function bk(){
      window.location.href='<@spring.url "/gocontent.nsf" />?id=${id}';
  }
  
       $(function(){
           	if($("#employeenumber")!=null){
    		$("#employeenumber").next().html("*");
    	}
     $("#userlog").click(function(){
     	$("#Table22").show();
     	document.getElementById('underframe').height="1px";
    	 document.getElementById('Table23').action="<@spring.url "/audit/resourceLog.nsf"/>";
    	 $("#Table23").submit();
    	 });
  });
       $(function(){
     $("#userlogclose").click(function(){
     		$("#Table22").hide();
    	 });
  });
  
  
  function isMaxLen(o){  
 var nMaxLen=o.getAttribute? parseInt(o.getAttribute("maxlength")):"";  
 if(o.getAttribute && o.value.length>nMaxLen){  
 o.value=o.value.substring(0,nMaxLen)  
 }  
}
  $(function(){
     $("#mainpid").click(function(){
  	    
        var    url='<@spring.url "/publictree.nsf"/>?type=radio&deptuuids='+$("#maindeptuuid").val();    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	     $("#maindeptuuid").val(returnVal.substring(0,returnVal.length-1));
  	      $("#deptuuid").val(returnVal);
	  	   var url="<@spring.url "/publicdata.nsf"/>"
	       $.post(url,{deptuuid:returnVal},
	       function(data){
	data=checkAjaxData(data);
	         $("#maindeptName").val(data.replace(/,/g, ""));
	       });
       }
  	 });
  });
$(function(){
    $("#searchgroup").click(function(){
        var date=new Date;
        var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&checkedPa=true&mygroupuuid=no&flag=${superstatus?string}&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,$("#groupuuid").val(),'dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
        if(returnVal!=null){
            var url="<@spring.url "/publicgroupdata.nsf"/>"
            $.post(url,{groupuuids:returnVal},
            function(data){
                data=checkAjaxData(data);
                $("#groupuuid").val(returnVal);
                $("#groupAreName").text(data);
                fitheight(window);
            });
        }
    });
});
$(function(){
    $("#jssearchgroup").click(function(){
        var date=new Date;
        var time=parseInt(date.getSeconds());
        var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#jsgroupuuid").val()+'&checkedPa=&mygroupuuid=no&flag=${superstatus?string}&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
        if(returnVal!=null){
            $("#jsgroupuuid").val(returnVal);
            var url="<@spring.url "/publicgroupdata.nsf"/>"
            $.post(url,{groupuuids:returnVal},
            function(data){
                data=checkAjaxData(data);
                $("#jsgroupAreName").text(data);
                fitheight(window);
            });
        }
    });
});
function changeimg(id){
		var b = 1;
		for(var a=0;a<4;a++){
		alert(b++);
			document.getElementById(b++).src="<@spring.url "/style/default/images/tag4.gif" />";
			var d = b++;
			document.getElementById(d).style.backgroundImage="url(<@spring.url "/style/default/images/tag5.gif" />)";
			document.getElementById('s'+d).style.display="none";
			document.getElementById(b++).src="<@spring.url "/style/default/images/tag6.gif" />";
		}
		b=1;
		document.getElementById(--id).src="<@spring.url "/style/default/images/tag11.gif" />";
		var c = ++id;
		document.getElementById(c).style.backgroundImage="url(<@spring.url "/style/default/images/tag21.gif" />)";
		document.getElementById('s'+c).style.display="";
		document.getElementById(++id).src="<@spring.url "/style/default/images/tag31.gif" />";
		var type = (c-2)/3;
			var url="<@spring.url "/user/userData.nsf"/>"
			if(document.getElementById('s'+c).innerHTML.length<5){
				popupDiv('pop-div');
	       		$.post(url,{userId:$("#userid").val(),type:type},function(data){
					data=checkAjaxData(data);
	       			document.getElementById('s'+c).innerHTML=data;
	       			hideDiv('pop-div');
	       			fitheight(window);
	       		});
			}
		fitheight(window);
	}
  $(function(){
     $("#userBirthday").click(function(){
  		  WdatePicker();
  	});
  });
   $(function(){
     $("#newpassword").click(function(){
  		 window.location.href='<@spring.url "/user/editUserPwd.nsf" />?userid=${user.uuid}&id=${id}';
  	});
  });
//]]>
</script>
<form action="<@spring.url "/updateuserform.nsf" />" id="updateuserform" method="post" >
<!-- 标签开始 -->
<table width="70%" align="center" border="0" cellSpacing="0" cellPadding="0">
      <tr>
        <td width="1%"  align="right"><img id="1" src="<@spring.url "/style/default/images/tag11.gif" /> "/></td>
        <td width="18%" id="2" align="center" style="cursor:hand" background="<@spring.url "/style/default/images/tag21.gif"/>" onclick="changeimg('2')"><strong>用户基本信息 </strong></td>
        <td width="1%" align="left"><img  id="3" src="<@spring.url "/style/default/images/tag31.gif"/>"/></td>

        <td width="1%"  align="right"><img id="4" src="<@spring.url "/style/default/images/tag4.gif" />"/></td>
        <td width="18%" id="5" align="center" style="cursor:hand" background="<@spring.url "/style/default/images/tag5.gif" />" onclick="changeimg('5')"><strong>用户通讯信息</strong></td>
        <td width="1%"  align="left"><img id="6" src="<@spring.url "/style/default/images/tag6.gif" />" /></td>

        <td width="1%"  align="right"><img id="7" src="<@spring.url "/style/default/images/tag4.gif" />"/></td>
        <td width="18%" id="8" align="center" style="cursor:hand" background="<@spring.url "/style/default/images/tag5.gif" />" onclick="changeimg('8')"><strong>用户职务信息</strong></td>
        <td width="1%"  align="left"><img id="9" src="<@spring.url "/style/default/images/tag6.gif" />" /></td>
        
        <#if macro??>
        <td width="1%" <#if !bo>style="display:none;"</#if> align="right"><img id="10" src="<@spring.url "/style/default/images/tag4.gif" />" /></td>
        <td width="18%" <#if !bo>style="display:none;"</#if> id="11" align="center" style="cursor:hand" background="<@spring.url "/style/default/images/tag5.gif" />" onclick="changeimg('11')" ><strong>应用系统授权信息</strong></td>
        <td width="1%" <#if !bo>style="display:none;"</#if> align="left"><img id="12" src="<@spring.url "/style/default/images/tag6.gif" />" /></td>
        </#if>
       </tr>
</table>
<!-- 标签结束   -->
        <div id='pop-div' style="width: 300px;" class="pop-box">  
            <h4>标题位置</h4>  
            <div class="pop-box-body" >  
                <p>  
                    数据加载中，请稍等…………
                </p>  
            </div>  
        </div>

<!-- 用户基本信息 -->
<div id="s2" style="display:">
		<table class="tableborder1" id="tt" width="100%" align="center" border="0" cellspacing="2"
		cellpadding="0">
			<TR >
			<TD align="right"  bgcolor="#fffff0">姓名</TD>
			<TD align=left colspan="3"  bgcolor="#fffff0"><input name="name" maxlength="15" id="name" value="${user.name}" style="width:70%" onkeypress="checkEventName();" <#if !bo>readonly</#if> /><font color="red" size="3">*</font></TD>
		</TR>
		<tr>
		<TD align="right"   bgcolor="#fffff0">用户标识</TD>
		<TD  align=left colspan="3"  bgcolor="#fffff0">
		<input id="userid"  name="userid" maxlength="15" value="${user.id}" readonly style="width:70%" onkeydown="if(event.keyCode==8) return false"/>
		</TD>
		</tr>
		<tr style="display:none">
		<TD align="right" bgcolor="#fffff0">关联用户标识</TD>
		<TD align=left colspan="3" bgcolor="#fffff0"><input id="primaryUserId" style="width:70%" name="primaryUserId" maxlength="15" value="<#if user.getPrimaryUser()??>${user.getPrimaryUser().getId()}</#if>"  onKeypress="checkEventID();" onkeydown="if(event.keyCode==32) return false"/><font color="red" id="confirmationRelationUser" size="3"></font></td>
	 
		</tr>
		<#if isMultiDept>
		<TR >
			<TD  align=right width="10%"  bgcolor="#fffff0">所在部门</TD>
			<TD  align=left colspan="3"  bgcolor="#fffff0">
			<input name="deptName" id="deptName" style="width:70%" type="text" value="<#list deptlist as dept><#compress>${dept.name},</#compress></#list>" disabled/>
			<input name="deptuuid" id="deptuuid" type="hidden" value="<#if  deptlist ?? ><#list deptlist as dept><#compress>${dept.uuid},</#compress></#list><#else>${user.getPrimaryDepartment().getUuid()},</#if>" >
			<#if bo><input type="Button" id="pid" value="选择"  class="button" /><font color="red" size="3">*</font></#if>
			</TD>
		</TR>
		<#else>
		   <TR >
			<TD  align=right width="10%"  bgcolor="#fffff0">所在部门</TD>
			<TD  align=left colspan="3"  bgcolor="#fffff0">
			<input name="deptName" id="deptName" style="width:70%" type="text" value="<#list deptlist as dept><#compress>${dept.name},</#compress></#list>" disabled/>
			<input name="deptuuid" id="deptuuid" type="hidden" value="<#if  deptlist ?? ><#list deptlist as dept><#compress>${dept.uuid},</#compress></#list><#else>${user.getPrimaryDepartment().getUuid()},</#if>" >
			<#if bo><input type="Button" id="pidS" value="选择"  class="button" /><font color="red" size="3">*</font></#if>
			</TD>
		</TR>
		</#if>
		<TR>
			<TD align="right"  bgcolor="#fffff0">用户权限组</TD>
			<TD align=left  colspan="3"  bgcolor="#fffff0">
			<input id="groupAreName" style="width:70%" name="groupAreName" value="<#if  grouplist ?? ><#list grouplist as group><#compress>${group.name},</#compress></#list></#if>" disabled/>
			<input name="groupuuid" id="groupuuid"  type="hidden" value="<#if  grouplist ?? ><#list grouplist as group><#compress>${group.uuid},</#compress></#list></#if>">
			<#if bo><input type="Button" id="searchgroup" value="选择"   class="button" /><font color="red" size="3">*</font></#if>
			</TD>
		</TR>
		<TR>
			<TD align="right" bgcolor="#fffff0">可管理该用户的组</TD>
			<TD align=left  colspan="3" bgcolor="#fffff0">
			<input id="jsgroupAreName"  name="jsgroupAreName" value="<#if  usergroup ?? ><#list usergroup as ug><#compress>${ug.name},</#compress></#list></#if>" disabled style="width:70%"/>
			<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="<#if  usergroup ?? ><#list usergroup as ug><#compress>${ug.uuid},</#compress></#list></#if>" style="width:70%">
			<#if bo><input type="Button" <#if isDefaultAdminUser?? && isDefaultAdminUser='false'><#else>style="display:none;"</#if></#if> id="jssearchgroup" value="选择"  class="button" /><font color="red" size="3">*</font></#if>
			</TD>
		</TR>
		<TR class='tb1'>
			<TD align="right"  bgcolor="#fffff0" >排序号</TD>
			<TD align="left"   bgcolor="#fffff0" colspan="3" >
			 <input name="orderNum" id="orderNum" value="${user.order}" maxlength="5" id="orderNum" onkeypress="checkEventNUM();"/>
			
			*排序号对于所有的组织机构都有效
			</TD>
		</TR>
		<#list attributelist0 as attribute0>
		<#assign attributetype=attribute0.type>
		<TR class='tb1'>
			<TD align="right" bgcolor="#fffff0" >${attributetype.name}</TD>
			<TD align=left   colspan="3" bgcolor="#fffff0">
				<#switch attributetype.pageType >
				<#case "select" >
					<select name="${attributetype.id}" id="${attributetype.id}" style="width=50%" >
						<#if attribute0.value?exists>
							<option value="${attribute0.value?if_exists}" >${attribute0.value?if_exists}</option>
							<#if attributetype.candidateItems ??>
								<#list attributetype.candidateItems as a0>
									<#if a0.value != attribute0.value >
										<option value="${a0.value}" >${a0.value}</option>
									</#if> 
								</#list>
							</#if>
						<#else>  
							<#list attributetype.candidateItems as a0>
								<option value="${a0.value}" >${a0.value}</option>
							</#list>
						</#if> 
					</select>
					<#break>
				<#case "radio" >
					<#if attribute0.value?exists>
						<input type="radio" id="${attribute0.value}" value="${attribute0.value}" checked name="${attributetype.id}"/><label for="${attribute0.value}">${attribute0.value}</label>
						<#if attributetype.candidateItems ??>
							<#list attributetype.candidateItems as a0>
								<#if attribute0.value!=a0.value>
									<input type="radio" id="${a0.value}" value="${a0.value}" name="${attributetype.id}"/><label for="${a0.value}">${a0.value}</label>
								</#if>
							</#list>
						</#if>
					<#else>
						<#if attributetype.candidateItems ??>
							<#list attributetype.candidateItems as a0>
								<input type="radio" id="${a0.value}" value="${a0.value}" name="${attributetype.id}"/><label for="${a0.value}">${a0.value}</label>
							</#list>
						</#if>
					</#if>
					<#break>
				<#case "textarea" >
					<textarea name="${attributetype.id}" id="${attributetype.id}" onkeypress="checkEvent();" rows="4" style="width:70%" maxlength="100" onkeyup="return isMaxLen(this)">${attribute0.value?if_exists}</textarea>&nbsp;&nbsp;长度不能超过100个字符
					<#break>
				<#default>
				<#if attributetype.id!="dataCameFrom">
					<input id="${attributetype.id}" name="${attributetype.id}" <#if attributetype.rule?exists && attributetype.rule!="0"> onblur="checkType(this);"</#if> typename="${attributetype.name}" attvalue="${attributetype.value?if_exists}" onkeypress="checkEvent();" style="width:70%" value="${attribute0.value?if_exists}" maxlength="<#if attributetype.length?exists>${attributetype.length}<#else>100</#if>" <#if !bo>readonly</#if> />
					<font color="red" size="3"></font>
				<#else>
					<input id="${attributetype.id}" style="display:none" name="${attributetype.id}" value="${attribute0.value?if_exists}"/>
					<input value="<#if attribute0.value??><#if attribute0.value='1'>新增用户<#else>ERP用户</#if></#if>" style="width:70%" maxlength="50" readonly/>
				</#if>
				</#switch>
			</TD>
		</TR>
	 </#list>
	</table>
</div>
<!-- 用户基本信息结束 -->


<!-- 用户通讯信息 -->
<div id="s5" style="display: none">
</div>
<!-- 用户通讯信息结束 -->


<!-- 用户职务信息 -->
<div id="s8" style="display: none">
</div>


<!-- 用户应用信息 -->
<div id="s11" style="display:none;">
</div>
<p><p>
<table id="Table2" class="tableborder1" width="100%" border="0"
    cellspacing="1" align="center">
	<table id="table" align="center">
	   <TR >
			<TD align=center colspan=4  bgcolor="#fffff0" >
		     <input name="useruuid" id="useruuid" type="hidden" value="${user.uuid}">
			<#if bo> <input type="button"  value="修改" id="updateuserbutton"  class="button" /> 
			 <input type="button" id="newpassword"  value="重置密码"  class="button" /></#if>
			  <input type="button"  value="返回" id="back" onclick="bk()" class="button" />
		    <#if bo> <input type="button"  value="查看记录" id="userlog" class="button" />
		     <input type="button"  value="关闭记录" id="userlogclose" class="button" /></#if>
			</TD>
		</TR>
</table>
<table style="display:none" cellspacing="1" border="0" align="center" width="100%" class="tableborder1" id="Table22">
	<tbody><tr><td>
	<iframe name="underframe" id="underframe" src="" frameborder="0" width="100%" scrolling=no></iframe>
	</td></tr></tbody>
</table>

</table>
 
</form>
<form id="Table23" target="underframe" method="post" >
<input type="hidden" name="uuid" value="${user.uuid}">
<input type="hidden" name="page" value="1">
<input type="hidden" name="pagesize" value="3">
</form>
</body>
</html>