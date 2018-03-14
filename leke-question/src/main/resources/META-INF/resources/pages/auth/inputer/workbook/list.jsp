<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>习题册设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<style type="text/css">
.c-form>ul>li .title {
	width: 100px;
	background-color: #fff;
	height: 28px;
}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div data-bind="component:{
		name: 'workbook-list',
		params: {}
	}"></div>
	</div>
</div>

<script>
seajs.use('question/view/workbook/workbook-list.js?t=${_t}');
</script>
</body>
</html>