<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<c:if test="${not empty questions}">
	<c:forEach items="${questions}" var="que" varStatus="queStat">
	<div class="questionContent clearfix j-que-que-warp">
		<div class="quesStyleHead">
			<%@ include file="/pages/common/queHeadLeft.jsp"%>
			
			<div class="quesStyleSet">
			录入时间：<em><fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" /></em>
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
	</c:forEach>
	
	<%@ include file="/pages/common/quePage.jsp"%>
</c:if>
<c:if test="${empty questions}">
	<div>没有找到相关题目！</div>
</c:if>