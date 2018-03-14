<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>添加子题型 - 乐课网</title>
</style>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<style type="text/css">
.c-form .con label{
	display: inline-block;
	margin-left: 20px;
}
</style>
</head>
<body>
<div class="m-form c-form">
	<ul>
		<li class="form-item">
			<label for="" class="title" data-bind="text: queType.questionTypeName"></label>
				<div class="con" data-subjectId="1" data-bind="foreach: parseQueTypes">
					<label data-bind="visible: vis">
						<input type="checkbox"  name="questionTypeId" data-bind="
						value: questionTypeId,
						attr:{id: 'que_type_' + questionTypeId},
						checked: check
						" /> 
						<em data-bind="text: questionTypeName"></em>
					</label>
				</div>
		</li>
</ul>
<div class="submit">
	<input type="button" value="确定" data-bind="click: save" class="u-btn u-btn-nm u-btn-bg-turquoise">
	<input type="button" value="取消" data-bind="click: close" class="u-btn u-btn-nm u-btn-bg-gray">
</div>
</div>
<script type="text/javascript">
	window.QuestionCtx = {
			queType: ${queType},
			queTypes: ${queTypes},
			queTypeSubs: ${queTypeSubs}
	}
	seajs.use('question/questionType/editQuestionTypeSub');
</script>
</body>
</html>