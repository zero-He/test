<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="questionContent clearfix j-que-que-warp" data-question-id="${que.questionId}">
	<div class="quesStyleHead">
		<div>
			<%@ include file="/pages/common/queHeadLeft.jsp"%>
			
			<div class="quesStyleSet">
				<a class="j-btn-tchshare-pass f-csp s-c-turquoise" data-question-id="${que.questionId}">通过</a>
				<a class="j-btn-tchshare-reject f-csp s-c-turquoise" data-question-id="${que.questionId}">不通过</a>
			</div>
		</div>
		<div>
			<span class="f-fr">
				${que.schoolName} ${que.creatorName}
				<fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" />
				录入
			</span>
		</div>
	</div>
	
	<leke:question questionId="${que.questionId}" showCheckbox="true" 
		 showSequence="false" showExplain="true" showProperty="true" 
		showPropertyEdit="false" showRightAnswer="true"/>
	<hr/>

</div>