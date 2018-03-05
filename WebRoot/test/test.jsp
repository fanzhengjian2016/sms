<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>长兴</title>
  </head>
  <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="js/jquery.json-2.4.min.js"></script>

<body style="margin:0px;overflow:hidden;">
		<div class="call-div-hw">
			<table class="call-div-hw" cellpadding="0" cellspacing="0">
			${list}
			</table>
		</div>
	</body>
</html>