<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.homeworkstat.name"/><c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/task/task.css?t=20171115">
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<p class="m-tips"><i></i><span>当前试卷还未编辑完成，请联系相关老师完成试卷编辑。</span></p>
	</div>
</div>
</body>
</html>