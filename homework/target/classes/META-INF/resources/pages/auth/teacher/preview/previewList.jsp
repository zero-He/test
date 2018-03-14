<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="homework.preview.preview"/> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<form action="" method="post" id="formPage" autocomplete="off">
				<div class="m-search-box">
					<input type="hidden" id="jHomeworkType" name="homeworkType" value="1" />
					<label class="title"><locale:message code="homework.preview.title"/>：</label>
					<input type="text" name="homeworkName" class="u-ipt u-ipt-nm">
					<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="<locale:message code="homework.common.search"/>" id="bHomeworkList" />
				</div>
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th><locale:message code="homework.preview.preview"/></th>
								<th><locale:message code="homework.homework.forclass"/></th>
								<th><locale:message code="homework.homework.startTime"/></th>
								<th><locale:message code="homework.homework.closeTime"/></th>
								<th><locale:message code="homework.homework.completion"/></th>
								<th><locale:message code="homework.homework.correcting"/></th>
								<th><locale:message code="homework.homework.average"/></th>
								<th><locale:message code="homework.homework.operatingarea"/></th>
							</tr>
						</thead>
						<tbody id="homeworkListContext"></tbody>
					</table>
					<div class="page" id="divPage"></div>
				</div>
			</form>
		</div>
	</div>
<textarea id="homeworkContext_tpl" style="display: none;">
	{{#dataList}} 
	<tr> 
		<td>{{homeworkName}}</td>
		<td>{{className}}</td>
		<td>{{startTimeStr}}</td>
		<td>{{closeTimeStr}}</td>
		<td>{{finishNumStr}}/{{totalNum}}</td>
		<td>{{correctNumStr}}/{{finishNumStr}}</td>
		<td>{{avgScore}}</td> 
		<td class="operation">
			{{^isInvalid}}
			<a href="${ctx}/auth/teacher/homework/homeworkDetail.htm?homeworkId={{homeworkId}}" target="_blank" class="link"><locale:message code="homework.homework.correct.text" /></a>
			<a href="${initParam.diagServerName}/auth/teacher/homework/statistics.htm?homeworkId={{homeworkId}}" target="_blank" class="link"><locale:message code="homework.common.statistical"/></a>
			<a href="${initParam.diagServerName}/auth/teacher/homework/analysis.htm?homeworkId={{homeworkId}}" 
					data-href="${initParam.diagServerName}/auth/teacher/homework/generate.htm?homeworkId={{homeworkId}}" 
					data-homeworkid="{{homeworkId}}" target="_blank" class="jStats link"><locale:message code="homework.common.analysis" /></a>
			{{/isInvalid}}
			{{#isInvalid}}<locale:message code="homework.homework.correct.already"/>{{/isInvalid}}
		</td>
	</tr> 
	{{/dataList}}
</textarea>

<script type="text/javascript">
	seajs.use('homework/homework/preview/homeworkList');
</script>

</body>
</html>