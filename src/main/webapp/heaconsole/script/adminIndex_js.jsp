<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	String indexType = request.getParameter("indexType");
	request.setAttribute("indexType",indexType);
 %>
<script type="text/javascript">
		//注册左侧树事件响应到tab页
	
	var tree=null;
	function refreshTree(id){
		if(id && tree && tree.getIndexById(id) != null){
			tree.refreshItem(id) ;
			tree.openItem(id);
		}
	}
		
	function loadConsoleTree(){
	tree=new dhtmlXTreeObject('sidebar',"100%","100%",0);
	tree.setImagePath("${ctx}/common/component/dhtmlxtree/images/");
	tree.setXMLAutoLoading("${ctx}/heaIndexAction.hea?action=loadNextNodes&indexType=${indexType}");
	tree.loadXML("${ctx}/heaIndexAction.hea?action=initTree&indexType=${indexType}");//load root level from xml
	
	tree.setOnClickHandler(function(id){
				 	var url=this.getUserData(id,"url");
				 	if(url==null || url=="")return ;
				 	$("#hiddenFrame").attr("src",'${ctx}/heaUserAction.hea?action=isSessionValidate');
					var name= this.getItemText(id);
					
					var t=$(".selectedTreeRow");
            			
            			
	                    $.fn.jerichoTab.addTab({
	                    	uniqueId:id,
	                        tabFirer:t,
	                        title: name,
	                        closeable: true,
	                      	iconImg:  '${ctx }/common/component/jerichotab/images/jerichotab.png',
	                        loadOnce: true,
	                        contentCss: { 'height': $('#rightContent').height() },
	                        data: {
	                            dataType: 'iframe', //formtag,html,iframe,ajax
	                            dataLink: url
	                        },
	                        type:'add'
	                    }).showLoader().loadData();         
				
			});
	
			
			//初使化tab页
			 var jericho = {
           
            buildTree: function() {
            		$("span.standartTreeRow").mousedown(function() {
            			var t = $(".selectedTreeRow")[0] ;
            			alert(t);
            			tree.setOnClickHandler(function(){
            				$.fn.jerichoTab.addTab({
	                        tabFirer: t,
	                        title: $(this).text(),
	                        closeable: true,
	                       	iconImg:  '${ctx }/common/component/jerichotab/images/jerichotab.png',
	                        loadOnce: true,
	                        contentCss: { 'height': $('#rightContent').height() },
	                        data: {
	                            dataType: 'iframe', //formtag,html,iframe,ajax
	                            dataLink: url
	                        },
	                        type:'add'
	                    }).showLoader().loadData();
            			});
	                    
            	}); 
            },
            
            
            //初使第一个默认tab页
            buildTabpanel: function() {
            	
                $.fn.initJerichoTab({
                    renderTo: '#rightContent',
                    uniqueId: 'tab_index',
                    tabWidth:120,
                    contentCss: { 'height': $('#rightContent').height() },
                    tabs: [{
                        title: '首页',
                        closeable: false,
                       	iconImg:  '${ctx }/common/component/jerichotab/images/jerichotab.png',
                        data: { dataType: 'iframe', dataLink: '${ctx}/heaconsole/web/indextab.jsp' },
                        onLoadCompleted: function(h) {
                            $('<b style="color:red" />').html('The JerichoTab processed in ').appendTo(h);
                        }
                    }],
                    activeTabIndex: 0,
                    loadOnce: true,
                    type:'init'
                    });
                }
            }
			jericho.buildTree();
			jericho. buildTabpanel();
	       	
	       	
	       	
	       	//树构事件响应，每点击结点动态添加tab页
	       	var clickedNode=new Array();
	       	var temp=null;
	       	$("span.standartTreeRow").mousedown(function(){
				        	temp=$(this);
				        });
	       	
	       	
	       //	var t=null;
			
			
	       // $(function() {
	        	//页面加载后去掉图片边框样试
				$("#home").css({"border":0,"width":25,"height": 25,"cursor":"pointer"});
	        	
	        	//div+css布局样式控制
	        	var h=$('#sidebar').height()+32;
	           $('#sidebar').css('height',h);
	        //});
		}
	
	function autoWindowHeight() {
		var headerHeight = document.getElementById('header').offsetHeight;
		var actionmenu = document.getElementById('login').offsetHeight;
		var footerHeight = document.getElementById('footer').offsetHeight;
		var mainnavHeight = 0;//document.getElementById('mainnav').offsetHeight;
		var clientHeight = getDocumentBody().clientHeight;
		var clientWidth = getDocumentBody().clientWidth;
		var sidebarWidth = $("#sidebar").width();
		var maincontentHeight = clientHeight - headerHeight - mainnavHeight - footerHeight;
		var maincontentWidth = clientWidth - sidebarWidth - 5;
		$("#sidebar").height( maincontentHeight - actionmenu);
		$("#maincontent").height(maincontentHeight);
		
		;// - loginHeight;
		
		$("#rightContent").height(maincontentHeight);
		$("#rightContent").width(maincontentWidth - sidebarWidth);
		var tabObject = $("#rightContent>.jericho_tab");
						$(tabObject).css('height',maincontentHeight);
						$(tabObject).css('width',maincontentWidth - 5);
						$('.jericho_tab>.tab_content').css('height',maincontentHeight - 30);//30为tab标签的高度
	}

	function getDocumentBody() {
	    return (document.compatMode && document.compatMode != "BackCompat") ? document.documentElement : document.body;
	}
	window.onresize = autoWindowHeight;
 </script>