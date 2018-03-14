<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>作业复批 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-paper.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn p-exam f-pb0 f-mb0">
			<input type="hidden" id="homeworkId" value="${homeworkDtl.homeworkId }">
			<input type="hidden" id="homeworkDtlId" value="${homeworkDtl.homeworkDtlId }">
			<input type="hidden" id="isAgain" value="${isAgain }">
			<leke:sheet paperId="${homeworkDTO.paperId}" viewMode="${viewMode}" studentName="${homeworkDtl.studentName}"
				studentScore="${homeworkDtl.score}" comment="${homeworkDtl.soundFile}" selfCheck="${homeworkDTO.isSelfCheck}"
				showRightAnswer="true" questionResultMap="${questionResultMap}" paperDTO ="${paperDto }" />
		</div>
	</div>
	
		<!-- 答题卡状态面板 -->
	<!-- ko component: {
    	name: "question-panel-right",
    	params: {paperIsIntact: ${paperIsIntact},workType: 3 }
	} -->
	<!-- /ko -->
	
	<div id="jCommentary" class="p-remark-edit" style="display: none;">
		<div class="message">你确定要完成批阅吗？</div>
		<div class="comment"><textarea placeholder="请输入试卷评语">${homeworkDtl.soundFile }</textarea></div>
		<div>
			<select class="u-select u-select-lg constant">
				<option value="">---常用评语---</option>
				<option value="做得很认真，完成的非常好！">做得很认真，完成的非常好！</option>
				<option value="完成的不错，继续努力！">完成的不错，继续努力！</option>
				<option value="做得不够认真，要加油！">做得不够认真，要加油！</option>
				<option value="发挥不够理想，继续努力！">发挥不够理想，继续努力！</option>
				<option value="做得很好，继续努力，不要骄傲！">做得很好，继续努力，不要骄傲！</option>
			</select>
		</div>
	</div>

	<script type="text/javascript">
		seajs.use('homework/homework/reviewWork');
	</script>
</body>
</html>