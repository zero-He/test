<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>提成管理 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="m-search-box">
				<label class="title">查询月份：</label>
				<span>${month}</span>
				<c:if test="${not empty sellerName}">
					<label class="title">客户经理：</label>
					<span>${sellerName}</span>
				</c:if>
				<label class="title">老师姓名：</label>
				<span>${customerName}</span>
				<label class="title">绑定时长：</label>
				<span>第${bindYear}年</span>
			</div>
			<div class="m-table m-table-center">
				<table>
					<thead>
						<tr>
							<th>充值时间</th>
							<th>充值金额</th>
							<th>提成比例</th>
							<th>提成金额</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="commission" items="${commissionList }">
							<tr>
								<td><fmt:formatDate value="${commission.tradeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatNumber value="${commission.tradeAmount}" pattern="#,##0.00" /></td>
								<td><fmt:formatNumber value="${commission.commRate}" pattern="###" />%</td>
								<td><fmt:formatNumber value="${commission.commAmount}" pattern="#,##0.00" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div>
					<div style="padding: 5px;">提成总额：<span><fmt:formatNumber value="${total}" pattern="#,##0.00" /></span> 元</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>