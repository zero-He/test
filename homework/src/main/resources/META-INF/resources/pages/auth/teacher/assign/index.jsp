<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="common.page.nav.ac.homework" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/assign.css?t=${_t}" type="text/css">
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div id="container">
			
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/scripts/homework/assign/base64.js?t=${_t}"></script>
	<script type="text/javascript">
		var resources = ${resJson};
		var classes = ${classes};
		seajs.use('homework/assign/assign.js?_t=${_t}');
	</script>
</body>
</html>