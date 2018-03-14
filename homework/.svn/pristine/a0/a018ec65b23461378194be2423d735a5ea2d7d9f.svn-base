<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title><locale:message code="homework.doubt.doubt.title" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/doubt/doubt.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
</head>
<body>
<div style="overflow: auto; height: 450px;">
	<form name="doubtForm" method="post" class="m-form" style="padding:0;">
		<ul>
			<li class="form-item" style="padding-top:0;">
				<label class="title">
					<span class="require">*</span>
					<locale:message code="homework.doubt.form.changtea" />：
				</label>
				<div class="con">
				<c:if test="${teacherId == null }">
					<select id="iDoubtTeacherId" name="teacherId" class="u-select u-select-lg">
						<c:forEach var="teacher" items="${teacherList }">
							<option value="${teacher.id}">${teacher.userName}</option>
						</c:forEach>
					</select>
				</c:if>
				<c:if test="${teacherId != null }">
					<input type="hidden" id="iDoubtTeacherId" name="teacherId" value="${teacherId }">
					${teacherName }
				</c:if>
				</div>
			</li>
			<li class="form-item">
				<label class="title">
					<span class="require">*</span>
					<locale:message code="homework.doubt.describe" />：
				</label>
				<div class="con">
					<textarea id="iDoubtDiscript" name="doubtContent" style="width: 500px; height: 300px;"></textarea>
					<p>
						<span class="word_surplus"><locale:message code="homework.doubt.form.spantext" /></span>
						<span class="onError_top" tx="<locale:message code="homework.doubt.form.description" />" id="sDoubtContentErr" style="color: red"></span>
					</p>
				</div>
			</li>
			<li class="form-item">
				<label class="title">
					<locale:message code="homework.doubt.recordContent" />：
				</label>
				<div class="con">
					<dfn class="j-dfn" data-type="recorder" data-elementid="audioUrl"></dfn>
				</div>
			</li>
		</ul>
		<div style="text-align: center;">
			<input type="hidden" id="audioUrl">
			<input type="hidden" id="jHomeworkDtlId" value="${homeworkDtlId}">
			<c:if test ="${not empty questionId }">
			<input type="hidden" id="hQuestionId" name="questionId" value="${questionId}">
			</c:if>
			<input type="hidden" id="hSubjectId" value="${subjectId}">
			<input type="hidden" id="hSourceId" value="${sourceId}">
			<input type="hidden" id="hSourceType" value="${sourceType}">
			<input type="hidden" id="hSourceName" value="${sourceName}">
		</div>
	</form>
</div>
<div class="f-tac f-mt5">
	<input type="button" name="button" value="<locale:message code="homework.common.submit" />" id="bPaperEdit" class="u-btn u-btn-nm u-btn-bg-turquoise">
</div>
<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName}" />
<input type="hidden" id="jFileServerName" value="${initParam.fileServerName}" />

<script>
	var ticket = '${ticket}';
	seajs.use("homework/doubt/doubtForPaper");
</script>
</body>
</html>