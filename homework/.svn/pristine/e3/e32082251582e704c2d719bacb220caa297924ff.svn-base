<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>查看作业 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-paper.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/globalnote.css?t=${_t}">
<style type="text/css">
.g-bd{padding-bottom:0px; min-height:0; }
</style>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="p-exam">
			<input type="hidden" id="homeworkType" value="${homeworkDTO.homeworkType }" />
			<input type="hidden" id="homeworkDtlId" value="${homeworkDtl.homeworkDtlId}">
			<input type="hidden" id="jCorrectError" value="${not empty homeworkDtl.correctTime }" />
			<input type="hidden" id="jSubjectId" value="${homeworkDTO.subjectId }" />
			<input type="hidden" id="jTeacherId" value="${homeworkDTO.teacherId }" />
			<input type="hidden" id="jQuestionSource" value="${homeworkDTO.homeworkName }" />
			<input type="hidden" id="jFileServerName" value="${initParam.fileServerName }" />
			<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName }" />
			<leke:sheet paperId="${homeworkDTO.paperId}" viewMode="${viewMode}" studentName="${homeworkDtl.studentName}"
				studentScore="${homeworkDtl.score}" usedTime="${homeworkDtl.usedTime}" comment="${homeworkDtl.soundFile}"
				showRightAnswer="${showRightAnswer}" selfCheck="${homeworkDTO.isSelfCheck}" questionResultMap="${questionResultMap}" paperDTO="${paperDto }" />
			<div class="p-exam-foot m-imgpreview">
				<c:if test="${not empty sheetBookId}">
				<a class="u-btn u-btn-lg u-btn-bg-turquoise" href="${ctx}/auth/common/sheet/bookInfo.htm?bookId=${sheetBookId}">查看答题卡</a>
				</c:if>
				<a class="u-btn u-btn-lg u-btn-bg-gray" href="javascript:window.close();">关闭窗口</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('homework/homework/stuViewWork.js?t=${_t}');
	</script>
</body>
</html>