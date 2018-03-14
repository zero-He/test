<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>添加习题</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div data-bind="component: 'scope-title'"></div>
	<div class="g-mn">
		<%@ include file="/pages/common/qedB64Tip.jsp"%>
		<div data-bind="component: {
			name: 'qed-home',
			params: $data
		}"></div>
	</div>
</div>

<leke:pref/>
<script>
window.EdCtx = {
		initJson: ${initJson}
};
seajs.use('question/pages/common/question/add/index');
</script>
</body>
</html>