<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html >
<html>
<head>
<title><locale:message code="homework.doubt.viewQuestion"/> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/task/task.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="examination" style="background: #fff;">
				<input type="hidden" id="ticket" name="ticket" value="${ticket}">
				<div id="dDoubtList" class="test question-wrp" style="padding: 0 20px">
					<div class="question-title">
						<div class="img"></div>
						<span class="question">【<c:if test="${ doubt.doubtType eq 1}">我要提问</c:if> 
							<c:if test="${ doubt.doubtType eq 2}">批改错误</c:if>】${doubt.doubtTitle}
						</span>
						<span class="time"><locale:message code="homework.doubt.askTime"/>：${doubt.createdOnString}</span>
						<span class="asker"><locale:message code="homework.doubt.asker"/>：${doubt.userName}</span>
					</div>
					<div class="m-form" style="padding-left: 0">
						<ul>
							<li class="form-item" style="margin-left: -40px;">
								<label class="title">教材章节:</label>
								<div class="con">
									<span id="jSectionSelect">${doubt.materialPathName }</span>
									<a id="jUpdateSection" style="color: #0ba299;padding-left: 10px;">修改</a>
								</div>
							</li>
						</ul>
					</div>
					<div class="timu">
						<div class="timu-style answer f-w-auto">
							<c:if test="${doubt.doubtContent ne ''}">
								<div class="f-clearfix">
									<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.describe"/>】</span>
									<div class="f-bfc f-fs14 f-lwb">${doubt.doubtContent}</div>
								</div>
							</c:if>
							<c:if test="${doubt.doubtAudio ne '' && doubt.doubtAudio ne null}">
								<div class="f-clearfix">
									<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.record"/>】</span>
									<div class="f-bfc f-fs14">
										<dfn class="j-dfn" data-type="audio" data-url="${doubt.doubtAudio}"></dfn>
									</div>
								</div>
							</c:if>
							<c:if test="${doubt.questionId ne 0}">
								<div class="f-clearfix">
									<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.content"/>】</span>
									<div class="f-bfc f-fs14">
										<c:choose>
										<c:when test="${doubt.doubtType == 2 and not empty doubt.homeworkDtlId}">
											<leke:questionResult questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="true"
												showProperty="true" showPropertyEdit="false" showRightAnswer="true" questionResult="${questionResult}"
												showOperate="true" showResult="true" showResultScore="true" />
										</c:when>
										<c:otherwise>
											<leke:question questionId="${doubt.questionId}" showCheckbox="false" showSource="false" showExplain="true"
												showProperty="true" showPropertyEdit="false" showRightAnswer="true" showInputArea="false" />
										</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:if>
						</div>

						<c:if test="${ not empty explains}">
							<c:forEach items="${explains}" var="explain">
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
								<div class="dialog f-cb">
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
								</div>
							</c:forEach>
						</c:if>

						<div>
							<label style="float: left; width: 80px; color: #333; text-align: right; line-height: 32px;"><locale:message code="homework.doubt.answerContent"/>：</label>
							<textarea id="tExplainContent" name="doubtContent" style="width: 700px; height: 200px;"></textarea>
							<p style="padding-left: 80px;">
								<span class="word_surplus">还可以输入1000个字.</span>
								<span class="onError_top" tx="<locale:message code="homework.doubt.answerDescribe"/>" id="sDoubtContentErr" style="color: red"></span>
							</p>
						</div>
						<div class="f-mt20 f-mb20">
							<label style="float: left; width: 80px; color: #333; text-align: right; line-height: 32px;"><locale:message code="homework.doubt.recordContent"/>：</label>
							<dfn class="j-dfn" data-type="recorder" data-elementid="audioUrl"></dfn>
						</div>
					</div>
				</div>
				<div class="btnSave center">
					<input type="hidden" id="audioUrl">
					<input type="hidden" name="doubtId" id="hDoubtId" value="${doubt.doubtId}">
					<input type="hidden" name="userName" id="hUserName" value="${teacherName}">
					<input type="hidden" name="resolved" id="resolved" value="true">
					<input type="hidden" name="doubtId" id="hDoubtId" value="${doubt.doubtId}">
					<input type="hidden" name="subjectId" id="subjectId" value="${doubt.subjectId}">
					<input type="hidden" name="doubtTitle" id="doubtTitle" value="${doubt.doubtTitle}">
					<input type="hidden" name="questionId" id="questionId" value="${doubt.questionId}">
					<input type="hidden" name="targetType" id="targetType" value="${doubt.targetType}">
					<input type="hidden" name="teacherId" id="teacherId" value="${doubt.teacherId}">
					<input type="hidden" name="createdBy" id="createdBy" value="${doubt.createdBy}">
					<input type="hidden" id="jSchoolStageId" value="${doubt.schoolStageId }">
					<input type="hidden" id="jMaterialNodeId" value="${doubt.materialNodeId }">
					<input type="hidden" id="jMaterialId" value="${doubt.materialId }">
					<input type="hidden" id="jPressId" value="${doubt.pressId }">
					<input type="button" name="button" value="<locale:message code="homework.common.submit"/>" id="bExpainPaper" class="u-btn u-btn-nm u-btn-bg-turquoise">
					<c:if test="${not empty homeworkDtlId }">
					<a href="${ctx }/auth/teacher/homework/correctWork.htm?homeworkDtlId=${homeworkDtlId}&isAgain=true" target="_blank"
						 class="u-btn u-btn-nm u-btn-bg-turquoise"><locale:message code="homework.common.recorrect"/></a>
					</c:if>
					<input type="button" name="button" value="<locale:message code="homework.common.back"/>" id="aBackTeacherDoubtList" class="u-btn u-btn-nm u-btn-bg-gray">
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName}" />
	<input type="hidden" id="jFileServerName" value="${initParam.fileServerName}" />
	<script>
		var ticket = '${ticket}';
		seajs.use("homework/doubt/teacherExplain");
	</script>
</body>
</html>
