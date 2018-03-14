<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>查看练习 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<leke:exercise data="${exerciseData }" />
	<script>
		var Csts = ${workModel};
		seajs.use('homework/work/exercise/doViewerExercise');
	</script>
</body>
</html>