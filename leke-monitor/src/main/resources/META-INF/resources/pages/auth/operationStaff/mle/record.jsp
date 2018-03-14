<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>监听器异常 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<style type="text/css">
.m-form.m-form-compact ul .form-item {padding: 0 0;}
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
			query: MleRecord.query
		}
	}"></div>
</div>

<script>
window.MleRecord = {
	query : ${query}
};
seajs.use('monitor/pages/operationStaff/mle/record');
</script>
</body>
</html>