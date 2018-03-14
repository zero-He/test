<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<c:if test="${not empty questions}">
<ul class="c-errorcorrection toexamine">
<c:forEach items="${questions}" var="que">
	<li class="questionContent" data-qid="${que.questionId}" data-school-stage-id="${que.schoolStageId}" data-subject-id="${que.subjectId}">
		<div class="num">题号：${que.questionId}<span class="date">${que.creatorName} <fmt:formatDate value="${que.createdOn}" pattern="yyyy-MM-dd" /> 录入</span></div>
		<div class="item">
	         <div class="con">
	         	<div class="subject">
					<leke:question questionId="${que.questionId}" showCheckbox="${questionStatus eq 1}" showSequence="false" showExplain="true" showProperty="true" 
						showPropertyEdit="true" showRightAnswer="true"/>
				</div>
	        </div>
	    </div>
        <div class="info">
            <div class="operation">
             	<span class="edit j-btn-edit-checked"><a><i></i>编辑</a></span>
            	<c:if test="${questionStatus eq 1}">
                <span class="ignore j-btn-checked"><a><i></i>审核通过</a></span>
                </c:if>
            </div>
        </div>
	</li>
</c:forEach>
</ul>
<c:if test="${questionStatus eq 1}">
	<div><label><input type="checkbox" class="jSelectAll">全选</label></div>
</c:if>
</c:if>
<c:if test="${empty questions}">
	<p class="m-tips"><i></i>
		<span>
		<c:if test="${questionStatus eq 1}">暂无习题需要审核！</c:if>
		<c:if test="${questionStatus eq 4}">暂无习题通过审核！</c:if>
		</span>
	</p>
</c:if>
<div class="toexaminebtn">
	<c:if test="${questionStatus eq 1 && not empty questions}">
		<input type="button" value="审核通过" class="u-btn u-btn-nm u-btn-bg-orange j-btn-checkeds">
	</c:if>
	<input type="button" value="预览" class="u-btn u-btn-nm u-btn-bg-turquoise j-btn-paper-view">
	<c:if test="${paperStatus eq 1 }">
		<input type="button" value="审核完成" class="u-btn u-btn-nm u-btn-bg-turquoise j-btn-paper-checked">
	</c:if>
</div>
