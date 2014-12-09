<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
		var deleteIndexId = "";
		function updateIndex(indexuuid,appId){
			if(!indexuuid || deleteIndexId==indexuuid){
				alert("没选择任何资源");
				return ;
			}
			if(indexuuid != 'hea_indexroot_0'){
			document.getElementById('iframeIndex').src = '${pageContext.request.contextPath}/heaIndexAction.hea?action=toUpdateIndex&indexuuid='+indexuuid + '&appId=' + appId;
			}
		}
		
		function addIndex(indexuuid,appId){
			if(!indexuuid || deleteIndexId==indexuuid){
				alert("没选择任何资源");
				return ;
			}
			document.getElementById('iframeIndex').src = '${pageContext.request.contextPath}/heaIndexAction.hea?action=toAddIndex&parentUuid=' + indexuuid + '&appId=' + appId;
		}
		
		function deleteIndex(indexuuid,appId){
			if(!indexuuid || deleteIndexId==indexuuid){
				alert("没选择任何资源");
				return ;
			}
			if(confirm('删除指标将删除该指标的下级指标！')){
				document.getElementById('iframeIndex').src = '${pageContext.request.contextPath}/heaIndexAction.hea?action=deleteIndex&indexuuid=' + indexuuid + '&appId=' + appId;
				deleteIndexId = indexuuid;
			}
		}
		
		function updateIndexParent(indexuuid,appId){
			if(!indexuuid || deleteIndexId==indexuuid){
				alert("没选择任何资源");
				return ;
			}
			document.getElementById('iframeIndex').src = '${pageContext.request.contextPath}/heaconsole/web/rbac/index/updateIndexParent.jsp?indexuuid=' + indexuuid + '&appId=' + appId;
		}
		function updateIGTree(indexuuid,appId){
			if(!indexuuid || deleteIndexId==indexuuid){
				alert("没选择任何资源");
				return ;
			}
			document.getElementById('iframeIndex').src = '${pageContext.request.contextPath}/heaIndexAction.hea?action=toIndexGroupTree&indexId=' + indexuuid + '&appId=' + appId;
		}
	</script>