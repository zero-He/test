<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${userNamePrefix}作业分析报告<c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<meta name="viewport" content="width=1280">
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/diag/analyse.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<c:if test="${not empty phases}">
			<div class="m-tab">
			<ul>
				<c:forEach var="phase" items="${phases}">
					<c:if test="${phase.selected == true}">
						<li class="active"><a>${phase.usePhaseName}</a></li>
					</c:if>
					<c:if test="${phase.selected == false}">
						<li><a href="${phase.homeworkId}-${phase.homeworkDtlId}.htm">${phase.usePhaseName}</a></li>
					</c:if>
				</c:forEach>
			</ul>
			</div>
			</c:if>
			<div id="container"></div>
		</div>
	</div>
	<script type="text/javascript" src="${assets}/scripts/common/ui/ui-echarts/echarts-all-current.js"></script>
	<script>
		var ReportCst = ${ReportCst};
		seajs.use('diag/report/homework/person');
	</script>
</body>
</html>