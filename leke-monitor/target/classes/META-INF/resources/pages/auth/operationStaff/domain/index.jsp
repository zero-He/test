<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="mon.title.domain"></locale:message></title>
<%@ include file="/pages/common/meta.jsp" %>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: 'domain-list'"></div>
</div>

<script>
seajs.use('monitor/pages/operationStaff/domain/index');
</script>
</body>
</html>