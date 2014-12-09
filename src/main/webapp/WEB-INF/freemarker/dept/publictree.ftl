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
	});
  $(function(){
     $(".publicbutton").click(function(){
  		   var   a   =   document.getElementsByName("treedeptid");  
			  if(a!=null){
			  var  j=0  ;
			  var valueStr="";
			  var b=document.getElementById("deptuuids").value;
			  var deptuuids = b.split(",");
			  for (i=0;i<deptuuids.length;i++){
			  if("a"+deptuuids[i]+"a"!="aa"){
			  	var c = document.getElementById(deptuuids[i]);
			  	
			  	if(c==null||c==undefined){
			  		valueStr+=deptuuids[i]+',';
			  	}else{
			  		var groups = c.childNodes[0];
			  		if(groups==null||groups==undefined){
			  			valueStr+=deptuuids[i]+',';
			  		}
			  	}
			  }
			  }
			  
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked) {
                   j++;
                   valueStr+=a[i].value+',';   
                }  
                }
               }
               //if(valueStr.split(",").length>2){
               //alert("所在部门不能超过一个！")
               //return false;
               //}
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
  $(function(){
     $("#publicclose").click(function(){
  		 window.close();   
  	});
  });
//]]>
</script>

</#if>

<#if deptTreeRoot?? >
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
	<div>
	<ul class="simpleTree">
	<li class="root" id="${deptTreeRoot.uuid}" ><span><@spring.message "department" /></span>
		<ul>
			<#list deptChildren as dept>
			<#if mydeptuuid!=dept.uuid>
			<li id="${dept.uuid}" ><input style="vertical-align: middle;" type="${type}" value="${dept.uuid}" <#if deptuuids?contains(dept.uuid)>  checked </#if> id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${dept.name?html}</span>
				<#if dept.hasChildren>
					<ul class="ajax">
						<li>{url:<@spring.url "/dept/simpletree/publicajax.nsf?id="+dept.uuid+"&type="+type+"&deptuuids=${deptuuids}&flag=${flag}&mydeptuuid=${mydeptuuid}"/>}</li>
					</ul>
				</#if>
			</li>
			</#if>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<#else>
	<@spring.message "department.notfound" />
</#if>
<div id="treefooter">
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
<input type="hidden" id="deptuuids" value="<#if type!='radio'>${deptuuids}</#if>">
<div>
