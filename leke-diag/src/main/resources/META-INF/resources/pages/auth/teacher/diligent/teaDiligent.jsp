<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.diligent.name"/> - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<div class="f-fl">
				<label class="title"><locale:message code="diag.common.search.subject"/>：</label>
				<select id="jStageSubject" class="u-select u-select-nm"></select>
				<input type="hidden" id="jSubjectId" name="subjectId">
				<label class="title">课程名称：</label>
				<input type="text" class="u-ipt u-ipt-nm"  name="courseName" >
				<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="<locale:message code="diag.common.search.query"/>"> 
			</div>
			<div class="f-fr">
				<input type="hidden" name="startDate" value="${startDate }"  /> 
				<span> 时间：${startDate } ~  ${finishDate }</span>
			</div>
		</form>
		<div class="m-table">
			<table >
				<thead>
					<tr>
						<th>课程名称</th>
						<th>学科</th>
						<th>作业数</th>
						<th>提交率</th>
						<th>延迟率</th>
						<th>未交率</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="jtbodyData">

				</tbody>
			</table>
			<div class="page" id="jPageFoot"><ul></ul></div>
			<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
		</div>
	</div>

<script id="jtemplateData" type="text/handlebars">
		{{#dataList}}
			<tr>
				<td>{{className}}</td>
				<td>{{subjectName}}</td>
				<td>{{homeworkCount}}</td>
				<td>{{num finishRate '#0.##%' '--'}}</td>
				<td>{{num delayRate '#0.##%' '--'}}</td>
				<td>{{num noFinishRate '#0.##%' '--'}}</td>
				<td class="operation">
					<a class="detailsStudent" target="_blank"  href="${ctx}/auth/teacher/diligent/stuDiligentDtl.htm?classId={{classId}}&subjectId={{subjectId}}">查看详情</a>
				</td>
			</tr>
		{{/dataList}}
</script>
		
</div>
<leke:pref />
<script type="text/javascript">
	seajs.use('diag/teacher/teaDiligent');
</script>
</body>
</html>