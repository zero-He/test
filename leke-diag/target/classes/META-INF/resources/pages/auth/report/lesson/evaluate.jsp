<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学生评价详情<c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<meta name="viewport" content="width=1280">
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/diag/analyse.css">
<link rel="stylesheet" href="${assets}/styles/tutor/evaluate/teacherEvaluateAvgDetail.css" />
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="z-classreviewreport">
				<h3 class="tips">
					<i class="date"></i>
					<fmt:formatDate value="${lesson.startTime}" pattern="yyyy-MM-dd HH:mm" />
					-
					<fmt:formatDate value="${lesson.endTime}" pattern="HH:mm" />
					${lesson.subjectName}
					${lesson.courseSingleName}
				</h3>
				<h3 class="evaluationtitle">来自学生的评价</h3>
				<form id="jTableForm" style="margin:10px;">
					<input type="hidden" name="courseSingleId" value="${lesson.courseSingleId}" />
					<select id="jHasText" name="hasText" class="u-select u-select-nm">
						<option value="false">全部评价</option>
						<option value="true">有评论内容</option>
					</select>
					<input type="radio" class="f-ml35" name="summary" value="" checked="checked">全部
					<input type="radio" class="f-ml35" name="summary" value="2">好评
					<input type="radio" class="f-ml35" name="summary" value="1">中评
					<input type="radio" class="f-ml35" name="summary" value="0">差评
				</form>
				<div class="m-table m-table-center">
					<table>
						<thead>
							<tr>
								<th style="width: 25%;">评价</th>
								<th style="width: 25%;">总评分</th>
								<th style="width: 25%;">各项目评分</th>
								<th style="width: 25%;">评价者</th>
							</tr>
						</thead>
						<tbody id="jTableBody"></tbody>
					</table>
					<div id="jTablePage"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
		seajs.use('diag/report/lesson/evaluate');
	</script>
	<script id="jTemplate" type="text/mustache">
	{{#.}}
		<tr>
			<td>
				{{map summary '{"1":"中评","2":"好评"}' '差评'}}
				{{#content}}<br />{{.}}{{/content}}
			</td>
			<td>
				<div class="table-point">
					<dl class="e-eval-score  f-tac">
						<dd class="f-dib f-fn  f-vam"><em><i style="width: {{overallRate}}%;"></i></em></dd>
						<dd class="f-dib f-fn  f-vam"><b>{{overallLevel}}</b></dd>
					</dl>
				</div>
			</td>
			<td>
				<div class="table-point">
					<dl class="e-eval-score">
						<dt>教学效果：</dt>
						<dd><em><i style="width: {{professionalRate}}%;"></i></em></dd>
						<dd><b>{{professionalLevel}}</b></dd>
					</dl>
					<dl class="e-eval-score">
						<dt>教学态度：</dt>
						<dd><em><i style="width: {{rhythmRate}}%;"></i></em></dd>
						<dd><b>{{rhythmLevel}}</b></dd>
					</dl>
					<dl class="e-eval-score">
						<dt>课堂互动：</dt>
						<dd><em><i style="width: {{interactionRate}}%;"></i></em></dd>
						<dd><b>{{interactionLevel}}</b></dd>
					</dl>
				</div>
			</td>
			<td>
				<p><b>{{createdName}}</b></p>
				<p>{{fmt createdOn 'yyyy-MM-dd HH:mm'}}</p>
			</td>
		</tr>
	{{/.}}
	</script>
</body>
</html>