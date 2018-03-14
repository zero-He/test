<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html >
<html>
<head>
<title><locale:message code="homework.doubt.doubt.title" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/doubt/doubt.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form name="doubtForm" method="post">
			<div class="base_view" style="padding-left: 0">
				<c:if test="${teacherId == null}">
					<div class="questionCon">
						<em><locale:message code="homework.homework.subject" />:</em>
						<select id="jSubject" class="u-select u-select-nm"></select>
					</div>
				</c:if>
				<div class="questionCon">
					<em>提问老师:</em>
					<div class="con">
					<c:if test="${teacherId !=null }">
						<input type="hidden" id="iDoubtTeacherId" name="teacherId" value="${ teacherId}" />
						${teacherName }
					</c:if>
					<c:if test="${teacherId == null}">
						<select id="iDoubtTeacherId" name="teacherId" class="u-select u-select-nm">
						</select>
					</c:if>
					</div>
				</div>
				<div class="questionCon">
					<em>教材章节:</em>
					<div class="con">
						<input type="text" class="u-ipt u-ipt-nm" id="jSectionSelect" name="section" style="width: 273px;"  readonly="readonly" title="">
					</div>
				</div>
				<div class="f-pb20">
					<em style="float: left; width: 105px; padding-right: 20px; text-align: right;"><locale:message code="homework.doubt.form.description" />:</em>

					<div style="overflow: hidden;">
						<textarea id="iDoubtDiscript" name="doubtContent" style="width: 700px; height: 300px;"></textarea>
						<p>
							<span class="word_surplus"><locale:message code="homework.doubt.form.spantext" /></span>
							<span class="onError_top" tx="<locale:message code="homework.doubt.form.description" />" id="sDoubtContentErr" style="color: red"></span>
						</p>
					</div>
				</div>
				<c:if test="${comeFromExam == true}">
					<div class="f-pb20">
						<em><locale:message code="homework.doubt.form.examcontent" />:</em>
						<div>
							<leke:question questionId="${questionId}" sequence="1" showRightAnswer="false" showExplain="false" />
						</div>
					</div>
				</c:if>
				<div class="questionCon">
					<em><locale:message code="homework.doubt.recordContent" />:</em>
					<div id="recorder"></div>
					<dfn class="j-dfn" data-type="recorder" data-elementid="audioUrl"></dfn>
				</div>
				<div class="btnSave question">
					<input type="hidden" id="audioUrl">
					<input type="hidden" id="hSubjectId" value="">
					<input type="hidden" id="hSubjectName" value="">
					<input type="hidden" id="jSchoolStageId" value="${clazz.schoolStageId }">
					<input type="hidden" id="jMaterialNodeId">
					<input type="hidden" id="jMaterialId">
					<input type="hidden" id="jPressId">
					<input type="button" name="button" value="<locale:message code="homework.common.submit" />" id="bPaperEdit" class="u-btn u-btn-nm u-btn-bg-turquoise">
				</div>
			</div>
		</form>
	</div>
</div>
<input type="hidden" id="j_teacherId" value="${teacherId }" />
<input type="hidden" id="jHomeworkServerName" value="${initParam.homeworkServerName}" />
<input type="hidden" id="jFileServerName" value="${initParam.fileServerName}" />

<textarea id="teacherOption_tpl" style="display: none">
	{{#resultList}}
		<option value="{{id}}">{{userName}}</option>
	{{/resultList}}  
</textarea>

<leke:pref/>
<c:if test="${cross == 1}">
	<script>
		document.domain = '${initParam.mainDomain}';
	</script>
</c:if>
<script>
	var ticket = '${ticket}';
	seajs.use("homework/doubt/doubt");
</script>

</body>
</html>