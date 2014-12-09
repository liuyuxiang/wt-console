<#import "/spring.ftl" as spring />
<link rel="stylesheet" href="<@spring.url "/common/component/bootstrap-3.2.0/css/bootstrap.min.css"/>"/>
<script src="<@spring.url "/common/component/bootstrap-3.2.0/js/jquery.min.js"/>"></script>
<script src="<@spring.url "/common/component/bootstrap-3.2.0/js/bootstrap.min.js"/>"></script>

<script type="text/javascript">
//<![CDATA[
	
$(function(){
	$(".publicbutton").click(function(){
		$("#parseString").click();
		var valueStr=$("#textarea").val();
		if($("#roleId",parent.document)!=null){
			$("#roleId",parent.document).val(valueStr);
			//$("#closemodal",parent.document).click();
		}
		if(window.opener) {
			window.opener.returnValue = valueStr;  
		} else {  
			window.returnValue=valueStr;  
		} 
		window.close();		
	});
	
	$("#andadd").click(function(){
		$("#changetype").val("and");
		cleanModel();
	});
	$("#oradd").click(function(){
		$("#changetype").val("or");
		cleanModel();
	});
	$("#nonadd").click(function(){
		$("#changetype").val("non");
		cleanModel();
	});
	$("input[type='radio']").each(function(){
		var url;
		var returnVal;
		$(this).click(function(){
			switch($(this).val()){
			case "group":
				url='<@spring.url "/group/publicgtree.nsf"/>';
				break;
			case "duty":
				url='<@spring.url "/duty/publictree.nsf"/>';
				break;
			case "user":
				url='<@spring.url "/publicudtree.nsf"/>';
				break;
			default:
				alert("选择有误");
			}
			$("#iframe1").attr("src",url);
		});
	});
	
	$("#parseString").click(function(){
		var text = "";
		if($("#andcheck:checked").val()=="on"){
			$("#andlist").find("td[name='remark']").each(function(){
				text+="+|"+$(this).parent().find("input:checked").length+"|"+$(this).text()+",";
			});
		}
		if($("#orcheck:checked").val()=="on"){
			$("#orlist").find("td[name='remark']").each(function(){
				text+="*|"+$(this).parent().find("input:checked").length+"|"+$(this).text()+",";
			});
		}
		if($("#noncheck:checked").val()=="on"){
			$("#nonlist").find("td[name='remark']").each(function(){
				text+="-|"+$(this).parent().find("input:checked").length+"|"+$(this).text()+",";
			});
		}
		$("#textarea").val(text);
	});
	
	$("#parseUser").click(function(){
		$.ajax({
		type: "POST",
		url: '<@spring.url "/rest/duty/getusers"/>',
		data: {rule : $("#textarea").val()},
		//data: "rule="+encodeURIComponent($("#textarea").val()),
		success: function(msg){
			$("#users").val("");
			$(msg).each(function(){
				$("#users").val($("#users").val()+this.name+";");
			});
			}
		});
	});
	
	//如果需要先初始化则判定，自操作时获取父页面的roleId的数据
	if($("#roleId",parent.document)!=null){
		//获取数据
		init($("#roleId",parent.document).val());
	}
	//确认选择后处理数据
	$("#xuanzhong").click(function(){
		if($("#selfDept:checked").val()=="on"){
			var remark = "d|selfDept|当前部门";
			append2list(remark);
		}
		if($("#allUser:checked").val()=="on"){
			var remark = "u|allUser|所有用户";
			append2list(remark);
		}
		$("#iframe1").contents().find(".publicbutton").click();
		var testJson = $.parseJSON($("#iframe1").contents().find("#container").val());
		$(testJson).each(function(){
			var remark = $(this).attr("type")+"|"+$(this).attr("id")+"|"+$(this).attr("name");
			append2list(remark);
		});
	});

});

function append2list(remark){
	//如果重复就不添加了
	if($("#"+$("#changetype").val()+"list").find(":contains('"+remark+"')").html()==undefined){
		var arr = remark.split("|");
		var temp = "<tr><td>"+arr[0]+"</td><td>"+arr[2]+"</td><td name='remark'>"+remark+"</td><td><input type='checkbox'/></td><td><input type='button' class='btn btn-danger btn-sm' onclick='remove1(this)' value='删除'/></td></tr>";
		$("#"+$("#changetype").val()+"list").append(temp);
	}
}

