<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>学生成绩统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<div class="f-fl">
				<label class="title">学科：${subjectName }</label>
				<label class="title">课程名称：${courseName }</label>
			</div>
			<div class="f-fr">
				<label class="title">时间：${startDate } ~  ${finishDate }</label>
				<input type="hidden" id="jStartDate" name="startTime" value="${startDate }" /> 
				<input type="hidden" id="jFinishDate" name="endTime" value="${finishDate }" />
				<input type="hidden" name="courseId" value="${courseId }" />
				<input type="hidden" name="subjectId" value="${subjectId }" />
			</div>
		</form>
		<div class="u-chart">
			<div id="achievementStatTeacher" style="height: 400px; margin: 20px"></div>
			<div class="f-ml40">注：每次作业满分不同，系统自动将每次作业按照满分100分的比例算出相应成绩。</div>
		</div>
			
	</div>
</div>
<leke:pref />
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/teacher/achievementDetails');
</script>
</body>
</html>