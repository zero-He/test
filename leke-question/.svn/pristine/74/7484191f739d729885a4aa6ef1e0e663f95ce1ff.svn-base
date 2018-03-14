<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>

<c:if test="${not empty questions}">
	<c:forEach items="${questions}" var="que" varStatus="queStat">
		<div class="questionContent clearfix j-que-que-warp" data-qid="${que.questionId}"
			data-school-stage-id="${que.schoolStageId}" data-subject-id="${que.subjectId}">
			<div class="quesStyleHead">
				<%@ include file="/pages/common/queHeadLeft.jsp"%>
				
				<div class="quesStyleSet">
					<span>
						${que.creatorName}
						<fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" />
						录入
					</span>
				<c:if test="${statusType == 15 || statusType == 17 || statusType == 4}">
					<a class="f-csp j-btn-edit-checked s-c-turquoise" data-question-id="${que.questionId}">编辑</a>
				</c:if>
				<c:if test="${statusType == 1}">
					<a class="f-csp j-btn-edit s-c-turquoise" data-question-id="${que.questionId}">编辑</a>
					<a class="f-csp j-btn-checked s-c-turquoise" data-question-id="${que.questionId}">审核通过</a>
					<a class="f-csp j-btn-reject s-c-turquoise" data-question-id="${que.questionId}">退回</a>
				</c:if>
					<a class="f-csp j-btn-disable s-c-turquoise" data-question-id="${que.questionId}">禁用</a>
				</div>
			</div>
			
			<leke:question questionId="${que.questionId}" showCheckbox="${statusType == 1 || statusType == 17}" 
				 showSequence="false" showExplain="true" showProperty="true" 
				showPropertyEdit="${statusType == 1}" showRightAnswer="true"/>
			<hr/>
	
		</div>
	</c:forEach>
	
	<%@ include file="/pages/common/quePage.jsp"%>
</c:if>
<c:if test="${empty questions}">
	<div class="m-tips jQueEmpty">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message>
		</span>
	</div>
</c:if>