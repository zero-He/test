<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="que.title.question.view"></locale:message></title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/user-que.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
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
	<div style="text-align: center;">
        <input type="button" value="关闭" class="u-btn u-btn-nm u-btn-bg-turquoise j-close"
        	data-bind="click: function() {
        		window.close();
        	}"/>
    </div>
</div>

<script>
seajs.use('question/pages/provost/question/view');
</script>
</body>
</html>
