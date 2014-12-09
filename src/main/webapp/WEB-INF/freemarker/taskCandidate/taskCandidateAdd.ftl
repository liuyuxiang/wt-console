<#import "/spring.ftl" as spring />
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
<body >
<script type="text/javascript">
//<![CDATA[
$(function(){
	$("#addTaskCandidatebutton").click(function(){
		if($("#id").val().length<=0){
			alert("候选项ID不能为空");
			return false;
		}
		if($("#linkName").val().length<=0){
			alert("候选项名称不能为空");
			return false;
		}
		if($("#linkUrl").val().length<=0){
			alert("候选项链接地址不能为空");
			return false;
		}
		if($("#linkOrder").val().length<=0){
			alert("候选项排序号不能为空");
			return false;
		}
		if(confirm("确定添加该待办信息吗？"))	{
			$("#addTaskCandidateform").submit();
		}else{
			return false;
		}
	});
	
	$("#jssearchgroup").click(function(){
		var date=new Date;
		var time=parseInt(date.getSeconds());
		var url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#jsgroupuuid").val()+'&mygroupuuid=no&flag=true&time='+time;
		var returnVal=window.showModalDialog(url ,'','dialogWidth=300px;dialogHeight=300px;status=0;help=0');
		if(returnVal!=null){
			$("#jsgroupuuid").val(returnVal);
			var url="<@spring.url "/publicgroupdata.nsf"/>"
			$.post(url,{groupuuids:returnVal},function(data){
				data=checkAjaxData(data);
				$("#jsgroupAreName").val(data);
			});
		}
	});
	  	 
	$("#cancel").click(function(){
		window.location.href='<@spring.url "/taskCandidate/taskCandidateList.nsf" />';
	});

});

//]]>
</script>
<form action="<@spring.url "/taskCandidate/addTaskCandidatefrom.nsf" />" method="post" name="addTaskCandidateform" id="addTaskCandidateform">
	<table class="tableborder1" id="tt" width="50%" align="center" border="0" cellspacing="2"
		cellpadding="0">
			<TR class='tb1'>
			<TD align="left"  bgcolor="#fffff0" style="width:25%">候选项ID</TD>
			<TD align="left"  bgcolor="#fffff0">
			<input align="left" name="id" maxlength="50" id="id" value=""  style="width:75%" style="ime-mode:disabled;" onkeypress="checkEventID();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\w]/ig,''))" /><font color="red" size="3">*</font>
			</TD>
		</TR>
		
		<TR class='tb1'>
			<TD align="left"  bgcolor="#fffff0" style="width:25%">候选项名称</TD>
			<TD align="left"   bgcolor="#fffff0">
			<input align="left" name="linkName" maxlength="50" id="linkName" value="" onkeypress="checkEventName();" style="width:75%" onkeydown="if(event.keyCode==32) return false"  /><font color="red" size="3">*</font>
			</TD>
		</TR>
		
		<TR class='tb1'>
			<TD align="left"  bgcolor="#fffff0" style="width:25%">候选项链接地址</TD>
			<TD align=left    bgcolor="#fffff0">
			<input align="left" name="linkUrl" maxlength="50" id="linkUrl" value=""  style="width:75%" style="ime-mode:disabled;" onkeypress="checkEventID1();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\w\.\/]/ig,''))" /><font color="red" size="3">*</font>
			</TD>
		</TR>
		
		<TR class='tb1'>
			<TD align="left"  bgcolor="#fffff0" style="width:25%">候选项排序号</TD>
			<TD align="left"    bgcolor="#fffff0">
			<input align="left" name="linkOrder" maxlength="3" id="linkOrder" value="0" style="ime-mode:disabled;" onkeypress="checkEventNUM();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" /><font color="red" size="2">*必须为数字</font>
			</TD>
		</TR>
		
		<TR class='tb1'>
						<TD align="left"  bgcolor="#fffff0" style="width:25%">可查看该候选项的组</TD>
						<TD align=left  bgcolor="#fffff0">
						<textarea name="jsgroupAreName" id="jsgroupAreName" rows="5" style="width:75%"
				         value="" disabled></textarea>
						<input name="jsgroupuuid" id="jsgroupuuid" type="hidden" value="" style="width:70%">
						<input type="Button" id="jssearchgroup" value="选择"  class="button" /></TD>
		          </TR>
		
	</table>

<table id="Table2" width="100%" border="0" cellspacing="1" align="center">
	<TR class='tb1'>
			<TD align=center  bgcolor="#fffff0">
			     <input type="button" class="button" value="提交" id="addTaskCandidatebutton" />
				 <input type="button" id="cancel" class="button" value="取消" />
			</TD>
		</TR>
</table>
 
</form>
</body>
</html>