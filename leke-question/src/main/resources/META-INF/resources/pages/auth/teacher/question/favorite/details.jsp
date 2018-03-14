<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>

<c:if test="${not empty records}">
	<ul class="resource-list">
	<c:forEach items="${records}" var="record" varStatus="recordStat">
		<li class="item" data-question-id="${record.resId}" data-question-type-id="${record.questionTypeId}" >
			<div class="content">
 				<div class="name">
 					<leke:question questionId="${record.resId}" showCheckbox="false" 
						showSequence="false" showExplain="false" showProperty="false" 
						showPropertyEdit="false" showRightAnswer="false"/>
 				</div> 
				<div class="info">
					<div class="left">
						<%@ include file="/pages/common/queInfoLeft.jsp"%>
					</div>
					<div class="right">
						<label class="j-add-quecart"><i class="iconfont">&#xf0148; </i>加入习题篮</label>
						<label><i class="iconfont" title="使用次数">&#xf00c3; </i><c:out value="${record.usedCount }" default="0"/></label>
						<label class="j-praise"><i class="iconfont" title="点赞次数">&#xf013c; </i> 
						<span class="j-praise-count"><c:out value="${record.praiseCount }" default="0"/></span></label>
					</div>
				</div>
			</div>
			<div class="operation">
				<div class="m-btns c-btns">
					<div class="init-btn">
						<a href="${ctx}/auth/common/question/view.htm?questionId=${record.resId}&curScope=4" target="_blank">查看</a><b><i></i></b>
					</div>
					<menu>
						<li><a class="j-edit">编辑</a></li>
						<li><a class="j-unfavorite">取消收藏</a></li>
					</menu>
				</div>
			</div>
		</li>
	</c:forEach>
	</ul>
	<%@ include file="/pages/common/quePage.jsp"%>
</c:if>
<c:if test="${empty records}">
	<p class="m-tips">
		<i></i>
		<span>
		当前没有教学资源，去
		<a class="s-c-turquoise" href="${initParam.repositoryServerName}/auth/teacher/leke/home.htm" target="_blank">乐课库</a> 逛逛？
		</span>
	</p>
</c:if>