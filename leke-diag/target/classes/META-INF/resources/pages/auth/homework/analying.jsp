<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.homework.analysis.name" /><c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/diag/analyse.css?t=20171115">
</head>
<body>
	<div class="z-load">
		<div class="hd-bg">
			<div class="hd"></div>
		</div>
		<div class="con-bg">
			<h2>题型得分率分析正在生成中</h2>
			<img src="${assets}/images/common/loading.gif" height="37" width="37" alt="">
		</div>
	</div>

	<script type="text/javascript">
		var homeworkId = '${homework.homeworkId}';
		seajs.use('diag/homework/analying');
	</script>

</body>
</html>