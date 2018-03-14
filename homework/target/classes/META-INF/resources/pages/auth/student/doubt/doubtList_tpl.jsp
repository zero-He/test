<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=20171115">
<c:if test="${not empty page.dataList}">
	<c:forEach items="${page.dataList}" var="doubt">

		<div class="quesStyleHead">
			<span>【<locale:message code="homework.doubt.ask" />】 ${doubt.doubtTitle}</span>
			<div class="quesStyleSet">
				<!-- <em>提问者：</em> <em>${doubt.userName}</em> -->
				<em><locale:message code="homework.doubt.askTime" />：</em> <em>${doubt.createdOnString}</em>
			</div>
		</div>
		<div class="timu">
			<div class="timu-style">
				<c:if test="${doubt.doubtContent ne ' '}">
					<div class="f-clearfix">
						<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.describe" />】</span>
						<div class="f-bfc f-fs14 f-lwb">${doubt.doubtContent}</div>
					</div>
				</c:if>
				<c:if test="${doubt.questionId ne null}">
					<div class="f-clearfix">
						<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.content" />】</span>
						<div class="f-bfc">
							<leke:question questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="false"
								showProperty="false" showPropertyEdit="false" showRightAnswer="false" showInputArea="false" />
						</div>
					</div>
				</c:if>
			</div>
			<div class="cancle">
				<span>${doubt.isSoveString}</span>
				<input type="button" class="u-btn u-btn-nm u-btn-bg-orange doubtDetail" value="<locale:message code="homework.common.toview" />" data-id="${doubt.doubtId}">
			</div>
		</div>
	</c:forEach>
	<%@ include file="/pages/common/page.jsp"%>
</c:if>

<c:if test="${empty page.dataList}">
	<p class="m-tips">
		<i></i>
		<span><locale:message code="homework.doubt.noAsk" />！</span>
	</p>
</c:if>