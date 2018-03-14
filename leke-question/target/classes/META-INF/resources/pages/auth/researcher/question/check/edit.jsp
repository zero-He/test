<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>编辑习题 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body class="c-dialog">
	<div data-bind="component: 'scope-title'"></div>
	<div data-bind="component: {
		name: 'qed-home',
		params: $data
	}"></div>

<leke:pref/>
<script>
window.EdCtx = {
	initJson: ${initJson}
};
document.domain = '${initParam.mainDomain}';
seajs.use('question/pages/researcher/question/check/edit');
</script>
</body>
</html>