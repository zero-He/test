<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<!DOCTYPE html>
<html>
<head>
<title>习题预览 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/user-que.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div data-bind="component: 'scope-title'"></div>
	<div class="g-mn">
		<div class="tests queWrapper">
			<div class="testQuestionsTotal">
				<span class="head">
					<locale:message code="que.question.text.queNum"></locale:message>：${questionId}
				</span>
			</div>
			<div class="timu">
				<div class="timu-style">
					<leke:question questionId="${questionId}" showCheckbox="false" 
					showSequence="false" showExplain="true" showProperty="true" 
					showPropertyEdit="false" showRightAnswer="true"/>
				</div>
			</div>
		</div>
	</div>
	<div data-bind="component: 'checkview-btns'"></div>
</div>

<script>
window.CheckCtx = {
	olnode: ${olnodeJson}
};
seajs.use('question/pages/schoolResearcher/question/school/checkView');
</script>
</body>
</html>
