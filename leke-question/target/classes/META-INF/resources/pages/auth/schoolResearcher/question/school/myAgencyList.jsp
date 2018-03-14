<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>我的代录</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="z-resource f-bfc"  data-bind="component: {
			name: 'agency-question-list',
			params: config
		}"></div>
	</div>
</div>
<div class="wrap maxwidth" data-bind="component: 'que-cart'"></div>
<leke:pref/>
<script>
document.domain = '${initParam.mainDomain}';
seajs.use('question/pages/schoolResearcher/question/school/myAgencyList');
</script>
</body>
</html>