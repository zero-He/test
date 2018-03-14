<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>我的代录</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="z-resource" data-bind="component: {
		name: 'agency-workbook-list',
		params: config
	}"></div>
</div>

<leke:pref/>
<script>
seajs.use('question/pages/researcher/workbook/fmstch/myAgencyList');
</script>
</body>
</html>