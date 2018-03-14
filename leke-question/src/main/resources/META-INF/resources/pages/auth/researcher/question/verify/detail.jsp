<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="questionContent clearfix j-que-que-warp" data-question-id="${que.questionId}">
	<div class="quesStyleHead">
		<%@ include file="/pages/common/queHeadLeft.jsp"%>
		
		<div class="quesStyleSet">
			<span>
				${que.checkLog.userName}
				<fmt:formatDate value="${que.checkLog.createdOn}" pattern="yyyy-MM-dd" />
				审核
			</span>
			<a class="j-btn-verify-edit f-csp" data-question-id="${que.questionId}">编辑</a>
			<a class="j-btn-disable f-csp" data-question-id="${que.questionId}">禁用</a>
		</div>
	</div>
	
	<leke:question questionId="${que.questionId}" showCheckbox="true" 
		 showSequence="false" showExplain="true" showProperty="true" 
		showPropertyEdit="false" showRightAnswer="true"/>
	<hr/>

</div>