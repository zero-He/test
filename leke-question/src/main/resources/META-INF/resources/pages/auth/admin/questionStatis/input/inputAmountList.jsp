<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>录入量统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<form>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
	<!-- ko component: {name: 'que-inputer-input-amount-list',params: {} } -->
	<!-- /ko -->
	</div>
</div>
</form>

<script type="text/javascript">
	seajs.use('question/questionStatis/inputAmountList');
</script>
</body>
</html>