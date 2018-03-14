<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="que.title.question.league"></locale:message></title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="z-resource" data-bind="component: {
		name: 'question-list',
		params: config
	}"></div>
</div>
<div data-bind="component: 'que-cart'"></div>
<leke:pref/>
<script>
window.LeagueCtx = {
	leagueId: ${leagueId}
};
seajs.use('question/pages/provost/question/league/list');
</script>
</body>
</html>