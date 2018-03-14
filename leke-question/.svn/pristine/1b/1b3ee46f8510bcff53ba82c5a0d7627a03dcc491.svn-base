<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="que.title.question.share"></locale:message></title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: {
		name: 'question-share',
		params: {
			backend: backend
		}
	}"></div>

</div>
<script>
window.Share = {
	backend : ${backend}
};
seajs.use('question/pages/teacher/question/share/share');
</script>
</body>
</html>
