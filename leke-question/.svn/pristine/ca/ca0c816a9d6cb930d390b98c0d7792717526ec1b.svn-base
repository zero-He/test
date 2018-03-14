<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<div class="error-collection-content">
<c:if test="${not empty records}">
<c:forEach items="${records}" var="record" varStatus="recordStat">
<div class="error-collection-content-item" data-homework-id="${record.homeworkId }" data-id="${record.id}" data-question-id="${record.questionId}" data-question-type-id="${record.questionTypeId}">
	<!--试题详情头部-->
       <div class="error-collection-content-header">
       	<div class="erro-source">
       		来源：<span class="erro-source-address">
       				${record.sourceName}
				</span>
       		<span class="erro-source-time">${record.assignDateStr}</span>
           </div>
           <button class="erro-delete c-basket-btn-hov j-delete"></button>
       </div>
      	<!--内容区-->
      	<div class="error-collection-content-main">
      		<div class="error-question">
      			<leke:question questionId="${record.questionId}" showCheckbox="true" 
				showSequence="false" showExplain="true" showProperty="false" 
				showPropertyEdit="false" showRightAnswer="true"/>
      		</div>
       </div>
       <!--底部-->
       <div class="error-collection-content-footer">
       	<div class="error-collection-fl">
               <span class="lately-addtime">最近加入时间：<i class="lately-i">${record.modifiedOnStr}</i></span>
               <span class="lately-addsum">加入次数：<i class="lately-i">${record.errorTotal}</i></span>
               <span class="lately-addgrade">单题班级得分率：<i class="lately-i">${record.rate}%</i></span>
           </div>
           <div class="error-collection-fr">
            <c:if test = "${record.wrongStuTotal != null }">
               <button class="error-operate-btn c-basket-btn-hov look-students j-view-student">查看学生名单</button>
            </c:if>
               <button class="error-operate-btn error-operate-btn-show c-basket-btn-hov j-answer">显示答案</button>
               <button class="error-operate-btn c-basket-btn-hov j-favorite">收藏</button>
               <a href="${initParam.questionServerName}/auth/common/question/view.htm?questionId=${record.questionId}" target="_blank" 
                	class="error-operate-btn error-operate-btn-more c-basket-btn-hov">查看详情</a>
               <button class="u-btn u-btn-at u-btn-bg-turquoise j-add-quecart">加入习题篮</button>
               <button class="u-btn u-btn-at u-btn-bg-turquoise u-btn-bg-gray j-remove-quecart">移出习题篮</button>
           </div>
           <div class="e-students-list" style="display: none;">
               <ul class="j-student-ul">
                  <!-- <li class="item"><a href="##">马东马东马东马东马东</a></li> -->
               </ul>
            </div>
	</div>
</div>
</c:forEach>
</c:if>
</div>
<div class="quePage">
	<%@ include file="/pages/common/page.jsp"%>
</div>
<c:if test="${empty records}">
	<p class="m-tips"><i></i><span>暂无错题加入~</span></p>
</c:if>