<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>订正 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>

	<leke:paper paper="${paper}" respond="${respond}" />
	
	<input type="hidden" id="global-toolbar-note-params" value="${globalNoteParams}" />

	<script type="text/javascript">
		var Csts = ${workModel};
		seajs.use('homework/work/doBugfixWork');
	</script>
</body>
</html>