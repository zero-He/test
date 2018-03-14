<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>

<c:if test="${not empty records}">
	<ul class="resource-list">
	<c:forEach items="${records}" var="record" varStatus="recordStat">
		<li class="item" 
			data-question-id="${record.questionId}" data-question-type-id="${record.questionTypeId}"
			data-template="${record.template}" data-has-sub="${record.hasSub}" 
			data-subjective="${record.subjective}" data-handwritten="${record.handwritten}">
			<div class="f-fr j-que-btns">
			</div>
			<div class="content">
				<div class="name">
 					<leke:question questionId="${record.resId}" showCheckbox="false" 
						showSequence="false" showExplain="false" showProperty="false" 
						showPropertyEdit="false" showRightAnswer="false"/>
 				</div>
				<div class="info">
					<div class="left">
						<label><b>学科：</b><span>${record.subjectName }</span></label>
						<label><span>${record.typeName }</span></label>
						<label for=""><b>分享人：</b><span>${record.creatorName}</span></label>
						<label><span>${record.createdOnStr }</span></label>
					</div>
					<div class="right">
						<label><i class="iconfont" title="使用次数">&#xf00c3; </i><c:out value="${record.usedCount }" default="0"/></label>
						<label class="j-praise"><i class="iconfont" title="点赞次数">&#xf013c; </i> 
						<span class="j-praise-count"><c:out value="${record.praiseCount }" default="0"/></span></label>
					</div>
				</div>
			</div>
		</li>
	</c:forEach>
	</ul>
	<div class="quePage">
		<%@ include file="/pages/common/page.jsp"%>
	</div>
</c:if>
<c:if test="${empty records}">
	<div class="c-searchnull"><i></i><p>当前没有教学资源！</p></div>
</c:if>
