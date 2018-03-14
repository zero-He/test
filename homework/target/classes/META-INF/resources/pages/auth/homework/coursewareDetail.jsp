<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>课件详情页 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link type="text/css" rel="stylesheet" href="${assets}/styles/homework/homework.css?t=20171115" />

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="c-teachcheckwork">
			<div class="c-teachcheckwork-trbp">
				<div class="head-infor">
					<span class="work-name">课件名称：
						<c:if test="${homework.paperId != null }">
							<a class="u-link" title="${homework.homeworkName}"
							href="${initParam.beikeServerName}/auth/common/courseware/preview.htm?coursewareId=${homework.paperId}"
							target="_blank">${homework.homeworkName}</a>
						</c:if>
						<c:if test="${homework.paperId == null }">
						${homework.homeworkName}
						</c:if>
					</span> 
					<span class="class">班级：<span>${homework.className}</span></span>
				</div>
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th>学生姓名</th>
								<th>完成学习时间</th>
								<th>最近学习时间</th>
								<th>学习次数</th>
							</tr>
						</thead>
						<tbody id="homeworkDtlListContext">

						</tbody>
					</table>
					<div class="m-tips j-no-data f-hide">
						<i></i><span>对不起，没有您要查询的数据</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="j_questionNum" value="${questionNum }" />
	<input type="hidden" id="j_homeworkId" value="${homework.homeworkId }" />

<script id="homeworkDtlContext_tpl" type="x-mustache">
	{{#dataList}} 
	<tr> 
		<td>{{studentName}}</td>
		<td>{{fmt submitTime 'yyyy-MM-dd HH:mm:ss'}}</td>
		<td>{{fmt bugFixTime 'yyyy-MM-dd HH:mm:ss'}}</span></td>
		<td class="operation">{{bugFixCount}}</td>
	</tr> 
	{{/dataList}}
</script>

	<script type="text/javascript">
		seajs.use('homework/homework/homeworkDetail');
	</script>
</body>
</html>