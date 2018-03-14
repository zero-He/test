<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>异常统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: 'stat-list'"></div>
</div>

<script>
seajs.use('monitor/pages/operationStaff/exception/stat');
</script>
</body>
</html>