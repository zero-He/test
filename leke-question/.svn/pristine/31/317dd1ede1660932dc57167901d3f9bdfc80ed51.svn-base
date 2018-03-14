<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="listoperatingarea">
    <span class="checkbox"><input type="checkbox" class="selectedAll" id="selectedAll"></span>
    <div class="f-fr f-mr5">
		<input type="button" class="c-resouce-btnhollow j-add-batch-quecart" value="加入习题篮" >
		<input type="button" class="c-resouce-btnhollow j-batch-favorite" value="收藏" >
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
				<span>${record.createdOnStr }</span>
				<span>引用次数:<c:out value="${record.usedCount }" default="0"/></span>
				<span>收藏次数:<c:out value="${record.favCount }" default="0"/></span>
			</div>
			<div class="c-maxoperationlistbtn">
				<a class="name" href="${initParam.questionServerName}/auth/common/question/view.htm?questionId=${record.resId}&curScope=2" target="_blank">
					<i class="see ico">查看</i>
				</a>
				<i class="exercises j-add-quecart ico">加入习题篮</i>
				<i class="nocollection j-favorite ico">收藏</i>
				<i class="like j-praise ico">点赞</i>	
			</div>
		</div>
	</li>
	</c:forEach>
</ul>
<%@ include file="/pages/common/quePage.jsp"%>
<c:if test="${empty records}">
	<div class="c-searchnull"><i></i><span>当前没有教学资源!<em style="color: red;">你可以试试其他筛选条件或者清空教材章节</em></span></div>
</c:if>