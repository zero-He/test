<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学生名单<c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<%@ include file="/pages/common/meta.jsp"%>
<style type="text/css">
td span {
	display: inline-block;
	min-width: 80px;
	padding: 5px;
}
</style>
</head>
<body>
	<div class="m-table">
		<table>
			<thead>
				<tr>
					<th width="15%">答案</th>
					<th width="85%">学生姓名</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${nameList}">
					<tr>
						<td>${item.name }</td>
						<td>
							<c:forEach var="user" items="${item.datas}">
								<span>${user.userName }</span>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>