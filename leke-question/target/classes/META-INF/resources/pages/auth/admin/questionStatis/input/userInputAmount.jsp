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
	<div class="g-mn" data-bind="component: {
			name: 'que-inputer-input-amount',
			params: {
				userId: '${userId}',
				userName: '${userName}',
				startDate: '<fmt:formatDate value="${startDate}" type="both" pattern="yyyy-MM-dd"/>',
				endDate: '<fmt:formatDate value="${endDate}" type="both" pattern="yyyy-MM-dd"/>',
				roleId: '${currentRoleId}'
			}
		}">
	</div>
</div>
<script type="text/javascript">
	window.PaperCtx = {
		userId: '${userId}',
		roleId: '${currentRoleId}'
	};
	seajs.use('question/questionStatis/userInputAmount');
</script>
</body>
</html>