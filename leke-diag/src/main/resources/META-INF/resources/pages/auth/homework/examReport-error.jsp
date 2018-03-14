<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>创建成绩单失败</title>
<%@ include file="/pages/common/meta.jsp"%>

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<p class="m-tips">
				<i></i><span>${msg}</span>
			</p>
			<div class="f-tac s-bg-w f-pb45">
				<button class="u-btn u-btn-nm u-btn-bg-turquoise" type="button"
					onclick="window.history.go(-1);">返回</button>
			</div>
		</div>
	</div>
</body>
</html>