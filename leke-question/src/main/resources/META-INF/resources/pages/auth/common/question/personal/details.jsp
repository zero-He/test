<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="c-resource-list">
	<div class="listoperatingarea">
		<span class="checkbox"><input type="checkbox" class="selectedAll" id="selectedAll"></span>
		<div class="f-fr f-mr5">
			<input type="button" class="c-resouce-btnhollow j-add-batch-quecart" value="加入习题篮">
			<input type="button" class="c-resouce-btnhollow batchMove" value="移动">
			<c:if test="${userResGroupId != null}">
				<input type="button" class="c-resouce-btnhollow batchRemove" value="移出分组">
			</c:if>
			<c:if test="${shareScope == 4}">
				<input type="button" class="c-resouce-btnhollow batchUnfavorite" value="取消收藏">
			</c:if>
		</div>
	</div>
	<ul>
		<c:forEach items="${records}" var="record" varStatus="recordStat">
		<li class="item" data-question-id="${record.resId}" data-question-type-id="${record.questionTypeId}"  data-share-school="" data-share-platform="">
			<div class="resourcelist">
				<span class="checkbox"> <input type="checkbox" name="resId" value="${record.resId}"></span>
				<div class="info">
					<i class="c-restype question"></i>
					<div class="resourcecon">
						<leke:question questionId="${record.resId}" showCheckbox="false" showSequence="false" 
										showExplain="false" showProperty="false" showPropertyEdit="false" showRightAnswer="false"/>
					</div>
				</div>
			</div>
			<div class="bottomoperation">
				<div class="attr"> <span>${record.createdOnStr }</span></div>
				<div class="c-maxoperationlistbtn">
					<i class="edit ico j-edit">编辑</i>
					<i class="ico exercises j-add-quecart" >加入习题篮</i>
					<a  target="_blank" href="${ctx}/auth/common/question/view.htm?questionId=${record.resId}&curScope=${shareScope}&curResGrpId=${userResGroupId}">
						<i class="see ico">查看</i>
					</a>
					<c:if test="${shareScope == 4}">
					<a class="j-unfavorite"><i class="ico collection">取消收藏</i></a>
					</c:if>
					<div class="m-btns c-btns f-fr f-mt11">
						<div class="init-btn"><a href="#">更多</a><b><i></i></b></div>
						<menu>
							<li><a href="#" class="j-move">移动</a></li>
							<li title="复制添加"><a href="${initParam.questionServerName}/auth/common/question/add/index.htm?action=create&curScope=1&questionId=${record.resId}" 
								target="_blank" class="j-copy-add">复制添加</a>
							</li>
							<c:if test="${not empty userResGroupId}">
								<li title="移出分组"><a href="#" class="j-moveOut">移出分组</a></li>
							</c:if>	
							<c:if test="${shareScope  == 1 }">
								<li><a class="j-disable">删除</a></li>
							</c:if>
							<c:if test="${shareScope  == 1 && currentRoleId == 101}">
								<li><a class="j-share">分享</a></li>
							</c:if>
						</menu>
					</div>
				</div>
		</li>
		</c:forEach>
	</ul>         
</div>
<%@ include file="/pages/common/quePage.jsp"%>
</div>
<c:if test="${empty records}">
<div class="c-searchnull"><i></i><p>当前没有教学资源！</p></div>
</c:if>