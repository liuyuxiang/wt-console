<#import "/spring.ftl" as spring />

<#if deptTreeRoot?? >

<#include "/style/jquery.simpletree.ftl" >

<#include "/script/jquery.simpletree.ftl" >

<script type="text/javascript">
//<![CDATA[

	var simpleTreeCollection;
	$(function(){
		simpleTreeCollection = $('.simpleTree').simpleTree({
			autoclose: true,
			afterClick:function(destination,node){
			
			},
			afterDblClick:function(node){
				 //alert("text2-"+$('span:first',node).text());
			},
			afterMove:function(destination, source, pos){
				 //alert("destination-"+destination.attr('id')+" source-"+source.attr('id')+" pos-"+pos);
			},
			afterAjax:function()
			{
				// alert('Loaded');
			},
			animate:false,
			drag:false,
			stylePath:"<@spring.url "/style/jquery/simpletree/" />"
		});

$(".publicbutton").click(function(){
	var a = document.getElementsByName("treedeptid");
	if(a!=null){
		var valueStr="";
		for(i=0;i<a.length;i++){
			if(a[i].checked){
				valueStr+=a[i].value+',';
			}
		}
	}
	if(valueStr.length>0){
		if(window.opener) {  
	            window.opener.returnValue = valueStr;  
	        } else {  
	            window.returnValue=valueStr;  
	        } 
		window.close();
	}else{
		alert("你还没选择吧！");
	}
});

  });
//]]>
</script>

</#if>

<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
	<div>
	<ul class="simpleTree">
	<li class="root" id="${deptTreeRoot.uuid}" ><span><@spring.message "department" /></span>
		<ul>
			<#list deptChildren as dept>
			<li id="${dept.uuid}" ><span style="vertical-align: middle;" >${dept.name?html}</span>
					<ul class="ajax">
						<li>{url:<@spring.url "/user/simpletree/publicajax.nsf?id="+dept.uuid+"&type="+type+"&changetype=${changetype}&groupuuids=${groupuuids?if_exists}"/>}</li>
					</ul>
			</li>
			</#list>
			<#list members as user>
			<li id="${user.uuid}" ><input style="vertical-align: middle;" type="${type}" value="${user.uuid}" id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${user.name?html}</span>
			</li>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<input type="hidden" name="groupuuids" value="${groupuuids?if_exists}"/>
<div id="treefooter">
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
</div>
