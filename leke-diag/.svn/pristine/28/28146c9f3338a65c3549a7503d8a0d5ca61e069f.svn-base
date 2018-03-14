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
			<input type ="hidden" id="jDataList" />
			<div class="f-fl">
				<label class="title"><locale:message code="diag.common.search.subject"/>：</label>
				<select id="jStageSubject" class="u-select u-select-nm"></select>
				<input type="hidden" id="jSubjectId" name="subjectId">
				<input type ="hidden" id="jStudentId" value="${studentId }" />
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
		{{#.}}
			<tr>
				<td>{{subjectName}}</td>
				<td>{{def statNum '--'}}</td>
				<td>{{num submitRate '#0.##%' '--'}}</td>
				<td>{{num delayRate '#0.##%' '--'}}</td>
				<td>{{num undoneRate '#0.##%' '--'}}</td>
				<td class="operation">
					<a class="jDetails" target="_blank"  
						data-href="${ctx}/auth/common/stuHomeworkInfo.htm?spm=100020&classId={{classId}}
							&className={{className}}&subjectId={{subjectId}}&subjectName={{subjectName}}">
					查看详情</a>
				</td>
			</tr>
		{{/.}}
</script>
		
</div>

<script type="text/javascript">
	seajs.use('diag/student/myHomeworkList');
</script>
</body>
</html>