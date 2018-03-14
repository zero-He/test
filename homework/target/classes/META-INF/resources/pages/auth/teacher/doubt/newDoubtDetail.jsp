<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title><locale:message code="homework.doubt.detail.title" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/doubt/doubt.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="z-doubt">
				<input id="jCreatedBy" type="hidden" value="${doubt.createdBy}">
				<div class="c-header">
					<div class="c-header__title">
						<span class="c-header__name">【${doubt.sourceStr}】${doubt.doubtTitle}</span>
                        <div class="c-header__operation">
                            <a class="c-header__mark <c:if test="${doubt.teacherCollect==1 }">c-header__mark--act</c:if>"></a>
                            <a class="c-header__remove"></a>
                        </div>
					</div>
					<div class="c-header__info">
                        	教材章节:
                        <span id="jSectionSelect">${doubt.materialPathName }</span>
						<a id="jUpdateSection" style="color: #0ba299;padding-left: 10px;">修改</a>
                    </div>
				</div>
				
				<div class="c-doubt">
                    <div class="c-doubt__header">
                        <span class="c-doubt__avatar">
                            <img src="${initParam.fileServerName}/${doubt.photo}" alt="" onerror="this.src = 'https://static.leke.cn/images/index/people-photo.png'">
                        </span>
                        <div class="c-doubt__info">
                            <div class="c-doubt__name">${doubt.userName}</div>
                            <div class="c-doubt__time">${doubt.createdOnString}</div>
                        </div>
                    </div>
                    <div class="c-doubt__con">
                    	
                    	<c:if test="${doubt.doubtAudio != null && doubt.doubtAudio != ''}">
							<div class="c-doubt__audio">【录音】
	                            <dfn class="j-dfn" data-type="audio" data-url="${doubt.doubtAudio}"></dfn>
	                        </div>
						</c:if>
                        
                        <div class="c-doubt__para">
                        	${doubt.doubtContent}
                        	<c:if test="${doubt.questionId ne null}">
								<div class="f-clearfix" style="margin-left:-8px">
									<span class="title f-fl f-w60 f-fs14">【<locale:message code="homework.doubt.content" />】</span>
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
							<div id="jWrongDiv"><div>
                        </div>
                    </div>
                </div>
				
				<div class="c-doubt__reply">
					<ul style="margin-top: 20px;">
					<c:forEach items="${explains}" var="explain" varStatus="e">
						<li class="c-doubt <c:if test="${doubt.createdBy eq explain.createdBy}">c-doubt--self</c:if>">
							<div class="c-doubt__header">
                                <span class="c-doubt__avatar">
                                    <c:if test="${teacherId eq explain.createdBy}">
					            	<img src="${teacherPhoto}" class="photo" onerror="this.src = 'https://static.leke.cn/images/index/people-photo.png'" alt="">
					        	</c:if>
					        	<c:if test="${teacherId ne explain.createdBy}">
					            	<img src="${studentPhoto}" class="photo" onerror="this.src = 'https://static.leke.cn/images/index/people-photo.png'" alt="">
					        	</c:if>
                                </span>
                                <div class="c-doubt__info">
                                    <div class="c-doubt__name">${explain.userName}</div>
                                    <div class="c-doubt__time">${explain.createOnString}</div>
                                </div>
                            </div>
                            <div class="c-doubt__con">
                                <c:if test="${explain.expainAudio ne '' && explain.expainAudio ne null}">
                            		<div class="c-doubt__audio">
	                                 	【录音】
										<dd>
											<dfn class="j-dfn" data-type="audio" data-url="${explain.expainAudio}"></dfn>
										</dd>
                            		</div>
								</c:if>
                                <div class="c-doubt__para">${explain.explainContent}</div>
                            </div>
						</li>
					</c:forEach>
					</ul>
				</div>
				<div style="margin-left: 59px;margin-top: 20px">
					<div>
						<textarea id="tExplainContent" name="doubtContent" style="width: 700px; height: 200px;"></textarea>
						<p>
							<span class="word_surplus">还可以输入1000个字.</span>
							<span class="onError_top" tx="<locale:message code="homework.doubt.answerDescribe"/>" id="sDoubtContentErr" style="color: red"></span>
						</p>
					</div>
					<div style="margin-top: 20px">
						<label><locale:message code="homework.doubt.recordContent"/>：</label>
						<dfn class="j-dfn" data-type="recorder" data-elementid="audioUrl"></dfn>
					</div>
					<div class="btnSave center c-commit">
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
	</div>
	<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName}" />
	<input type="hidden" id="jFileServerName" value="${initParam.fileServerName}" />
	<script>
		var ticket = '${ticket}';
		seajs.use("homework/doubt/teacherExplain");
	</script>

</body>
</html>
