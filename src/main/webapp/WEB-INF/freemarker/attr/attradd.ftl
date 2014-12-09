<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
  function back(){
  	 document.location="<@spring.url "/attr/attrlist.nsf"/>?id=${id}&type=${type}";
  }
  function reset1(){
		document.getElementById("ac").reset();
		    var url="<@spring.url "/publicgroupdata.nsf"/>"
		    $.post(url,{groupuuids:$("#adminGroupsuuid").val()},function(data){
				data=checkAjaxData(data);
				if(data.length>0){
		    		$("#adminGroupsName").text(data);
		    	}else{
		    		$("#adminGroupsName").text("无")
		    	}
		    });
	
		    var url="<@spring.url "/publicgroupdata.nsf"/>"
		    $.post(url,{groupuuids:$("#groupsuuid").val()},function(data){
				data=checkAjaxData(data);
				if(data.length>0){
			    	$("#groupsName").text(data);
				}else{
		    		$("#groupsName").text("无")
		    	}
		    });
  	
  }

$(function(){
	$("#confirm").click( function () {
		if($("#name").val()==""){
			alert("请填写[属性名称]");
			return false;
		}
		if($("#order").val()==""){
			alert("请填写[排序号],且必须为数字");
			return false;
		}
//		if($("#adminGroupsuuid").val()==""){
//			alert("请选择[属性被管理的组]");
//			return false;
//		}
//		if($("#groupsuuid").val()==""){
//			alert("请选择[属性类型所属的组]");
//			return false;
//		}
		if($("#attrid").val()==""){
			alert("请填写[属性标识]");
			return false;
		}
		if($("#catagory").val()==""){
			alert("请选择[扩展属性类别]");
			return false;
		}
		if($("#pageType").val()==""){
			alert("请选择[页面展示属性]");
			return false;
		}
		if($("#pageType").val()!="text" && $("#typepage :first-child").children().length==0){
			alert("非文本显示属性请填写[页面展示属性内容]");
			createNode();
			return false;
		}
		var flag = true;
		$(".typetext").each(function(){
			if($.trim($(this).val())==""){
				alert("[选项值]和[选项提示]不能为空！");
				flag = false;
				return false;
			}
		});
		if(!flag){
			return false;
		}
		var url="<@spring.url "/attr/confirmationattr.nsf"/>";
		$.post(url,{code:$("#attrid").val()},function(data){
			data=checkAjaxData(data);
			if(data!="false"){
				$("#confirmationattr").html("属性标识已存在");
				$("#attrid").focus();
				return false;
			}else{
				$("#ac").submit();
			}
		});
	});
});

	$(function(){
		$("#adminGroups").click(function(){
		var date=new Date;
		var time=parseInt(date.getSeconds());
			var    width=300; 
			var    height=300; 
			var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#adminGroupsuuid").val()+'&mygroupuuid=no&flag=true&time='+time;
			var    returnVal;
			returnVal=window.showModalDialog(url ,'','dialogWidth='+width+'px;dialogHeight='+ height+'px;resizable=no;help=no;center=yes;status=no;scroll=yes;edge=sunken');
			if(returnVal!=null){
				$("#adminGroupsuuid").val(returnVal);
				var url="<@spring.url "/publicgroupdata.nsf"/>"
				$.post(url,{groupuuids:returnVal},function(data){
					data=checkAjaxData(data);
					$("#adminGroupsName").text(data);
				});
			}
		});
	});

	$(function(){
		$("#groups").click(function(){
		var date=new Date; 
		var time=parseInt(date.getSeconds()); 
			var    width=300;
			var    height=300;
			var    url='<@spring.url "/publicgrouptree.nsf"/>?type=checkbox&groupuuids='+$("#groupsuuid").val()+'&mygroupuuid=no&flag=true&time='+time;
			var    returnVal;
			returnVal=window.showModalDialog(url ,'','dialogWidth='+width+'px;dialogHeight='+ height+'px;resizable=no;help=no;center=yes;status=no;scroll=yes;edge=sunken');
			if(returnVal!=null){
				$("#groupsuuid").val(returnVal);
				var url="<@spring.url "/publicgroupdata.nsf"/>"
				$.post(url,{groupuuids:returnVal},function(data){
					data=checkAjaxData(data);
					$("#groupsName").text(data);
				});
			}
		 });
	});

	function createNode(){
		var objDiv = $(".typetext");
		if(objDiv.length==0||$(".typetext:last").val()!=""){
			var tr = "<tr><td align='center' bgcolor='#fffff0'>选项值&nbsp;&nbsp;<input name='typetext' class='typetext'>&nbsp;<font color='red'>必填</font>&nbsp;&nbsp;选项提示<input name='typecaption' class='typetext'>&nbsp;<font color='red'>必填</font>&nbsp;<input type='button' class='button' onclick='createNode()' value='添加'>&nbsp;&nbsp;<input type='button' class='button' onclick='this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)' value='删除'></td></tr>";
			$(tr).appendTo($("#typepage"));
		}
	}
	function typechange(object){
		if(object==""||object=="text"){
			$("#typepage").html("<tbody></tbody>");
		}else{
			$("#type").show();
			if(document.getElementById("typepage").childNodes[0].childNodes.length==0){
				createNode();
			}
		}
	}
