<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>学生成绩统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="jSearchForm" class="m-search-box">
			<div class="f-fl">
				<label class="title">学科：</label>
				<select id="jStageSubject" class="u-select u-select-nm">
				</select>
				<label class="title">课程名称：</label>
				<input type="text" name="className" class="u-ipt u-ipt-nm" />
				<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
			</div>
			<div class="f-fr">
				<label class="title">时间：${startDate } ~ ${finishDate }</label>
				<input type="hidden" name="startDate" value="${startDate }" />
				<input type="hidden" name="finishDate" value ="${finishDate }" />
				<input type="hidden" name ="subjectId" id="jsubjectId" />
			</div>
		</form>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th>课程</th>
							<th>学科</th>
							<th>作业数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jBody"></tbody>
				</table>
				<div class="page" id="jPageFoot"></div>
			</div>
		</div>
	</div>
	<script id="jTemplate" type="text/handlebars">
	{{#dataList}}
		<tr>
			<td>{{className}}</td>
			<td>{{subjectName}}</td>
			<td>{{homeworkCount}}</td>
			<td class="operation">
				<a class="detailsStudent" target="_blank"
				 	href="${ctx}/auth/teacher/achievement/achievementDetails.htm?
							courseId={{classId}}&subjectId={{subjectId}}&courseName={{className}}">
					查看详情
				</a>
			</td>
		</tr>
	{{/dataList}}
</script>
<leke:pref />
<script type="text/javascript">
	seajs.use('diag/teacher/achievement');
</script>
</body>
</html>