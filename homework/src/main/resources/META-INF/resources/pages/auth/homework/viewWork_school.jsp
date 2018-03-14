<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="homework.homework.checkhomework" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-paper.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn p-exam">
			<leke:sheet paperId="${homeworkDTO.paperId}" viewMode="${viewMode}" studentName="${homeworkDtl.studentName}"
				studentScore="${homeworkDtl.score}" usedTime="${homeworkDtl.usedTime}" comment="${homeworkDtl.soundFile}"
				showRightAnswer="${showRightAnswer}" selfCheck="${homeworkDTO.isSelfCheck}" 
				questionResultMap="${questionResultMap}" paperDTO="${paperDto }" />
			<div class="p-exam-foot">
				<a class="u-btn u-btn-lg u-btn-bg-gray" href="javascript:window.close();"><locale:message code="homework.homework.closewindow" /></a>
				<c:if test="${not empty sheetBookId}">
				<a class="u-btn u-btn-lg u-btn-bg-turquoise" href="${ctx}/auth/common/sheet/bookInfo.htm?bookId=${sheetBookId}">查看答题卡</a>
				</c:if>
				<a href="javascript:void(0);" class="u-btn u-btn-lg u-btn-bg-turquoise j-jump" data-url="prevId.htm"
					data-homeworkid="${homeworkDtl.homeworkId }" data-homeworkdtlid="${homeworkDtl.homeworkDtlId }"><locale:message code="homework.homework.last" /></a>
				<a href="javascript:void(0);" class="u-btn u-btn-lg u-btn-bg-turquoise j-jump" data-url="nextId.htm"
					data-homeworkid="${homeworkDtl.homeworkId }" data-homeworkdtlid="${homeworkDtl.homeworkDtlId }"><locale:message code="homework.homework.next" /></a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('homework/homework/viewWork');
	</script>
</body>
</html>