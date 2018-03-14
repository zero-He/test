<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="que.title.question.school"></locale:message></title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="z-resource"  data-bind="component: {
		name: 'question-list',
		params: config
	}"></div>
</div>
<div data-bind="component: 'que-cart'"></div>
<leke:pref/>
<script>
seajs.use('question/pages/teacher/question/school/list');
</script>
</body>
</html>