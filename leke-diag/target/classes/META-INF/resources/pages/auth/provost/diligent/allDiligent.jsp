<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>作业勤奋报告 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div>
			<ul id="jULid" class="m-tab">
				<li onclick="location.href='teaDiligent.htm'"><a>选修班级</a></li>
				<li class="active"><a>行政班级</a></li>
			</ul>
		</div>
		<form id="searchForm" class="m-search-box">
			<input type="hidden" id="jClassType" name="classType" value="1" />
			<label class="title">年级学科：</label>
			<select id="jGradeSubject" class="u-select u-select-nm"></select>
			<input type="hidden" id="jGradeId" name="gradeId"/>
			<input type="hidden" id="jSubjectId" name="subjectId"/>
			<label class="title">时间：</label>
			<input id="jStartTime" name="startTime" class="u-ipt u-ipt-sm Wdate"/> —
			<input id="jEndTime" name="endTime" class="u-ipt u-ipt-sm Wdate"/>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
		</form>
		
		<div class="u-chart">
			<div>
				<div class="f-fl" style="width: 45%; height: 250px; margin: 20px;" id="gradeDiligent"></div>
				<div class="f-fl" style="width: 45%; height: 250px; margin: 20px;" id="clsDiligent"></div>
				<div class="f-cb"></div>
			</div>
			<div id="allDiligent" style="height: 330px; margin: 20px"></div>
		</div>
	</div>
</div>
<leke:pref />
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/allDiligent');
</script>

</body>
</html>