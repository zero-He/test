<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>资源审核</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="z-resource  f-pl0 f-pt0 f-pr0"  data-bind="component: {
			name: 'check-question-list'
		}"></div>
	</div>
</div>

<leke:pref/>
<script>
seajs.use('question/pages/schoolResearcher/question/school/checkList');
</script>
</body>
</html>