<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="c-resource-list">
	<ul>
		<c:forEach items="${records}" var="record" varStatus="recordStat">
		<li class="item" data-question-id="${record.resId}" data-share-log-id="${record.attrs.check.shareLogId}" data-question-type-id="${record.questionTypeId}" >
			<div class="resourcelist">
				<div class="info">
					<i class="c-restype question"></i>
					<div class="resourcecon">
						<leke:question questionId="${record.resId}" showCheckbox="false" showSequence="false" 
										showExplain="false" showProperty="false" showPropertyEdit="false" showRightAnswer="false"/>
					</div>
				</div>
			</div>
			<div class="bottomoperation">
				<div class="attr">
					<span>分享时间：${record.attrs.check.createdOnStr} </span>
                 	<span>分享范围：${ record.attrs.check.shareScopeStr}</span>
					<c:if test="${attrs.check.shareScope eq 6}">
                 	<span>收藏次数：<c:out value="${record.favCount}" default="0"></c:out></span>
                 	<span>引用次数：<c:out value="${record.usedCount}" default="0"></c:out></span>
                	</c:if>
                	<c:if test="${record.attrs.check.shareScope eq 2 && record.attrs.assoc != null }">
	                <span>状态：<c:out value="${record.attrs.assoc.toStatusStr}"></c:out></span>
	                <c:if test="${record.attrs.assoc.toStatus != 0 }">
	                	<span class="source" title="${record.attrs.assoc.checkNote}"><c:out value="${record.attrs.assoc.checkNote}" ></c:out></span>
	                </c:if>
	                </c:if>
				
				</div>
				<div class="c-maxoperationlistbtn">
					 <a href="${ctx}/auth/common/question/view.htm?questionId=${record.resId}" target="_blank"><i class="see ico">查看</i></a>
	                 <i class="move ico j-remove">移除</i>
				</div>
			</div>

		</li>
		</c:forEach>
	</ul
</div>
<%@ include file="/pages/common/quePage.jsp"%>
<c:if test="${empty records}">
<div class="c-searchnull"><i></i><p>当前没有教学资源！</p></div>
</c:if>