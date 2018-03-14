<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>作业完成详情</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/mobile/analyse.css">
</head>
<body>
	<div id="app"></div>
	<script type="text/javascript">var ReportCst = ${ReportCst}</script>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react.min.js"></script>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react-dom.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/m/diag/lib/dropload.bundle.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/m/diag/report/stuanaly-behavior-homework.bundle.js"></script>
</body>
</html>
