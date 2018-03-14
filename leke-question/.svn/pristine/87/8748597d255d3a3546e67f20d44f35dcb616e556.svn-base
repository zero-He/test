<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>

<c:if test="${not empty questions}">
	<c:forEach items="${questions}" var="que" varStatus="queStat">
	<%@ include file="./detail.jsp"%>
	</c:forEach>
	
	<%@ include file="/pages/common/quePage.jsp"%>
	
	<c:if test="${reviewed == false}">
		<div class="f-tac">
			<label class="f-mr20">
				<input type="checkbox" class="j-select-all"/>
				全选
			</label>
			
			<button class="u-btn u-btn-nm u-btn-bg-turquoise j-btn-review">审核通过</button>
		</div>
	</c:if>
</c:if>
<c:if test="${empty questions}">
	<p class="m-tips">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message>
		</span>
	</p>
</c:if>