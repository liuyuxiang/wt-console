<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String indexType = request.getParameter("indexType");
	request.setAttribute("indexType",indexType);
 %>
 
<script type="text/javascript">
	var tree=null;
	function refreshTree(id){
		if(id && tree && tree.getIndexById(id) != null ){
			tree.refreshItem(id) ;
			tree.openItem(id);
		}
	}
	//左侧树初始化
	$(function(){
		tree=new dhtmlXTreeObject('sidebar',"100%","100%",0);
		tree.setImagePath("${pageContext.request.contextPath}/common/component/dhtmlxtree/images/");
		tree.setXMLAutoLoading("${pageContext.request.contextPath}/heaIndexAction.hea?action=loadNextNodesIndex&indexType=${indexType}&appId=${param.appId}");
		tree.loadXML("${pageContext.request.contextPath}/heaIndexAction.hea?action=initTreeIndex&indexType=${indexType}&appId=${param.appId}");//load root level from xml
		
		tree.setOnClickHandler(function(id){
			if(id == '__Y__'){
				document.getElementsByName('iframeContent')[0].src = '';
				return;
			}
			document.getElementsByName('iframeContent')[0].src = '${pageContext.request.contextPath}/heaconsole/web/rbac/index/indexManager.jsp?indexId=' + id + '&appId=${param.appId}';
		});
	});
 </script>