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
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<div class="f-fl">
				<input type="hidden" name="subjectId" value="${subjectId }">
				<input type="hidden" name="classId" value="${classId }">
				<label class="title">学生姓名：</label>
				<input type="text" class="u-ipt u-ipt-nm"  name="studentName" >
				<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="<locale:message code="diag.common.search.query"/>"> 
			</div>
			<div class="f-fr">
				<input type="hidden" name="startDate" value="${startDate }"  /> 
				<input type="hidden" name="finishDate" value="${finishDate }" />
				 <span> 时间：${startDate } ~  ${finishDate }</span>
			</div>
		</form>
		<div class="m-table">
			<table >
				<thead>
					<tr>
						<th>学生姓名</th>
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
		</div>
	</div>

<script id="jtemplateData" type="text/handlebars">
		{{#dataList}}
			<tr>
				<td>{{studentName}}</td>
				<td>{{homeworkCount}}</td>
				<td>{{num finishRate '#0.##%' '--'}}</td>
				<td>{{num delayRate '#0.##%' '--'}}</td>
				<td>{{num noFinishRate '#0.##%' '--'}}</td>
				<td class="operation">
					<a class="detailsStudent" target="_blank"  href="${ctx}/auth/common/stuHomeworkInfo.htm?spm=101022&classId={{classId}}
								&className={{className}}&subjectId={{subjectId}}&subjectName={{subjectName}}&stuId={{studentId}}">查看详情</a>
				</td>
			</tr>
		{{/dataList}}
</script>
		
</div>

<script type="text/javascript">
	seajs.use('diag/teacher/stuDiligentDtl');
</script>
</body>
</html>