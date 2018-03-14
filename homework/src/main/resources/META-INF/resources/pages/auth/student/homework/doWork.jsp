<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="homework.homework.title.name" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-paper.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/globalnote.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=${_t}">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="p-exam">
			<input type="hidden" id="homeworkDtlId" value="${homeworkDtl.homeworkDtlId }">
			<leke:sheet paperId="${homeworkDTO.paperId}" viewMode="${viewMode}" studentName="${homeworkDtl.studentName}"
				showRightAnswer="false"  questionResultMap="${questionResultMap}" paperDTO ="${paperDto }" />
		</div>
	</div>
	
	<!-- 答题卡状态面板 -->
	<!-- ko component: {
    	name: "question-panel-right",
    	params: {paperIsIntact: ${paperIsIntact},workType: 0 }
	} -->
	<!-- /ko -->
	
	
	<!-- 动态 -->
	<!-- ko component: {
    	name: "do-work-right",
    	params: { homeworkId: ${homeworkDTO.homeworkId}, fileServerName: "${initParam.fileServerName}",
    		staticServerName: "${initParam.staticServerName}",incentiveServerName: "${initParam.incentiveServerName}",paperIsIntact: ${paperIsIntact} }
	} -->
	<!-- /ko -->
	<input type="hidden" id="jSubjectId" value="${homeworkDTO.subjectId }" />
	<input type="hidden" id="homeworkId" value="${homeworkDTO.homeworkId }" />
	<input type="hidden" id="homeworkType" value="${homeworkDTO.homeworkType }" />
	<input type="hidden" id="jTeacherId" value="${homeworkDTO.teacherId }" />
	<input type="hidden" id="jQuestionSource" value="${homeworkDTO.homeworkName }" />
	<input type="hidden" id="jFileServerName" value="${initParam.fileServerName }" />
	<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName }" />
	<input type="hidden" id="global-toolbar-note-params" value="id=${homeworkDtl.homeworkDtlId }&type=1&name=${homeworkDTO.homeworkName }&subjectId=${homeworkDTO.subjectId }" />
	<script type="text/javascript">
		var ticket = '${ticket}';
		seajs.use('homework/homework/doWork');
	</script>
</body>
</html>