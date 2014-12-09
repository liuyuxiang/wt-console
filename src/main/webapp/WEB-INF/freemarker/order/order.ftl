<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/script/jquery.ftl" >
	<head>
		<title></title>
		<style type='text/css'>
		thead
		{
		    font-size:12pt;
		    font-weight:bold;
		    color:#444;
		}
		
		table
		{
			border-collapse: collapse;
		}
		
		tbody
		{
		    font-size:10pt;
		    color:#444;		    
		}
		
		td,th
		{
		     border:solid 1px #E6E6E6;
		     padding-left:3px;
		}
		
		th
		{
			background-image:url(<@spring.url "/style/default/images/title_bg.gif" />);
		}
		
		.alternation
		{
		    background-color:#D2FFD2;
		}
		
		.alternation3
		{
		    background-color:#DDEEDD;
		}
		
		.mouseOver
		{
		    background-color:#DFD;
		}
		
		.selected
		{
		    background-color:#B3E1A3;
		}
		.tr1
		{
		    background-color:#DAE9F9;
		}
		.tr2
		{
		    background-color:#EEEEEE;
		}
		.tr3
		{
		    background-color:#F7BE77;
		}
		
    </style>
<script type="text/javascript">
//<![CDATA[
	
$(function(){

	if('${type}'=='user'){
		$("#dept").toggle();
		$("#user").toggle();
	}

	$("#type > option[value='${type}']").attr("selected","selected");
	$("#type").change(function() {
		$("#dept").toggle();
		$("#user").toggle();
	});
	deptSort();
	userSort();
	$(".button").click(function(){
		var url="<@spring.url "/order/modifyorder.nsf"/>"
		var data=new Array($(".deptuuid").size());
		$(".deptuuid").each(function(i){
			data[i]=this.value;
		});
		var data1=new Array($(".useruuid").size());
		$(".useruuid").each(function(i){
			data1[i]=this.value;
		});
		if($("#change").val()=="true"){
			$.post(url,{deptuuid:data,useruuid:data1},function(date){
				$("#change").val("false");
				alert("更新成功！");
			});
		}
	});
});
  
function go(obj,move){
	$(obj).parent().parent().removeClass().addClass("tr3");
  	var data=null;
  	var type=$("#type").val();
 	if(type=='department'){
		data=new Array($(".deptuuid").size());
		$(".deptuuid").each(function(i){
			data[i]=this.value;			
		});
	}else{
		data=new Array($(".useruuid").size());
		$(".useruuid").each(function(i){
			data[i]=this.value;			
		});
	}
	var index=parseInt(obj.name);
	var thisuuid=null;
	var otheruuid=null;
	var isSubmit="true";
	if(move=="move"){
		thisuuid=data[index];
		otheruuid=data[index-1];
		if(otheruuid==null){
			isSubmit="false";
			alert("this is top");
		}
	}else{		
		thisuuid=data[index];
		otheruuid=data[index+1];
		if(otheruuid==null){
			isSubmit="false";
			alert("this is bottom");
		}
	}	
	if(isSubmit=="true" && type=="user"){
		window.location.href='<@spring.url "/order/modifyuserorder.nsf" />?thisuuid='+thisuuid+'&otheruuid='+otheruuid+'&type='+type;
	}else{
		window.location.href='<@spring.url "/order/modifydeptorder.nsf" />?thisuuid='+thisuuid+'&otheruuid='+otheruuid+'&type='+type;
	}
}
function gotop(obj,top){
	$(obj).parent().parent().removeClass().addClass("tr3");
	var data=null;
  	var type=$("#type").val();
	var thisuuid=obj.id;
	window.location.href='<@spring.url "/order/modifyordertop.nsf" />?thisuuid='+thisuuid+'&type='+type+'&isTop='+top;
}
		
function deptSort(){
	var tbody = $("#table1 > tbody");
	var testr = tbody.children();
	for(var i=0;i<testr.length;i++) {
	    if(i%2==0){
	    	$(testr[i]).addClass("tr1");
	    }else{
	    	$(testr[i]).addClass("tr2");
	    }
	}
	//tableSort(tbody);//拖动功能
}
function userSort(){
	var tbody = $("#table2 > tbody");
	var testr = tbody.children();
	for(var i=0;i<testr.length;i++) {
	    if(i%2==0){
	    	$(testr[i]).addClass("tr1");
	    }else{
	    	$(testr[i]).addClass("tr2");
	    }
	}
	//tableSort(tbody);//拖动功能
}
function tableSort(obj) {
	var tbody = obj;
	var rows = tbody.children();
	var selectedRow;
	//压下鼠标时选取行
	rows.mousedown(function(){
		selectedRow = this;
		tbody.css('cursor', 'move');
		return false;	//防止拖动时选取文本内容，必须和 mousemove 一起使用
	});
	rows.mousemove(function(){
		return false;	//防止拖动时选取文本内容，必须和 mousedown 一起使用
	});
	//释放鼠标键时进行插入
	rows.mouseup(function(){
		if(selectedRow)
		{
			if(selectedRow != this)
			{
				$("#change").val("true");
				var a = $(this).children("td:first").children("span:first").html();
				var b = $(selectedRow).children("td:first").children("span:first").html();
				if(a>b){
					$(this).after($(selectedRow)).removeClass('mouseOver'); //插入
				}else{
					$(this).before($(selectedRow)).removeClass('mouseOver'); //插入
				}
				var testr = tbody.children();
				for(var i=0;i<testr.length;i++) {
				    if(i%2==0){
				    	$(testr[i]).removeClass("tr2").addClass("tr1");
				    }else{
				    	$(testr[i]).removeClass("tr1").addClass("tr2");
				    }
					$(testr[i]).children("td:first").children("span:first").text(i+1);
				}
			}
			tbody.css('cursor', 'default');
			selectedRow = null;	
		}
	});
	//标示当前鼠标所在位置
	rows.hover(
		function(){
			if(selectedRow && selectedRow != this)
			{
				$(this).addClass('mouseOver');	//区分大小写的，写成 'mouseover' 就不行
			}
		},
		function(){
			if(selectedRow && selectedRow != this)
			{
				$(this).removeClass('mouseOver');
			}
		}
	);
	//当用户压着鼠标键移出 tbody 时，清除 cursor 的拖动形状，以前当前选取的 selectedRow
	tbody.mouseover(function(event){
		event.stopPropagation(); //禁止 tbody 的事件传播到外层的 div 中
	});	
}

