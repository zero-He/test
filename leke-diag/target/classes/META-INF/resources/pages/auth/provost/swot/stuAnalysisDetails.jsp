<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>学科优劣分析 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="jsearchForm" class="m-search-box">
			<div class = "f-fl">
				<label class="title">年级：<span id="jGradeName">${gradeName}</span>  </label>
			</div>
			<div class="f-fr">
				<input type="hidden" name="startDate" value="${startDate }"  /> 
				<input type="hidden" name="finishDate" value="${finishDate }" />
				<input type="hidden" name="schoolStage" />
				<label class="title"> 时间：${startDate } ~  ${finishDate }</label>
				<input type="hidden" id="jGradeId" value="${gradeId }" />
			</div>
		</form>
				<div class="m-table">
			<table >
				<thead>
					<tr>
						<th>学科</th>
						<th>统计人数</th>
						<th>平均分</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="jBodyData">

				</tbody>
			</table>
			<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
		</div>
	</div>

	<script id="jTemplate" type="text/handlebars">
		{{#.}}
			<tr>
				<td>{{subjectName}}</td>
				<td>{{statNum}}</td>
				<td>{{num avgScore '#0.##' '--'}}</td>
				<td class="operation">
					<a class="jDetails" target="_blank"  data-subjectname="{{subjectName}}"
						data-href="${ctx}/auth/provost/swot/stuAnalysisDetailsBar.htm?subjectId={{subjectId}}&gradeId=${gradeId}">查看详情</a>
				</td>
			</tr>
		{{/.}}
	</script>

</div>
<script type="text/javascript">
	seajs.use('diag/provost/stuAnalysisDetails');
</script>
</body>
</html>