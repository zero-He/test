<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>学科优劣分析 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" >
			<input type="hidden" name="studentId" value="${studentId }" />
			<input type="hidden" name="startDate" value ="${startDate }" />
		</form> 
		<div class="f-fl">
			<label class ="title">班级：${className } &nbsp;</label>
			<label class ="title">学生：${studentName }</label>
		</div>
		<div class="f-fr">
			<label class ="title">时间：${startDate } ~ ${finishDate} </label>
		</div>
		<div class="u-chart">
			<div id="stuAnalysisBar" style="height: 330px; margin: 20px"></div>
		</div>
	</div>
</div>

<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/classTeacher/stuSubjectAnalysis');
</script>

</body>
</html>