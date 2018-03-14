<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title><locale:message code="homework.doubt.detail.title" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/task/task.css?t=20171115">

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="question-wrp">
				<div class="question-title">
					<div class="img"></div>
					<span class="question">【<c:if test="${ doubt.doubtType eq 1}">我要提问</c:if> 
						<c:if test="${ doubt.doubtType eq 2}">批改错误</c:if>】${doubt.doubtTitle}
					</span>
					<span class="time"><locale:message code="homework.doubt.askTime" />：${doubt.createdOnString}</span>
					<span class="asker"><locale:message code="homework.doubt.asker" />：${doubt.userName}</span>
				</div>
				<div class="m-form" style="padding-left: 0">
					<ul>
						<li class="form-item" style="margin-left: -40px;">
							<label class="title">教材章节:</label>
							<div class="con">
								<span>${doubt.materialPathName }</span>
							</div>
						</li>
					</ul>
				</div>
				<div class="answer">
					<c:if test="${doubt.doubtContent ne ' '}">
						<div class="f-clearfix">
							<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.describe" />】</span>
							<div class="f-bfc f-fs14 f-lwb">${doubt.doubtContent}</div>
						</div>
					</c:if>
					<c:if test="${doubt.doubtAudio != null && doubt.doubtAudio != ''}">
						<div class="f-clearfix">
							<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.record" />】</span>
							<div class="f-bfc f-fs14">
								<dfn class="j-dfn" data-type="audio" data-url="${doubt.doubtAudio}"></dfn>
							</div>
						</div>
					</c:if>
					<c:if test="${doubt.questionId ne null}">
						<div class="f-clearfix">
							<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.content" />】</span>
							<div class="f-bfc f-fs14">
							<c:choose>
							<c:when test="${doubt.doubtType == 2 and not empty doubt.homeworkDtlId}">
								<leke:questionResult questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="false"
									showProperty="true" showPropertyEdit="false" showRightAnswer="false" questionResult="${questionResult}"
									showOperate="true" showResult="true" showResultScore="true" />
							</c:when>
							<c:otherwise>
								<leke:question questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="false"
									showProperty="true" showPropertyEdit="false" showRightAnswer="false" showInputArea="false" />
							</c:otherwise>
							</c:choose>
							</div>
						</div>
					</c:if>
				</div>
				<button id="butMyAgain" class="u-btn u-btn-sm u-btn-bg-orange special"><locale:message code="homework.doubt.form.toask" /></button>
				<div class="dialog f-cb">
					<c:forEach items="${explains}" var="explain" varStatus="e">
						<c:set var="nameSuffix"><locale:message code="homework.doubt.answer" /></c:set>
						<c:set var="roleClass" value="teacher"></c:set>
						<c:set var="contentNum" value="1"></c:set>
						<c:set var="colorVal" value="#ff5500"></c:set>
						<c:if test="${doubt.createdBy eq explain.createdBy}">
							<c:set var="nameSuffix"><locale:message code="homework.doubt.askForExplanation" /></c:set>
							<c:set var="roleClass" value="student"></c:set>
							<c:set var="contentNum" value="2"></c:set>
							<c:set var="colorVal" value="#0066cc"></c:set>
						</c:if>
						<div class="${roleClass } f-bfc">
							<div class="left">
								<span style=" color:${colorVal};"> ${explain.userName}${nameSuffix }：</span>
								<br>
								<span style="color: #666;">${explain.createOnString} </span>
							</div>
							<div class="right">
								<div class="arrow${contentNum }">
									<img src="${assets}/images/task/arrow${contentNum }.png">
								</div>
								<div class="content${contentNum } f-lwb">
									<p>${explain.explainContent}</p>
									<c:if test="${explain.expainAudio ne '' && explain.expainAudio ne null}">
										<dd>
											<dfn class="j-dfn" data-type="audio" data-url="${explain.expainAudio}"></dfn>
										</dd>
									</c:if>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<div id="divAgain" style="display: none;">
					<ul class="inpuit">
						<li style="margin-bottom: 20px;">
							<label><locale:message code="homework.doubt.form.questionscontent" />：</label>
							<div class="enter">
								<textarea id="iDoubtDiscript" name="doubtContent" style="width: 700px; height: 300px; display: none;"></textarea>
								<span class="word_surplus"><locale:message code="homework.doubt.form.spantext" /></span>
								<span class="onError_top" tx="<locale:message code="homework.doubt.form.description" />" id="sDoubtContentErr" style="color: red"></span>
							</div>
						</li>
						<li>
							<div style="line-height: 32px;">
								<label><locale:message code="homework.doubt.recordContent" />：</label>
								<dfn class="j-dfn" data-type="recorder" data-elementid="audioUrl"></dfn>
							</div>
						</li>
						<li>
							<input type="hidden" id="doubtId" name="doubtId" value="${doubt.doubtId}" />
							<input type="hidden" id="audioUrl" name="audioUrl" />
							<input type="hidden" id="userName" name="userName" value="${doubt.userName}" />
							<input type="hidden" name="resolved" id="resolved" value="false">
							<input type="hidden" name="subjectId" id="subjectId" value="${doubt.subjectId}">
							<input type="hidden" name="doubtTitle" id="doubtTitle" value="${doubt.doubtTitle}">
							<input type="hidden" name="questionId" id="questionId" value="${doubt.questionId}">
							<input type="hidden" name="targetType" id="targetType" value="${doubt.targetType}">
							<input type="hidden" name="teacherId" id="teacherId" value="${doubt.teacherId}">
							<input type="hidden" name="createdBy" id="createdBy" value="${doubt.createdBy}">
							<button class="u-btn u-btn-nm u-btn-bg-turquoise" id="butAgainDoubt" style="margin: 20px 0 0 80px;"><locale:message code="homework.common.confirm" /></button>
							<button class="u-btn u-btn-nm u-btn-bg-gray" id="aBackDoubtList" style="margin: 20px 0 0 20px;"><locale:message code="homework.common.cancel" /></button>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName}" />
	<input type="hidden" id="jFileServerName" value="${initParam.fileServerName}" />
	<script>
		var ticket = '${ticket}';
		seajs.use("homework/doubt/backDoubtList");
		seajs.use('homework/common/sheet.viewer.js?t=20171115');
	</script>

</body>
</html>
