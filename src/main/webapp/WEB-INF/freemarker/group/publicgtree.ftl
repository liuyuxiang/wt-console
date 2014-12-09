<#import "/spring.ftl" as spring />
<#include "/style/style.ftl" >
<#include "/style/jquery.simpletree.ftl" >
<#include "/script/jquery.ftl" >
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
	var arrChk=$("#selectedjson div");
	var valueStr="[";
	$.each(arrChk, function(i,val){
		if(arrChk.length>1&&i>0&&i<=arrChk.length-1){
			valueStr+=",";
		}
		valueStr+=$(val).html();
	});   
	valueStr+="]";
	if(arrChk.length<=0){
		valueStr="";
	}
	$("#container").val(valueStr);
//	if(window.opener) {
//		window.opener.returnValue = valueStr;  
//	} else {  
		window.returnValue=valueStr;  
//	} 
	window.close();		
});


  });

function checkboxchanged(obj){
	var check=$(obj).prop('checked');
	if(check){
		var spanv=$(obj).parent().find("span[id="+$(obj).val()+"]").text();
		$("#selectedjson").append("<div id='"+$(obj).val()+"'>"+spanv);
		$(obj).parent().find("ul input[type='checkbox'][id='treegroupid']").each(function(){
			$(this).prop("disabled",true).prop("checked",false);
			$("#selectedjson div[id='"+$(this).val()+"']").remove();
		});
	}else{
		$("#selectedjson div[id='"+$(obj).val()+"']").remove();
		$(obj).parent().find("ul input[type='checkbox'][id='treegroupid']").prop("disabled",false)
	}
}

//]]>
</script>
<div id="wrapper1" >
<input type="hidden" id="container" />
	<div id="selectedjson" style="display:none;">${selectedjson?if_exists}</div>
	<div <#if nobutton??>style="display:none;"</#if>>
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
	</div>
	<div>
	<ul class="simpleTree">
	<li class="root" ><span><@spring.message "group" /></span>
		<ul>
			<#list groupList as group>
			<li id="${group.uuid}" ><input onchange="checkboxchanged(this)" style="vertical-align: middle;" type="checkbox" value="${group.uuid}" id="treegroupid" name="treegroupid" /><span style="vertical-align: middle;" >${group.name?html}</span><span id="${group.uuid}" style="display:none;">{"uuid":"${group.uuid?html}","id":"${group.code?html}","name":"${group.name?html}","type":"g"}</span>
					<ul class="ajax">
						<li>{url:<@spring.url "/group/simpletree/publicgajax.nsf?uuid="+group.uuid/>}</li>
					</ul>
			</li>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<div id="treefooter">
	<div <#if nobutton??>style="display:none;"</#if>>
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
	</div>
</div>
</div>