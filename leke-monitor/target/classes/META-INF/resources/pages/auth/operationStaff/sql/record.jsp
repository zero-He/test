<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>SQL 记录 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<style type="text/css">
pre {
  word-break: break-word;
}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: {
		name: 'record-list', 
		params: {
			query: SQLRecord.query
		}
	}"></div>
</div>

<script>
window.SQLRecord = {
	query : ${query}
};
seajs.use('monitor/pages/operationStaff/sql/record');
</script>
</body>
</html>