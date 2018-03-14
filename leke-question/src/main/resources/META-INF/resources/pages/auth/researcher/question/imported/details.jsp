<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>

<c:if test="${not empty questions}">
	<c:forEach items="${questions}" var="que" varStatus="queStat">
<div class="questionContent clearfix j-que-wraper" data-question-id="${que.questionId}" data-question-type-id="${record.questionTypeId}“>
	<div class="quesStyleHead">
		<div>
			<%@ include file="/pages/common/queHeadLeft.jsp"%>
			
			<div class="quesStyleSet">
				<c:if test="${que.questionStatus == 12 || que.questionStatus == 15 || que.questionStatus == 1  || que.questionStatus == 2}">
				<a class="f-csp jQueEdit" data-question-id="${que.questionId}">编辑</a>
				<a class="f-csp jQueDelete" data-question-id="${que.questionId}">删除</a>
				</c:if>
			</div>
		</div>
		<div>
			<span class="f-fr">
				${que.creatorName}
				<fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" />
				导入
			</span>
		</div>
	</div>
	
	<leke:question questionId="${que.questionId}" showCheckbox="false" 
		showSequence="false" showExplain="true" showProperty="false" 
		showPropertyEdit="false" showRightAnswer="true"/>

	<c:if test="${que.questionStatus == 15}">
	<div class="q-reject">
		<label>【退回信息】</label>
		<div>
			${que.importRejectLog.reason}
		</div>
	</div>
	</c:if>
	<div class="f-hr"></div>
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