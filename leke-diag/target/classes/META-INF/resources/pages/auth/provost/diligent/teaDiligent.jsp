<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<form id="searchForm" class="m-search-box">
				<div class="f-fl"></div>
				<div class="f-fr">
					<input type="hidden" name="startDate" value="${startDate }" />
					<input type="hidden" name="finishDate" value="${finishDate }" />
					<span> 时间：${startDate } ~ ${finishDate }</span>
				</div>
			</form>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th>年级</th>
							<th>学生数</th>
							<th>提交率</th>
							<th>延迟率</th>
							<th>未交率</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jPageBody"></tbody>
				</table>
			</div>
		</div>
	</div>
	<script id="jStudentsTemplate" type="text/handlebars">
	{{#.}}
		<tr>
			<td>{{gradeName}}</td>
			<td>{{statNum}}</td>
			<td>{{num submitRate '#0.0%' '--'}}</td>
			<td>{{num delayRate '#0.0%' '--'}}</td>
			<td>{{num undoneRate '#0.0%' '--'}}</td>
			<td class="operation">
				<a class="detailsStudent" target="_blank" href="${ctx}/auth/provost/diligent/teaDiligentDetails.htm?gradeId={{gradeId}}">查看详情</a>
			</td>
		</tr>
	{{/.}}
</script>

	<script type="text/javascript">
		seajs.use('diag/provost/teaDiligent');
	</script>
</body>
</html>