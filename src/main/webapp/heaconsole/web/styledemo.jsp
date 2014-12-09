<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/modules/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>index/final_display.jsp</title>
    <link id="themePath" rel="Stylesheet" href="${ctx}/heaconsole/css/subindex.css" />
  </head>
  <body>
  	<fieldset>
  	<legend>查询条件</legend>
	<table class="tab_3">
		<tr>
			<td class="td_1" >报表名称：</td>
			<td><input class="ipt_2"/></td>
          	<td class="td_1">主题：</td>
			<td><input class="ipt_2"/></td>
        	<td class="td_1">日期：</td>
			<td><input class="ipt_2"/></td>
		</tr>
        <tr>
       	  <td class="td_1">所属公司：</td>
            <td>
			<select class="ipt_2">
			  <option>深圳海联讯北京技术研发中心</option>
			  <option>海联讯广州分公司</option>
			  <option>华北电网</option>
			  <option>北京供电局</option>
			</select>		  </td>
            <td class="td_1">报表类型：</td>
            <td>
			<select class="ipt_2">
			  <option>全部</option>
			  </select>			</td>
            <td class="td_1">报表周期：</td>
            <td>
			<select class="ipt_2">
			  <option>月报</option>
			</select>			</td>
        </tr>
		<tr>
        <td class="td_1">一级大类：</td>
            <td>
			<select class="ipt_2">
			  <option>全部</option>
			  </select>			</td>
            <td class="td_1">二级大类：</td>
            <td>
			<select class="ipt_2">
			  <option>全部</option>
			</select>			</td>
           	<td colspan="2">
		 		<div class="td_2" >
					<input class="btn"  type="button" value="查询"/>
				</div>
          </td>		
        </tr>
	</table>
  </fieldset>
	  <div class="line"></div> 
  		<div class="title">信息列表示例</div>
		<div class="infor">我是一些文字，和title一样</div>
		<div class="r"><input type="submit" name="button3" class="btn" id="button3" value="添加" onclick="javascript:window.location.href ='FormDemo.jsp'"/>
		<input type="submit" name="button4" class="btn" id="button4" value="删除"  onclick="javascript:window.location.href ='FormDemo2.jsp'"/></div>
		<div class="tag_container">
			  <div class="tagCurrent">河南</div>
			  <div class="tagCurrent_right"></div><!--当前页签的右边框-->
			  <div class="tag">洛阳</div>
			  <div class="tag_right"></div><!--非当前页签的右边框-->
			  <div class="tag">开封</div>
			  <div class="tag_right"></div><!--非当前页签的右边框-->
			  <div class="tag">南阳</div>
			  <div class="tag_right"></div><!--非当前页签的右边框-->
		  </div> 
  		
		<table class="tab" >
			<tr class="tab_title">
			  <td ><input type="checkbox" /></td>
			  <td>资源链接名称</td>
			  <td>类型</td>
			  <td>状态</td>
			  <td>层级数</td>
			  <td>描述信息</td>
			  <td>操作项</td>
 			</tr>
			<tr class="tab_trcurrent_1" onmouseover="this.className='over'" onmouseout="this.className='tab_trcurrent_1'">
				<td><input type="checkbox"/></td>
				<td>下载同业对标评价</td>
				<td class="red">未知</td>
				<td>启用</td>
				<td>145,691,109</td>
				<td >新浪体育频道今日焦点：国新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯新浪体育频道今日焦点：国王杯</td>
				<td>
					<a class="edit" href="#">[编辑]</a>
					<a class="delete" href="#">[删除]</a>						
				</td>
	 		</tr>
		 	<tr class="tab_trcurrent" onmouseover="this.className='over'" onmouseout="this.className='tab_trcurrent'">
				<td><input type="checkbox"  /></td>
				<td>管理对标</td>
				<td class="red">未知</td>
				<td class="green">已归档</td>
				<td>1,256,412</td>
				<td>人人网</td>
				<td>
					<a class="edit" href="#">[编辑]</a>
					<a class="delete" href="#">[删除]</a>						
				</td>
		 	</tr>
		 	<tr class="tab_trcurrent_1"  onmouseover="this.className='over'" onmouseout="this.className='tab_trcurrent_1'">
				<td><input type="checkbox"  /></td>
				<td>电网建设</td>
				<td >未知</td>
				<td class="blue">已校验</td>
				<td>1,564</td>
				<td>网易财经频道</td>
				<td>
					<a class="edit" href="#">[编辑]</a>
					<a class="delete" href="#">[删除]</a>						
				</td>
		 	</tr>
		 	<tr class="tab_trcurrent"  onmouseover="this.className='over'" onmouseout="this.className='tab_trcurrent'">
				<td><input type="checkbox"  /></td>
				<td>人力资源</td>
				<td >未知</td>
				<td class="red">归档退回</td>
				<td>27,854</td>
				<td>淘宝客服提醒：如果在该期限内淘宝客服提醒</td>
				<td>
					<a class="edit" href="#">[编辑]</a>
					<a class="delete" href="#">[删除]</a>						
				</td>
			</tr>
		 	<tr class="tab_trcurrent">
				<td colspan="7">
		                    	<div class="changepage1">
					共20页，当前第2页 
					<a  href="#">首页</a>
					<a class="prepage" href="#">上一页</a> 
					<a class="nextpage" href="#">下一页</a>          
					 <a   href="#">尾页</a>
					 每页 <input type="text"  class="no" id="no" value="10"  /> 条 
					
					 跳转 <input type="text" name="no" class="no" id="no" value="10"  />页
		            				<input type="submit" name="button" class="btn" id="button" value="确定" />
		            			</div>
				</td>
			
		 	</tr>
		 	<tr class="tab_trcurrent">
						<td colspan="7">
                        	<div class="changepage1">
							共20页，当前第2页 
							<a  href="#">首页</a>
							<a class="pre_1" href="#">上一页</a> 
							<a class="next_1" href="#">下一页</a>          
							 <a   href="#">尾页</a>
							 每页 <input type="text"  class="no" id="no" value="10"  /> 条 
							
							 跳转 <input type="text" name="no" class="no" id="no" value="10"  />页
                				<input type="submit" name="button" class="btn" id="button" value="确定" />
                			</div>
						</td>
						
			 	 	</tr>
		</table>
  		<div class="note">备注:未生成,<span class="blue">已生成,已生成(编辑),已校验,</span><span class="red">归档退回,</span><span class="green">已归档</span></div>	
 
		 <div class="title">表单元素示意</div>
 
	  	<table class="tab_2">
			<tr>
				<td class="td_1">主题名称：</td>
				<td><input class="ipt"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">主题：</td>
				<td><input class="ipt"/></td>
	            <td class="star">*</td>
			</tr>
			<tr>
				<td class="td_1">主题路径：</td>
				<td><input class="ipt"/></td>
			</tr>
			<tr>
				<td class="td_1">所属区域：</td>
				<td><input type="radio" name="area"/>北京<input name="area" type="radio"/>上海</td>
			</tr>
			<tr>
				<td class="td_1">文件浏览：</td>
				<td><input type="file" name="area" class="openfile"/></td>
	            <td class="star">*</font></td>
			</tr>
			<tr>
				<td class="td_1">所属公司：</td>
				<td>
				<select class="ipt">
				  <option>深圳海联讯北京技术研发中心</option>
				  <option>海联讯广州分公司</option>
				  <option>华北电网</option>
				  <option>北京供电局</option>
				</select>
				</td>
			</tr>
			<tr>
				<td class="td_1">描述：</td>
				<td><textarea rows="5" class="ipt" ></textarea></td>
			</tr>
			<tr>
				<td  colspan="3"  class="td_2" >
					<input class="btn"  type="button" value="确定"/>
					<input class="btn"  type="button" value="取消"/>
				</td>
			</tr>
		</table>
</body>
</html>
