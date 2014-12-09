<#import "/spring.ftl" as spring />

<#if groupTreeRoot?? >

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
			  var b=document.getElementById("groupuuids").value;
			  var groupuuids = b.split(",");
			  for (i=0;i<groupuuids.length;i++){
			  if("a"+groupuuids[i]+"a"!="aa"){
			  	var c = document.getElementById(groupuuids[i]);
			  	
			  	if(c==null||c==undefined){
			  		valueStr+=groupuuids[i]+',';
			  	}else{
			  		var groups = c.childNodes[0];
			  		if(groups==null||groups==undefined){
			  			valueStr+=groupuuids[i]+',';
			  		}
			  	}
			  }
			  }
              for   (i=0;i<a.length;i++)  {  
              if   (a[i].checked&&(valueStr.indexOf(a[i].value)==-1)) {
                   valueStr+=a[i].value+',';   
                }  
                }
               }
               if(valueStr.length>0){
                   window.returnValue=valueStr;
                   window.close();   
                }else{
                 alert("你还没选择吧！");
                } 
  	});
  });
//]]>
</script>

</#if>

<#if groupTreeRoot?? >
	<div>
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
	<ul class="simpleTree">
	<li class="root" id="${groupTreeRoot.uuid}" ><span><@spring.message "group" /></span>
		<ul>
		 <#list groupChildren as group>
		 <#if mygroupuuid!=group.uuid>
		  <#if isCqGroup??>
			<li id="${group.uuid}" ><input style="vertical-align: middle;" type="${type}" value="${group.uuid}" <#if groupuuids?contains(group.uuid)>  checked </#if> id="pa${group.uuid}" name="treedeptid" /><span style="vertical-align: middle;" >${group.name?html}</span>
				<#if group.hasChildren>
					<ul class="ajax">
						<li>{url:<@spring.url "/group/simpletree/publicajax.nsf?id="+group.uuid+"&type="+type+"&checkedPa=${autoChecked}&groupuuids=${groupuuids}&mygroupuuid=${mygroupuuid}&flag=${flag}&adminGroups=${adminGroups?if_exists}"/>}</li>
					</ul>
				</#if>
			</li>
	<#elseif (isSuper??&&isSuper)>
		    <li id="${group.uuid}" ><input style="vertical-align: middle;" type="${type}" value="${group.uuid}" <#if groupuuids?contains(group.uuid)>  checked </#if> id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${group.name?html}</span>
				<#if group.hasChildren>
					<ul class="ajax">
						<li>{url:<@spring.url "/group/simpletree/publicajax.nsf?id="+group.uuid+"&type="+type+"&groupuuids=${groupuuids}&mygroupuuid=${mygroupuuid}&flag=${flag}&adminGroups=${adminGroups?if_exists}"/>}</li>
					</ul>
				</#if>
			</li>
		  <#else>
		    <li id="${group.uuid}" ><input style="vertical-align: middle;" <#if adminGroups??&&!adminGroups?contains(group.uuid)>disabled</#if> type="${type}" value="${group.uuid}" <#if groupuuids?contains(group.uuid)>  checked </#if> id="treedeptid" name="treedeptid" /><span style="vertical-align: middle;" >${group.name?html}</span>
				<#if group.hasChildren>
					<ul class="ajax">
						<li>{url:<@spring.url "/group/simpletree/publicajax.nsf?id="+group.uuid+"&type="+type+"&groupuuids=${groupuuids}&mygroupuuid=${mygroupuuid}&flag=${flag}&adminGroups=${adminGroups?if_exists}"/>}</li>
					</ul>
				</#if>
			</li>
		  </#if>
			</#if>
			</#list>
		</ul>
	</li>
	</ul>
	</div>	
<#else>
	<@spring.message "group.notfound" />
</#if>
<div id="treefooter">
<input type="button" class="publicbutton" value="确定"/> &nbsp;<input type="button" id="publicclose" value="取消" onclick="window.close()"/>   
<input type="hidden" id="groupuuids" value="${groupuuids}">
<div>