//]]>
</script>

<form name="form1" id="form1" method="post" action="">
	<table  cellspacing="0" width="100%" align="center"  >
		<tr><input id="change" type="hidden" value="false"/>
			<td width="100%" align="left" valign="top">
				<table width="100%" style="background-color:#DAE9F9;font-size:12px;height:30px;">
					<tr>
					<td>排序对象:
						<select id="type" name="type">
							<option value="department">部门</option>
							<option value="user">人员</option>
						</select>
					</td>
					<td style="text-align:right;"><input type="button" class="button" style="display:none;" value="提交排序"/>
					</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="8">
			<div style="height:30px;overflow:hidden;">
				<table style="width:100%;height:100%;">
					<tr>
						<th style="width:10%;">序号</th>
						<th>名称</th>
						<th style="width:17%;">操作</th>
					</tr>
				</table>
			</div>
			<div id="dept" style="height:410px;overflow-x:hidden;overflow-y:auto;">
				<table width="100%" id="table1" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" style="height:0px;">
				<thead>
				</thead>
				<tbody id="content">
					<#if deptList??>
						<#list deptList as dept>
							 <tr height="21" align="center" <#if uuid??><#if dept.uuid=uuid>class="tr3"</#if></#if>>
						 		<td style="width:10%;" ><span>${dept_index+1}</span></td>
						 		<td >${dept.name?html}<input type="hidden" class="deptuuid" value="${dept.uuid}" /></td>
						 		<td style="width:17%;">
						 		<#if dept_index!=0>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-up.png" />' style="width:15px;height:15px;cursor:pointer;" title="上移" id="${dept.uuid}" name="${dept_index}" onclick="go(this,'move');"/>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-top.png" />' style="width:15px;height:15px;cursor:pointer;" title="置顶" id="${dept.uuid}" name="${dept_index}" onclick="gotop(this,'top');"/>
						 		</#if>
						 		<#if dept_has_next>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-down.png" />' style="width:15px;height:15px;cursor:pointer;" title="下移" id="${dept.uuid}" name="${dept_index}" onclick="go(this,'down');"/>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-bottom.png" />' style="width:15px;height:15px;cursor:pointer;" title="置底" id="${dept.uuid}" name="${dept_index}" onclick="gotop(this,'bottom');"/>
						 		</#if>
						 		</td>
							</tr>
						</#list>
					</#if>
				</tbody>
				</table>
			</div>
			<div id="user" style="display:none;height:410px;overflow-x:hidden;overflow-y:auto;">
				<table width="100%" id="table2" border=1px; cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" style="height:0px;">
				<thead>
				</thead>
				<tbody id="content">
					<#if userList??>
						<#list userList as user>
							 <tr height="21" align="center" <#if uuid??><#if user.uuid=uuid>class="tr3"</#if></#if>>
						 		<td style="width:10%;" ><span>${user_index+1}</span></td>
						 		<td >${user.name?html}<input type="hidden" class="useruuid" value="${user.uuid}" /></td>
						 		<td style="width:17%;">
						 		<#if user_index!=0>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-up.png" />' style="width:15px;height:15px;cursor:pointer;" title="上移" id="${user.uuid}" name="${user_index}" onclick="go(this,'move');"/>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-top.png" />' style="width:15px;height:15px;cursor:pointer;" title="置顶" id="${user.uuid}" name="${user_index}" onclick="gotop(this,'top');"/>
						 		</#if>
						 		<#if user_has_next>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-down.png" />' style="width:15px;height:15px;cursor:pointer;" title="下移" id="${user.uuid}" name="${user_index}" onclick="go(this,'down');"/>
						 			<img src='<@spring.url "/style/hirisun/images/arrow-bottom.png" />' style="width:15px;height:15px;cursor:pointer;" title="置底" id="${user.uuid}" name="${user_index}" onclick="gotop(this,'bottom');"/>
								</#if>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
				</table>
			</div>
			</td>
		</tr>
	</table>
</form>
