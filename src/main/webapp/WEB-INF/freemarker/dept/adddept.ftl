<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>统一用户管理</title>
<#include "/script/jquery.ftl" >
<#include "/style/style.ftl" >
</head>
<body >
<script type="text/javascript">
//<![CDATA[
$(function(){

  	$("#code").attr("style","ime-mode:disabled");
  	$("#code").keypress(function(e){
  		return checkKeyPressNum(e);
  	});
  	$("#code").bind('paste',function(){
  		return false;
  	});

  	$("#sucode").attr("style","ime-mode:disabled");
  	$("#sucode").keypress(function(e){
  		return checkKeyPressNum(e);
  	});
  	$("#sucode").bind('paste',function(){
  		return false;
  	});

	$("#Submit").click(function(){
		if($("#name").val().length<=0){
			alert("部门中文显示名不能为空");
			return false;
		}else{
			if($("#orderNum").val().length<=0){
				alert("排序号不能为空");
				return false;
			}else{
			if($("#deptcode").val().length<=0){
				alert("部门ID不能为空");
				return false;
			}else{
				if($("#code").val().length<=0){
					alert("部门ERP编码不能为空");
					return false;
				}else{
					var flag = true;
					var url="<@spring.url "/dept/confirmationdept.nsf"/>?id=${deptChildren.uuid}&me="
					$.post(url,{code:$("#code").val(),name:$("#name").val(),deptcode:$("#deptcode").val()},function(data){
data=checkAjaxData(data);
						$("#confirmationdeptname").html("*");
						$("#confirmationdept").html("*(请使用字母或者数字)");
						$("#confirmationdeptcode").html("*(请使用字母或者数字)");
						if(data.indexOf("name")>-1){
							$("#confirmationdeptname").html("部门中文名已经存在!");
							flag=false;
						}
						if(data.indexOf("code")>-1){
							$("#confirmationdept").html("部门ERP编码已经存在!");
							flag=false;
						}
						if(data.indexOf("deptc")>-1){
							$("#confirmationdeptcode").html("部门ID已经存在!");
							flag=false;
						}
						if(flag){
							$.post("<@spring.url "/dept/createdepartment.nsf"/>",$("#adddeptform").serialize(),function(data){
								if(data=='false'){
									alert("创建失败,请联系管理员!");
									return false;
								}else{
									alert("创建成功!");
									window.parent.location.reload();
								}
								return false;
							});
							return false;
						}else{
							return false;
						}
					});
				}}
			}
		}
	});
});
   $(function(){
     $("#Submitadmin").click(function(){
     		if($("#suname").val().length<=0){
			alert("部门中文显示名不能为空");
			return false;
		}else{
			if($("#suorderNum").val().length<=0){
				alert("排序号不能为空");
				return false;
			}else{
			if($("#sudeptcode").val().length<=0){
				alert("部门编码不能为空");
				return false;
			}else{
				if($("#sucode").val().length<=0){
					alert("部门ID不能为空");
					return false;
				}else{
					var flag = true;
					var url="<@spring.url "/dept/confirmationdept.nsf"/>?id=${uumService.getDepartmentRoot().uuid}&me="
					$.post(url,{code:$("#sucode").val(),name:$("#suname").val(),deptcode:$("#sudeptcode").val()},function(data){
data=checkAjaxData(data);
						$("#suconfirmationdeptname").html("*");
						$("#suconfirmationdept").html("*(请使用字母或者数字)");
						$("#suconfirmationdeptcode").html("*(请使用字母或者数字)");
						if(data.indexOf("name")>-1){
							$("#suconfirmationdeptname").html("部门中文名已经存在!");
							flag=false;
						}
						if(data.indexOf("code")>-1){
							$("#suconfirmationdept").html("部门ID已经存在!");
							flag=false;
						}
						if(data.indexOf("deptc")>-1){
							$("#suconfirmationdeptcode").html("部门编码已经存在!");
							flag=false;
						}
						if(flag){
							$.post("<@spring.url "/dept/createdepartment.nsf"/>",$("#adddeptformadmin").serialize(),function(data){
								if(data=='false'){
									alert("创建失败,请联系管理员!");
									return false;
								}else{
									alert("创建成功!");
									window.parent.location.reload();
								}
								return false;
							});
							return false;
						}else{
							return false;
						}
					});
				}
			}}
		}
	 }); 
  });
   $(function(){
     $("#jssearchgroup").click(function(){
        var date=new Date;
        var time=parseInt(date.getSeconds());
        var    url= '<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#jsgroupuuid").val()+'&mygroupuuid=no&flag=${superstatus?string}&time='+time;    
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
   $(function(){
     $("#sujssearchgroup").click(function(){
        var date=new Date;
        var time=parseInt(date.getSeconds());
        var    url= '<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#sujsgroupuuid").val()+'&mygroupuuid=no&flag=${superstatus?string}&time='+time;    
        var    returnVal;    
        returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');    
  	     if(returnVal!=null){
  	       $("#sujsgroupuuid").val(returnVal);
	  	   var url="<@spring.url "/publicgroupdata.nsf"/>"
	       $.post(url,{groupuuids:returnVal},
	       function(data){
data=checkAjaxData(data);
	        $("#sujsgroupAreName").text(data);
	        fitheight(window);
	       });
       }
  	 });
  });
//]]>
</script>
<table><tr><td align="left">
<form action="<@spring.url "/adddeptfrom.nsf" />" method="post"  id="adddeptform">
<table width="600">
		<tr>
           <td align="left">
			<table width="100%" height="25" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="250" height="25" valign="middle" background="<@spring.url "/style/default/images/back2.gif" />">
					<table width="251" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="12%">&nbsp;</td>
							<td width="88%"  ><b>【${deptChildren.name}】</b></td>
						</tr>
					</table>
					</td>
					<td height="21" align="right">&nbsp;</td>
				</tr>
			</table>
			</td>
			</tr>
		<tr>
			<td>
			<table cellspacing="1" cellpadding="0" width="100%" border="0">
				<tr>
					<td width="45%" align="right">部门中文显示名：</td>
					<td align="left">
					<input name="name" id="name" onkeypress="checkEventName();" onkeyup='this.value=this.value.replace(/\s+/g,"");' value="" maxlength="30" /></td><td  width="50%" align="left"><font id="confirmationdeptname" color="#FF0000" >*</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">排序号：</td>
					<td align="left">
					<input name="orderNum" id="orderNum" value="0" maxlength="5" id="orderNum"  style="ime-mode:disabled" onkeypress="checkEventNUM();" onpaste="return !clipboardData.getData('text').match(/\D/)"/></td>
				</tr>
				<tr>
					<td align="right" width="45%">部门ID：</td>
					<td align="left">
					<input name="code" id="code" value="" onkeypress="checkEventID();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="30"/></td><td width="50%" align="left"><font id="confirmationdept" color="#FF0000">*(请使用字母或者数字)</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">部门编码：</td>
					<td align="left">
					<input name="deptcode" id="deptcode" <#if sgccOrgId ??>value="${sgccOrgId}" readOnly onkeydown="if(event.keyCode == 8) return false;"<#else> onkeypress="checkEventID();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="15"</#if>/></td><td width="50%" align="left"><font id="confirmationdeptcode" color="#FF0000">*</font></td>
				</tr>
					<TR>
						<TD align="right"  >可管理该部门的组：</TD>
						<TD >
						<span name="jsgroupAreName" id="jsgroupAreName" >无</span>
						</td><td align=left  colspan="2">
						<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="" style="width:70%">
						<input type="Button" id="jssearchgroup" value="选择"  class="button" /></TD>
		          </TR>
                 <td align="right" width="45%">&nbsp;</td>
					<td align="left"><input type="button" id="Submit" value="确定"  class="button" /> 
					 <input  type="reset"   value="重置" class="button" /> 
					 <input  type="button"   value="返回" onclick="history.back()" class="button" />
					 </td>
				</tr>
				<tr><td><input name="id" type="hidden" id="id" value="${deptChildren.uuid}" /></td></tr>
			</table>
			</td>
		</tr>
	</table>
</form>
</td></tr>
<tr><td>
<form action="<@spring.url "/adddeptfrom.nsf" />" method="post"  id="adddeptformadmin">
<#if  uumService.isUserinSuperGroup(uumService.getLoginUser()) >
		<table cellspacing="1" cellpadding="0" width="600" border="0">
				<tr>
					<td colspan="3">建立一级栏目</td>
				</tr>
				<tr>
					<td colspan="3"><hr></td>
				</tr>
				
				<tr>
					<td align="right" width="45%">父部门名称：</td>
					<td align="left"> ${uumService.getDepartmentRoot().name} </td>
				</tr>
				<tr>
					<td width="45%" align="right">部门中文显示名：</td>
					<td align="left"><input name="name" id="suname" onkeyup='this.value=this.value.replace(/\s+/g,"");' onkeypress="checkEventName();" value="" maxlength="30" /></td><td  width="50%" align="left"><font id="suconfirmationdeptname" color="#FF0000" >*</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">排序号：</td>
					<td align="left">
					<input name="orderNum" id="suorderNum" value="0" maxlength="5" id="orderNum" onkeypress="checkEventNUM();"/></td>
				</tr>
				<tr>
					<td align="right" width="45%">部门ID：</td>
					<td align="left">
					<input name="code" id="sucode" value="" onkeypress="checkEventNUM();" onpaste="return !clipboardData.getData('text').match(/\D/)" maxlength="10"/></td><td width="50%" align="left"><font id="suconfirmationdept" color="#FF0000">*(请使用数字)</font></td>
				</tr>
				<tr>
					<td align="right" width="45%">部门编码：</td>
					<td align="left">
					<input name="deptcode" id="sudeptcode"  <#if ROOTsgccOrgId ??>value="${ROOTsgccOrgId}" readOnly onkeydown="if(event.keyCode == 8) return false;"<#else> onkeypress="checkEventID();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="15"</#if>/></td><td width="50%" align="left"><font id="suconfirmationdeptcode" color="#FF0000">*</font></td>
				</tr>
					<TR>
						<TD align="right"  >可管理该部门的组：</TD>
						<TD align="left">
						<span name="jsgroupAreName" id="sujsgroupAreName" >无</span>
						</td><td align=left  colspan="2">
						<input name="jsgroupuuid" id="sujsgroupuuid" type="hidden" value="" style="width:70%">
						<input type="Button" id="sujssearchgroup" value="选择"  class="button" /></TD>
		          </TR>
                 <td align="right" width="45%">&nbsp;</td>
					<td align="left"><input type="button" id="Submitadmin" value="确定"  class="button" /> 
					<input  type="reset"   value="重置" class="button" /> 
					 </td>
				</tr>
				<tr><td><input name="id" type="hidden" id="id" value="${uumService.getDepartmentRoot().uuid}" /></td></tr>
			</table>
 </#if>
 </form>
 </td></tr></table>
</body>
</html>