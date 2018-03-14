<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<!DOCTYPE html>
<html>
<head>
	<title>查看试卷 - 乐课网</title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=${_t}">
	<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-paper.css?t=${_t}">
	<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=${_t}">
	<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="g-mn p-exam">
		<input type="hidden" id="homeworkDtlId" value="${homeworkDtl.homeworkDtlId}">
		<input type="hidden" id="jSubjectId" value="${homeworkDTO.subjectId }"/>
		<input type="hidden" id="jQuestionSource" value="${homeworkDTO.homeworkName }"/>
		<input type="hidden" id="jFileServerName" value="${initParam.fileServerName }"/>
		<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName }"/>

		<leke:sheet paperId="${homeworkDTO.paperId}" viewMode="${viewMode}" studentName="${homeworkDtl.studentName}"
		            studentScore="${homeworkDtl.score}" usedTime="${homeworkDtl.usedTime}" comment="${homeworkDtl.soundFile}"
		            showRightAnswer="${showRightAnswer}" questionResultMap="${questionResultMap}" paperDTO="${paperDto }"/>
		<div class="p-exam-foot">
			<a class="u-btn u-btn-lg u-btn-bg-gray" href="javascript:window.close();">关闭窗口</a>
			<c:if test="${not empty sheetBookId}">
				<a class="u-btn u-btn-lg u-btn-bg-turquoise" href="${ctx}/auth/common/sheet/bookInfo.htm?bookId=${sheetBookId}">查看答题卡</a>
			</c:if>
		</div>
	</div>
</div>
<script type="text/javascript">
	seajs.use('homework/homework/stuViewWork.js?t=${_t}')
</script>
</body>
</html>