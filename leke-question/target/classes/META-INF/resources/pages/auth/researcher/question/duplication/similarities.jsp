<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>

<c:if test="${not empty similarities.dataList}">
	<c:forEach items="${similarities.dataList}" var="que">
		<%@ include file="./detail.jsp"%>
	</c:forEach>
	<c:set var="page" value="${similarities}"/>
	<%@ include file="/pages/common/page.jsp"%>
</c:if>
<c:if test="${empty similarities.dataList}">
	<p class="m-tips">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message>
		</span>
	</p>
</c:if>