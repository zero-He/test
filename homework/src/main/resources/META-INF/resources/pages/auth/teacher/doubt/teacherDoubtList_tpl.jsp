<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<c:if test="${not empty page.dataList}">
	<c:forEach items="${page.dataList}" var="doubt">
		<c:set var="asName"><locale:message code="homework.doubt.ask"/></c:set>
		<c:set var="cTime" value=""></c:set>
		<c:if test="${not empty doubt.explains}">
			<c:forEach items="${doubt.explains}" var="explain">
				<c:if test="${doubt.createdBy eq explain.createdBy}">
					<c:set var="asName"><locale:message code="homework.doubt.askForExplanation"/></c:set>
					<c:set var="cTime" value="${explain.createOnString}"></c:set>
				</c:if>
			</c:forEach>
		</c:if>

		<div id="cutsubCon0" class="quesTotalCon" style="display: block;">
			<div class="test" style="padding: 0 20px">
				<div class="quesStyleHead">
					<span>【<locale:message code="homework.doubt.ask"/>】${doubt.doubtTitle} </span>
					&nbsp;&nbsp;
					<div class="quesStyleSet">
						<em>${asName }<locale:message code="homework.doubt.ze"/>：</em> <em>${doubt.userName}</em> <em>${asName }<locale:message code="homework.doubt.time"/>：</em> <em> <c:choose>
								<c:when test="${cTime != '' }">
							    		${cTime }
							    	</c:when>
								<c:otherwise>
							    		${doubt.createdOnString}
							    	</c:otherwise>
							</c:choose>
						</em>
					</div>
				</div>
				<div class="timu">
					<div class="timu-style">
						<c:if test="${doubt.doubtContent ne ''}">
							<div class="f-clearfix">
								<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.describe"/>】</span>
								<div class="f-bfc f-fs14 f-lwb">${doubt.doubtContent}</div>
							</div>
						</c:if>
						<c:if test="${doubt.questionId ne 0}">
							<div class="f-clearfix">
								<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.content"/>】</span>
								<div class="f-bfc">
									<leke:question questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="true"
										showProperty="false" showPropertyEdit="false" showRightAnswer="true" showInputArea="false" />
								</div>
							</div>
						</c:if>
					</div>
					<div class="cancle">
						<input type="button" class="u-btn u-btn-nm u-btn-bg-orange doubtDetail" value="<locale:message code="homework.common.toview"/>" data-id="${doubt.doubtId}">
					</div>
				</div>
			</div>
		</div>

	</c:forEach>
	<%@ include file="/pages/common/page.jsp"%>
</c:if>

<c:if test="${empty page.dataList}">
	<p class="m-tips">
		<i></i>
		<span><locale:message code="homework.doubt.noAsk"/></span>
	</p>
</c:if>