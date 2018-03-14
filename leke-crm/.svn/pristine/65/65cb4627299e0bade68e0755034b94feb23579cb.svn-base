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
			<form id="jPageForm" action="" method="post" class="m-search-box">
				<label class="title">查询月份：</label>
				<span>${monthView}</span>
				<label class="title">客户经理：</label>
				<span>${sellerName}</span>
				<input type="hidden" name="month" value="${month}" />
				<input type="hidden" name="sellerId" value="${sellerId}" />
				<label class="title">教师姓名：</label>
				<input type="text" name="customerName" class="u-ipt u-ipt-nm">
				<input type="hidden" id="jPageOrder" name="order" value="" />
				<input type="hidden" id="jPageSort" name="sort" value="" />
				<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="jPageSearch" value="查询">
			</form>
			<div class="m-table m-table-center">
				<table>
					<thead id="jPageHead">
						<tr>
							<th>教师姓名</th>
							<th>绑定时长</th>
							<th>充值金额</th>
							<th class="m-sort-leke" data-column="commAmount">提成金额</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jPageBody"></tbody>
				</table>
				<div style="position: relative; min-height: 50px;">
					<div id="jPageFoot" class="page"></div>
					<div style="position: absolute; bottom: 0; padding: 5px;">提成总额：<span id="jPageTotal"></span> 元</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script id="jPageTpl" type="text/handlebars">
{{#each dataList as | comm index|}}
<tr>
	<td>{{customerName}}</td>
	<td>第{{bindYear}}年</td>
	<td>{{money tradeAmount}}</td>
	<td>{{money commAmount}}</td>
	<td class="operation"><a href="recordlist.htm?customerId={{customerId}}&sellerId=${sellerId}&month=${month}">详情</a></td>
</tr>
{{/each}}
</script>
<leke:pref />
<script type="text/javascript">
	seajs.use('scs/commission/commdtllist');
</script>
</html>