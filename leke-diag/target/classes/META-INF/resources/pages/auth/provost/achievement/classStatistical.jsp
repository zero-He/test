<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>班级成绩统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<label class="title">年级学科：</label>
			<select id="jGradeSubject" class="u-select u-select-nm"></select>
			<input type="hidden" id="jGradeId" name="gradeId"/>
			<input type="hidden" id="jSubjectId" name="subjectId"/>
			<span id="jTimeDiv">
				<label class="title">时间：</label>
				<input id="jStartTime" name="startTime" class="u-ipt u-ipt-sm Wdate"/> —
				<input id="jEndTime" name="endTime" class="u-ipt u-ipt-sm Wdate"/>
			</span>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
		</form>
		
		<div class="u-chart">
			<div id="gradeScoreStat" style="height: 400px; margin: 20px"></div>
			<div id="jAchievementMes" class="f-ml40">注：每次作业满分不同，系统自动将每次作业按照满分100分的比例算出相应成绩。</div>
		</div>
	</div>
</div>
<leke:pref />
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/classStatistical');
</script>
</body>
</html>