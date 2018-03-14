<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.homework.analysis.name"/><c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-paper.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<style type="text/css">
.names td span {
	display: inline-block;
	min-width: 80px;
	padding: 5px;
}
</style>
</head>
<body>
<c:if test="${device != 'hd'}">
<%@ include file="/pages/header/header.jsp"%>
</c:if>
<div class="g-bd">
	<div class="g-mn p-exam">
		<c:if test="${currentRoleId == 100 }">
			<leke:sheet paperDTO="${paperDTO}" viewMode="${viewMode}" studentName="${homeworkDtl.studentName}"
				studentScore="${homeworkDtl.score}" usedTime="${homeworkDtl.usedTime}" comment="${homeworkDtl.soundFile}"
				showRightAnswer="${homework.isOpenAnswer}" questionResultMap="${questionResultMap}" />
		</c:if>
		<c:if test="${currentRoleId != 100 }">
			<leke:sheet paperDTO="${paperDTO}" viewMode="0" showRightAnswer="true" />
		</c:if>
	</div>
</div>
	<!-- 面板 -->
	<div class="m-anchorpanel" style="position: fixed;">
		<div id = "j_question_panel" class="box" style="display: block; right: 0;">

		</div>
	</div>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain-2.0.2.js"></script>
<script type="text/javascript">
	var homeworkId = ${homework.homeworkId};
	var ReportCst = ${ReportCst};
	seajs.use('homework/sheet.struct');
	seajs.use('homework/sheet.render');
	seajs.use('diag/homework/analysis');
</script>
<c:if test="${currentRoleId == 100}">
	<input type="hidden" id="jSubjectId" value="${homework.subjectId }" />
	<input type="hidden" id="homeworkId" value="${homework.homeworkId }" />
	<input type="hidden" id="homeworkType" value="${homework.homeworkType }" />
	<input type="hidden" id="jTeacherId" value="${homework.teacherId }" />
	<input type="hidden" id="jQuestionSource" value="${homework.homeworkName }" />
	<script type="text/javascript">
		seajs.use('homework/common/sheet.extend');
	</script>
</c:if>
</body>
</html>