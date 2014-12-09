<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	与tam集成取header头的例子
	<br> ===============================
	<p>


		<%
			out.println("public=" + request.getAttribute("public"));

			for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();) {
				String aa = e.nextElement().toString();
				out.println(aa + " == " + request.getHeader(aa).trim());
			}

		%>
	
	<p>
		<%
			for (Enumeration b = request.getAttributeNames(); b.hasMoreElements();) {
				String bb = b.nextElement().toString();
				out.println(bb + " == " + request.getAttribute(bb));
			}
		%>

	<p>

		session message++++++++++++<br> ===============================
	<p>

		<%
			out.print("<br>" + "session is new:" + session.isNew());

			Date created = new Date(session.getCreationTime());
			//得到session对象创建的时间
			Date accessed = new Date(session.getLastAccessedTime());
			//得到最后访问该session对象的时间
			out.println("<br>" + "ID " + session.getId() + " ");
			//得到该session的id，并打印
			out.println("<br>" + "Created: " + created + " ");
			//打印session创建时间
			out.println("<br>" + "Last Accessed: " + accessed + " ");
			//打印最后访问时间

			session.setAttribute("Name", "Tom");
			//在session中添加变量Name=Tom
			session.setAttribute("UID", "12345678");
			//在session中添加变量UID=12345678

			Enumeration e = session.getAttributeNames();
			//得到session中变量名的枚举对象
			while (e.hasMoreElements()) { //遍历每一个变量
				String name = (String) e.nextElement(); //首先得到名字
				String value = session.getAttribute(name).toString();
				//由名字从session中得到值
				out.println("<br>" + name + " = " + value + " "); //打印
			}
		%>
	
</body>
</html>