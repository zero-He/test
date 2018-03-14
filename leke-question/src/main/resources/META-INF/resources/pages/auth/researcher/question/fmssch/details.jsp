<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="m-table">
<c:if test="${not empty records}">
	<table class="c-exercisestable">
		<tbody>
			<c:forEach items="${records}" var="record" varStatus="recordStat">
			<tr class="item" data-question-id="${record.resId}" data-school-id="${record.attrs.assoc.schoolId}" data-question-type-id="${record.questionTypeId}">
				<td><i class="c-restype question"></i>
					<div class="item">
						<div class="item maxwidthexercises">
							<leke:question questionId="${record.resId}" showCheckbox="false" 
								showSequence="false" showExplain="false" showProperty="false" 
								showPropertyEdit="false" showRightAnswer="false"/>
						</div>
						<div class="remarks">
							<span>贡献者: ${record.creatorName}</span>
							<span>${record.attrs.assoc.createdOnStr }</span>
							<span>引用次数:<c:out value="${record.usedCount }" default="0"/></span>
						</div>
					</div>
				</td>
				<td width="290">
					<div class="c-maxoperationlistbtn">
						<i class="nocollection j-favorite" title="收藏"></i>
						<i class="exercises j-add-quecart" title="加入习题篮"></i>
						<i class="like j-praise" title="点赞"></i>
						<a href="${ctx}/auth/common/question/view.htm?questionId=${record.resId}&curScope=9" target="_blank" title="查看"><i class="see" ></i></a>
						<i class="move j-removeFmSch" title="移除"></i>
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
		当前没有教学资源!<em style="color: red;">你可以试试其他筛选条件或者清空教材章节</em>
		</span>
	</p>
</c:if>
</div>