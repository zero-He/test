<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="m-table">
<c:if test="${not empty records}">
	<table class="c-exercisestable">
		<tbody>
			<tr>
				<th width="20px"><span class="checkbox"><input type="checkbox" class="selectedAll" id="selectedAll"></span></th>
				<th></th>
				<th width="260">
					<div class="f-fr f-mr5">
						<input type="button" class="c-resouce-btnhollow j-add-batch-quecart" value="加入习题篮" >
					</div>
				</th>
			</tr>
			<c:forEach items="${records}" var="record" varStatus="recordStat">
			<tr class="item" data-question-id="${record.resId}" data-question-type-id="${record.questionTypeId}">
				<td><span class="checkbox"> <input type="checkbox" name="resId" value="${record.resId}"></span></td>
				<td><i class="c-restype question"></i>
					<div class="item maxwidthexercises">
						<leke:question questionId="${record.resId}" showCheckbox="false" 
							showSequence="false" showExplain="false" showProperty="false" 
							showPropertyEdit="false" showRightAnswer="false"/>
						<div class="remarks">
							<span>贡献者: ${record.creatorName}</span>
							<span>${record.attrs.assoc.createdOnStr}</span>
							<span>引用次数:<c:out value="${record.usedCount }" default="0"/></span>
							<span>收藏次数:<c:out value="${record.favCount }" default="0"/></span>
						</div>
					</div>
				</td>
				<td width="260">
					<div class="c-maxoperationlistbtn">
						<c:if test="${record.attrs.assoc != null }">
						<a target="_blank"
							href="${initParam.questionServerName}/auth/common/question/view.htm?questionId=${record.resId}&curScope=9&action=override&curFmsSchId=${record.attrs.assoc.schoolId}" title="查看">
							<i class="see"></i>
						</a>
						<i class="edit j-edit" title="编辑" 
							data-url="${initParam.questionServerName}/auth/common/question/questionEdit.htm?questionId=${record.resId}&curScope=9&action=override&curFmsSchId=${record.attrs.assoc.schoolId}&crossDomain=true"></i>
						</c:if>
					</div>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="/pages/common/quePage.jsp"%>
</c:if>
<c:if test="${empty records}">
	<p class="m-tips">
		<i></i>
		<span>
		当前没有教学资源!
		</span>
	</p>
</c:if>
</div>