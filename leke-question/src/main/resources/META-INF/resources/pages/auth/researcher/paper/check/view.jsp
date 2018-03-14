<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>试卷审核</title>

<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/question/question-paper.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/question/question-sheet.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}">

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn" data-bind="component: {name: 'paper-check-list',params: {paper: paper}}">
        </div>
	</div>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
document.domain = '${initParam.mainDomain}';
window.PaperCtx = {
	initJson: ${initJson}
};
seajs.use('question/pages/researcher/paper/check/view');
seajs.use('homework/sheet.struct');
seajs.use('homework/sheet.render');
</script>
</body>
</html>