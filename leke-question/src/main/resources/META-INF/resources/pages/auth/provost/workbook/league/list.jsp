<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>学校习题册 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="z-resource" data-bind="component: {
		name: 'workbook-list',
		params: config
	}"></div>
</div>

<leke:pref/>
<script>
window.LeagueCtx = {
		leagueId: ${leagueId}
	};
seajs.use('question/pages/provost/workbook/league/list');
</script>
</body>
</html>