<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>题型管理- 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<style type="text/css">
.c-form .con label{
	display: inline-block;
	margin-left: 20px;
}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="m-form c-form">
			
			<ul>
				<c:forEach items="${subjects}" var="sub">
				<li class="form-item">
					<label for="" class="title">${sub.subjectName}</label>
					<form>
					<div class="con" data-subjectId="${sub.subjectId}">
						<input type="hidden" name="subjectId" value="${sub.subjectId}" />
						<c:forEach items="${types}" var="typ">
						<label for="s_${sub.subjectId}_${typ.questionTypeId}">
						<input type="checkbox"  name="questionTypeId" id="s_${sub.subjectId}_${typ.questionTypeId}" value="${typ.questionTypeId}"/> 
						${typ.questionTypeName}</label>
						</c:forEach>
					</div>
					<div class="submit"><input type="button" id="save" name="save" value="保存" class="u-btn u-btn-nm u-btn-bg-turquoise"></div>
                </form>
				</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
seajs.use('question/questionType/setSubjectQuestion')
</script>
</body>
</html>