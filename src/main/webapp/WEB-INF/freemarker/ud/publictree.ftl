<#import "/spring.ftl" as spring />

<#if deptTreeRoot?? >

<#include "/style/jquery.simpletree.ftl" >

<#include "/script/jquery.simpletree.ftl" >

<script type="text/javascript">
//<![CDATA[
	
//tree
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
		if(window.opener) {
			window.opener.returnValue = valueStr;  
	    } else {  
			window.returnValue=valueStr;  
		} 
		window.close();		
	});
  });



function checkboxchanged(obj){
		var check=$(obj).prop('checked');
			
		if(check){
		var spanv=$(obj).parent().find("span[id="+$(obj).val()+"]").text();
			$("#selectedjson").append("<div id='"+$(obj).val()+"'>"+spanv);
			
			$(obj).parent().find("ul input[type='checkbox'][id='treedeptid']").each(function(){
				$(this).prop("disabled",true).prop("checked",false);
				$("#selectedjson div[id='"+$(this).val()+"']").remove();
			});
			
		}else{
			$("#selectedjson div[id='"+$(obj).val()+"']").remove();
			$(obj).parent().find("ul input[type='checkbox'][id='treedeptid']").prop("disabled",false)
		}
		
		//$(obj).parent().find("ul input[type='checkbox'][id='treedeptid']").prop("disabled",check).prop("checked",false);
	}

//]]>
</script>

</#if>
<input type="hidden" id="container" />
	<div id="selectedjson" style="display:none;">${selectedjson}</div>
	<div <#if nobutton??>style="display:none;"</#if>>
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
	</div>
	<div>
	<ul class="simpleTree">
	<li class="root" id="${deptTreeRoot.uuid}" ><span><@spring.message "department" /></span>
		<ul>
			<#list deptChildren as dept>
			<li id="${dept.uuid}" ><input onchange="checkboxchanged(this)" style="vertical-align: middle;" type="${type}" value="${dept.uuid}" id="treedeptid" name="treedeptid" <#if selecteduuids?seq_contains(dept.uuid)>  checked </#if> /><span style="vertical-align: middle;" >${dept.name?html}</span><span id="${dept.uuid}" style="display:none;">{"uuid":"${dept.uuid?html}","id":"${dept.deptCode?html}","name":"${dept.name?html}","type":"d"}</span>
					<ul class="ajax">
						<li>{url:<@spring.url "/ud/simpletree/publicajax.nsf?id="+dept.uuid+"&type="+type+"&changetype=${changetype}&selected=${selected}"/>}</li>
					</ul>
			</li>
			</#list>
			<#list members as user>
			<li id="${user.uuid}" ><input onchange="checkboxchanged(this)" style="vertical-align: middle;" type="${type}" value="${user.uuid}" id="treedeptid" name="treedeptid" <#if (selecteduuids?seq_contains(user.uuid))>  checked </#if>/><span style="vertical-align: middle;" >${user.name?html}</span><span id="${user.uuid}" style="display:none;">{"uuid":"${user.uuid?html}","id":"${user.id?html}","name":"${user.name?html}","type":"u"}</span>
			</li>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<input type="hidden" name="groupuuids" value="${groupuuids?if_exists}"/>
<div id="treefooter">
	<div <#if nobutton??>style="display:none;"</#if>>
		<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
	</div>
</div>