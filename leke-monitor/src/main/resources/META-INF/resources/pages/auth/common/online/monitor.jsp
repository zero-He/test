<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>乐课网动态监控</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/monitor/monitoring.css" type="text/css"/>
</head>
<body>
	
	<div data-bind="with: route">
		<div data-bind="component: {
			name: $data.view,
			params: $data.params
		}"></div>
	</div>

<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-all-current.js"></script>
<script>
seajs.config({
    alias: {
    	'signals': 'common/signals.min.js',
    	'crossroads': 'common/crossroads.min.js',
    	'hasher': 'common/hasher.min.js'
    }
});
</script>
<script>
seajs.use("monitor/pages/common/online/monitor");
</script>
</body>
</html>