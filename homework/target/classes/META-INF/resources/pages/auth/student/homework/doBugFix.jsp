<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>
	<c:if test="${homework.exam}">订正试卷</c:if>
	<c:if test="${!homework.exam}">订正作业</c:if>
	-
	<locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-paper.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/globalnote.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="p-exam">
			<input type="hidden" id="homeworkDtlId" value="${homeworkDtl.homeworkDtlId }">
			<leke:sheet paperId="${homework.paperId}" viewMode="${viewMode}" studentName="${homeworkDtl.studentName}"
				comment="${homeworkDtl.soundFile}" showRightAnswer="false" selfCheck="${homework.isSelfCheck}"
				questionResultMap="${questionResultMap}" paperDTO = "${paperDto }" />
		</div>
	</div>
	
	<!-- 答题卡状态面板 -->
	<!-- ko component: {
    	name: "question-panel-right",
    	params: {workType: 2 }
	} -->
	<!-- /ko -->
	<input type="hidden" id="jSubjectId" value="${homework.subjectId }" />
	<input type="hidden" id="homeworkType" value="${homework.homeworkType }" />
	<input type="hidden" id="jQuestionSource" value="${homework.homeworkName }" />
	<input type="hidden" id="jTeacherId" value="${homework.teacherId }" />
	<input type="hidden" id="jFileServerName" value="${initParam.fileServerName }" />
	<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName }" />

	<script type="text/javascript">
		var ticket = '${ticket}';
		seajs.use('homework/homework/doBugFix');
	</script>
</body>
</html>