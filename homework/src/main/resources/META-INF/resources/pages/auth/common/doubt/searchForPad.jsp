<%@ page pageEncoding="UTF-8"%>
<div class="c-searchbar">
    <div class="c-searchbar__inputcon">
        <form id="jForm">
        	<input type="search" class="c-searchbar__ipt" name="doubtTitle" placeholder="关键词">
			<input type="hidden" id="jResolved" name="resolved" value="${resolved}" /> 
			<input type="hidden" id="jCollect" name="<c:if test="${currentRoleId==100}">studentCollect</c:if><c:if test="${currentRoleId==101}">teacherCollect</c:if>" value="0" /> 
			<input type="hidden" id="jSource" name="source"> 
			<input type="hidden" id="jSubject" name="subjects">
		</form>
    </div>
    <div class="c-searchbar__filterbtn">筛选</div>
    <div class="c-searchbar__filter" style="display: none;">
        <div class="c-searchbar__fcon">
        	<div class="c-searchbar__ftitle">
				收藏
            </div>
            <div class="c-searchbar__fsel j-collect">
                <span class="c-searchbar__fcheck c-searchbar__fcheck--act" data-i="0">全部</span> 
				<span class="c-searchbar__fcheck" data-i="1">收藏</span> 
            </div>
            <div class="c-searchbar__ftitle">
				来源
            </div>
            <div class="c-searchbar__fsel j-source">
                <span class="c-searchbar__fcheck c-searchbar__fcheck--act" data-i="0">全部</span> 
				<span class="c-searchbar__fcheck" data-i="1">课外</span> 
				<span class="c-searchbar__fcheck" data-i="3">课堂</span> 
				<span class="c-searchbar__fcheck" data-i="2">题目</span> 
				<span class="c-searchbar__fcheck" data-i="4">点播</span>
            </div>
            <c:if test="${currentRoleId==100}">
            <div class="c-searchbar__ftitle">
				学科
            </div>
            <div class="c-searchbar__fsel j-subject">
                <c:forEach items="${subjectList }" var="subject" varStatus="status">
					<span class="c-searchbar__fcheck" data-i="${subject.subjectId }">${subject.subjectName }</span>
				</c:forEach>
            </div>
            </c:if>
        </div>
        <div class="c-searchbar__foperate">
            <a id="jCleanSubject" class="c-searchbar__fclear">清空学科</a>
            <a id="jSearch" class="c-searchbar__fconfirm">确定</a>
        </div>
    </div>
</div>