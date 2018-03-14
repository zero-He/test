<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet"
	href="${assets}/styles/common/global.css">
<title>乐课网学科</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="ts-tab-wrap">
				<div class="m-table m-table-center">
					<table>
						<c:forEach items="${subjectList}" var="sub">
							<tr>
								<td>${sub.schoolStageName}</td>
								<td>${sub.subjectName}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
            	<button type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" onclick="window.close();">关闭</button>
			</div>
		</div>
</body>
</html>