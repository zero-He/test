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
			导入：${que.creatorName}
			<em><fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" /></em>
			<c:if test="${statusType == 96}">
			<a class="jReject" data-question-id="${que.questionId}" style="cursor: pointer;">退回</a>
			</c:if>
			</div>
		</div>
		
		<leke:question questionId="${que.questionId}" showCheckbox="true" 
			 showSequence="false" showExplain="true" showProperty="true" 
			showPropertyEdit="${statusType == 96}" showRightAnswer="true"/>
		<hr/>
	</div>
	</c:forEach>
	
	<%@ include file="/pages/common/quePage.jsp"%>
	
</c:if>
<c:if test="${empty questions}">
<div class="jQueEmpty">
	<div class="m-tips">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message>
		</span>
	</div>
</div>
</c:if>
