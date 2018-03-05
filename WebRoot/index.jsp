<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <c:set var="path" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>长兴支付宝接口调试</title>
  </head>
  <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="js/jquery.json-2.4.min.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$("#query_btn").click(function(){
  			var url="system/test.action?method=query";
  			$("#returnValue").val("");
  			$.post(url,$("#query_Info").serialize(), function(result){
  				$("#returnValue").val($.toJSON(result));
 			});
  		});
  	
  	});
  	
  </script>
  <body>
   <a href="system/test.action?method=testSelect">首页</a>
  <form action="" method="post" id="query_Info">
    <table width="50%" height="500px" style="border: 1px solid #15A0F5;" align="center" >
    <tr>
    <td colspan="6" height="5px;"  style="text-align: left"><input type="radio" name="select" checked="checked" value="1"/>欠费查询</td>
    </tr>
    <tr height="20px;">
    <td style="text-align:right;">查询类别</td>
    <td><select name="queryType" style="width:94%">
    	<option value="0">用户编号</option>
    	<option value="2">身份证</option>
    	<option value="1">手机号码</option>
    	<option value="3">原户号</option>
    </select></td>
    <td style="text-align:right;">查询条件</td>
    <td><input type="text" name="queryValue"/></td>
    <td style="text-align: right;">查询日期</td>
    <td><input type="text" name="queryTime"  /></td>
    </tr>
    <tr>
    <td colspan="6" height="5px;" style="text-align:left"><input type="radio" name="select" value="2"/>缴费</td>
    </tr>
    <tr height="20px;">
    	<td style="text-align: right;">交易金额</td>
    	<td><input type="text" name="rcvAmt" /></td>
    	<td style="text-align: right;">用户户号</td>
    	<td><input type="text" name="consNo"  /></td>
    	<td style="text-align: right;">应收年月</td>
    	<td><input type="text" name="rcvblYm" /> </td>
    </tr>
    <tr height="20px;">
    <td style="text-align: right;">应收标识</td>
    	<td><input type="text" name="rcvblAmtId" /></td>
    </tr>
    
    <tr>
    <td colspan="6" height="5px;" style="text-align:left"><input type="radio" name="select" value="3"/>对账</td>
    </tr>
    <tr height="20px;">
    	<td style="text-align:right;">文件类型</td>
    	<td>
    	<select name="fileType" style="width: 94%">
    		<option value="DSDZ">代收对账文本</option>
    		<option value="REDK">代扣扣款反馈文</option>
    	</select>
    	</td>
    	<td style="text-align:right;"></td>
    	<td></td>
    	<td style="text-align:right;"></td>
    	<td></td>
    </tr>
    
    <tr>
    <td colspan="6" style="height:30px;">
    <input type="button" id="query_btn" value="调          试" style="width: 100%;">
    </td>
    </tr>
    <tr>
    <td colspan="6" style="text-align:center;height:5px;">
    	返回结果
    </td>
    </tr>
    
    <tr height="200px">
    <td colspan="6">
    <textarea rows="20" cols="100" id="returnValue" style="height:100%"></textarea>
    </td>
    </tr>
    </table>
    </form>
  </body>
</html>
