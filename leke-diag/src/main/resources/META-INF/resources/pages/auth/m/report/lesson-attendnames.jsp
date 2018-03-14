<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="title" value="名单" />
<c:if test="${status == 0}"><c:set var="title" value="非全勤名单" /></c:if>
<c:if test="${status == 2}"><c:set var="title" value="缺勤名单" /></c:if>
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