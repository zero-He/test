<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>PullToRefresh测试</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<style type="text/css">
.aaa {
	height: 300px;
}
</style>
</head>
<body>
	<div id="app"></div>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react.min.js"></script>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react-dom.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/m/diag/report/testing-pull.bundle.js"></script>
</body>
</html>