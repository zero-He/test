<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>考勤表<c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<meta name="viewport" content="width=1280">
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
<div class="g-mn">
<div>
	<div class="title">${reportCycle.label} -- ${clazz.className} -- 考勤表</div>
	<div class="m-table m-table-center" id="app"></div>
</div>
</div>
</div>
<script>var Csts = {datas: ${datas}};</script>
<script>seajs.use('diag/report/tchanaly/klass-behavior-attend');</script>
</body>
</html>