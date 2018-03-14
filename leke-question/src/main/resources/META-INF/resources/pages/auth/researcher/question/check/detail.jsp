<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="questionContent clearfix j-que-que-warp" data-question-id="${que.questionId}"
	data-school-stage-id="${que.schoolStageId}" data-subject-id="${que.subjectId}">
	<div class="quesStyleHead">
		<%@ include file="/pages/common/queHeadLeft.jsp"%>
		
		<div class="quesStyleSet">
			<span>
				${que.creatorName}
				<fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" />
				录入
			</span>
		<c:if test="${statusType == 96}">
			<a class="f-csp j-btn-edit s-c-turquoise" data-question-id="${que.questionId}">编辑</a>
			<a class="f-csp j-btn-reject s-c-turquoise" data-question-id="${que.questionId}">退回</a>
			<a class="f-csp j-btn-del s-c-turquoise" data-question-id="${que.questionId}">删除</a>
		</c:if>
		<c:if test="${statusType == 97}">
			<a class="f-csp j-btn-edit-checked s-c-turquoise" data-question-id="${que.questionId}">编辑</a>
			<a class="f-csp j-btn-disable s-c-turquoise" data-question-id="${que.questionId}">禁用</a>
		</c:if>
		</div>
	</div>
	
	<leke:question questionId="${que.questionId}" showCheckbox="true" 
		 showSequence="false" showExplain="true" showProperty="true" 
		showPropertyEdit="${statusType == 96}" showRightAnswer="true"/>
	<hr/>

</div>