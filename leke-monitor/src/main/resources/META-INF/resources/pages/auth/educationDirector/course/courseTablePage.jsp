<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>监控课堂 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/home/personal-center.css">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: {
		name: 'coursetable-week',
		params: {
			options: {
				showArea: false,
				showSchool: true,
				showStats: false,
				dailyUrl: null,
				itemsUrl: Leke.ctx + '/auth/educationDirector/course/courseTables.htm'
			}
		}
	}"></div>
</div>

<script>
seajs.use('monitor/pages/educationDirector/course/courseTablePage');
</script>
</body>
</html>