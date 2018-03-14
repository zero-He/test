<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>作业完成态度详情</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/mobile/analyse.css">
</head>
<body>
<section class="c-top-title">${reportCycle.label} -- ${clazz.className}</section>
<section class="c-detail">
	<div class="ana-module">
		<div id="app" class="c-table-bg-green"></div>
	</div>
</section>
<script>var Csts = ${Csts};</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js?_t=${_t}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/diag/report/tchanaly/klass-behavior-work.bundle.js?t=${_t}"></script>
</body>
</html>
