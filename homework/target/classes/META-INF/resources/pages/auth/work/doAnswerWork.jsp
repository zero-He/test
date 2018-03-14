<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>开始答题 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>

	<input type="hidden" id="global-toolbar-note-params" value="${globalNoteParams}" />
	<leke:paper paper="${paper}" respond="${respond}" />

	<script type="text/javascript">
		var Csts = ${workModel};
		seajs.use('homework/work/doAnswerWork');
	</script>
</body>
</html>