<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>习题领取设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			最大领取习题量：
			<input type="text" id="taskCount" class="u-ipt u-ipt-nm" />
			<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="确定" id="bAdd">
		</div>
		
	</div>
</div>

<script type="text/javascript">
	seajs.use('question/questionTaskConfig/taskConfig');
</script>
</body>
</html>