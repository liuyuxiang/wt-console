<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
<script type="text/javascript">
  function back(){
  	 document.location="<@spring.url "/attr/attrlist.nsf"/>?id=${id}&type=${type}";
  }
  function delect(){
  	if(confirm("确定删除?")){
  	  window.location.href='<@spring.url "/attr/attrdel.nsf"/>?id=${id}&type=${type}&attruuid=${app.uuid}';
  	}
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
  
  
function check(){
	var attrname = document.getElementById("name").value;
	var attrorder = document.getElementById("order").value;
	var adminGroupuuid = document.getElementById("adminGroupsuuid").value;
	var resourceType = document.getElementById("resourceType").value;
	var groupuuid = document.getElementById("groupsuuid").value;
	var attrid = document.getElementById("attrid").value;
	var catagory = document.getElementById("catagory").value;
	var pageType = document.getElementById("pageType").value;
	if(attrname==""){
		alert("请填写[属性名称]");
		return false;
	}
	if(attrorder==""){
		alert("请填写[排序号],且必须为数字");
		return false;
	}
//	if(adminGroupuuid==""){
//		alert("请选择[属性被管理的组]");
//		return false;
//	}
	if(resourceType==""){
		alert("请选择[属性对应的资源类型]");
		return false;
	}		
//	if(groupuuid==""){
//		alert("请选择[属性类型所属的组]");
//		return false;
//	}
	if(attrid==""){
		alert("请填写[属性标识]");
		return false;
	}
	if(catagory==""){
		alert("请选择[扩展属性类别]");
		return false;
	}
	if(pageType==""){
		alert("请选择[页面展示属性]");
		return false;
	}
	if(pageType!="text" && $("#typepage :first-child").children().length==0){
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
	if(flag){
		document.getElementById("ac").submit();
	}
}
$(function(){

	$("#rule option[value='${app.rule}']").attr("selected","selected");

	$("#adsearchgroup").click(function(){
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
	$("#jssearchgroup").click(function(){
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

function deleteNode(object){
	object.parentNode.parentNode.parentNode.removeChild(object.parentNode.parentNode);
}
	function createNode(){
		var objDiv = $(".typetext");
		if(objDiv.length==0||$(".typetext:last").val()!=""){
			var tr="<tr><td align='center' bgcolor='#fffff0'>选项值";
			tr+="	<input name='typeuuid' type='hidden' value=''>";
			tr+="	<input name='typetext' class='typetext' >&nbsp;<font color='red'>必填</font>&nbsp;";
			tr+="	选项提示<input name='typecaption' class='typetext' >&nbsp;<font color='red'>必填</font>";
			tr+="	<input type='button' class='button' onclick='createNode()' value='添加'>&nbsp;";
			tr+="	<input type='button' class='button' onclick='deleteNode(this)' value='删除'>";
			tr+="</td></tr>";
			$(tr).appendTo($("#typepage"));
		}
	}
	function typechange(object){
		if(object==""||object=="text"){
			$("#typepage").html("<tbody></tbody>");
		}else{
			$("#type").show();
			if($("#typepage :first-child").children().length==0){
				createNode();
			}
		}
	}

</script>
<div style="overflow-y:scroll;height:420px;width:100%;background:#FFFFFF;" >
	<form name="ac" id="ac" action="<@spring.url "/attr/attreditsuc.nsf" />?id=${id}&type=${type}" method="post">
		<table id="tt"  width="100%" style="background-color:#B7D6F5;border:0px;color:#000000;font-size:12px;" align="center" border="0" cellspacing="1" cellpadding="0">
			<input type="hidden" name="attruuid" id="attruuid" value="${app.uuid}"/>
			<input type="hidden" name="userids" id="userids" value=""/>
			<tr height="24px">
				<td align="right" colspan="3"  bgcolor="#fffff0"></TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
								<input name="resourceType" id="resourceType" type="hidden" value="${id}"/>
								<span ><font>
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
								</span></font>
				</td>
			</tr>
			<tr height="24px">
				<td align="right" colspan="3"  bgcolor="#fffff0">属性标识</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<span name="attrid" id="attrid" >${app.id?if_exists}</span>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性名称</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input maxlength="15" name="name" id="name" onkeypress="checkEventName();" value="${app.name}"/>
					<font color="red">必填</font></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">排序号</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<input type="text" maxlength="5" name="order" id="order" style="ime-mode:disabled;" onkeypress="checkEventNUM();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" value="<#if app.order??>${app.order}<#else>&nbsp;</#if>"/>
				<font color="red">必须为数字 </font></td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性信息长度</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="text" name="length" maxlength="2" id="length" value="${app.length?if_exists}" style="ime-mode:disabled;" onkeypress="checkEventNUM();" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
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
					<input type="radio" name="hidden" id="hidden1" value="hidden" <#if app.hidden==true>checked</#if>><label for="hidden1">隐藏</label>
					<input type="radio" name="hidden" id="hidden2" value="display" <#if app.hidden==false>checked</#if>><label for="hidden2">非隐藏</label>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">是否为多值属性</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="radio" name="multivalued" id="multivalued1" value = "multiple" <#if app.multivalued==true>checked</#if>><label for="multivalued1">多值</label>
					<input type="radio" name="multivalued" id="multivalued2" value = "sole" <#if app.multivalued==false>checked</#if>><label for="multivalued2">单一值</label>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">是否设置为同步属性</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<input type="checkbox" name="sync" id="sync" <#if sync>checked</#if>/>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性被管理的组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<span name="adminGroupsName" id="adminGroupsName" >${jsAdminGroupName}</span>
				<input type="text" style="display:none;" name="adminGroupsuuid" id="adminGroupsuuid" value="${jsAdminGroupUuid}" >
				<input type="button" class="button" id="adsearchgroup" value="选择组"/>
				<font color="red"></font>
				</td>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">属性类型所属的组</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
				<span name="groupsName" id="groupsName">${jsgroupAreName}</span>
				<input type="text" style="display:none;" name="groupsuuid" id="groupsuuid" value="${jsgroupuuid}" >
				<input type="button" class="button" id="jssearchgroup" value="选择组"/>
				<font color="red"></font>
				</td>
			</tr>
			
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">扩展属性类别</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<select name="catagory" id="catagory">
						<option value="">《======请选择======》</option>
						<#if app.catagory??>
						<option value="0" <#if app.catagory=="0">selected</#if>>基本</option>
						<option value="1" <#if app.catagory=="1">selected</#if>>通信</option>
						<option value="2" <#if app.catagory=="2">selected</#if>>职务</option>
						<option value="3" <#if app.catagory=="3">selected</#if>>应用</option>
						<#else>
						<option value="0">基本</option>
						<option value="1">通信</option>
						<option value="2">职务</option>
						<option value="3">应用</option>
						</#if>
					</select>
					<font color="red">必选</font>
				</td>
			</tr>
			</tr>
			<tr >
				<td align="right" colspan="3"  bgcolor="#fffff0">页面展示属性</TD>
				<td  align="left" colspan="3"  bgcolor="#fffff0">
					<select name="pageType" id="pageType" onchange="typechange(this.value);">
						<option value="">《======请选择======》</option>
						<#if app.pageType??>
						<option value="text" <#if app.pageType=="text">selected</#if>>文本框</option>
						<option value="radio" <#if app.pageType=="radio">selected</#if>>单选框</option>
						<!--option value="checkbox" <#if app.pageType=="checkbox">selected</#if>>复选框</option-->
						<option value="select" <#if app.pageType=="select">selected</#if>>下拉列表</option>
						<#else>
						<option value="text">文本框</option>
						<option value="radio">单选框</option>
						<!--option value="checkbox">复选框</option-->
						<option value="select">下拉列表</option>
						</#if>
					</select>
					<font color="red">必选</font>
				</td>
			</tr>
				<script type="text/javascript">
					<#if app.catagory??>$("#catagory").val(${app.catagory})</#if>
					typechange($("#pageType").val());
				</script>
			<tr style="width:100%;">
			<td align="left" colspan="6" bgcolor="#fffff0">
			<div id="type" style=
				<#if app.pageType?? >
					<#if app.pageType=="">
						"display:none"
					<#elseif app.pageType=="text">
						"display:none"
					<#else>
						"display:block"
					</#if>
				<#else>
					"display:none"
				</#if>>
				<table id="typepage" style="width:100%;border:0px;color:#000000;font-size:12px;" align="center" border="0" cellspacing="1" cellpadding="0">
				<#if app.candidateItems??>
				<#list app.candidateItems as candidateItems>
				<tr><td align="center" bgcolor="#fffff0">选项值
					<input name='typeuuid' type="hidden" value="${candidateItems.uuid}">
					<input name='typetext' class='typetext' value="<#if candidateItems.value ??>${candidateItems.value}</#if>">&nbsp;<font color='red'>必填</font>&nbsp;
					选项提示<input name='typecaption' class='typetext' value="<#if candidateItems.caption ??>${candidateItems.caption}</#if>">&nbsp;<font color='red'>必填</font>
					<input type='button' class='button' onclick='createNode()' value='添加'>&nbsp;
					<input type='button' class='button' onclick='deleteNode(this)' value='删除'>
				</td></tr>
				</#list>
				</#if>
				</table>
			</div>
			</td>
			</tr>
			<tr><td align="center" colspan="6" bgcolor="#fffff0">
				<input type="button" class="button" value="提交" onclick="check()"/>&nbsp;&nbsp;
				<input type="button" class="button" value="重置" onclick="reset1()"/>&nbsp;&nbsp;
				<input type="button" class="button" value="删除" onclick="delect()"/>&nbsp;&nbsp;
				<input type="button" class="button" value="返回" onclick="back()"/>&nbsp;&nbsp;
			</td></tr>
		</table>
	</form>
</div>
