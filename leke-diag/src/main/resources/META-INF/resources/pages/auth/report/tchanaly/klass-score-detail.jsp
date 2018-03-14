<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${userName}的分析报告<c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<meta name="viewport" content="width=1280">
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/diag/analyse.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div id="container"></div>
		</div>
	</div>
	<script type="text/javascript" src="${assets}/scripts/common/ui/ui-echarts/echarts-all-current.js"></script>
	<script>
		var Csts = ${Csts};
		seajs.use('diag/report/stuanaly/person-score');
	</script>
</body>
</html>