</script>
	<div style="overflow-y:scroll;height:420px;width:100%;background:#FFFFFF;" >
	<form id="ac" action="<@spring.url "/attr/attraddsuc.nsf" />" method="post">
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" name="type" value="${type}"/>
		<table id="tt" style="background-color:#B7D6F5;border:1px;color:#000000;font-size:12px;" width="100%" align="center" border="0" cellspacing="1" cellpadding="0">
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性对应的资源类型</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0" height="24px">
								<input name="resourceType" id="resourceType" type="hidden" value="${id}"/>
								<span >
													<#switch id >
													  <#case "0" >
													  	用户属性
													  <#break>
													  <#case "1" >
													  	角色属性
													  <#break>
													  <#case "2" >
													       部门属性
													  <#break>
													  <#case "3" >
													    应用系统属性
													  <#break>
													  <#default>
													</#switch>
</span>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性标识</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" maxlength="20" name="attrid" id="attrid" value="" style="ime-mode:disabled;" onkeypress="checkEventID();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\w]/ig,''))"/>
					<font id="confirmationattr" color="red">必填</font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="name" maxlength="15"  onkeypress="checkEventName();" id="name" value=""/>
					<font color="red">必填</font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">排序号</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="order" maxlength="5" id="order" value="0" style="ime-mode:disabled;" onkeypress="checkEventNUM();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
					<font color="red">必须为数字</font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性信息长度</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="length" maxlength="2" id="length" value="" style="ime-mode:disabled;" onkeypress="checkEventNUM();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
					<font color="red">为空时默认100位，该设置仅对页面展示为文本框的属性有效</font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性信息规则</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<select name="rule" id="rule">
						<option value="">《======请选择======》</option>
						<option value="0">基本文本</option>
						<option value="1">数字类型</option>
						<option value="2">日期类型</option>
						<option value="3">身份证类型</option>
						<option value="4">英文类型</option>
						<option value="5">英数字类型</option>
						<option value="6">电子邮箱类型</option>
						<option value="7">中文类型</option>
						<option value="8">电话号码类型</option>
						<option value="9">ERP编码类型</option>
					</select>
					<font color="red">该设置仅对页面展示为文本框的属性有效</font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性是否隐藏</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="radio" name="hidden" id="hidden1" value="hidden"><label for="hidden1">隐藏</label>
					<input type="radio" name="hidden" id="hidden2" value="display" checked><label for="hidden2">非隐藏</label>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">是否为多值属性</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="radio" name="multivalued" id="multivalued1" value = "multiple"><label for="multivalued1">多值</label>
				<input type="radio" name="multivalued" id="multivalued2" value = "sole" checked><label for="multivalued2">单一值</label>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">是否设置为同步属性</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="checkbox" name="sync" id="sync" />
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性被管理的组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<span name="adminGroupsName" id="adminGroupsName" >无</span>
				<input type="text" style="display:none;" name="adminGroupsuuid" id="adminGroupsuuid" >
				<input type="button" class="button" id="adminGroups" value="选择组"/>
				<font color="red"></font></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性类型所属的组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<span name="groupsName" id="groupsName">无</span>
				<input type="text" style="display:none;" name="groupsuuid" id="groupsuuid" >
				<input type="button" class="button" id="groups" value="选择组"/>
				<font color="red"></font></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">扩展属性类别</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<select name="catagory" id="catagory">
						<option value="">《======请选择======》</option>
						<option value="0">基本</option>
						<option value="1">通信</option>
						<option value="2">职务</option>
						<option value="3">应用</option>
					</select>
					<font color="red">必选</font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">页面展示属性</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<select name="pageType" id="pageType" onchange="typechange(this.value);">
						<option value="">《======请选择======》</option>
						<option value="text">文本框</option>
						<option value="radio">单选框</option>
						<option value="select">下拉列表</option>
					</select>
					<font color="red">必选</font>
				</td>
			</tr>
			<tr style="width:100%;">
			<td align="left" colspan="6" bgcolor="#fffff0">
			<div id="type" style="display:none">
				<table id="typepage" style="width:100%;border:1px;color:#000000;font-size:12px;" align="center" border="0" cellspacing="2" cellpadding="0">
				<tbody></tbody>
				</table>
			</div>
			</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0"></td>
				<td align="left" colspan="3"  bgcolor="#fffff0">
					<input type="button" id="confirm" class="button" value="提交"/>&nbsp;&nbsp;
					<input type="button" class="button" value="重置" onclick="reset1()"/>&nbsp;&nbsp;
					<input type="button" class="button" value="返回" onclick="back()" />
				</td>
			</tr>
		</table>
	</form>
</div>