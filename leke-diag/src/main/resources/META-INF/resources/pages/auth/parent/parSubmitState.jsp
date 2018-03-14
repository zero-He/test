<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<ul id="jStudentList" class="m-tab">
					<c:forEach var="student" items="${studentList }" varStatus="s">
						<li data-stuid="${student.id}" class="${student.id == studentId ? 'active' : ''}"><a>${student.userName}</a></li>
					</c:forEach>
				</ul>
			</div>
			<form id="searchForm" class="m-search-box">
				<input type="hidden" id="studentId" name="studentId" value="${studentId }" />
				<label class="title">学科：</label>
				<select id="jStageSubject" class="u-select u-select-nm"></select>
				<input type="hidden" id="jSubjectId" name="subjectId">
				<label class="title">时间：</label>
				<input id="startTime" name="startTime" class="u-ipt u-ipt-nm Wdate" />
				—
				<input id="endTime" name="endTime" class="u-ipt u-ipt-nm Wdate" />
				<input id="s-search" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
			</form>
			<div id="submitState" style="height: 500px;"></div>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th>作业名称</th>
							<th>提交状态</th>
							<th>提交时间</th>
							<th>截至时间</th>
						</tr>
					</thead>
					<tbody id="stuSubmitStateBody"></tbody>
				</table>
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
	<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
	<script type="text/javascript">
		seajs.use('diag/parents/stuSubmitState');
	</script>
</body>
</html>