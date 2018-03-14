<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>教研员校对统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<form>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div  data-bind="component: {
			name: 'que-researcher-verify-amount',
			params: {
				userId: '${userId}',
				userName: '${userName}',
				startDate: '<fmt:formatDate value="${startDate}" type="both" pattern="yyyy-MM-dd"/>',
				endDate: '<fmt:formatDate value="${endDate}" type="both" pattern="yyyy-MM-dd"/>',
				roleId: '${currentRoleId}'
			}
		}"></div>
	</div>
</div>
</form>

<script type="text/javascript">
	seajs.use('question/questionStatis/researcherVerifyAmount');
</script>
</body>
</html>