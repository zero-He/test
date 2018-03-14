<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>教材大纲设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/paper/paper.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/task/task.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-sheet.css?t=${_t}">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn" data-bind="component: {
			name: 'wbpap-edit',
			params: {
				outline: outline
			}
		}">
		</div>
	</div>
	
<script>
document.domain = '${initParam.mainDomain}';
window.OLCtx = {
	outline: ${outline },
	/* curShareScope: ${curShareScope} */
}; 
seajs.use("question/syllabus/outline/edit");
</script>
</body>
</html>