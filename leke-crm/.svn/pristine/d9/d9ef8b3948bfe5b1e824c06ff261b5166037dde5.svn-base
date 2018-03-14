<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>客户详情 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="m-search-box">
				<label class="title">客户名称：</label>
				<span>${customer.customerName }</span>
				<label class="title">客户乐号：</label>
				<span>${customer.lekeLoginName }</span>
				<label class="title">绑定时间：</label>
				<span>
					<fmt:formatDate value="${customer.bindTime}" pattern="yyyy-MM-dd HH:mm" />
				</span>
				<label class="title">首次消费时间：</label>
				<span>
					<fmt:formatDate value="${customer.consumeTime}" pattern="yyyy-MM-dd HH:mm" />
				</span>
			</div>
			<div>
				<ul id="jModuleList" class="m-tab">
					<li data-module="PointModule">课点信息</li>
					<!-- 
					 -->
					<li data-module="AttendModule">考勤信息</li>
				</ul>
				<div id="jModuleContainer"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	Leke.Page = {
		lekeId : '${customer.lekeId}',
		customerId : '${customer.customerId}',
		customerType : '${customer.customerType}'
	};
	seajs.use('scs/customer/info');
</script>
</html>