<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="questionContent clearfix j-que-que-warp" data-question-id="${que.questionId}">
	<div class="quesStyleHead">
		<%@ include file="/pages/common/queHeadLeft.jsp"%>
		
		<div class="quesStyleSet">
		录入时间：<em><fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" /></em>
		<!-- 
		<a class="f-csp btnChangeQueType" data-question-id="${que.questionId}"
			data-subject-id="${que.subjectId}" data-question-type-id="${que.questionTypeId}">修改题型</a>
		 -->
		<a class="f-csp btnQueEdit" data-question-id="${que.questionId}">编辑</a>
		<a class="f-csp btnQueDel" data-question-id="${que.questionId}">删除</a>
		</div>
	</div>
	
	<leke:question questionId="${que.questionId}" showCheckbox="true" 
		 showSequence="false" showExplain="true" showProperty="true" 
		showPropertyEdit="false" showRightAnswer="true"/>
		
	<c:if test="${que.questionStatus == 2}">
	<div class="q-reject">
		<label>【退回信息】</label>
		<div>
			${que.rejectLog.reason}
		</div>
	</div>
	</c:if>
	<hr/>
</div>