<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.diligent.name"/> - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
		<div class="c-homework-report">
			<div class="m-search-box">
				<div class="title">
					<c:if test="${className != null }">${className } </c:if>
					<c:if test="${subjectName != null }">${subjectName } </c:if>
					${student.userName } 作业勤奋报告
				</div>
			</div>
			<div class="con">
				<form id="searchForm">
					<input type="hidden" id="studentId" name="studentId" value="${student.id }" />
					<input type ="hidden" name="endTime" value="${finishDate }" />
					<input type ="hidden" name="startTime" value="${startDate }" />
					<if test="${ subjectId == null && classId == null}">
						<input type ="hidden" name="subjectId" value="${subjectId }" />
						<input type ="hidden" name="classId" value="${classId }" />
					</if>
				</form>
				
				<div class="u-chart">
					<div id="submitState" style="height: 500px;"></div>
				</div>
				
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th style="width: 60%;"><locale:message code="diag.table.column.homeworkname"/></th>
								<th><locale:message code="diag.table.column.submitstate"/></th>
								<th><locale:message code="diag.table.column.subtime"/></th>
								<th><locale:message code="diag.table.column.endtime"/></th>
							</tr>
						</thead>
						<tbody id="stuSubmitStateBody"></tbody>
					</table>
				</div>
			</div>
		</div>
		</div>
	</div>
	<script id="stuSubmitState_tpl" type="x-mustache">
	<tr>
	<td>{{homeworkName }}</td>
	<td>{{submitStatus }}</td>
	<td>{{submitTime }}</td>
	<td>{{closeTime }}</td>
	</tr>
	</script>
	<leke:pref />
	<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
	<script type="text/javascript">
		seajs.use('diag/common/stuHomeworkInfo');
	</script>
</body>
</html>