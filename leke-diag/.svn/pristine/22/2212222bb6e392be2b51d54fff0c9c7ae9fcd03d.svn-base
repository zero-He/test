<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title><c:if test ="${currentRoleId eq 102 }">子女成长曲线</c:if>
<c:if test ="${currentRoleId eq 100 }">个人成长曲线</c:if> - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<c:if test ="${currentRoleId eq 102 }">
			<div>
				<ul id="jStudentList" class="m-tab">
					<c:forEach var="student" items="${studentList }" varStatus="status">
						<li data-stuid="${student.id}" <c:if test = "${status.index eq 0 }">class="active"</c:if> ><a>${student.userName}</a></li>
					</c:forEach>
				</ul>
				<c:if test ="${studentList.size() == 0 }">
					您还没有关联子女~
				</c:if>
			</div>
			</c:if>
			
			<form id="searchForm" class="m-search-box">
				<div class ="f-fl">
					<input type="hidden" id="jStudentId" name="studentId" value="${ userId}" />
					<label class="title" >学生：<span id ="jStudentName">${studentName }</span></label>
				</div>
				<div class ="f-fr">
					<label class="title">
						<locale:message code="diag.common.search.time"/>：${startDate } ~ ${finishDate }
					</label>
					<input type="hidden" name="startTime"  value="${startDate }" />
					<input type="hidden" name="endTime" value="${finishDate }" />
				</div>
			</form>
			<div class="m-table">
			<table >
				<thead>
					<tr>
						<th>学科</th>
						<th>作业数</th>
						<th>最高分</th>
						<th>平均分</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="jtbodyData">

				</tbody>
			</table>
			<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
		</div>
	</div>

<script id="jtemplateData" type="text/handlebars">
		{{#.}}
			<tr>
				<td>{{subjectName}}</td>
				<td>{{statNum}}</td>
				<td>{{num maxScore '#0.##' '--'}}</td>
				<td>{{num avgScore '#0.##' '--'}}</td>
				<td class="operation">
					<a class="jDetails" target="_blank"
					 data-href="${ctx}/auth/student/data/line/stuStatistical.htm?spm=101020&subjectId={{subjectId}}">
						查看详情
					</a>
				</td>
			</tr>
		{{/.}}
</script>
		</div>
	<script type="text/javascript">
		seajs.use('diag/student/selfStatisticalList');
	</script>
</body>
</html>