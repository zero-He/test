<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="que.question.title.questionList"></locale:message></title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/user-que.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: {
		name: 'que-check-list',
		params: {
				schoolId: '${currentSchoolId}'
		}
	}">
	</div>
</div>
<leke:pref />
<script>
window.PaperCtx = {
	userId: '${userId}',
	roleId: '${currentRoleId}',
	schoolId: '${currentSchoolId}',
	ticket: '${ticket}'
};
document.domain = '${initParam.mainDomain}';
seajs.use('question/view/question/league/checkList');
</script>
</body>
</html>