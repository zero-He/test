<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<c:if test="${not empty questions}">
	<c:forEach items="${questions}" var="que" varStatus="queStat">
	<div class="tests queWrapper j-que-que-warp">
		<div class="testQuestionsTotal">
			<span class="ctrl">
				<a class="btnQueIgnore s-c-turquoise" href="javascript:void(0)" data-id="${que.questionId}">忽略</a>
				<a class="btnQueDisable s-c-turquoise" href="javascript:void(0)" data-id="${que.questionId}">禁用</a>
				<a class="btnQueEdit s-c-turquoise" href="javascript:void(0)" data-id="${que.questionId}">编辑</a>
			</span>
			<span class="head">
				<%@ include file="/pages/common/queHeadLeft.jsp"%>
			</span>
		</div>
		
		<div class="timu">
			<div class="timu-style">
				<leke:question questionId="${que.questionId}" showCheckbox="true" 
					 showSequence="false" showExplain="true" showProperty="true" 
					showPropertyEdit="false" showRightAnswer="true"/>
				<div>
				<c:if test="${not empty que && not empty que.feedback}">
					<c:forEach items="${que.feedback}" var="f" varStatus="fStat">
						纠错${fStat.index + 1 }：纠错类型:${f.questionFeedbackTypeStr} 详情:${f.feedbackContent }(${f.userName}  ${f.createdOnStr})&nbsp;&nbsp;
						<a class="btnQueIgnoreFeedback s-c-turquoise" href="javascript:void(0)" data-id="${que.questionId}" data-question-feedback-id="${f.questionFeedbackId }">忽略</a><br>
					</c:forEach>
				</c:if>
				<div class="comment">
					<a href="javascript:void(0)" class="comt1 f-csd" title="组卷次数">
						(<span class="spPaperCount">${que.usedCount == null ? 0 : que.usedCount}</span>)
					</a>
					<a href="javascript:void(0)" class="comt2 btnPraise f-csd" title="点赞次数">
						(<span class="spPraiseCount">${que.praiseCount == null ? 0 : que.praiseCount}</span>)
					</a>
					<a href="javascript:void(0)" class="comt3 btnShowComments f-csd" title="评论次数">
						(<span class="spCommentCount">${que.commentCount == null ? 0 : que.commentCount}</span>)
					</a>
				</div></div>
				<hr/>
			</div>
		</div>
	</div>
	</c:forEach>
</c:if>
<%@ include file="/pages/common/quePage.jsp"%>
<c:if test="${empty questions}">
	<p class="m-tips">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message>
		</span>
	</p>
</c:if>
