<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>访问记录</title>
<%@ include file="/pages/common/meta.jsp" %>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: {
		name: 'record-list', 
		params: {
			query: AccessRecord.query
		}
	}"></div>
</div>

<script>
window.AccessRecord = {
	query : ${query}
};
seajs.use('monitor/pages/operationStaff/access/record');
</script>
</body>
</html>