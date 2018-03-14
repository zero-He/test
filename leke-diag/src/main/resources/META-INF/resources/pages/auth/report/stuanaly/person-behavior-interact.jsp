<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>课堂行为明细<c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<meta name="viewport" content="width=1280">
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/diag/analyse.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<h3 class="f-mb10">${reportCycle.label}课堂行为明细</h3>
			<form id="jTableForm">
				<input type="hidden" name="cycleId" value="${reportCycle.id}" />
			</form>
			<div class="m-table m-table-center">
				<table>
					<thead>
						<tr>
							<th>科目</th>
							<th>课堂名称</th>
							<th>时间</th>
							<th>老师</th>
							<th>金榜题名</th>
							<th>点到</th>
							<th>举手</th>
							<th>被授权</th>
							<th>课堂笔记</th>
							<th>随堂作业</th>
							<th>分组讨论</th>
							<th>快速问答</th>
							<th>献花</th>
							<th>评价</th>
						</tr>
					</thead>
					<tbody id="jTableBody"></tbody>
				</table>
				<div id="jTablePage"></div>
			</div>
		</div>
	</div>
	<script>
		seajs.use('diag/report/stuanaly/person-behavior-interact');
	</script>
	<script id="jTemplate" type="text/mustache">
	{{#.}}
		<tr>
			<td>{{subjectName}}</td>
			<td>{{courseSingleName}}</td>
			<td>{{fmt start 'yyyy-MM-dd HH:mm'}} - {{fmt end 'HH:mm'}}</td>
			<td>{{teacherName}}</td>
			<td>{{map student.rank '{"1":"状元","2":"榜眼","3":"探花"}' '--'}}</td>
			<td>{{student.called}}/{{callNum}}</td>
			<td>{{student.raised}}</td>
			<td>{{student.authed}}</td>
			<td>{{student.noteNum}}</td>
			<td>{{student.examNum}}/{{examNum}}</td>
			<td>{{student.discuNum}}/{{discuNum}}</td>
			<td>{{student.quickNum}}/{{quickNum}}</td>
			<td>{{student.flower}}</td>
			<td>{{#cif 'this.student.isEval'}}有{{else}}无{{/cif}}</td>
		</tr>
	{{/.}}
	</script>
</body>
</html>