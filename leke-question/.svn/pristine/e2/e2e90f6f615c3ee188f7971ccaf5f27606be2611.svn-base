<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<c:if test="${not empty comments}">
	<div>
		【评论】：
	</div>
	
	<div>
		<c:forEach items="${comments}" var="cmt" varStatus="cmtStat">
		<div class="u-comment-wrapper">
			<div style="float:left;width: 90px;text-align:right;">${cmt.userName}</div>
			<div style="margin-left:100px;">${cmt.comment}</div>
		</div>
		</c:forEach>
	</div>
	
	<%@ include file="/pages/common/page.jsp"%>
</c:if>
<c:if test="${empty comments}">
	<div>没有相关评论！</div>
</c:if>