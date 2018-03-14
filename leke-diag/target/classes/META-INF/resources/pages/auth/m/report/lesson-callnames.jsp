<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/mobile/analyse.css">
</head>
<body>
	<div class="names">
		<c:forEach var="userName" items="${userNames}">
			<span>${userName}</span>
		</c:forEach>
	</div>
</body>
</html>