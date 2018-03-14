<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>查看 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>

	<leke:paper paper="${paper}" respond="${respond}" />
	<c:if test="${currentRoleId == 100}">
	<input type="hidden" id="global-toolbar-note-params" value="${globalNoteParams}" />
	</c:if>
	
	<script type="text/javascript">
		var Csts = ${workModel};
		seajs.use('homework/work/doViewerWork');
	</script>
</body>
</html>