<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="listoperatingarea">
    <span class="checkbox"><input type="checkbox" class="selectedAll" id="selectedAll"></span>
    <div class="f-fr f-mr5">
		<c:if test="${query.status == 0}">
			<input type="button" class="c-resouce-btnhollow j-batch-pass" value="通过" />
			<input type="button" class="c-resouce-btnhollow j-batch-reject" value="不通过" />
		</c:if>
		<c:if test="${query.status == 3}">
			<input type="button" class="c-resouce-btnhollow j-batch-pass" value="改为已通过" />
		</c:if>
		<input type="button" class="c-resouce-btnhollow j-batch-remove" value="移出" />
	</div>
</div>

<ul>
	<c:forEach items="${records}" var="record" varStatus="recordStat">
	<li class="item" data-question-id="${record.resId}" data-question-type-id="${record.questionTypeId}">
		<div class="resourcelist">
			<span class="checkbox"> <input type="checkbox" name="resId" value="${record.resId}"></span>
			<div class="info"><i class="c-restype question"></i>
	          <div class="resourcecon">
	              <leke:question questionId="${record.resId}" showCheckbox="false" 
							showSequence="false" showExplain="false" showProperty="false" 
							showPropertyEdit="false" showRightAnswer="false"/>
	          </div>
          	</div>
       	</div>
       	<div class="bottomoperation">
			<div class="attr">
				<span>贡献者: ${record.creatorName}</span>
				<span>${record.attrs.assoc.createdOnStr }</span>
				<span>引用次数:<c:out value="${record.usedCount }" default="0"/></span>
				<span>审核状态：${record.attrs.assoc.statusStr}</span>
				<c:if test="${record.attrs.assoc.status != 0}">
					<span  class="source" title="${record.attrs.assoc.editLog}">${record.attrs.assoc.editLog}</span>
					<span>审核时间：${record.attrs.assoc.createdOnStr}</span>
				</c:if>
			</div>
			<div class="c-maxoperationlistbtn">
				<c:if test="${record.attrs.assoc.status == 0}">
					<a title="查看" href="${ctx}/auth/schoolResearcher/question/school/checkView.htm?questionId=${record.resId}" target="_blank">
					<i class="see ico">查看</i></a>
					<i class="j-pass adopt ico" title="通过">通过</i>
					<i class="j-reject notthrough ico" title="不通过">不通过</i>
				</c:if>
				<c:if test="${record.attrs.assoc.status == 3}">
					<i class="j-pass adopt ico" title="改为已通过">改为已通过</i>
				</c:if>
				<i class="j-remove move ico" title="移除">移除</i>
			</div>
		</div>
	</li>
	</c:forEach>
</ul>
<%@ include file="/pages/common/quePage.jsp"%>
<c:if test="${empty records}">
	<p class="m-tips">
		<i></i>
		<span>
		当前没有教学资源!
		</span>
	</p>
</c:if>