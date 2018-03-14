<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>作业分析报告</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/mobile/analyse.css">
</head>
<body>
	<c:if test="${not empty phases}">
	<div class="c-nav">
	<ul>
		<c:forEach var="phase" items="${phases}">
			<c:if test="${phase.selected == true}">
				<li class="cur">${phase.usePhaseName}</li>
			</c:if>
			<c:if test="${phase.selected == false}">
				<li><a href="${phase.homeworkId}.htm">${phase.usePhaseName}</a></li>
			</c:if>
		</c:forEach>
	</ul>
	</div>
	</c:if>
	<div id="app"></div>
	<script>var ReportCst = ${ReportCst};</script>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react.min.js"></script>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react-dom.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/m/diag/lib/echarts.bundle.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/m/diag/report/homework-overall.bundle.js"></script>
</body>
</html>