<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>异常 记录 - 乐课网</title>
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
			query: ExRecord.query
		}
	}"></div>
</div>

<script>
window.ExRecord = {
	query : ${query}
};
seajs.use('monitor/pages/operationStaff/exception/record');
</script>
</body>
</html>