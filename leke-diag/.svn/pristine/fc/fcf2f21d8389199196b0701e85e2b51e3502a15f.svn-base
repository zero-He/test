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
		<form id="searchForm" class="m-search-box">
			<div class="item">
				<div class="f-fl">
					<label class="title">班级：<span id="jClassName">${className }</span> </label>
					<input type="hidden" name="fileName" value="${className }" />
					<label class="title">学科：${subjectName }</label>
				</div>
				<div class="f-fr">
					<label class="title">时间：${startDate } ~ ${finishDate }</label>
					<input type="hidden" name="startDate" value="${startDate }" />
					<input type="hidden" name="finishDate" value="${finishDate }" />
					<input type="hidden" id="jClassId" name="classId" value = "${classId }" />
					<input type="hidden" id="jSubjectId" name="subjectId" value ="${subjectId }" />
					<input type="hidden" id="jDataJson" />
				</div>
			</div>
			<div class="item">
				<label class="title">学生姓名：</label>
				<input type="text" class="u-ipt u-ipt-nm" id="jStudentName" name ="studentName" />
				<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
				<div class="operation">
					<input class="u-btn u-btn-nm u-btn-bg-orange" type="button" id="jExportData" value="导出" title="导出全班成绩">
				</div>
			</div>
		</form>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th>学生</th>
							<th>作业数</th>
							<th>最高分</th>
							<th>平均分</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jBody"></tbody>
				</table>
				<div id="divPage" class="m-page" >
					<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
				</div>
			</div>
		</div>
	</div>
	<script id="jTemplate" type="text/handlebars">
	{{#.}}
		<tr>
			<td>{{userName}}</td>
			<td>{{statNum}}</td>
			<td>{{num maxScore '#0.0' '--'}}</td>
			<td>{{num avgScore '#0.0' '--'}}</td>
			<td class="operation">
				<a class="detailsStudent" target="_blank" 
				data-href="${ctx}/auth/classTeacher/achievement/achievementStuSubjectDetails.htm?classId=${classId}&subjectId=${subjectId}&studentId={{userId}}">查看详情</a>
			</td>
		</tr>
	{{/.}}
</script>
<script type="text/javascript">
	seajs.use('diag/classTeacher/achievementDetails');
</script>
</body>
</html>