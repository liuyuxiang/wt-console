<%@ page language="java" pageEncoding="UTF-8"%>
<script  type="text/javascript">
 	$(function(){
 		//校验关系
 		$("#btnReportVerifyRelation").click(function(){
 			if($("#rptId").val() == ""){
 				alert("请先定义报表信息，并保存!");
 				return false;
 			}
 			var rptForm=	$("#rptForm")[0];
 			rptForm.action="${pageContext.request.contextPath}/heaRptRuleInfoAction.hea?ops=init&rptId=${rptBaseInfo.rptId}";
 			rptForm.submit();
 		});
 		
 		//统计内容
 		$("#btnStatisticsContent").click(function(){
 			if($("#rptId").val() == ""){
 				alert("请先定义报表信息，并保存!");
 				return false;
 			}
 			
 			var rptForm=	$("#rptForm")[0];
 			rptForm.action="${pageContext.request.contextPath}/heaRptStatInfoAction.hea?action=display&ops=init&rptId=${rptBaseInfo.rptId}&isEdit="+$("#isEdit").val();
 			rptForm.submit();
 		});
 		
 		//关联报表
 		$("#btnRefReport").click(function(){
	 			if($("#rptId").val() == ""){
	 				alert("请先定义报表信息，并保存!");
	 				return false;
	 			}
	 			var rptForm=	$("#rptForm")[0];
	 			rptForm.action="${pageContext.request.contextPath}/heaRptRelaInfoAction.hea?action=display&isEdit=${isEdit}&ops=init&rptId=${rptBaseInfo.rptId}";
	 			rptForm.submit();
 		});
 		
 		
 		
 		//新增报表
 		$("#btnNew").click(function(){
 			var rptForm=	$("#rptForm")[0];
 			rptForm.action="${pageContext.request.contextPath}/heaRptBaseInfoAction.hea?action=display&ops=reset";
 			rptForm.submit();
 		});
 		
 		//取消
 		$("#btnCancel").click(function(){
 			window.open("${pageContext.request.contextPath}/heaSystemCodeAction.hea?action=loadPage","_self");
 		});
 		
 		//保存或编辑
 		$("#btnAdd").click(function(){
 			var rptForm=	$("#rptForm")[0];
 			//校验
		//	var rptBaseInfo_rptNo=$("#rptBaseInfo_rptNo").val();
 		//	var rptBaseInfo_rptName=$("#rptBaseInfo_rptName").val();
 		//	var rptBaseInfo_rptKey=$("#rptBaseInfo_rptKey").val();
 		//	if(rptBaseInfo_rptNo =="" ){
 		//		alert("报表编号不能为空!");
 		//		return false;
 		//	}
 		//	if(rptBaseInfo_rptNo.length>16){
 		//		alert("报表编号不能大于16位字符!");
 		//		return false;
 		//	}
 			
 		//	if(rptBaseInfo_rptName ==""){
 		//		alert("报表名称不能为空!");
 		//		return false;
 		//	}
 		//	if(rptBaseInfo_rptName.length>100){
 		//		alert("报表名称不能超过100个字符!");
 		//		return false;
 		//	}
 		//	if(rptBaseInfo_rptKey == ""){
 		//		alert("报表关键字不能为空!");
 		//		return false;
 		//	}
 		//	if(rptBaseInfo_rptKey.length >100){
 		//		alert("报表关键字不能超过100个字符!!");
 		//		return false;
 		//	}
 			
 			
 			var isEdit=$("#isEdit").val();
 			
 			if(isEdit ==""){
 				$("#ops").val("add");
 				rptForm.submit();
 			}else if(isEdit == "yes"){
 				$("#ops").val("edit");
 				rptForm.submit();
 			}
 		});
 		
 		//一级大类和二级大类的ajax联动
		$("#firType").change(function(){
			if($(this).val()!=-1){
				$.ajax({
				  type: "POST",
				  url: "heaRptSystemCodeAction.hea?firType="+$("#firType").val() ,
				  cache: false,
				  success: function(html){
						var json=eval(html);
						if(json.length>0){
							$("#secType").empty();
							for(var i=0;i<json.length;i++){
								$("#secType").append('<option value="'+json[i].regValue+'">'+json[i].regLabel+'</option>');
							}
						}
				  }
				});
				}
		});
 		
 	})
 	
 	//统计关系
 	function statisticsRelation (rptId){
 			if($("#rptId").val() == ""){
 				alert("请先定义报表信息，并保存!");
 				return false;
 			}
 			var rptForm=	$("#rptForm")[0];
 			rptForm.action="${pageContext.request.contextPath}/heaRptStatRelaOrgAction.hea?action=display&ops=init&rptId="+rptId;
 			rptForm.submit();
 	}
 	
 	//清除当前报表关联关系
 	function clearRef(targetId,rptId){
 			var rptForm=	$("#rptForm")[0];
 			rptForm.action="${pageContext.request.contextPath}/heaRptRelaInfoAction.hea?action=editDisplay&ops=delet&rptId="+rptId+"&targetId="+targetId;
 			rptForm.submit();
 	}
 	
 	//清除当前报表的校验关系
 	function deleteRulInfo(ruleId,rptId){
 		var rptForm=	$("#rptForm")[0];
 			rptForm.action="${pageContext.request.contextPath}/heaRptRuleInfoAction.hea?ops=delet&rptId="+rptId+"&ruleId="+ruleId;
 			rptForm.submit();
 	}
 	
 	//清除当前报表的统计内容
 	function deleteStatInfo(statId,rptId){
 		var rptForm=	$("#rptForm")[0];
 			rptForm.action="${pageContext.request.contextPath}/heaRptStatInfoAction.hea?action=display&ops=delet&rptId="+rptId+"&statId="+statId;
 			rptForm.submit();
 	}
 	
 	//报表设置页面是否编辑显示效果
 	$(function(){
			$("input[edits=reportEdit]").click(function(){
				if(this.value== '01'){
					$(".editRow").fadeIn('fast');
				}else{
					$(".editRow").fadeOut('fast');
				}
			});
		})
 	</script>