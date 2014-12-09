<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/modules/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>基础数据维护</title> 
	<!-- 添加hea角本库 -->
	${componentConfig.jqueryPath}
	<link id="themePath" rel="Stylesheet" href="${pageContext.request.contextPath}/heaconsole/css/subindex.css" />
	<script type="text/javascript">
		     function jump(){
		         var num=document.getElementById('pageNum').value;
		         var itemSize=document.getElementById("itemSize").value;
		          if(num>eval(0${page.totalPage})){
		          		alert('最大页数为${page.totalPage}页');return ;
		           };
		           window.open('heaSystemCodeAction.hea?action=loadPage&perPageRecord='+itemSize+'&currPageNum='+num,'_self');
		     }
	</script>

  </head>
  
  <body>
  <div class="title">系统常量维护</div>
  	 <p align="right" style="padding-right:100px;">
  	 	<!-- 
  	 	<input name="key" value=""/>
  		<input type="button" value="搜索" class="btn" align="right" class="ipt_3"/>
  		 -->
  		 <input type="button" class="btn" value="<bean:message key='ui.add'/>" onclick="window.open('${ctx}/heaconsole/web/rbac/systemcode/systemCodeAdd.jsp','_self')">
  	 </p>
    <table  class="tab tablestyle">
		<tr class="tab_title" >
			<td>注册编码</td>
			<td>注册值</td>
			<td>描述</td>
			<td >注册类型</td>
			<td >注册名称</td>
			<td >操作</td>
		</tr>
		
		<c:if test="${not empty page.data}">
			<c:forEach items="${page.data}" var="sc" varStatus="cnt">
				<tr ${cnt.count%2==0 ? "class=tab_trcurrent":"class=tab_trcurrent_1"} onmouseover="this.className='over'"  onmouseout="this.className='${cnt.count%2==0 ? "tab_trcurrent":"tab_trcurrent_1"}'">
					<td>
						<a href="${ctx}/heaconsole/web/rbac/systemcode/systemCodeAdd.jsp?pid=${sc.id}" target="_self">${sc.regCode }</a>
					</td>
					<td title="${sc.regValue }">${sc.regValue }</td>
					<td>${sc.regDesc}</td>
					<td>${sc.regType eq 0 ? "线性结构": "树型结构"}</td>
					<td >${sc.regName }</td>
					
					<td >
						<a href="${ctx}/heaSystemCodeAction.hea?action=toUpdate&id=${sc.id}"  >[<bean:message key='ui.edit'/>]</a>
						<a href="${ctx}/heaSystemCodeAction.hea?action=delete&id=${sc.id}" onclick="return confirm('确认删除吗?');">[<bean:message key='ui.delete'/>]</a>
						<a href="${ctx}/heaconsole/web/rbac/systemcode/systemCodeAdd.jsp" target="_self">[<bean:message key='ui.add'/>]</a>
					</td>
				</tr>
			</c:forEach>
				<tr class="tab_trcurrent">
				<td colspan="6" align="center">
		             <div class="position_cen" >
					总记录数 ${page.totalRecord} &nbsp;共${page.totalPage}页，当前第${page.currPageNum }页 
					<a href="heaSystemCodeAction.hea?action=loadPage&perPageRecord=${page.perPageRecord}&currPageNum=1" target="_self">首页</a>
					<a class="pre_2" href="heaSystemCodeAction.hea?action=loadPage&perPageRecord=${page.perPageRecord}&currPageNum=${page.currPageNum-1 == 0 ? 1:page.currPageNum-1}" target="_self">上一页</a> 
					<a class="next_2" href="heaSystemCodeAction.hea?action=loadPage&perPageRecord=${page.perPageRecord}&currPageNum=${page.currPageNum+1 > page.totalPage ? page.totalPage:page.currPageNum+1}" target="_self">下一页</a> 
					<a href="heaSystemCodeAction.hea?action=loadPage&perPageRecord=${page.perPageRecord}&currPageNum=${page.totalPage}" target="_self">尾页</a>
					 每页 <input type="text"  id="itemSize" value="${page.perPageRecord}" style="text-align: center; width: 25px;"/> 条 
					 跳转 <input type="text" name="no" id="pageNum" value="${page.currPageNum }" style="text-align: center; width: 25px;" />页
		            	<input type="button" name="button" class="btn" id="button" onclick="jump();" value="确定"/>
		            
		            </div>
				</td>
		 	</tr>
		</c:if>			
	</table>
	${message}
  </body>
</html>