function remove1(obj){
	$(obj).parent().parent().remove();
}
function cleanModel(){
	$("#myModal").find("input:checkbox").removeAttr("checked");
	$("#iframe1").attr("src","");
}
function init(data){
	var testArr = String(data).split(",");
	for(var i=0;i<testArr.length;i++){
		//格式为*|0|g|yfzxcwaudit|财务总监|a
		var arr = testArr[i].split("|");
		if(arr.length<5){
			//alert("不符合规则定义");
			continue;
		}
		var type;
		switch(arr[0])
		{
		case "*":
		  type = "or";
		  break;
		case "+":
		  type = "and";
		  break;
		case "-":
		  type = "non";
		  break;
		default:
		  alert("不符合规则定义");
		}
		var remark = arr[2]+"|"+arr[3]+"|"+arr[4];
		var check;
		if(arr[1]==1){
			check = "checked";
		}
		var temp = "<tr><td>"+arr[2]+"</td><td>"+arr[4]+"</td><td name='remark'>"+remark+"</td><td><input type='checkbox' "+check+"/></td><td><input type='button' onclick='remove(this)' value='删除'/></td></tr>";
		$("#"+type+"list").append(temp);
		$("#"+type+"check").attr("checked",true);
	}
	$("#parseString").click();
}

//]]>
</script>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
				&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
				选择要授权的类型<br>
					<input type="radio" value="group" name="radiogroup"/>角色
					<input type="radio" value="duty" name="radiogroup"/>职务
					<input type="radio" value="user" name="radiogroup"/>部门/用户
				<br><br>选择特殊授权类型<br>
					<input type="checkbox" id="selfDept"/>用户所在部门(selfDept)
					<input type="checkbox" id="allUser"/>所有用户(allUser)
				</h4>
			</div>
			<div class="modal-body">
				<iframe id="iframe1" src="" width="100%" height="300px" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="xuanzhong">确认选择</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<div class="container table-bordered">
<div class="form-group text-center">
	<h1>权限列表（规则生成器）</h1>
	<input type="hidden" id="changetype"/>
</div>

	<div id="and" class="form-group">
		<input type="checkbox" id="andcheck" /><label for="andcheck">与(+)</label>
		<button class="btn btn-link btn-lg" data-toggle="modal" id="changerule" data-target="#myModal">
			<img id="andadd" src="<@spring.url "/style/hirisun/images/arrow-bottom.png" />"/>
		</button>
		<table class="table table-hover table-bordered table-condensed" id="andlist" >
		<tr><th>type</th><th>name</th><th>remark</th><th>包含下级</th><th>exec</th></tr>
		</table>
	</div>	
	<div id="or" class="form-group">
		<input type="checkbox" id="orcheck"/><label for="orcheck">或(*)</label>
		<button class="btn btn-link btn-lg" data-toggle="modal" id="changerule" data-target="#myModal">
			<img id="oradd" src="<@spring.url "/style/hirisun/images/arrow-bottom.png" />"/>
		</button>
		<table class="table table-hover table-bordered table-condensed" id="orlist"  data-target="#myModal">
		<tr><th>type</th><th>name</th><th>remark</th><th>包含下级</th><th>exec</th></tr>
		</table>
	</div>	
	<div id="non" class="form-group">
		<input type="checkbox" id="noncheck"/><label for="noncheck">非(-)</label>
		<button class="btn btn-link btn-lg" data-toggle="modal" id="changerule" data-target="#myModal">
			<img id="nonadd" src="<@spring.url "/style/hirisun/images/arrow-bottom.png" />"/>
		</button>
		<table class="table table-hover table-bordered table-condensed" id="nonlist"  data-target="#myModal">
		<tr><th>type</th><th>name</th><th>remark</th><th>包含下级</th><th>exec</th></tr>
		</table>
	</div>
<div class="form-group">
	<div class="col-sm">
		<input type="button" id="parseString" class="btn-link btn-sm" value="解析"/><br>
		<textarea id="textarea" class="form-control"  rows="3">
		</textarea>
	</div>
	<div class="col-sm">
		<input type="button" id="parseUser" class="btn-link btn-sm" value="用户列表"/><br>
		<textarea id="users" class="form-control"  rows="3">
		</textarea>
	</div>
</div>
<div id="treefooter" style="display:none;">
<input type="button" class="publicbutton" value="确定"/>
</div>
</div>