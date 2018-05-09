<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'attrstyle.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	  li{
	    list-style-type:none;
	  }
	</style>
</head>
<body>
	<dl>
		<dt>关键属性</dt>
		<dd>
			<ul>
				<li>品牌<input name=""/></li>
				<li>款号<input name=""/></li>
				<li>颜色<input name=""/></li>
				<li>尺码<input name=""/></li>
			</ul>
		</dd>
	</dl>
	<dl>
		<dt>销售属性</dt>
		<dd>
			<ul>
				<li>上市时间<input name=""/></li>
				<li>功能<input name=""/></li>
				<li>适用季节<input name=""/></li>
				<li>适用对象<input name=""/></li>
				<li>是否商场同款<input name=""/></li>
			</ul>
		</dd>
	</dl>
	<dl>
		<dt>次要属性</dt>
		<dd>
			<ul>
				<li>罩杯款式<input name=""/></li>
				<li>罩杯厚度<input name=""/></li>
				<li>罩杯面料<input name=""/></li>
				<li>罩杯里料<input name=""/></li>
				<li>罩杯里料成分含量<input name=""/></li>
				<li>侧翼面料<input name=""/></li>
				<li>侧翼里料<input name=""/></li>
				<li>肩带样式<input name=""/></li>
				<li>搭扣排数<input name=""/></li>
				<li>插片<input name=""/></li>
				<li>有无钢圈<input name=""/></li>
				<li>文胸款式<input name=""/></li>
				<li>图案<input name=""/></li>
				<li>服装款式细节<input name=""/></li>
				<li>侧翼里料(含量)<input name=""/></li>
				<li>侧翼面料(含量)<input name=""/></li>
			</ul>
		</dd>
	</dl>

</body>
</html>
