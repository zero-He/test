<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<c:if test="${not empty questions}">
	<c:forEach items="${questions}" var="que" varStatus="queStat">
	<div class="questionContent clearfix j-que-que-warp">
		<div class="quesStyleHead">
			<%@ include file="/pages/common/queHeadLeft.jsp"%>
			
			<div class="quesStyleSet">
				<c:if test="${query.operateType == 1}">
				录入时间：<em><fmt:formatDate value="${que.inputLog.createdOn}" pattern="yyyy-MM-dd" /></em>
				</c:if>
				<c:if test="${query.operateType == 4}">
				审核时间：<em><fmt:formatDate value="${que.checkLog.createdOn}" pattern="yyyy-MM-dd" /></em>
				</c:if>
				<c:if test="${query.operateType == 7}">
				校对时间：<em><fmt:formatDate value="${que.verifyLog.createdOn}" pattern="yyyy-MM-dd" /></em>
				</c:if>
			</div>
		</div>
		
		<leke:question questionId="${que.questionId}" showCheckbox="true" 
			 showSequence="false" showExplain="true" showProperty="true" 
			showPropertyEdit="false" showRightAnswer="true"/>
		<hr/>
	</div>
	</c:forEach>
	
	<%@ include file="/pages/common/quePage.jsp"%>
</c:if>
<c:if test="${empty questions}">
	<p class="m-tips">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message>
		</span>
	</p>
</c:if>