<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="questionContent j-que-warp" data-question-id="${que.questionId}">
	<div class="quesStyleHead">
		<div>
			<%@ include file="/pages/common/queHeadLeft.jsp"%>
			
			<div class="quesStyleSet">
				<a class="j-btn-not-dup s-c-turquoise f-csp" data-question-id="${que.questionId}">非重复</a>
				<c:if test="${que.questionStatus == 1 || que.questionStatus == 2 || que.questionStatus == 12 || que.questionStatus == 15}">
					<a class="j-btn-del s-c-turquoise f-csp" data-question-id="${que.questionId}">删除</a>
				</c:if>
				<a class="j-btn-disable s-c-turquoise f-csp" data-question-id="${que.questionId}">禁用</a>
				<a class="j-btn-disable-others s-c-turquoise f-csp" data-question-id="${que.questionId}">禁用其它</a>
			</div>
		</div>
		<div>
			<span class="f-fr" data-status="${que.questionStatus}">
				${que.creatorName}
				<fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" />
				添加
			</span>
		</div>
	</div>
	
	<div class="f-ml10 f-mr10">
	<leke:question questionId="${que.questionId}" showCheckbox="false" 
		showSequence="false" showExplain="true" showProperty="false" 
		showPropertyEdit="false" showRightAnswer="true"/>
	</div>
	
</div>