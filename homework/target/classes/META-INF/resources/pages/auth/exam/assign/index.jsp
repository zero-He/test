<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>发布考试 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/assign-exam.css?t=20171115" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div id="container">
			
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var resources = ${resJson};
		var classes = ${classes};
		seajs.use('homework/assign/assign-exam.js?_t=20171115');
	</script>
</body>
</html>