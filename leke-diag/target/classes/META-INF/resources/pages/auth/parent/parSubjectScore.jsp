<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>学科优劣分析 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div>
				<ul id="jStudentList" class="m-tab f-bfc f-pt0">
					<c:forEach var="student" items="${studentList }" varStatus="s">
						<li data-stuid="${student.id}" class="${student.id == studentId ? 'active' : ''}">${student.userName}</li>
					</c:forEach>
				</ul>
			</div>
			<div>
				<ul id="jULid" class="m-tab">
					<li class="active" data-id="2"><a>选修班级</a></li>
					<li data-id="1"><a>行政班级</a></li>
				</ul>
			</div>
			<form id="searchForm">
				<label class="title">班级：</label>
				<select id="jClassId" name="classId" class="u-select u-select-nm">
					<c:choose>
						<c:when test="${fn:length(clazzList) == 0}">
							<option value="0">没有对应班级</option>
						</c:when>
						<c:otherwise>
							<c:forEach var="clazz" items="${clazzList }">
								<option value="${clazz.classId }">${clazz.className }</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
				<input type="hidden" id="jClassType" name="classType" value="2" />
				<input type="hidden" id="jStudentId" name="studentId" value="${studentId }" />
			</form>
			<div class="u-chart">
				<div id="subjectScore" style="height: 500px;"></div>
			</div>
		</div>
	</div>
	<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
	<script type="text/javascript">
		seajs.use('diag/parents/stuSubjectScore');
	</script>
</body>
</html>