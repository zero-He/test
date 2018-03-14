<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>乐课网 - 我们的快乐课堂</title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/homework/homework.css">
	<link rel="stylesheet" href="${assets}/styles/homework/assign.css">
</head>
<body class="m-middlepage">
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="g-mn">
		<div class="c-transfer-box">
			<h3 class="tr-tit">
				${middle.title}
			</h3>

			<c:if test="${not empty middle.message}">
				<p class="tr-notes">
					${middle.message}
					<c:if test="${not empty middle.lekeVal}">，乐豆+${middle.lekeVal}</c:if>
					<c:if test="${not empty middle.expVal}">，经验+${middle.expVal}</c:if>
				</p>
			</c:if>

			<p class="tr-tips">
				<c:forEach var="button" items="${middle.buttons}" varStatus="status">
					<a class="item" href="${button.link}">${button.name}</a>
					<c:if test="${status.index > 0}"><span class="item-line"></span></c:if>
				</c:forEach>
				<c:if test="${middle.closeWindow}">
					<span class="item-line"></span>
					<a class="item" href="javascript:window.close();">关闭窗口</a>
				</c:if>
			</p>
		</div>
	</div>
</div>

<div class="g-foot">
	<%@ include file="/pages/common/foot.jsp" %>
</div>
</body>
</html>
