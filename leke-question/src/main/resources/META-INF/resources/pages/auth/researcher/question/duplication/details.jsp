<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>

<c:if test="${not empty groups}">
	<c:set var="grpPage" value="${page}"/>
	<c:forEach items="${groups}" var="grp">
		<div class="j-que-group" data-dup-group-id="${grp.record.questionId}" data-question-type-id="${record.questionTypeId}">
			<c:set var="que" value="${grp.record}"/>
			<%@ include file="./detail.jsp"%>
			
			<div>
				<div class="f-fl f-w70">
					<label class="f-dib f-fwb f-mt20">相似习题：</label>
				</div>
				<div class="f-bfc j-que-group-sims">
				<c:set var="similarities" value="${grp.similarities}"/>
				<%@ include file="./similarities.jsp"%>
				</div>
			</div>
		</div>
	</c:forEach>
	
	<c:set var="page" value="${grpPage}"/>
	<%@ include file="/pages/common/quePage.jsp"%>
</c:if>
<c:if test="${empty groups}">
	<p class="m-tips">
		<i></i>
		<span>
		<locale:message code="que.question.noQuestion"></locale:message>
		</span>
	</p>
</c:if>