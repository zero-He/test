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
				<div class="c-header">
					<div class="c-header__title">
						<span class="c-header__name">【${doubt.sourceStr}】${doubt.doubtTitle}</span>
                        <div class="c-header__operation">
                            <a class="c-header__mark <c:if test="${doubt.studentCollect==1 }">c-header__mark--act</c:if>"></a>
                            <a class="c-header__remove"></a>
                        </div>
					</div>
					<div class="c-header__info">
                        	教材章节:
                        <span>${doubt.materialPathName }</span>
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
                    <div class="c-doubt__con" style="margin-bottom: 50px">
                    	
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
	                        <div id="jWrongDiv"><div>
                        </div>
                    </div>
                    <div class="c-doubt__para">
	                    <button id="butMyAgain" style="float: right;" class="u-btn u-btn-sm u-btn-bg-orange special">我要追问</button>
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
				<div id="divAgain" style="display: none;margin-left: 59px;margin-top: 20px;">
					<ul class="inpuit">
						<li>
							<div class="enter">
								<textarea id="iDoubtDiscript" name="doubtContent" style="width: 700px; height: 300px; display: none;"></textarea>
								<span class="word_surplus"><locale:message code="homework.doubt.form.spantext" /></span>
								<span class="onError_top" tx="<locale:message code="homework.doubt.form.description" />" id="sDoubtContentErr" style="color: red"></span>
							</div>
						</li>
						<li>
							<div style="margin-top: 20px">
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
							<div class="c-commit">
								<button class="u-btn u-btn-nm u-btn-bg-turquoise" id="butAgainDoubt" style="margin: 20px 0 0 80px;"><locale:message code="homework.common.confirm" /></button>
								<button class="u-btn u-btn-nm u-btn-bg-gray" id="aBackDoubtList" style="margin: 20px 0 0 20px;"><locale:message code="homework.common.cancel" /></button>
							</div>
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
