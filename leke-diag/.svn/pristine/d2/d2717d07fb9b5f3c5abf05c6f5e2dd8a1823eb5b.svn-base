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
		<div>
			<ul id="jULid" class="m-tab">
				<li class="active" data-id="2"><a>选修班级</a></li>
				<li data-id="1"><a>行政班级</a></li>
			</ul>
		</div>
		<form id="searchForm" class="m-search-box">
			<input type="hidden" id="jClassType" name="classType" value="2" />
			<label class="title">年级学科：</label>
			<select id="jGradeSubject" class="u-select u-select-nm"></select>
			<input type="hidden" id="jGradeId" name="gradeId"/>
			<input type="hidden" id="jSubjectId" name="subjectId"/>
			<label class="title">班级：</label>
			<select id="jClassId" name="classId" class="u-select u-select-nm">
				<c:choose>
					<c:when test="${fn:length(classes) == 0}">
						<option value="0">没有对应班级</option>
					</c:when>
					<c:otherwise>
						<c:forEach var="clazz" items="${classes }">
							<option value="${clazz.classId }">${clazz.className }</option>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</select>
			<span id="jTimeDiv" style="display: none;">
				<label class="title">时间：</label>
				<input id="jStartTime" name="startTime" class="u-ipt u-ipt-sm Wdate"/> —
				<input id="jEndTime" name="endTime" class="u-ipt u-ipt-sm Wdate"/>
			</span>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
		</form>
		
		<div class="u-chart">
			<div id="achievementStat" style="height: 400px; margin: 20px"></div>
			<div class="f-ml40">注：每次作业满分不同，系统自动将每次作业按照满分100分的比例算出相应成绩。</div>
		</div>
	</div>
</div>
<leke:pref />
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/achievement');
</script>
</body>
</html>