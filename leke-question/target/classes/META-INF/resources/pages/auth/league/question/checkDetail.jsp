<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<c:if test="${not empty questions}">
	<c:forEach items="${questions}" var="que" varStatus="queStat">
	<div class="tests queWrapper" 
		data-question-id="${que.questionId}" data-question-type-id="${que.questionTypeId}" data-league-question-id=${que.sharedLeagues[0].leagueQuestionId }>
		<div class="testQuestionsTotal">
			<span class="ctrl">
				<c:if test="${leagueQuestionState == 1}">
					<a class="f-csp jCheckBtn" data-check-state="2"><locale:message code="que.question.btn.pass"></locale:message></a>
					<a class="f-csp jCheckBtn" data-check-state="3"><locale:message code="que.question.btn.return"></locale:message></a>
				</c:if>
				<c:if test="${leagueQuestionState != 1}">
					<a class="f-csp jDisable"><locale:message code="que.question.btn.disable"></locale:message></a>
				</c:if>
			</span>
			<span class="head">
				<%@ include file="/pages/common/queHeadLeft.jsp"%>
				<c:if test="${que.creatorName != null}">
					<locale:message code="que.question.inputTeacher"></locale:message>：${que.creatorName}
				</c:if>
				<c:if test="${que.sharedLeagues != null}">
					<locale:message code="que.question.shareLeague"></locale:message>：
					<c:forEach var="league" items="${que.sharedLeagues}">
					${league.leagueName}
					</c:forEach>
				</c:if>
			</span>
		</div>
		
		<div class="timu">
			<div class="timu-style">
				<leke:question questionId="${que.questionId}" showCheckbox="true" 
					 showSequence="false" showExplain="true" showProperty="true" 
					showPropertyEdit="false" showRightAnswer="true"/>
			</div>
		</div>
	</div>
</c:forEach>
	
	<%@ include file="/pages/common/quePage.jsp"%>
	
	<c:if test="${leagueQuestionState == 1}">
		<label>
			<input type="checkbox" class="jSelectAll">
			<span><locale:message code="que.question.selectAll"></locale:message></span>
		</label>
		<a class="u-btn u-btn-nm u-btn-bg-turquoise jCheckPass" ><locale:message code="que.question.btn.pass"></locale:message></a>
	</c:if>
</c:if>
<c:if test="${empty questions}">
	<div><p class="m-tips">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message></p>
		</span>
	</div>
</c:if>