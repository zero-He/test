<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.onlygrowth.name"/> - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<form id="searchForm" class="m-search-box">
				<div class ="f-fl">
					<input type="hidden" id="studentId" name="studentId" value="${studentId }" />
					
					<label class="title ">学生：${studentName}</label>
					<c:if test="${!empty subjectId}">
						<label class="title">
							学科：${subjectName }
							<input type="hidden" id="jSubjectId" name="subjectId" value = "${subjectId }">
						</label>
					</c:if>
					<c:if test="${currentRoleId == 101 && not empty subjects }">
						<select class="u-select u-select-nm" id="jSubjectId" name="subjectId">
						<c:forEach items="${subjects }" var = "subject">
							<option value="${subject.subjectId }">${subject.subjectName }</option>
						</c:forEach>
						</select>
					</c:if>
				</div>
				<div class ="f-fr">
					<label class="title">
						<locale:message code="diag.common.search.time"/>：${startDate } ~ ${finishDate }
					</label>
					<input type="hidden" name="startTime"  value="${startDate }" />
					<input type="hidden" name="endTime" value="${finishDate }" />
				</div>
			</form>
			<div class="u-chart">
				<div id="selfStatistical" style="height: 500px; margin: 20px"></div>
			</div>
		</div>
	</div>
	<leke:pref />
	<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
	<script type="text/javascript">
		seajs.use('diag/student/selfStatistical');
	</script>
</body>
</html>