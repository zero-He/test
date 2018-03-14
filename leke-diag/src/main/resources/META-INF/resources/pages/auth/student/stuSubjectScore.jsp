<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.subanalysis.name"/> - 乐课网</title>
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
				<div class="f-fl">
					<label class="title"><locale:message code="diag.common.search.class"/>：<span id="jclassName"></span></label>
					<input type ="hidden" name="classId" id="jclassId" />
					<label class="title"><locale:message code="diag.table.column.studentname"/>：<span id="jStudentName">${student.userName }</span></label>
				</div>
				<div class="f-fr">
					<label class="title">时间：${startDate } ~ ${finishDate }</label>
				</div>
				<input type="hidden" id="jStudentId" name="studentId" value="${student.id }" />
				<input type="hidden" id="jClassType" name="classType" value="1" />
			</form>
			<div class="u-chart">
				<div id="subjectScore" style="height: 500px;"></div>
			</div>
		</div>
	</div>
	<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
	<script type="text/javascript">
		seajs.use('diag/student/stuSubjectScore');
	</script>
</body>
</html>