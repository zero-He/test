<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>身份异常处理 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/abnormal.css?t=20171115" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div id="container"></div>
		</div>
	</div>
</body>
<script>
	var firstPageId = '${pageId}';
	seajs.use('homework/sheet/lekeno');
</script>
</html>
