<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet"
	href="${assets}/styles/common/global.css">
<link rel="stylesheet"
	href="${assets}/styles/homework/ts-pc.css">
<title>成绩单明细</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="ts-tab-wrap">
				<c:if test="${statusCd == 1 && isCreatedBySelf == 1}">
            		<button type="button" id="publishExamReport" name="publishExamReport" class="u-btn u-btn-nm u-btn-bg-orange f-mb20 f-fr">发布</button>
            	</c:if>
				<c:if test="${isNewCreated == 1}">
					<div id="tips" class="special f-mb20 f-tal">
						<p style="color: #333;">成绩单创建成功，结果如下，请确认后点击发布</p>
					</div>
				</c:if>
				<div class="m-table m-table-center f-cb">
					<table>
						<thead>
							<tr>
								<th width="10%">乐号</th>
								<th width="10%">姓名</th>
								<th width="10%">班级</th>
								<th width="10%">学科</th>
								<th width="10%">成绩</th>
								<th width="10%">平均分</th>
								<th width="10%">最高分</th>
								<th width="10%">最低分</th>
							</tr>
						</thead>
						<tbody id="jtbodyData"></tbody>
					</table>
				</div>
			<div id="jTablePage"/>
			</div>
			
		</div>

	</div>
	
	<input type="hidden" id="examReportId" name="examReportId" value="${examReportId}" />
	<input type="hidden" id="isCreatedBySelf" name="isCreatedBySelf" value="${isCreatedBySelf}" />

	<script id="examReportListTpl" type="x-mustache">
		{{#dataList}}
			<tr>
				<td>{{loginName}}</td>
				<td>{{studentName}}</td>
				<td>{{className}}</td>
				<td>{{subjectName}}</td>
				<td>{{score}}</td>
				<td>{{avgScore}}</td>
				<td>{{maxScore}}</td>
				<td>{{minScore}}</td>
			</tr>
		{{/dataList}}
	</script>
	
	<script type="text/javascript">
		seajs.use('diag/homework/showExamReportDtl');
	</script>

</body>
</html>