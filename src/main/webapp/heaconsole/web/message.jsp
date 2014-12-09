<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
    <title>index/final_display.jsp</title>
	<link href="css/subcss.css" rel="stylesheet" type="text/css" />
    <style>
.warningbox{ width:400px; height:200px; border:solid #DCEDF5 1px; margin:100px auto; }
.title1{  height:40px; padding:15px 0 0 20px; background-color:#3A91AF; font:25px "宋体"; font-weight:bold; color:#FFFFFF;  }
.content_2{ font:15px "宋体"; color:#B0AFAF; text-indent:2em; padding:55px 0 0 0; line-height:20px; }
.content_3{ float:left; width:120px; height:150px; background:url(heaconsole/images/warning.jpg) no-repeat center right;}
	</style>
  </head>
  <body>
  <div class="warningbox">
  	<div class="title1">系统提示  	</div>
    <div class="content_3">
    </div>
    <div class="content_2">${message }    </div>
  </div>
</body>
</html>
