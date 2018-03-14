<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>习题审核 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/quedit.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div data-bind="component: {
					name: 'que-statistic-list',
					params: {
						userId: '${userId}'
					}
				}"></div>
	</div>
</div>

<leke:pref />
<script>
window.PaperCtx = {
		userId: '${userId}'
	};
document.domain = '${initParam.mainDomain}';
seajs.use('question/view/question/researcher/statistic/index');
</script>
</body>
</html>