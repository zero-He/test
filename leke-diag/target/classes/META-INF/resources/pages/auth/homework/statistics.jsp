<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.homeworkstat.name"/><c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/task/task.css?t=20171115">
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<div class="g-mn">
		<div class="operating-statistics">
			<div class="special">
				<span class="title">${homework.homeworkName}（${homework.homeworkTypeStr}） </span>
				<p style="color: #333;">（${paperDesc }）</p>
			</div>
			
			<table>
				<tr>
					<td class="os1"><locale:message code="diag.homework.table.column.classname"/>：<span>${homework.className}</span></td>
					<td class="os1"><locale:message code="diag.homework.table.column.pubdate"/>：<span><fmt:formatDate value="${homework.startTime}" pattern="yyyy-MM-dd HH:mm" /></span></td>
					<td class="os1"><locale:message code="diag.homework.table.column.deadline"/>：<span><fmt:formatDate value="${homework.closeTime}" pattern="yyyy-MM-dd HH:mm" /></span></td>
				</tr>
				<tr>
					<td class="os1"><locale:message code="diag.homework.table.column.receivedmuch"/>：<span>${homework.finishNumStr}/${homework.totalNum}</span></td>
					<td class="os1"><locale:message code="diag.homework.table.column.correctinghomework"/>：<span>${homework.correctNumStr}/${homework.finishNumStr}</span></td>
				</tr>
				<tr>
					<td class="os1"><locale:message code="diag.homework.table.column.topscore"/>：<span><fmt:formatNumber value="${homework.maxScore + 0.0001}" pattern="0.#" /></span></td>
					<td class="os1"><locale:message code="diag.homework.table.column.lowestmark"/>：<span><fmt:formatNumber value="${homework.minScore + 0.0001}" pattern="0.#" /></span></td>
					<td class="os1"><locale:message code="diag.homework.table.column.average"/>：<span><fmt:formatNumber value="${homework.avgScore + 0.0001}" pattern="0.#" /></span></td>
				</tr>
				<tr>
					<td class="os1"><locale:message code="diag.homework.table.column.passrate"/>：<span id="jRateC">0</span>%</td>
					<td class="os1"><locale:message code="diag.homework.table.column.goodrate"/>：<span id="jRateB">0</span>%</td>
					<td class="os1"><locale:message code="diag.homework.table.column.excellentrates"/>：<span id="jRateA">0</span>%</td>
				</tr>
				<!-- 如果是学生，显示个人排名和得分 -->
				<c:if test="${roleId =='100' }">
					<tr >
						<td class="os1"><locale:message code="diag.homework.table.column.myscore"/>：<span id="J_stuScore">--</span></td>
						<td class="os1"><locale:message code="diag.homework.table.column.ranking"/>：<span id="J_stuRank">--</span></td>
					</tr>
				</c:if>
			</table>
		</div>
		<div id="jChartParams" class="f-dn">${params }</div>

		<div id="hwScoreStat" class="f-mt20 f-mb20"></div>
		<c:if test="${currentRoleId != 100 }">
		<div >
		<input id="jExport"  class="u-btn u-btn-nm u-btn-bg-orange" data-id="${homework.homeworkId }" type="button" style="float:right;"value="导出">
		<iframe id="iframes" name="iframes" style="display:none"></iframe>
		</div>
		</c:if>
		<div id="jHwScoreList" class="m-table f-pt20" style="border-top:1px solid #dfdfdf;">
			<table id="J_studentRank">
				<thead id="hwScoreListHeader">
					<tr>
						<th style="width: 70px;">&nbsp;</th>
						<th>&nbsp;</th>
						<th style="width: 70px;">&nbsp;</th>
						<th><locale:message code="diag.homework.table.column.ranking"/></th>
						<th><locale:message code="diag.homework.table.column.name"/></th>
						<th><locale:message code="diag.homework.table.column.totalpoints"/> （${totalScore }分）</th>
					</tr>
				</thead>
				<tbody id="jHwScoreListBody1"></tbody>
				<tbody id="jHwScoreListBody2"></tbody>
				<tbody id="jHwScoreListBody3"></tbody>
				<tbody id="jHwScoreListBody4"></tbody>
			</table>
		</div>
	</div>
</div>
<script id="jHomeworkListTpl" type="x-mustache">
{{#dataList}}
<tr data-stu="{{studentId}}">
	{{{marge}}}
	<td class="rank">{{rank}}</td>
	<td>{{studentName}}</td>
	<td class="totalScore">{{score}}<locale:message code="diag.java.subAnalysis.mark"/></td>
</tr>
{{/dataList}}
</script>
<script id="jHomeworkMargeTpl" type="x-mustache">
<td rowspan="{{size}}" class="f-fwb">{{max}} - {{min}}</td>
<td rowspan="{{size}}" style="background-color: {{color}}; width: 2px;">&nbsp;</td>
<td rowspan="{{size}}" class="f-fwb">{{size}}人</td>
</script>

<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/homework/statistics');
</script>

</body>
</html>