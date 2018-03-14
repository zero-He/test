<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="questionContent clearfix j-que-que-warp" data-question-id="${que.questionId}">
	<div class="quesStyleHead">
		<%@ include file="/pages/common/queHeadLeft.jsp"%>
		
		<div class="quesStyleSet">
			<span>
				${que.creatorName}
				<fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" />
				录入
			</span>
		<c:if test="${reviewed == false}">
			<a class="f-csp j-btn-review-edit" data-question-id="${que.questionId}">编辑</a>
		</c:if>
		</div>
	</div>
	
	<leke:question questionId="${que.questionId}" showCheckbox="true" 
		 showSequence="false" showExplain="true" showProperty="true" 
		showPropertyEdit="false" showRightAnswer="true"/>
	<hr/>

</div>