<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.subanalysis.name"/> - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div id="jClassTypeCon"></div>
		<form id="searchForm" class="m-search-box">
			<label class="title"><locale:message code="diag.common.search.subject"/>：</label>
			<select id="jStageSubject" class="u-select u-select-nm"></select>
			<input type="hidden" id="jSubjectId" name="subjectId">
			<label class="title"><locale:message code="diag.common.search.class"/>：</label>
			<input type="hidden" id="jClassType" name="classType" value="2">
			<select id="jClassId" name="classId" class="u-select u-select-nm"></select>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="<locale:message code="diag.common.search.query"/>">
		</form>
		
		<div class="u-chart">
			<div id="subAnalysis" style="height: 400px; margin: 20px"></div>
		</div>
		
		<div id="subAnalysisList" class="m-table">
			<table>
				<thead>
					<tr>
						<th class="width_70"><locale:message code="diag.table.column.number"/></th>
						<th><locale:message code="diag.table.column.studentname"/></th>
						<th class="width_120"><locale:message code="diag.table.column.subjectrank"/></th>
						<th class="width_120"><locale:message code="diag.table.column.avgscore"/></th>
					</tr>
				</thead>
				<tbody id="subAnalysisListBody"></tbody>
			</table>
		</div>
	</div>
</div>
<leke:pref />
<script id="subAnalysisList_tpl" type="x-mustache">
{{#dataList}}
<tr>
	<td>{{index}}</td>
	<td>{{studentName}}</td>
	<td><font class="f-fwb">{{#scoreLevel}}{{scoreLevel}}{{/scoreLevel}}{{^scoreLevel}} -- {{/scoreLevel}}</font></td>
	<td><font class="f-fwb">{{avgScoreView}}</font></td>
</tr>
{{/dataList}}
</script>

<script id="jClassListTpl" type="x-mustache">
<c:forEach var="clazz" items="${clazzList }">
	<option value="${clazz.classId }">${clazz.className }</option>
</c:forEach>
</script>
<script id="jCourseListTpl" type="x-mustache">
<c:forEach var="virtual" items="${virtualClazzList }">
	<option value="${virtual.classId }">${virtual.className }</option>
</c:forEach>
</script>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/teacher/subAnalysis');
</script>

</body>
</html>