<#import "/spring.ftl" as spring />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>应用管理员列表</title>
</head>
	<frameset  frameborder="YES" border="1" framespacing="0">
	  <frameset rows="*" cols="20%,*" frameborder="1" >
	    <frame src="<@spring.url "/app/apptree.nsf"/>" name="lu" id="lu">
	    <frame src="<@spring.url "/app/applist.nsf"/>" name="eu" id="eu">
	  </frameset>
	</frameset>
<body>
</body>
</html>