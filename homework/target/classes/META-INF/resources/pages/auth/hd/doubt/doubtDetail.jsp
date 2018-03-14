<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
	<div class="c-questioner">
		<div class="c-questioner__imgcon">
			<img src="${studentPhoto}" alt="" onerror="this.src = 'https://static.leke.cn/images/index/people-photo.png'" class="c-questioner__img">
		</div>
		<div class="c-questioner__info">
			<div class="c-questioner__name">${doubt.userName}</div>
			<div class="c-questioner__time">${doubt.createdOnString}</div>
			<div class="c-questioner__spread">展开</div>
		</div>
		<div class="c-questioner__moreinfo">
            <p>${doubt.sourceStr}：<span>${doubt.doubtTitle}</span></p>
            <p>教材：<span>${doubt.materialPathName}</span></p>
            <p>老师：<span>${doubt.teacherName}</span></p>
            <p>科目：<span>${doubt.subjectName}</span></p>
        </div>
	</div>
	<div class="c-answer">
		<c:if test="${doubt.doubtAudio ne '' && doubt.doubtAudio ne null}">
			<div class="c-media">
				<audio class="audio" src="${doubt.doubtAudio}" controls="controls" loop="false" hidden="true"></audio>
				<button class="media-btn">
					<span class="waves"> <b class="w1"><i>1</i></b> <b class="w2"><i>2</i></b> <b class="w3"><i>3</i></b>
					</span>
				</button>
				<c:if test="${doubt.duration ne null && doubt.duration ne ''}">
					<span class="length">${doubt.duration}</span>
				</c:if>
			</div>
		</c:if>
		<div class="c-answer__text" style="margin-top: 10px;">${doubt.doubtContent}</div>
		<c:if test="${doubt.questionId ne null}">
			<div class="f-clearfix">
				<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.content"/>】</span>
				<div class="f-bfc f-fs14">
					<c:choose>
					<c:when test="${doubt.doubtType == 2 and not empty doubt.homeworkDtlId}">
						<leke:questionResult questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="${isTeacher}"
							showProperty="true" showPropertyEdit="false" showRightAnswer="${isTeacher}" questionResult="${questionResult}"
							showOperate="true" showResult="true" showResultScore="true" />
					</c:when>
					<c:otherwise>
						<leke:question questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="${isTeacher}"
							showProperty="true" showPropertyEdit="false" showRightAnswer="${isTeacher}" showInputArea="false" />
					</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:if>
	</div>
	<c:if test="${ not empty explains}">
		<c:forEach items="${explains}" var="explain">
			<div>
				<div class="c-infobar">
					<div class="c-infobar__value">
						<span class="c-infobar__img"> 
							<c:if test="${teacherId eq explain.createdBy}">
								<img src="${teacherPhoto}" class="photo" alt="" onerror="this.src = 'https://static.leke.cn/images/index/people-photo.png'">
							</c:if> <c:if test="${teacherId ne explain.createdBy}">
								<img src="${studentPhoto}" class="photo" alt="" onerror="this.src = 'https://static.leke.cn/images/index/people-photo.png'">
							</c:if>
						</span> <span class="c-infobar__name">${explain.userName}</span>
					</div>
					<span class="c-infobar__time">${explain.createOnString}</span>
				</div>
				<div class="c-answer">
					<c:if test="${explain.expainAudio ne '' && explain.expainAudio ne null}">
						<div class="c-media">
							<audio class="audio" src="${explain.expainAudio}" controls="controls" loop="false" hidden="true"></audio>
							<button class="media-btn">
								<span class="waves"> <b class="w1"><i>1</i></b> <b class="w2"><i>2</i></b> <b class="w3"><i>3</i></b>
								</span>
							</button>
							<c:if test="${explain.duration ne null&&explain.duration ne ''}">
								<span class="length">${explain.duration}</span>
							</c:if>
						</div>
					</c:if>
					<div class="c-answer__text" style="margin-top: 10px;">${explain.explainContent}</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
